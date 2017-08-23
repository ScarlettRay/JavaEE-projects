<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>课程节点</title>
<link type="text/css" rel="stylesheet" href="${ctx}/static/common/ztree/css/zTreeStyle.css" />
<script type="text/javascript" src="${ctx}/static/common/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/static/common/ztree/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/static/common/ztree/jquery.ztree.exedit-3.5.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/kindeditor/themes/default/default.css" />
	<script type="text/javascript" src="${ctx}/kindeditor/kindeditor-all.js"></script>
<script type="text/javascript" src="${ctx}/static/admin/kpoint/kpoint.js"></script>
<script type="text/javascript" src="${ctx}/static/admin/teacher/select_teacher_list.js"></script>

<script type="text/javascript" src="${ctx}/static/common/uploadify/ccswfobject.js"></script>

	<script type="text/javascript" src="${ctximg}/static/common/uploadify/swfobject.js"></script>
	<script type="text/javascript" src="${ctximg}/static/common/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
<style type="text/css">
#swfDiv embed {
	position: absolute;
	z-index: 1;
}
#swfDiv{*position:absolute; z-index:2;}
</style>
<script type="text/javascript">
		var ztree='${kpointList}';
		$(function(){
			showKpointZtree(ztree);
			//文本编辑框
			initKindEditor_addblog('content', 580, 350,'courseContxt','true');
		});

        //上传控件加载
        function uploadPicLoad(fileupload,showId,fileQueue){
            //$("#fileuploadUploader").remove();
            $("#"+fileupload).uploadify({
                'uploader' : '/static/common/uploadify/uploadify.swf', //上传控件的主体文件，flash控件  默认值='uploadify.swf'
                'script'  :'<%=uploadServerUrl%>/video/uploadvideo',
                'scriptData':{"fileType":"mp4","param":"video"},
                'queueID' : fileQueue, //文件队列ID
                'fileDataName' : 'uploadfile', //您的文件在上传服务器脚本阵列的名称
                'auto' : true, //选定文件后是否自动上传
                'multi' :false, //是否允许同时上传多文件
                'hideButton' : false,//上传按钮的隐藏
                'buttonText' : 'Browse',//默认按钮的名字
                'buttonImg' : '/static/common/uploadify/liulan.png',//使用图片按钮，设定图片的路径即可
                'width' : 105,
                'simUploadLimit' : 3,//多文件上传时，同时上传文件数目限制
                'sizeLimit' : 5120000000,//控制上传文件的大小
                'queueSizeLimit' : 3,//限制在一次队列中的次数（可选定几个文件）
                'fileDesc' : '支持格式:mp4.',//出现在上传对话框中的文件类型描述
                'fileExt' : '*.MP4;*.mp4;',//支持的格式，启用本项时需同时声明fileDesc
                'folder' : '/upload',//您想将文件保存到的路径
                'cancelImg' : '/static/common/uploadify/cancel.png',
                onSelect : function(event, queueID,fileObj) {
                    fileuploadIndex = 1;
                    $("#"+fileQueue).html("");
                    if (fileObj.size > 5120000000) {
                        alert('文件太大最大限制5120000kb');
                        return false;
                    }
                },
                onComplete : function(event,queueID, fileObj, response,data) {
                    var obj = eval('(' + response + ')');
                    $("#"+showId).val(obj.url);
                    $("#"+showId).show();
                },
                onError : function(event, queueID, fileObj,errorObj) {
                    $("#"+fileQueue).html("<br/><font color='red'>"+ fileObj.name + "上传失败</font>");
                }
            });
        }
