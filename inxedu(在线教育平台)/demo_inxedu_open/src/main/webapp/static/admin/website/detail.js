function deleteDetail(id){
	if(confirm('确认要删除该推荐课程？')){
		document.location=baselocation+'/admin/detail/deletedeail/'+id;
	}
}
	
//修改排序值 
function updateSort(id,em){
	var td = $(em).parent('td').parent('tr').children('td')[3];
	var sort = $(td).text();
	$(td).html('<input style="width: 40px;" type="text" value="'+sort+'"/>')
	var input = $(td).children('input')[0];
	$(input).focus();
	$(input).blur(function(){
		var val = $(this).val();
		var reg=/^\d+$/;
		if(!reg.test(val)){
			val=sort;
		}
		$.ajax({
			url:baselocation+'/admin/detail/updatesort/'+id+'/'+val,
			type:'post',
			dataType:'json',
			success:function(result){
				if(result.success==true){
					$(td).text(val);
				}else{
					alert(result.message);
					$(td).text(sort);
				}
			},
			error:function(error){
				alert('系统繁忙，请稍后再操作！');
			}
		});
	});
}

/**
 * 打开选择课程页面
 */
function selectCousre(){
	window.open(baselocation+'/admin/cou/showrecommendList' ,'newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=150,left=300,width=923,height=600');
}

/**
 * 刷新页面
 */
function refurbishPage(url){
	document.location=url;
}