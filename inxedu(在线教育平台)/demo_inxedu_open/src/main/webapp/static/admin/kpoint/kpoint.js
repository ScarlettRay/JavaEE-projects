var ztreeObject;
var setting = {
	edit:{
		enable: true,
		renameTitle:'修改视频节点',
		removeTitle:'删除视频节点'
	},
	view:{
		showLine: true,
		showIcon: true,
		selectedMulti: false
	},
	data: {
		simpleData: {
			enable: true,
			idKey:'kpointId',
			pIdKey:'parentId',
			rootPid:''
		},
		key:{
			name:'name',
			title:'name'
		}
	},
	callback: {
		//修改视频节点
		beforeEditName:initUpdateKpoint,
		//删除节点
		beforeRemove:deleteKpoint,
		//修改视频父节点
		beforeDrop:updateKpointParentId
	}
};
$(function(){
	$('.ui-dialog-titlebar-close,.closeBut').click(function(){
		closeData();
	});
    courseKpointVideoTypechange();
    uploadPicLoad('fileupload','videourl','fileQueue');
});

/**
 * 选择视频类型change方法
 */
function courseKpointVideoTypechange(){
    $("#courseKpointVideoType").change(function(){
        //如果为本地上传视频则显示上传插件
		if($(this).val()=='uploadVideo'){
            $(".uploadVideo").show();
        }else{
            $(".uploadVideo").hide();
        }
    });
}
/**
 * 初始化视频节点树
 * @param ztree 视频节点数据
 */
function showKpointZtree(ztree){
	ztree = eval('('+ztree+')');
	ztreeObject = $.fn.zTree.init($("#kpointList"), setting, ztree);
	ztreeObject.expandAll(true);
}

/**
 * 创建视频节点
 * @param courseId 课程ID
 */
function addaKpoint(courseId){
	var parentId =0;
	ztreeObject = $.fn.zTree.getZTreeObj("kpointList");
	var seleNodes = ztreeObject.getSelectedNodes();
	if(seleNodes!=null && seleNodes.length>0){
		parentId =seleNodes[0].kpointId;
	}
	if(seleNodes!=null && seleNodes.length>0){
		var seleLevel=seleNodes[0].level;
		if(seleLevel!=0){
			alert("创建视频节点只支持二级,请重新选择一级节点再添加!");
			return;
		}
	}
	if(getKpointType(parentId)==false){//判断父级节点类型
		return;
	}
	var kpointType=0;
	if(parentId!=0){
		kpointType=1;
	}
	
	$.ajax({
		url:baselocation+'/admin/kpoint/addkpoint',
		type:'post',
		dataType:'json',
		data:{
			'courseKpoint.name':'新创建视频',
			'courseKpoint.parentId':parentId,
			'courseKpoint.courseId':courseId,
			'courseKpoint.kpointType':kpointType
		},
		success:function(result){
			if(result.success==false){
				alert(result.message);
			}else{
				var nodes =[result.entity];
				if(parentId>0){
					ztreeObject.addNodes(seleNodes[0],nodes);
				}else{
					ztreeObject.addNodes(null,nodes);
				}
			}
		},
		error:function(error){
			alert('系统繁忙，请稍后再操作！');
		}
	});
}

/**
 * 修改视频
 * @param treeNode
 */
function initUpdateKpoint(treeId, treeNode){
	closeData();
	var treeNodeLeve=treeNode.level;
	var childrenNodes = treeNode.children;
	
	$.ajax({
		url:baselocation+'/admin/kpoint/getkpoint/'+treeNode.kpointId,
		type:'post',
		dataType:'json',
		success:function(result){
			var obj = result.entity;
			$("input[name='courseKpoint.kpointId']").val(obj.kpointId);
			$("input[name='courseKpoint.name']").val(obj.name);
			if(treeNodeLeve==0){//一级节点 
				//$("input[name='courseKpoint.videoUrl']").parent().parent().hide();//不输入视频地址
			}else{
				$("input[name='courseKpoint.videoUrl']").parent().parent().show();
				$("#courseKpointVideoType").parent().parent().show();
				$("select[name='courseKpoint.kpointType']").val(1);//节点类型默认 为视频
				$("select[name='courseKpoint.kpointType']").parent().parent().hide();//节点类型 隐藏
			}
			if (childrenNodes&&childrenNodes.length>0) {//如果 当前节点有子节点 
				$("select[name='courseKpoint.kpointType']").val(0);//节点类型 为目录
				$("input[name='courseKpoint.videoUrl']").parent().parent().hide();//不输入视频地址
				$("#courseKpointVideoType").parent().parent().hide();
				$("select[name='courseKpoint.kpointType']").parent().parent().hide();//节点类型 隐藏
		    }else{
		    	$("#courseKpointKpointType").parent().parent().show();//显示
		    }
			if(treeNodeLeve!=0){
				$("#courseKpointKpointType").parent().parent().hide();//隐藏
			}
			
			$("#courseKpointKpointType").val(obj.kpointType);
			$("#courseKpointKpointType").change();
			$("#courseKpointVideoType").val(obj.videoType);
			$("#courseKpointVideoType").change();
			$("input[name='courseKpoint.videoUrl']").val(obj.videoUrl);
			$("#fileType").val(obj.fileType);
			$("#fileType").change();
			//清空文本内容
			EditorObject.html("");
			//设置HTML内容
			EditorObject.html(obj.content);
			$("input[name='courseKpoint.sort']").val(obj.sort);
			$("input[name='courseKpoint.playCount']").val(obj.playCount);
			$("input[name='courseKpoint.teacherId']").val(obj.teacherId);
			$("input[name='courseKpoint.playTime']").val(obj.playTime);

			$("#teacher").text(obj.teacherName==null?'':obj.teacherName);

			$("input[name='courseKpoint.isFree']").attr('checked',false);
			if(obj.free==1){
				$($("input[name='courseKpoint.free']")[0]).attr('checked',true);
			}else if(obj.free==2){
				$($("input[name='courseKpoint.free']")[1]).attr('checked',true);
			}
			$("#updateWin").show();
		},
		error:function(error){
			alert("系统繁忙，请稍后再操作！");
		}
	});
	return false;
}

