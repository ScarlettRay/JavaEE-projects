	$(function(){
		//初始化图片上传
		initSimpleImageUpload("imageFile","image",imgCallback);
		//初始化略缩图片上传
		initSimpleImageUpload("previewFile","image",previewCallback);
	});
	//图片上传回调
	function imgCallback(imgUrl){
		$("input[name='websiteImages.imagesUrl']").val(imgUrl);
		$("#imagesUrl").attr('src',imagesPath+imgUrl);
	}
	//略缩图片上传回调
	function previewCallback(imgUrl){
		$("input[name='websiteImages.previewUrl']").val(imgUrl);
		$("#previewUrl").attr('src',imagesPath+imgUrl);
	}
	
	/**
	 * 提交信息
	 */
	function saveImage(){
		var title = $("input[name='websiteImages.title']").val();
		if(title==null || $.trim(title)==''){
			alert("请填写图片标题");
			return false;
		}
		var typeId = $("select[name='websiteImages.typeId']").val();
		if(typeId<=0){
			alert("请选择图片类型");
			return false;
		}
		var sort = $("input[name='websiteImages.seriesNumber']").val();
		var reg=/^\d+$/;
		if(!reg.test(sort)){
			alert("排序只能填写正整数");
			return false;
		}
		var imagesUrl =$("input[name='websiteImages.imagesUrl']").val();
		if(imagesUrl==null || $.trim(imagesUrl)==''){
			alert("请上传图片");
			return false;
		}
		$("#saveImagesForm").submit();
	}
