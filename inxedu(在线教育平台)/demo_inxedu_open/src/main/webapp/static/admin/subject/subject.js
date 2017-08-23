var subjectList = null;

var setting = {
	view : {
		showLine : true,
		showIcon : true,
		selectedMulti : false,
		addDiyDom : addDiyDom
	},
	edit : {
		enable : true,
		removeTitle : '删除专业',
		renameTitle : '修改专业',
		beforeRename : updateSubject
	},
	data : {
		simpleData : {
			enable : true,
			idKey : 'subjectId',
			pIdKey : 'parentId',
			rootPid : ''
		},
		key : {
			name : 'subjectName',
			title : 'subjectName'
		}
	},
	callback : {
		// 修改父节点
		beforeDrop : updateParentId,
		// 修改专业名
		beforeRename : updateSubject,
		// 删除专业
		beforeRemove : deleteSubject
	}
};

var IDMark_A = "_a";
// 自定义插件
function addDiyDom(treeId, treeNode) {
	if (treeNode.parentNode && treeNode.parentNode.id != 2)
		return;
	var aObj = $("#" + treeNode.tId + IDMark_A);
	var editStr = '排序:<input type="text" class="selDemo" onblur="updateSort(' + treeNode.subjectId + ')" id="diyBtn_' + treeNode.subjectId + '"  value="' + treeNode.sort + '" style="width:40px"></input>';
	aObj.after(editStr);
}
//更新排序
function updateSort(id) {
	var sort = 0;
	sort = $("#diyBtn_"+id).val();
	var pattern = /^([0-9.]+)$/;
	if (!pattern.test(sort)) {
		alert("请输入数字");
		return;
	}
	$.ajax({
		url : baselocation + '/admin/subj/updatesort',
		type : 'post',
		data : {
			'subject.subjectId' : id,
			'subject.sort' : sort
		},
		async : false,
		dataType : 'json',
		success : function(result) {
			location.reload();
		}
	});
}

$(function() {
	subjectList = eval('(' + subjectList + ')');
	var treeObj = $.fn.zTree.init($("#ztreedemo"), setting, subjectList);
	treeObj.expandAll(true);
});

/**
 * 创建专业
 */
function addSubject() {
	var treeObj = $.fn.zTree.getZTreeObj("ztreedemo");
	var nodes = treeObj.getSelectedNodes();
	var parentId = 0;
	var subjectParentId = null;
	if (nodes != null && nodes.length > 0) {
		parentId = nodes[0].subjectId;
		subjectParentId = nodes[0].parentId;
	}
	// 专业最多只支持两级
	if (subjectParentId != null) {
		alert("专业最多只支持两级");
		return;
	}
	$.ajax({
		url : baselocation + '/admin/subj/createSubject',
		type : 'post',
		dataType : 'json',
		data : {
			'subject.parentId' : parentId,
			'subject.subjectName' : '新建专业'
		},
		success : function(result) {
			if (result.success == true) {
				var node = [ result.entity ];
				if (parentId > 0) {
					treeObj.addNodes(nodes[0], node);
				} else {
					treeObj.addNodes(null, node);
				}
			} else {
				alert(result.message);
			}
		},
		error : function(error) {
			alert('系统繁忙，请稍后再操作！');
		}
	});
}

/**
 * 修改专业父ID
 */
function updateParentId(treeId, treeNodes, targetNode, moveType) {
	var parentId = targetNode.subjectId;
	var subjectId = treeNodes[0].subjectId;
	var is = true;
	$.ajax({
		url : baselocation + '/admin/subj/updateparentid/' + parentId + '/' + subjectId,
		type : 'post',
		async : false,
		dataType : 'json',
		success : function(result) {
			if (result.success != true) {
				alert(result.message);
			}
			is = result.success;
		}
	});
	return is;
}

/**
 * 修改专业
 * 
 * @param treeId
 * @param treeNode
 * @param newName
 * @param isCancel
 */
function updateSubject(treeId, treeNode, newName, isCancel) {
	var treeObj = $.fn.zTree.getZTreeObj("ztreedemo");
	if (newName == null || $.trim(newName) == '') {
		return false;
	}
	$.ajax({
		url : baselocation + '/admin/subj/updatesubjectName',
		type : 'post',
		async : false,
		dataType : 'json',
		data : {
			'subject.subjectName' : newName,
			'subject.subjectId' : treeNode.subjectId
		},
		success : function(result) {
			if (result.success == false) {
				alert(result.message);
				document.location = baselocation + '/admin/subj/toSubjectList';
			}
		},
		error : function(error) {
			alert("系统繁忙，请稍后再操作！");
		}
	});
	return true;
}

/**
 * 删除专业
 */
function deleteSubject(treeId, treeNode) {
	var is = false;
	if (confirm('确认要删除[' + treeNode.subjectName + ']专业？')) {
		$.ajax({
			url : baselocation + '/admin/subj/deleteSubject/' + treeNode.subjectId,
			type : 'post',
			async : false,
			dataType : 'json',
			success : function(result) {
				if (result.success == false) {
					is = false;
					alert(result.message);
				} else {
					is = true;
					document.location = baselocation + '/admin/subj/toSubjectList';
				}
			},
			error : function(error) {
				alert("系统繁忙，请稍后再操作！");
			}
		});
	}
	return is;
}