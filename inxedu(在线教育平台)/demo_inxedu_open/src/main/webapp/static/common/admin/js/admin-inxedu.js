/**
 * 获取弹出框
 * @param title 标题
 * @param context 内容
 * @param index 下标
 * @param callback 回调函数
 * @param height 内容区域高度
 */
function dialog(title,context,index,callback,height) {
	$(".ui-dialog").remove();
	$.ajax({
		url:baselocation+'/common/dialog',
		type:'post',
		dataType:'text',
		data:{'title':title,'context':context,'index':index,'height':height},
		success:function(result){
			$('body').append(result);
			$("#cancelBut,.ui-icon-closethick").click(function(){
				$(".ui-dialog").remove();
			});
			$("#affirmBut").click(function(){
				$(".ui-dialog").remove();
				if(callback){
					callback();
				}
			});
		}
	});
}

