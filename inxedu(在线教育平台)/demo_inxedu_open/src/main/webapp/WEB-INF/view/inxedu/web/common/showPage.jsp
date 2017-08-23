<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<div class="ans_feiye" id="page_div"></div>
<script type="text/javascript">
	var pageNo = "${page.currentPage}";
	var totalPage = "${page.totalPageSize}";
	totalPage = totalPage < 1 ? 1 : totalPage;
  	function showPages() {
  		var startPage = 1;
  		var html = '<div class="mt50"><div class="pagination tac"><ul class="disIb">';

		if(pageNo > 6) {
			if((totalPage - pageNo)>5) {
				startPage = pageNo - 5;
			} else {
				startPage = totalPage - 10;
			}
			if(startPage < 1) {
				startPage = 1;
			}
		}

		if(pageNo == 1) {
			html += "<li><a class='disabled' title='上一页' href='javascript:void(0)'>«</a></li>";
		} else {
			html += "<li><a title='上一页' href='javascript:goPage(" + (pageNo - 1) + ")' >«</a></li>";
		}

		if((totalPage - startPage) <11) {
			for(var i=0; i <= (totalPage - startPage); i++) {
				if(pageNo == (startPage + i)) {
					html += "<li><a class='active' href='javascript:void(0)'>" + (startPage+i) + "</a></li>";
				} else {
					html += "<li><a href='javascript:goPage(" + (startPage+i) + ")'>" + (startPage+i) + "</a></li>";
				}
			}
		} else {
			for(var i=0; i<8; i++) {
				if(pageNo == (startPage + i)) {
					html += "<li><a  class='active' href='javascript:void(0)'>" + (startPage+i) + "</a></li>";
				} else {
					html += "<li><a href='javascript:goPage(" + (startPage+i) + ")'>" + (startPage+i) + "</a></li>";
				}
			}
			html += "<li><a class='active' href='javascript:void(0)'>…</a></li>";
			for(var i=1; i>=0; i--) {
				if(pageNo == (totalPage - i)) {
					html += "<li><a class='active' href='javascript:void(0)'>" + pageNo + "</a>";
				} else {
					html += "<li><a href='javascript:goPage(" + (totalPage-i) + ")'>" + (totalPage-i) + "</a></li>";
				}
			}
		}

		if(pageNo == totalPage) {
			html += "<li><a class='disabled' title='下一页' href='javascript:void(0)'>»</a></li>";
		} else {
			html += "<li><a title='下一页' href='javascript:goPage(" + (pageNo + 1) + ")'>»</a></li>";
		}
		$('#page_div').html(html+"</ul></div></div>");
		
	}
	
	function iptChange() {
		var iptPageNo = parseInt($("#iptPageNo").val());
		if(iptPageNo < 1) {
			$("#iptPageNo").val(1); 
		} else if(iptPageNo > totalPage) {
			$("#iptPageNo").val(totalPage);
		}
	}
	
	function goIptPage() {
		var iptPageNo = $("#iptPageNo").val();
		try{
			if(iptPageNo=='' || isNaN(iptPageNo) || iptPageNo.indexOf(".") != -1) {
				iptPageNo = 1;
			}
		}catch(e){
			iptPageNo = 1;
		}
		goPage(iptPageNo);
	}
	
	/* function goPage(pageNum){
        var pageReg = new RegExp("\\.currentPage=[0-9]*");
        var pageURL = "${pageUrlParms}";
        document.location=pageURL.replace(pageReg,".currentPage=" + pageNum);
    } */
    

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
    showPages();
</script>