var currentPage =0;
var totalPage =0;
function goPage(pageNum) {
	if (/^\d+$/.test(pageNum) == false) {
		return;
	}
	if (pageNum < 1) {
		pageNum = 1;
	}
	if (pageNum > totalPage) {
		if (totalPage > 0) {
			pageNum = totalPage;
		} else {
			pageNum = 1;
		}
	}
	$("#pageCurrentPage").val(pageNum);
	$("#searchForm").submit();
}

function showPageNumber() {
	var pageHtml = "";
	var maxNum_new = currentPage > 4 ? 6 : 7 - currentPage;//最大显示页码数
	var discnt = 1;
	for ( var i = 3; i > 0; i--) {
		if (currentPage > i) {
			//pageHtml = pageHtml + "<li><a href='javascript:goPage(" + (currentPage - i) + ")'>" + (currentPage - i) + "</a></li>";
			pageHtml = pageHtml + "<a href='javascript:goPage(" + (currentPage - i) + ")' title=''>" + (currentPage - i) + "</a>";
			discnt++;
		}
	}
	//pageHtml = pageHtml + '<li class="disabled"><a href="javascript:void(0)">' + currentPage + '</a></li>';
	pageHtml = pageHtml + '<a href="javascript:void(0)" title="" class="current undisable">' + currentPage + '</a>';
	for ( var i = 1; i < maxNum_new; i++) {
		if (currentPage + i <= totalPage && discnt < 7) {
			//pageHtml = pageHtml + "<li><a href='javascript:goPage("+ (currentPage + i) + ")'>" + (currentPage + i) + "</a></li>";
			pageHtml = pageHtml + "<a href='javascript:goPage(" + (currentPage + i) + ")' title=''>" + (currentPage + i) + "</a>";
			discnt++;
		} else {
			break;
		}
	}
	$(pageHtml).insertBefore("#nextpage");
}

function showAjaxPageNumber() {
	var pageHtml = "";
	var maxNum_new = currentPage > 4 ? 6 : 7 - currentPage;//最大显示页码数
	var discnt = 1;
	for ( var i = 3; i > 0; i--) {
		if (currentPage > i) {
			//pageHtml = pageHtml + "<li><a href='javascript:goPage(" + (currentPage - i) + ")'>" + (currentPage - i) + "</a></li>";
			pageHtml = pageHtml + "<a href='javascript:goPageAjax(" + (currentPage - i) + ")' title=''>" + (currentPage - i) + "</a>";
			discnt++;
		}
	}
	//pageHtml = pageHtml + '<li class="disabled"><a href="javascript:void(0)">' + currentPage + '</a></li>';
	pageHtml = pageHtml + '<a href="javascript:void(0)" title="" class="current undisable">' + currentPage + '</a>';
	for ( var i = 1; i < maxNum_new; i++) {
		if (currentPage + i <= totalPage && discnt < 7) {
			//pageHtml = pageHtml + "<li><a href='javascript:goPageAjax("+ (currentPage + i) + ")'>" + (currentPage + i) + "</a></li>";
			pageHtml = pageHtml + "<a href='javascript:goPageAjax(" + (currentPage + i) + ")' title=''>" + (currentPage + i) + "</a>";
			discnt++;
		} else {
			break;
		}
	}
	$(pageHtml).insertBefore("#nextpage");
}

function showAjaxPageNumber2() {
	var pageHtml = "";
	var maxNum_new = currentPage > 4 ? 6 : 7 - currentPage;//最大显示页码数
	var discnt = 1;
	for ( var i = 3; i > 0; i--) {
		if (currentPage > i) {
			//pageHtml = pageHtml + "<li><a href='javascript:goPage(" + (currentPage - i) + ")'>" + (currentPage - i) + "</a></li>";
			pageHtml = pageHtml + "<a href='javascript:goPageAjax(" + (currentPage - i) + ")' title=''>" + (currentPage - i) + "</a>";
			discnt++;
		}
	}
	//pageHtml = pageHtml + '<li class="disabled"><a href="javascript:void(0)">' + currentPage + '</a></li>';
	pageHtml = pageHtml + '<a href="javascript:void(0)" title="" class="current undisable">' + currentPage + '</a>';
	for ( var i = 1; i < maxNum_new; i++) {
		if (currentPage + i <= totalPage && discnt < 7) {
			//pageHtml = pageHtml + "<li><a href='javascript:goPageAjax("+ (currentPage + i) + ")'>" + (currentPage + i) + "</a></li>";
			pageHtml = pageHtml + "<a href='javascript:goPageAjax(" + (currentPage + i) + ")' title=''>" + (currentPage + i) + "</a>";
			discnt++;
		} else {
			break;
		}
	}
	$(pageHtml).insertBefore("#nextpage2");
}
