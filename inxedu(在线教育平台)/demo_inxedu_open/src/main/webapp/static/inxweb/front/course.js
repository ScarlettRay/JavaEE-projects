/**
 * 不同条件查询课程 
 * @param type 1班类型（专业） 2讲师 3排序条件
 * @param keyWord type==1(专业ID) type==2(老师ID) type=3(排序条件)
 */
function submitForm(type,keyWord){
	if(type==1){
		$("input[name='queryCourse.subjectId']").val(keyWord);
	}else if(type==2){
		$("input[name='queryCourse.teacherId']").val(keyWord);
	}else if(type==3){
		$("input[name='queryCourse.order']").val(keyWord);
	}else if(type==4){
		if(keyWord=='ONE'||keyWord=='NEW'||keyWord=='FOLLOW'){
			$("input[name='queryCourse.order']").val('DESCENDING');
		}else if(keyWord=='DESCENDING'){
			$("input[name='queryCourse.order']").val('ASCENDING');
		}else if(keyWord=='ASCENDING'){
			$("input[name='queryCourse.order']").val('DESCENDING');
		}
	}
	$("input[name='queryCourse.courseName']").val('');
	$("#searchForm").submit();
}