$(function(){
	$("#selectAll").click(function(){
		$("input[name='teacherId']").attr('checked',$(this).attr('checked'));
		
	});
	$("input[name='teacherId']").click(function(){
		var leng = $("input[name='teacherId']").length;
		var len = $("input[name='teacherId']:checked").length;
		if(leng!=null && leng==len){
			$("#selectAll").attr('checked',true);
		}else{
			$("#selectAll").attr('checked',false);
		}
	});
});

/**
 * 选择老师 
 * @param type 页面显示的单选还是多选 checkbox多选（默认），radio单选
 */
function selectTeacher(type){
	if(type==null || $.trim(type)==''){
		type='checkbox';
	}
	window.open(baselocation+'/admin/teacher/selectlist/'+type ,'newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=150,left=300,width=923,height=600');
}

/**
 * 确认选择
 */
function confirmSelect(){
	var teahcer = $("input[name='teacherId']:checked");
	if(teahcer==null || teahcer.length==0){
		alert("请选择讲师");
		return false;
	}
	var teahcerArr = new Array();
	for(var i=0;i<teahcer.length;i++){
		var tcId = $(teahcer[i]).val();
		var tcName = $(teahcer[i]).attr('title');
		var tc = {'id':tcId,'name':tcName};
		teahcerArr.push(tc);
	}
	window.opener.addTeahcerList(teahcerArr);
	closeWin();
}

/**
 * 关闭窗口
 */
function closeWin(){
	window.close();
}