/**
 * 执行修改
 */
function updateKpoint(){
	var params = ''
	$("#updateForm input").each(function(){
		params+=$(this).serialize()+"&";
    });
	var name = $("input[name='courseKpoint.name']").val();
	if(name==null || $.trim(name)==''){
		alert('视频节点名不能为空');
		return false;
	}
	var sort = $("input[name='courseKpoint.sort']").val();
	var reg=/^\d+$/;
	if(!reg.test(sort)){
		alert('排序必须是正整数！');
		return false;
	}

	if($("#courseKpointKpointType").val()==1) {//章节专属验证
		var fileType = $("#fileType").val();
		if (fileType == 'TXT') {
			if (isEmpty($("#content").val())) {
				alert("文本内容必填！");
				return;
			}
			var content = encodeURIComponent($("#content").val());
			params += "courseKpoint.content=" + content + "&";
		} else if (fileType == "VIDEO" && isEmpty($("#videourl").val())) {
			alert("请填写视频地址");
			return;
		}
	}


	var playCount =$('input[name="courseKpoint.playCount"]').val();
	if(!reg.test(playCount)){
		alert('播放数必须是正整数！');
		return false;
	}

	var kpointType=$("select").val();
	params+="courseKpoint.kpointType="+kpointType+"&";
	var videoType=$("#courseKpointVideoType").val();
	params+="courseKpoint.videoType="+videoType+"&";
	var fileType=$("#fileType").val();
	params+="courseKpoint.fileType="+fileType+"&";
	$.ajax({
		url:baselocation+'/admin/kpoint/updateKpoint',
		type:'post',
		dataType:'json',
		data:params,
		success:function(result){
			if(result.success==false){
				alert(result.message);
			}else{
				var obj = result.entity;
				ztreeObject = $.fn.zTree.getZTreeObj("kpointList");
				var node = ztreeObject.getNodeByParam('kpointId',obj.kpointId,null);
				node.name=obj.name;
				node.videoUrl=obj.videoUrl;
				node.sort = obj.sort;
				node.playCount = obj.playCount;
				node.free = obj.free;
				node.teacherId = obj.teacherId;
				ztreeObject.updateNode(node);
				closeData();
			}
		},
		error:function(error){
			alert('系统繁忙，请稍后再操作！');
		}
	});
}

var ids='';
/**
 * 递归节点的所有的子级节点的ID
 * @param node
 */
function getChildren(node){
	ids+=node.kpointId+',';
	var nodes = node.children;
	if(nodes!=null && nodes.length>0){
		for(var i=0;i<nodes.length;i++){
			getChildren(nodes[i]);
		}
	}
}

/**
 * 删除视频树节点
 * @param treeId 树ID
 * @param treeNode 视频节点对象
 */
function deleteKpoint(treeId, treeNode){
	var is = false;
	if(!confirm("确认要删除["+treeNode.name+"]及该节点的子级节点？")){
		return is;
	}
	getChildren(treeNode);
	$.ajax({
		url:baselocation+'/admin/kpoint/deletekpoint/'+ids,
		type:'post',
		dataType:'json',
		async:false,
		success:function(result){
			if(result.success==true){
				is=true;
			}else{
				alert(result.message);
			}
		},
		error:function(error){
			alert('系统繁忙，请稍后再操作');
		}
	})
	return is;
}

/**
 * 拖拽修改视频节点的父节点
 * @param treeId 目标节点 targetNode 所在 zTree 的 treeId
 * @param treeNodes 被拖拽的节点 JSON 数据集合
 * @param targetNode treeNodes 被拖拽放开的目标节点 JSON 数据对象。
 * @param moveType 指定移动到目标节点的相对位置
 */
