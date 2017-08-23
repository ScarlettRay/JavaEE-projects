var subjectList;
$(function(){
	//实例化编辑器 UE编辑器
	initUEEditor("content",'580','350');

	//initKindEditor_addblog('content', 580, 350,'courseContxt','true');
	initSimpleImageUpload('imageFile','course',callback);
	$("#endTime").datetimepicker({
		regional:"zh-CN",
        changeMonth: true,
        dateFormat:"yy-mm-dd",
        timeFormat: "HH:mm:ss"
    });
	//有效期类型change事件
	losetypeShow(); 
	$("#losetype").change();
});

/**
 * 图片上传回调
 * @param imgUrl 图片路径
 */
function callback(imgUrl){
	$("input[name='course.logo']").val(imgUrl);
	$("#showImage").attr('src',imagesPath+imgUrl);
}

/**
 * 选择讲师后的回调 
 * @param tcArr 讲师数组
 */
function addTeahcerList(tcArr){
	var pList = $("#teacherList p");
	var ids = $("input[name='teacherIdArr']").val();
	var tcHtml='';
	var tcIds='';
	for(var i=0;i<tcArr.length;i++){
		if(pList!=null && pList.length>0){
			for(var j=0;j<pList.length;j++){
				var id = $(pList[j]).attr('id');
				if(id==tcArr[i].id){
					$(pList[j]).remove();
					ids= ids.replace(id+',','');
				}
			}
		}
		tcIds+=tcArr[i].id+',';
		tcHtml+='<p id="'+tcArr[i].id+'" style="margin: 0 0 0em;">'+tcArr[i].name+'&nbsp;&nbsp;<a href="javascript:void(0)" onclick="deleteTc('+tcArr[i].id+')">删除</a></p>';
	}
	$("input[name='teacherIdArr']").val(tcIds+ids);
	$("#teacherList").append(tcHtml);
}

/**
 * 删除老师
 * @param tcId 老师ID
 */
function deleteTc(tcId){
	$("#teacherList #"+tcId).remove();
	var ids = $("input[name='teacherIdArr']").val();
	ids= ids.replace(tcId+',','');
	$("input[name='teacherIdArr']").val(ids);
}


/**
 * 保存课程
 */
function saveCourse(){

	var subjectId = $("input[name='course.subjectId']").val();
	if(subjectId<=0){
		alert("请选择课程专业");
		return false;
	}

	var regs=/(^\d+[\.][0-9]{1,2}$)|(^\d+$)/;
	var sourcePrice = $("input[name='course.sourcePrice']").val();
	var currentPrice = $("input[name='course.currentPrice']").val();
	if(!regs.test(sourcePrice) || !regs.test(currentPrice)){
		alert("价格必须是整数或最多带两小数点！");
		return false;
	}

	if ($("#losetype").val() == 0) {//有效期类型 到期时间
		var beginTime = new Date();
		var endTime = $("#endTime").val();
		if(endTime==null || $.trim(endTime)==''){
			alert("请选择有效期结束时间");
			return false;
		}
		/*beginTime = beginTime.replace(/-/g,'/');*/
		endTime = endTime.replace(/-/g,'/');
		if(beginTime > new Date(endTime)){
			alert("有效期结束时间要大于当前时间");
			return false;
		}
	}
	else if ($("#losetype").val() == 1) {//按天数
		var loseTime=$("#loseTime").val();
		if($.trim(loseTime)==""){
			alert("请输入课程有效期天数");
			return false;
		}
	}

	$("#saveCourseForm").submit();
}

/**
 * 有效期类型change事件
 */
function losetypeShow() {
	$("#losetype").change(function() {
		$(".loseTimeShow").hide();
		$(".endTimeShow").hide();
		if ($(this).val() == 1) {
			$(".loseTimeShow").show();
			$("#endTime").val('');
		}
		if ($(this).val() == 0) {
			$(".endTimeShow").show();
			$("#loseTime").val('');
		}
	});
}