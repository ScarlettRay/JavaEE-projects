var ztreeObject;
var setting = {
	check: {
		enable: true,
		chkboxType : { "Y" : "ps", "N" : "p" }
	},
	edit:{
		enable: true,
		showRemoveBtn:false,
		renameTitle:'修改权限'
	},
	view:{
		showLine: true,
		showIcon: true,
		selectedMulti: false,
		fontCss: function(treeId, treeNode){
			//如果是功能权限则标红
			return treeNode.functionType==2? {color:"red"} : {};
		}
	},
	data: {
		simpleData: {
			enable: true,
			idKey:'functionId',
			pIdKey:'parentId',
			rootPid:''
		},
		key:{
			name:'functionName',
			title:'functionName'
		}
	},
	callback: {
		//修改父节点
		beforeDrop:updateParentId,
		//修改权限
		beforeEditName:functionRename
	}
};

/**
 * 初始化权限树
 * @param ztree 权限数据
 */
function showFunctionZtree(ztree){
	ztree = eval('('+ztree+')');
	ztreeObject = $.fn.zTree.init($("#ztreedemo"), setting, ztree);
}

/**
 * 添加新权限
 */
function addFunction(){
	var parentId =0;
	ztreeObject = $.fn.zTree.getZTreeObj("ztreedemo");
	var seleNodes = ztreeObject.getSelectedNodes();
	if(seleNodes!=null && seleNodes.length>0){
		parentId =seleNodes[0].functionId;
	}
	$.ajax({
		url:baselocation+'/admin/sysfunctioin/addfunction',
		type:'post',
		dataType:'json',
		data:{'sysFunction.parentId':parentId},
		success:function(result){
			if(result.success){
				var obj = result.entity;
				var nodes=[eval('('+obj+')')];
				if(parentId>0){
					ztreeObject.addNodes(seleNodes[0],nodes);
				}else{
					ztreeObject.addNodes(null,nodes);
				}
			}else{
				alert(result.message);
			}
		}
	});
}

/**
 * 拖拽修改父节点
 * @param event
 * @param treeId 树结构ID
 * @param treeNodes 被拖拽的节点数组
 * @param targetNode 标上节点
 * @param moveType 
 */
function updateParentId(treeId, treeNodes, targetNode, moveType){
	var parentId = targetNode.functionId;
	var functionId = treeNodes[0].functionId;
	var is = true;
	$.ajax({
		url:baselocation+'/admin/sysfunctioin/updateparentid/'+parentId+'/'+functionId,
		type:'post',
		async:false,
		dataType:'json',
		success:function(result){
			if(result.success!=true){
				alert(result.message);
			}
			is = result.success;
		}
	});
	return is;
}

/***
 * 初始化修改权限信息
 * @param treeId 权限树ID
 * @param treeNode 当前要修改的权限节点
 */
function functionRename(treeId, treeNode){
	closeData();
	$("#functionName").focus();
	$("#updateWin").show();
	$("#functionId").val(treeNode.functionId);
	$("#functionName").val(treeNode.functionName);
	$("#functionUrl").val(treeNode.functionUrl);
	$("#functionType").val(treeNode.functionType);
	$("#sort").val(treeNode.sort);
	$("#imageUrl").val(treeNode.imageUrl);
	var imageurl = "/static/admin/assets/logo.png";
	if(treeNode.imageUrl!=null&&treeNode.imageUrl!=""){
		imageurl = imagesPath+treeNode.imageUrl;
	}
	$("#showImage").attr('src',imageurl);
	return false;
	
}

/**
 * 执行修改
 */
function updateFunction(){
	var params = ''
	$("#updateForm input,#updateForm select").each(function(){
		params+=$(this).serialize()+"&";
    });
	var sort = $("#sort").val();
	var reg=/^\d+$/;
	if(!reg.test(sort)){
		alert('排序数只能是正整数！');
		return false;
	}
	$.ajax({
		url:baselocation+'/admin/sysfunctioin/updatefunction',
		type:'post',
		dataType:'json',
		data:params,
		success:function(result){
			if(result.success){
				var obj = result.entity;
				ztreeObject = $.fn.zTree.getZTreeObj("ztreedemo");
				var node = ztreeObject.getNodeByParam('functionId',obj.functionId,null);
				node.functionName=obj.functionName;
				node.functionType=obj.functionType;
				node.functionUrl=obj.functionUrl;
				node.sort=obj.sort;
				ztreeObject.updateNode(node);
				closeData();
			}
			alert(result.message);
		},
		error:function(error){
			alert("系统繁忙，请后再操作！");
		}
	});
}
/**
 * 删除选中权限节点
 */
function delFunctions(){
	ztreeObject = $.fn.zTree.getZTreeObj("ztreedemo");
	var nodes = ztreeObject.getCheckedNodes(true);
	if(nodes!=null && nodes.length>0){
		var ids='';
		for(var i=0;i<nodes.length;i++){
			ids+=nodes[i].functionId+',';
		}
		if(!confirm('确认要删除选中权限节点？')){
			return false;
		}
		$.ajax({
			url:baselocation+'/admin/sysfunctioin/deletefunction/'+ids,
			type:'post',
			dataType:'json',
			success:function(result){
				if(result.success){
					document.location=baselocation+'/admin/sysfunctioin/showfunctionztree';
				}else{
					alsert(result.message);
				}
			}
		});
	}else{
		alert("请选择要删除的权限节点！");
	}
}
/**
 * 取消所选
 */
function checkNodeFalse(){
	ztreeObject = $.fn.zTree.getZTreeObj("ztreedemo");
	ztreeObject.checkAllNodes(false);
}

/**
 * 清空修改窗口数据
 */
function closeData(){
	$("#updateWin").hide();
	$("#updateForm input:text").val('');
	$("#functionId").val(0);
}