/* 问答start */
//时间控件
$(function(){
	$("#beginCreateTime,#endCreateTime").datetimepicker({
		regional:"zh-CN",
        changeMonth: true,
        dateFormat:"yy-mm-dd",
        timeFormat: "HH:mm:ss"
    });
});

/* 删除问答 */
function delQuestions(questionsId){
	if(confirm("是否删除此问答?")){
		$.ajax({
			url:"/admin/questions/del/"+questionsId,
			data:{
			},
			dataType:"json",
			type:"post",
			async:true,
			success:function(result){
				if(result.success==true){
					window.location.reload();
				}else{
					dialog('提示信息',result.message,1);
				}
			}
		})
	}
}
/* 问答end */