</script>
</head>
<body>
<div class="mt20">
	<table width="100%" cellspacing="0" cellpadding="0" border="0" class="commonTab01">
		<thead>
		<tr>
			<th colspan="2" align="left">
				<span>${courseSellType}节点管理</span>
				<font color="red">*视频节点只支持二级</font>
			</th>
		</tr>
		</thead>
		<tbody>
		<tr>
			<td width="20%">
				<fieldset style="height: 662px;">
					<legend>
						<span>${courseSellType}节点管理</span>
						&gt;
						<span>节点列表</span>
					</legend>
					<div class="mt20">
						<div class="commonWrap">
							<div id="kpointList" class="ztree"></div>
							<a title="创建视频节点" onclick="addaKpoint(${courseId});" class="button tooltip" href="javascript:void(0)">
								<span></span>
								创建视频节点
							</a>
							<a title="取消选中" onclick="ztreeCancelSelectedNode();" class="button tooltip" href="javascript:void(0)">
								<span></span>
								取消选中
							</a>
						</div>
					</div>
				</fieldset>

			</td>
			<td width="80%"  >
				<fieldset id="updateWin" style="display: none; height: 662px;">
					<legend>
						&gt;
						<span>编辑节点</span>
					</legend>
					<div class="mt20">
						<div class="commonWrap">
							<form id="updateForm">
								<input type="hidden" name="courseKpoint.kpointId" />
								<input type="hidden" id="courseId" />
								<input type="hidden" name="courseKpoint.atlas" value="" id="atlas" />
								<input type="hidden" name="courseKpoint.atlasThumbnail" value="" id="atlasThumbnail" />
								<table style="line-height: 35px;" width="100%" cellspacing="0" cellpadding="0" border="0" class="commonTab01">
									<tr>
										<td>节点名称:</td>
										<td style="text-align: left;">
											<input name="courseKpoint.name" type="text" />
										</td>
									</tr>
									<tr>
										<td>节点类型:</td>
										<td style="text-align: left;">
											<select id="courseKpointKpointType" name="courseKpoint.kpointType" onchange="kpointTypeChange()">
												<option value="0">目录</option>
												<option value="1">章节</option>
											</select>
										</td>
									</tr>
									<tr style="display:none" class="tr_all">
										<td>课件类型:</td>
										<td style="text-align: left;">
											<select id="fileType" name="courseKpoint.fileType" onchange="chooseFileType()">
												<option value="VIDEO">视频</option>
												<option value="TXT">文本</option>
											</select>
										</td>
									</tr>
									<tr style="display:none" class="tr_all videoType ">
										<td>视频类型:</td>
										<td style="text-align: left;">
											<select id="courseKpointVideoType" name="courseKpoint.videoType" >
												<%--<option value="">--请选择--</option>--%>
												<option value="INXEDUVIDEO">因酷云</option>
												<%--<option value="baofeng">暴风</option>--%>
												<option value="IFRAME">其他</option>
												<option value="CC">CC视频</option>
												<option value="uploadVideo">上传本地视频</option>
											</select>
										</td>
									</tr>
									<tr class=" tr_fileType_control uploadVideo" style="display: none;">
										<td>上传进度:</td>
										<td style="text-align: left;">
											<input type="file" id="fileupload" class="vam"/>
											<font color="red vam ml10">请上传mp4文件（<a target="_blank" href="http://www.ckplayer.com/manual/12/66.htm">边下边播文档</a>）</font>
											<div id="fileQueue" class="mt10">
											</div>
										</td>
									</tr>
									<tr style="display:none" class="tr_all videoType">
										<td id="videoUrlTitle">视频地址:</td>
										<td style="text-align: left;">
											<input type="text" name="courseKpoint.videoUrl" id="videourl" value="" style="width: 360px;"/>
									</tr>
									<tr class="tr_all txtContent" style="display: none;">
										<td>文本内容:</td>
										<td><textarea id="content" name="courseKpoint.content" rows="" cols=""></textarea></td>
									</tr>

									<tr>
										<td>排序:</td>
										<td>
											<input type="text" value="0" name="courseKpoint.sort" />
										</td>
									</tr>
									<tr class="tr_all videoType">
										<td>播放次数:</td>
										<td>
											<input type="text" value="0" name="courseKpoint.playCount" disabled="disabled" readonly=""readonly/>
										</td>
									</tr>
									<tr class="tr_all videoType" id="timeLongTr">
										<td>播放时间:</td>
										<td>
											<input type="text" value="00:00" name="courseKpoint.playTime" />
										</td>
									</tr>
									<tr class="tr_all videoType">
										<td>是否免费:</td>
										<td style="text-align: left;" id="isfree">
											<input type="radio" name="courseKpoint.free" value="1" />
											是
											<input type="radio" name="courseKpoint.free" value="2" />
											否
											<font color="red vam ml10">文档、文本格式、图片集、音频等格式暂不支持试听</font>
										</td>
									</tr>
									<tr class="tr_all videoType" id="teacherTr">
										<td>视频讲师:</td>
										<td style="text-align: left;">
											<input type="hidden" name="courseKpoint.teacherId" value="0" />
											<p id="teacher" style="margin: 0 0 0em;"></p>
											<a href="javascript:void(0)" onclick="selectTeacher('radio')">选择老师</a>
										</td>
									</tr>

									<tr>
										<td colspan="2">
											<button class="ui-state-default ui-corner-all" style="float: left;" onclick="updateKpoint()" type="button">确定</button>
											<button class="ui-state-default ui-corner-all closeBut" style="float: left;" type="button">取消</button>
										</td>
									</tr>
								</table>
							</form>
						</div>
					</div>
				</fieldset>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="left">

			</td>
		</tr>
		</tbody>
	</table>
</div>
	<!-- 修改视频节点信息窗口，结束 -->
</body>
</html>