function updateKpointParentId(treeId, treeNodes, targetNode, moveType){
	var parentId = targetNode.kpointId;
	var kpointId = treeNodes[0].kpointId;
	var is = true;
	$.ajax({
		url:baselocation+'/admin/kpoint/updateparentid/'+parentId+'/'+kpointId,
		type:'post',
		async:false,
		dataType:'json',
		success:function(result){
			if(result.success==false){
				alert(result.message);
			}
			is = result.success;
		}
	});
	return is;
}

/**
 * 选择添加老师回调
 * @param arr 老师数组
 */
function addTeahcerList(arr){
	var teacher = arr[0];
	$("input[name='courseKpoint.teacherId']").val(teacher.id);
	$("#teacher").text(teacher.name);
}

/**
 * 取消
 */
function closeData(){
	$("#updateWin").hide();
	$("#updateForm input:text").val('');
	$("input[name='courseKpoint.kpointId']").val(0);
	$("input[name='courseKpoint.teacherId']").val(0);
	$("input[name='courseKpoint.isFree']").attr('checked',false);
	$("#teacher").text('');
}

/**
 * 视频节点类型 下拉改变
 */
function kpointTypeChange(){
	$(".tr_all").hide();
	var kpointType=$("#courseKpointKpointType").val();
	if(kpointType==0){
		$("input[name='courseKpoint.videoUrl']").val("");
	}else{
		$(".tr_all").show();
		$("#fileType").change();
	}
}

/**
 *判断节点类型 
 */
function getKpointType(parentId){
	var isTrue=true;
	$.ajax({
		url:baselocation+'/admin/kpoint/getkpoint/'+parentId,
		type:'post',
		dataType:'json',
		async:false,
		success:function(result){
			var obj = result.entity;
			if(obj!=null && obj!="" && obj.kpointType==1){
				isTrue=false;
				alert("创建视频节点只能在目录节点类型下添加!");
			}
		},
		error:function(error){
			isTrue=false;
			alert("系统繁忙，请稍后再操作！");
		}
	});
	return isTrue;
}

//-------------------
//文件选择成功后，Flash 会调用
//调用者：flash
//功能：选中上传文件，获取文件名函数
//时间：2010-12-22
//说明：用户可以加入相应逻辑
//-------------------
function on_spark_selected_file(filename) {
	$("#upload_title").val(filename);
	$("#upload_tag").val(filename);
	$("#upload_desp").val(filename);
	
	$.ajax({
		url:baselocation+'/admin/ajax/kpoint/ccVideoTHQSData',
		data:{"filename":filename},
		type:'post',
		dataType:'json',
		async:false,
		success:function(result){
			if(result.success==true){
				$("#uploadswf")[0].start_upload(result.message);
			}
		},
		error:function(error){
			isTrue=false;
			alert("系统繁忙，请稍后再操作！");
		}
	});
}

//	-------------------
//调用者：flash
//功能：验证上传是否正常进行函数
//时间：2010-12-22
//说明：用户可以加入相应逻辑
//-------------------
function on_spark_upload_validated(status, videoid) {
	if (status == "OK") {
		//alert("上传正常,videoid:" + videoid);
		$("input[name='courseKpoint.videoUrl']").val(videoid);//视频id
	} else if (status == "NETWORK_ERROR") {
		alert("网络错误");
	} else {
		alert("api错误码：" + status);
	}
}

//-------------------
//调用者：flash
//功能：通知上传进度函数
//时间：2010-12-22
//说明：用户可以加入相应逻辑
//-------------------
function on_spark_upload_progress(progress) {
	var uploadProgress = document.getElementById("up");
	if (progress == -1) {
		uploadProgress.innerHTML = "上传出错：" + progress;
		//alert( "上传出错：" + progress);
	} else if (progress == 100) {
		uploadProgress.innerHTML = "进度：100% 上传完成";
		//alert("进度：100% 上传完成");
	} else {
		uploadProgress.innerHTML = "进度：" + progress + "%";
		//alert( "进度：" + progress + "%");
	}
}


/**
 * 选择课件格式 控制页面效果的切换
 */
function chooseFileType(){
	////先隐藏所有
	$(".tr_all").hide();
	var kpointType=$("#courseKpointKpointType").val();//节点类型
	//课件类型
	var fileType= $.trim($("#fileType").val());
	//章节类型才会继续处理
	if(kpointType==0){//章节类型 处理显示
		return;
	}

	$("#fileType").parent().parent().show();
	if(fileType=='VIDEO'){//选择视频格式
		$(".videoType").show();
		var videoType=$("#videotype").val();
	}else if(fileType=='TXT'){//选择文本格式
		$(".txtContent").show();
		$("input[name='courseKpoint.free']:eq(1)").prop("checked","checked");
	}
}
/**
 * 取消树的选中状态
 */
function ztreeCancelSelectedNode(){
	ztreeObject = $.fn.zTree.getZTreeObj("kpointList");
	ztreeObject.cancelSelectedNode();
}
