<%@ page contentType="text/html; charset=euc-kr"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib uri='/WEB-INF/anyframe-page.tld' prefix='emp'%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.lang.String"%>
<%@ page import="anyframe.common.Page"%>
<%@ page import="com.sds.emp.common.EmpUtil"%>
<%@ page import="com.sds.emp.sale.services.SearchVO"%>
<%@ page import="com.sds.emp.sale.services.CategoryVO"%>
<%@ page import="com.sds.emp.sale.services.SaleVO"%>
<%@ page import="com.sds.emp.code.services.CodeVO"%>
<html>
<head>
<title>Search List of Sales</title>
<meta http-equiv="content-type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="<c:url value='/sample/css/admin.css'/>"
	type="text/css">

<%
    ArrayList categoryList = (ArrayList)request.getAttribute("categoryList");
    if (categoryList == null) categoryList = new ArrayList();

	Page resultPage = (Page)request.getAttribute("page");
	ArrayList resultList = (ArrayList)resultPage.getList();	
	SearchVO searchVO=(SearchVO)request.getAttribute("searchVO");
	//SaleForm saleForm = (SaleForm)request.getAttribute("saleForm");
	String keyword = EmpUtil.null2str(searchVO.getSearchKeyword());
	String condition = EmpUtil.null2str(searchVO.getSearchCondition());
	String categoryNo = EmpUtil.null2str(searchVO.getSearchCategoryNo());
	
	int pageSize = ((Integer)request.getAttribute("pageSize")).intValue();
%>

<script language="javascript"
	src="<c:url value='/sample/javascript/CommonScript.js'/>"></script>
<script language="JavaScript">
<!--
function fncGetSaleList(arg) {
	// Form validation
	if(FormValidation(document.listForm) != false) {
		if(arg == "1")
			document.listForm.pageIndex.value = 1;
	   	document.listForm.action="<c:url value='/emplistsale.do'/>";
	   	document.listForm.submit();	
	}	
}

function fncGetSale(prodNo) {
	document.listForm.prodNo.value = prodNo;
   	document.listForm.action="<c:url value='/empgetsale.do'/>";
   	document.listForm.submit();		
}

-->
</script>

</head>

<body bgcolor="#ffffff" text="#000000">

<form name="listForm" method="post"
	action="<c:url value='/emplistsale.do'/>"><!--************************** begin of contents *****************************-->

<!-- begin of title -->
<table width="100%" height="37" border="0" cellpadding="0"
	cellspacing="0">
	<tr>
		<td width="15" height="37"><img
			src="<c:url value='/sample/images/ct_ttl_img01.gif'/>" width="15"
			height="37"></td>
		<td background="<c:url value='/sample/images/ct_ttl_img02.gif'/>"
			width="100%" style="padding-left: 10px;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="93%" class="ct_ttl01">Search List of Sales</td>
			</tr>
		</table>
		</td>
		<td width="12" height="37"><img
			src="<c:url value='/sample/images/ct_ttl_img03.gif'/>" width="12"
			height="37"></td>
	</tr>
</table>
<!-- end of title --> <!-- begin of search -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	style="margin-top: 10px;">
	<tr>
		<td align="right"><select name="searchCondition"
			class="ct_input_g" style="width: 100px">
			<option value="0" <%= (condition.equals("0") ? "selected" : "")%>>Product
			No.</option>
			<option value="1" <%= (condition.equals("1") ? "selected" : "")%>>Product
			Name</option>
		</select> <input type="text" name="searchKeyword" value="<%= keyword %>"
			class="ct_input_g" style="width: 150px; height: 19px"
			fieldTitle="Keyword" maxLength="50" char="s"></td>
		<td align="right" width="250">Category Name&nbsp;&nbsp; 
		<select
			name="searchCategoryNo" class="ct_input_g" style="width:130px">
			<option value="A">All</option>
			<%	
					for(int i=0; i < categoryList.size(); i++) { 
						CategoryVO categoryVO = (CategoryVO)categoryList.get(i);
				%>
			<option value="<%= categoryVO.getCategoryNo() %>"
				<%= (categoryNo.equals(categoryVO.getCategoryNo() ) ? "selected" : "") %>><%= categoryVO.getCategoryName() %></option>
			<% } %>
		</select>
		&nbsp;&nbsp;</td>
		<td align="right" width="70">
		<table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="17" height="23"><img
					src="<c:url value='/sample/images/ct_btnbg01.gif'/>" width="17"
					height="23"></td>
				<td background="<c:url value='/sample/images/ct_btnbg02.gif'/>"
					class="ct_btn01" style="padding-top: 3px;"><a
					href="javascript:fncGetSaleList('1');">Search</a></td>
				<td width="14" height="23"><img
					src="<c:url value='/sample/images/ct_btnbg03.gif'/>" width="14"
					height="23"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<!-- end of search --> <!-- begin of list -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	style="margin-top: 10px;">
	<tr>
		<td class="ct_list_b" width="50">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">Category Name</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">Product No.</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">Product Name</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="100">Price</td>
		<td class="ct_line02"></td>
		<!-- <td class="ct_list_b" width="100">Status of Transaction</td>
		<td class="ct_line02"></td>-->
		<td class="ct_list_b" width="150">Registered Date</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	<% 	int currentPageIndex = resultPage.getCurrentPage();
		int no = (currentPageIndex - 1) * pageSize;
		
		for(int i=0; i<resultList.size(); i++) {
			SaleVO saleVO = (SaleVO)resultList.get(i);
	%>
	<tr class="ct_list_pop">
		<td align="center"><%=++no%></td>
		<td></td>
		<td align="center"><%= EmpUtil.null2str(saleVO.getCategoryName()) %></td>
		<td></td>
		<td align="center"><a
			href="javascript:fncGetSale('<%= saleVO.getProdNo() %>');"><%= saleVO.getProdNo() %></a></td>
		<td></td>
		<td align="left"><%= saleVO.getProdName() %></td>
		<td></td>
		<td align="right" style="padding-right: 3px;"><%= EmpUtil.toAmountStr(saleVO.getSellAmount()) %></td>
		<td></td>
		<td align="center"><%= EmpUtil.toDateStr(saleVO.getRegDate()) %></td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>
	<% } %>
</table>
<!-- selected prodNo --> <input type="hidden" name="prodNo"> <!-- end of list -->

<!--  begin of page Navigator -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	style="margin-top: 10px;">
	<tr>
		<td align="center"><input type="hidden" name="pageIndex"
			value="<%=resultPage.getCurrentPage()%>"> <emp:pagenavigator
			linkUrl="javascript:fncGetSaleList(2);" pages="<%=resultPage%>"
			formName="listForm"
			firstImg="${pageContext.request.contextPath}/sample/images/ct_btn_pre.gif"
			prevImg="${pageContext.request.contextPath}/sample/images/ct_btn_pre01.gif"
			lastImg="${pageContext.request.contextPath}/sample/images/ct_btn_next.gif"
			nextImg="${pageContext.request.contextPath}/sample/images/ct_btn_next01.gif" />
		</td>
	</tr>
</table>
<!--  end of page Navigator -->
</form>
<script language="JavaScript">
document.listForm.searchKeyword.focus();
</script>
</body>
</html>
