$(function(){
	ctSort();
});
/**
 * 显示专业
 */
function ctSort() {
	$("#allTeacher").hover(function() {
		$(this).toggleClass("hover");
	});
}

/**
 * 根据专业查询讲师
 * @param subjectId 专业ID
 */
function selectSubject(subjectId){
	$("input[name='queryTeacher.subjectId']").val(subjectId);
	$("#searchForm").submit();
}