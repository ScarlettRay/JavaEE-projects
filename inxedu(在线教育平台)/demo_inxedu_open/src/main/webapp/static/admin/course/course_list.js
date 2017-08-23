//subject ztree start
var subject_setting = {
    view:{
        showLine: true,
        showIcon: true,
        selectedMulti: false,
        dblClickExpand: false
    },
    data: {
        simpleData: {
            enable: true,
            idKey:'subjectId',
            pIdKey:'parentId',
            rootPid:''
        },
        key:{
            name:'subjectName',
            title:'subjectName'
        }
    },
    callback: {
        onClick: subject_treeOnclick
    }
};
//切换专业时操作
function subject_treeOnclick(e,treeId, treeNode) {
    hideSubjectMenu();
    $("#subjectId").val(treeNode.subjectId);
    $("#subjectNameBtn").val(treeNode.subjectName);
}
//清空专业的查询条件时同时清除考点
function subject_cleantreevalue(){
    hideSubjectMenu();
    $("#subjectId").val(0);
    $("#subjectNameBtn").val("");
}
function showSubjectMenu() {
    var cityObj = $("#subjectNameBtn");
    var cityOffset = $("#subjectNameBtn").offset();
    $("#subjectmenuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
    $("body").bind("mousedown", onBodyDown);
}
function hideSubjectMenu() {
    $("#subjectmenuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
    if (!(event.target.id == "subjectNameBtn" || event.target.id == "subjectmenuContent" || $(event.target).parents("#subjectmenuContent").length>0)) {
        hideSubjectMenu();
    }
}
function submitSearch(){
    $("#pageCurrentPage").val(1);
    $("#searchForm").submit();
}
//删除课程
function delCourse(id){
    if(confirm("是否删除?")){
        window.location.href=baselocation+"/admin/cou/del/"+id;
    }
}
//清空
function submitclear(){
    $("#subjectId").val(0);
    $("#membertype").val(0);
    $("#id").val("");
    $("#name").val("");
    $("#subjectNameBtn").val("");
    $("#isavaliable").val(-1);
}