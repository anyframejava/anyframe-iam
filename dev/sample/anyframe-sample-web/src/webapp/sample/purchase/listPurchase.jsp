<%@ page contentType="text/html; charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri='/WEB-INF/anyframe-page.tld' prefix='emp' %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.lang.String" %>
<%@ page import="anyframe.common.Page" %>
<%@ page import="com.sds.emp.common.EmpUtil" %>
<%@ page import="com.sds.emp.purchase.services.PurchaseVO" %>
<%@ page import="com.sds.emp.purchase.services.SearchVO" %>
<html>
<head>
<title>Search List of Purchase</title>
<meta http-equiv="content-type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="<c:url value='/sample/css/admin.css'/>" type="text/css">

<%
	Page resultPage = (Page)request.getAttribute("page");
	ArrayList resultList = (ArrayList)resultPage.getList();	
	
	SearchVO searchVO = (SearchVO)request.getAttribute("searchVO");
	String keyword = EmpUtil.null2str(searchVO.getSearchKeyword());
	String condition = EmpUtil.null2str(searchVO.getSearchCondition());
	String tranStatusCode = searchVO.getSearchTranStatusCode();
	
	int pageSize = ((Integer)request.getAttribute("pageSize")).intValue();
%>

<script language="JavaScript">
<!--
function fncGetPurchaseList(arg) {
	if(arg == "1")
		document.listForm.pageIndex.value = 1;
   	document.listForm.action="<c:url value='/emplistpurchase.do'/>";
   	document.listForm.submit();		
}

function fncGetPurchase(prodNo) {
	document.listForm.prodNo.value = prodNo;
   	document.listForm.action="<c:url value='/empgetpurchase.do'/>";
   	document.listForm.submit();		
}

function fncAddPurchaseView() {
    document.listForm.action="<c:url value='/empaddpurchaseview.do'/>";
    document.listForm.submit();
}
-->
</script>

</head>

<body bgcolor="#ffffff" text="#000000">

<form name="listForm"  method="post" action="<c:url value='/emplistpurchase.do'/>">

<!--************************** begin of contents *****************************-->

<!-- begin of title -->
<table width="100%" height="37" border="0" cellpadding="0"
	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="<c:url value='/sample/images/ct_ttl_img01.gif'/>" width="15" height="37"></td>
		<td background="<c:url value='/sample/images/ct_ttl_img02.gif'/>" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">Search List of Purchase</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="<c:url value='/sample/images/ct_ttl_img03.gif'/>" width="12" height="37"></td>
	</tr>
</table>
<!-- end of title -->

<!-- begin of search -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
	    <td align="right">
			<select name="searchCondition" class="ct_input_g" style=" width : 114px;">
				<option value="0" <%= (condition.equals("0") ? "selected" : "")%>>Product No.</option>
				<option value="1" <%= (condition.equals("1") ? "selected" : "")%>>Product Name</option>
			</select>
			<input type="text" name="searchKeyword" value="<%= keyword %>" class="ct_input_g" style="width:150px; height:19px" required fieldTitle="Keyword" maxLength="50">
		</td>
		<td align="right">
		Status of Transaction&nbsp;&nbsp;
		</td>
		<td align="left">
			<select name="searchTranStatusCode" class="ct_input_g" style=" width : 118px;">		
				<option value="A">All</option>

				<option value="002" <%= ("002".equals(tranStatusCode) ? "selected" : "")%>>Accept Order</option>
				<option value="003" <%= ("003".equals(tranStatusCode) ? "selected" : "")%>>Finish Delivery</option>			
			</select>		
		</td>
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23"><img src="<c:url value='/sample/images/ct_btnbg01.gif'/>" width="17" height="23"></td>
					<td background="<c:url value='/sample/images/ct_btnbg02.gif'/>" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetPurchaseList('1');">Search</a>
					</td>
					<td width="14" height="23"><img src="<c:url value='/sample/images/ct_btnbg03.gif'/>" width="14" height="23"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<!-- end of search -->

<!-- begin of list -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td class="ct_list_b" width="50">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">Product No.</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">Product Name</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="100">Price</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="100">Status of Transaction</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">Seller</td>		
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	<% 	int currentPageIndex = resultPage.getCurrentPage();
		int no = (currentPageIndex - 1) * pageSize;
		
		for(int i=0; i<resultList.size(); i++) {
			PurchaseVO purchaseVO = (PurchaseVO)resultList.get(i);
	%>
	<tr class="ct_list_pop">
		<td align="center"><%=++no%></td>
		<td></td>
		<td align="center"><a href="javascript:fncGetPurchase('<%= purchaseVO.getProdNo() %>');"><%= purchaseVO.getProdNo() %></a></td>
		<td></td>
		<td align="left"><%= purchaseVO.getProdName() %></td>
		<td></td>
		<td align="right"><%= purchaseVO.getSellAmount() %></td>
		<td></td>
		<td align="center"><%= purchaseVO.getTranStatusCodeName() %></td> 
		<td></td>
		<td align="center"><%= purchaseVO.getSellerId() %></td> 
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>
	<% } %>
</table>
<!-- selected userId -->
<input type="hidden" name="prodNo" >
<!-- end of list -->

<!--  begin of page Navigator -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	style="margin-top:10px;">
	<tr>
		<td align="center">
             <input type="hidden" name="pageIndex" value="<%=resultPage.getCurrentPage()%>">
             <emp:pagenavigator linkUrl="javascript:fncGetPurchaseList(2);" 
             	pages="<%=resultPage%>" 
             	formName="listForm"
              firstImg="sample/images/ct_btn_pre.gif" 
              prevImg="sample/images/ct_btn_pre01.gif" 
              lastImg="sample/images/ct_btn_next.gif" 
              nextImg="sample/images/ct_btn_next01.gif" />			
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
