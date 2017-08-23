var totalPageSize=0;//总页码
var currentPage =0;
var totalPage =0;

function showPageNumber() {
	var pageHtml = "";
	var maxNum_new = currentPage > 4 ? 6 : 7 - currentPage;//最大显示页码数
	var discnt = 1;
	for ( var i = 3; i > 0; i--) {
		if (currentPage > i) {
			pageHtml = pageHtml + "<li><a href='javascript:goPageAjax("
					+ (currentPage - i) + ")'>" + (currentPage - i)
					+ "</a></li>";
			discnt++;
		}
		;
	}
	pageHtml = pageHtml + '<li class="active"><a href="javascript:void(0)">'
			+ currentPage + '</a></li>';
	for ( var i = 1; i < maxNum_new; i++) {
		if (currentPage + i <= totalPage && discnt < 7) {
			pageHtml = pageHtml + "<li><a href='javascript:goPageAjax("
					+ (currentPage + i) + ")'>" + (currentPage + i)
					+ "</a></li>";
			discnt++;
		} else {
			break;
		}
		;
	}
	$(pageHtml).insertBefore("#nextpage");
}

//到某页方法
function goPageAjax(pageNum){
	queryAjaxPage(pageNum);
}

