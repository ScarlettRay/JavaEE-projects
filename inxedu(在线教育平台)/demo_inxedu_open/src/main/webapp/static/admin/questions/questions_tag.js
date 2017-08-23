/* 问答标签start */
var questionsTagList=null;

var setting = {
	view : {
		showLine : true,
		showIcon : true,
		selectedMulti : false
	},
	edit:{
		enable: true,
		removeTitle:'删除问答标签',
		renameTitle:'修改问答标签',
		beforeRename:updateQuestionsTag,
		drag: {
            prev: false,//允许向上拖动 拖拽
            next: false,//不允许向下拖动
            inner: false//允许当前层次内进行拖动
        }
	},
	data : {
		simpleData : {
			enable : true,
			idKey : 'questionsTagId',
			pIdKey : 'parentId',
			rootPid : ''
		},
		key : {
			name : 'questionsTagName',
			title : 'questionsTagName'
		}
	},
	callback : {
		//修改父节点
		beforeDrop:updateParentId,
		//修改问答标签名
		beforeRename:updateQuestionsTag,
		//删除问答标签
		beforeRemove:deleteQuestionsTag
	}
};
$(function(){
	questionsTagList = eval('('+questionsTagList+')');
	var treeObj = $.fn.zTree.init($("#ztreedemo"), setting, questionsTagList);
	treeObj.expandAll(true);
});

/**
 * 创建问答标签
 */
function addQuestionsTag(){
	var treeObj = $.fn.zTree.getZTreeObj("ztreedemo");
	var nodes = treeObj.getSelectedNodes();
	var parentId=0;
	if(nodes!=null && nodes.length>0){
		parentId = nodes[0].questionsTagId;
	}
	parentId=0;//只添加一级
	$.ajax({
		url:baselocation+'/admin/questions/createQuestionsTag',
		type:'post',
		dataType:'json',
		data:{'questionsTag.parentId':parentId,'questionsTag.questionsTagName':'新建问答标签'},
		success:function(result){
			if(result.success==true){
				var node=[result.entity];
				if(parentId>0){
					treeObj.addNodes(nodes[0],node);
				}else{
					treeObj.addNodes(null,node);
				}
			}else{
				alert(result.message);
			}
		},
		error:function(error){
			alert('系统繁忙，请稍后再操作！');
		}
	});
}

/**
 * 修改问答标签父ID
 */
function updateParentId(treeId, treeNodes, targetNode, moveType){
	var parentId = targetNode.questionsTagId;
	var questionsTagId = treeNodes[0].questionsTagId;
	var is = true;
	$.ajax({
		url:baselocation+'/admin/questions/updateparentid/'+parentId+'/'+questionsTagId,
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

/**
 * 修改问答标签 
 * @param treeId
 * @param treeNode
 * @param newName
 * @param isCancel
 */
function updateQuestionsTag(treeId, treeNode, newName, isCancel){
	var treeObj = $.fn.zTree.getZTreeObj("ztreedemo");
	if(newName==null || $.trim(newName)==''){
		return false;
	}
	$.ajax({
		url:baselocation+'/admin/questions/updatequestionsTagName',
		type:'post',
		async:false,
		dataType:'json',
		data:{'questionsTag.questionsTagName':newName,'questionsTag.questionsTagId':treeNode.questionsTagId},
		success:function(result){
			if(result.success==false){
				alert(result.message);
				document.location=baselocation+'/admin/questions/toQuestionsTagList';
			}
		},
		error:function(error){
			alert("系统繁忙，请稍后再操作！");
		}
	});
	return true;
}

/**
 * 删除问答标签
 */
function deleteQuestionsTag(treeId, treeNode){
	var is=false;
	if(confirm('确认要删除['+treeNode.questionsTagName+']问答标签？')){
		$.ajax({
			url:baselocation+'/admin/questions/deleteQuestionsTag/'+treeNode.questionsTagId,
			type:'post',
			async:false,
			dataType:'json',
			success:function(result){
				if(result.success==false){
					is = false;
					alert(result.message);
				}else{
					is= true;
					document.location=baselocation+'/admin/questions/toQuestionsTagList';
				}
			},
			error:function(error){
				alert("系统繁忙，请稍后再操作！");
			}
		});
	}
	return is;
}
/* 问答标签end */


