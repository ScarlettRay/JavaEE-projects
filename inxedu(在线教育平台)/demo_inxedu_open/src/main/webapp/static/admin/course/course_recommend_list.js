function addSubmit() {
    var recommendId = $("#recommendId").val();
    if (recommendId < 0) {
        alert("请选择推荐类型");
        return;
    }
    var imgIds = document.getElementsByName("ids");
    var num = 0;
    var ids = '';
    for (var i = 0; i < imgIds.length; i++) {
        if (imgIds[i].checked == true) {
            num++;
            ids += imgIds[i].value + ",";
        }
    }
    if (num == 0) {
        alert("请选择要推荐的课程");
        return;
    }
    $.ajax({
        url : baselocation+"/admin/website/addCourseDetail",
        data : {
            "ids" : ids,
            "recommendId" : recommendId
        },
        type : "post",
        dataType : "json",
        success : function(result) {
            if (result.message == 'true') {
                alert("推荐成功");
                window.opener.addCourseReload();
                window.close();
            } else if (result.message == 'than') {
                var recommendName = $("#recommendId").find(
                    "option:selected").text();
                alert(recommendName + "模块只能添加" + result.entity.courseNum
                    + "个课程");
            } else {
                alert("操作失败");
                window.close();
            }
        }
    });

}
function allCheck(th) {
    $("input[name='ids']:checkbox").prop('checked', th.checked);
}