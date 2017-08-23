$(function(){
	$("input[name='articelId']:checkbox").click(function(){
		var selectlen = $("input[name='articelId']:checked").length;
		var alllen = $("input[name='articelId']").length;
		if(alllen>0 && alllen==selectlen){
			$('input[name="allck"]:checkbox').attr('checked',true);
		}else{
			$('input[name="allck"]:checkbox').attr('checked',false);
		}
	});
	
	/* 时间控件 */
	$("#beginCreateTime,#endCreateTime").datetimepicker({
		regional:"zh-CN",
        changeMonth: true,
        dateFormat:"yy-mm-dd",
        timeFormat: "HH:mm:ss"
    });
});

/**
 * 单条记录的删除
 */
function thisDelete(em){
	$("input:checkbox").attr('checked',false);
	var checkbox= $($(em).parent("td").parent('tr').children('td')[0]).children('input:checkbox');
	$(checkbox).attr('checked',true);
	deleteArticle();
}

/**
 * 删除
 */
function deleteArticle(){
	var arr = $("input[name='articelId']:checked");
	if(arr==null || arr.length==0){
		alert('请选择要删除文章！');
		return false;
	}
	if( !confirm('确认要删除选择文章？')){
		return false;
	}
	$("#deleteForm").submit();
}
/**
 * 全选或反选 
 */
function selectAll(em){
	$("input[name='articelId']").attr('checked',$(em).attr('checked'));
}
/**
 * 保存文章
 */
function saveArticle(){
	/*var title = $("input[name='article.title']").val();
	if(title==null || $.trim(title)==''){
		alert("请填写文章标题！");
		return false;
	}
	var articleType = $("select[name='article.type']").val();
	if(articleType==0){
		alert("请选择文章类型！");
		return false;
	}
	var content = $("#content").val();
	if(content==null || $.trim(content)==''){
		alert('请填写文章内容！');
		return false;
	}*/
	$("#articleForm").submit();
}

/**
 * 图片上传回调函数
 * @param imgUrl 上传成功的图片路径
 */
function callback(imgUrl){
	$("input[name='article.imageUrl']").val(imgUrl);
	$("#showImage").attr("src",imagesPath+imgUrl)
}