<%@ page contentType="text/html; charset=euc-kr" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.lang.String" %>
<%@ page import="com.sds.emp.common.EmpUtil" %>
<%@ page import="com.sds.emp.sale.services.CategoryVO" %>
<%@ page import="com.sds.emp.sale.services.ProductVO" %>

<%
	ArrayList categoryList = (ArrayList)request.getAttribute("categoryList");
	if (categoryList == null) categoryList = new ArrayList();
	
	ProductVO productVO = (ProductVO)request.getAttribute("productVO");
%>

<html>
<head>
<title>Update Product Information</title>
<meta http-equiv="content-type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="<c:url value='/sample/css/admin.css'/>" type="text/css">

<script language="javascript" src="<c:url value='/sample/javascript/calendar.js'/>"></script>
<script language="javascript" src="<c:url value='/sample/javascript/CommonScript.js'/>"></script>
<script language="JavaScript">
<!--
function fncUpdateProduct() {
	// Form validation
	if(FormValidation(document.detailForm) != false) {
		
	    document.detailForm.action="<c:url value='/empupdateproduct.do'/>";
	    document.detailForm.submit();
	} 
}

function check_asYn(checkAsYn) {
	if (checkAsYn.checked) {
		document.detailForm.asYn.value = "Y";
	} else {
		document.detailForm.asYn.value = "N";
	}
}

function resetData() {
	document.detailForm.reset();
}
-->
</script>


</head>

<body bgcolor="#ffffff" text="#000000">

<form name="detailForm"  method="post" enctype="multipart/form-data">
<input type="hidden" name="name" value="abc"/>
<!--************************** begin of contents *****************************-->

<!-- begin of title -->
<table width="100%" height="37" border="0" cellpadding="0"
	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="<c:url value='/sample/images/ct_ttl_img01.gif'/>" width="15" height="37"></td>
		<td background="<c:url value='/sample/images/ct_ttl_img02.gif'/>" width="100%" style="padding-left:10px;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="93%" class="ct_ttl01">Update Product Information</td>
				<td width="20%" align="right">&nbsp;</td>
			</tr>
		</table>
		</td>
		<td width="12" height="37"><img src="<c:url value='/sample/images/ct_ttl_img03.gif'/>" width="12" height="37"></td>
	</tr>
</table>
<!-- end of title -->

<!-- begin of table -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:13px;">
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">Product No.</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<input type="text" name="prodNo" class="ct_input_bg" style="width:100px; height:19px" value="<%= EmpUtil.null2str(productVO.getProdNo()) %>" readonly required fieldTitle="Product No." maxLength="50">
		</td>
	</tr>
	<tr> 
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">Product Name <img src="<c:url value='/sample/images/ct_icon_red.gif'/>" width="3" height="3" align="absmiddle"></td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<input type="text" name="prodName" class="ct_input_g" style="width:100px; height:19px" value="<%= EmpUtil.null2str(productVO.getProdName()) %>" required fieldTitle="Product Name" maxLength="50" char="s">
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">Product Classification <img src="<c:url value='/sample/images/ct_icon_red.gif'/>" width="3" height="3" align="absmiddle"></td>		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<select name="categoryNo" class="ct_input_g" style="width:100px" required fieldTitle="Product Classification" >
			<%	String categoryNo = EmpUtil.null2str(productVO.getCategoryNo());
				for(int i=0; i < categoryList.size(); i++) { 
					CategoryVO categoryVO = (CategoryVO)categoryList.get(i);
			%>
				<option value="<%= categoryVO.getCategoryNo() %>" <%= (categoryNo.equals(categoryVO.getCategoryNo()) ? "selected" : "") %> ><%= categoryVO.getCategoryName() %></option>
			<% } %>
			</select>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">Detail Information <img src="<c:url value='/sample/images/ct_icon_red.gif'/>" width="3" height="3" align="absmiddle"></td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<textarea name="prodDetail" class="ct_input_g" cols="50" rows="5" required fieldTitle="Detail Information" maxLength="200" char="s"><%= EmpUtil.null2str(productVO.getProdDetail()) %></textarea>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">Manufactured Date</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<input type="text" name=manufactureDay class="ct_input_g" style="width:100px; height:19px" value="<%= EmpUtil.null2str(productVO.getManufactureDay()) %>" fieldTitle="Detail Information" maxLength="8" valCheck="DATE" >
			<a href="javascript:show_calendar(document.detailForm.rootPath.value,'document.detailForm.manufactureDay', document.detailForm.manufactureDay.value)" id="iconCalendar"><img src="<c:url value='/sample/images/ct_icon_date.gif'/>" width="16" height="18" border="0" align="absmiddle"></a>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">Sellable Quantity <img src="<c:url value='/sample/images/ct_icon_red.gif'/>" width="3" height="3" align="absmiddle"></td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<input type="text" name="sellQuantity" class="ct_input_g" style="width:100px; height:19px;text-align:right" value="<%= EmpUtil.null2str(productVO.getSellQuantity()) %>" fieldTitle="Sellable Quantity" required maxLength="5" num="i" fromNum="0">
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">Price <img src="<c:url value='/sample/images/ct_icon_red.gif'/>" width="3" height="3" align="absmiddle"></td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<input type="text" name="sellAmount" class="ct_input_g" style="width:100px; height:19px;text-align:right" value="<%= EmpUtil.null2str(productVO.getSellAmount()) %>" fieldTitle="Price" required maxLength="10" num="i" fromNum="0">
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">Product Image</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<img src="<%= EmpUtil.null2str(productVO.getImageFile()) %>" alt="Product Image" border="0" onError="this.width=0;">
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">A/S</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<input type="checkbox" name="checkAsYn" <%= EmpUtil.null2str(productVO.getAsYn()).equals("Y") ? "checked" : "" %>  onChange="check_asYn(this)"> A/S is possible.
			<input type="hidden" name="asYn" value="<%= EmpUtil.null2str(productVO.getAsYn()) %>">
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
</table>
<!-- end of table -->

<!-- begin of button -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	style="margin-top:10px;">
	<tr>
		<td width="53%">
		
		</td>
		<td align="right">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23"><img src="<c:url value='/sample/images/ct_btnbg01.gif'/>" width="17" height="23"></td>
					<td background="<c:url value='/sample/images/ct_btnbg02.gif'/>" class="ct_btn01" style="padding-top:3px;"><a href="javascript:fncUpdateProduct();">Update</a></td>
					<td width="14" height="23"><img src="<c:url value='/sample/images/ct_btnbg03.gif'/>" width="14" height="23"></td>
					
					<td width="30"></td>	
										
					<td width="17" height="23"><img src="<c:url value='/sample/images/ct_btnbg01.gif'/>" width="17" height="23"></td>
					<td background="<c:url value='/sample/images/ct_btnbg02.gif'/>" class="ct_btn01" style="padding-top:3px;"><a href="javascript:resetData();">Cancel</a></td>
					<td width="14" height="23"><img src="<c:url value='/sample/images/ct_btnbg03.gif'/>" width="14" height="23"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<!-- end of button -->
<input type="hidden" name="rootPath" value="<c:url value='/'/>"/>
<!-- search condition -->
<input type="hidden" name="searchTranStatusCode" value="${searchVO.searchTranStatusCode}">
<input type="hidden" name="pageIndex" value="${searchVO.pageIndex}">
<input type="hidden" name="searchCondition" value="${searchVO.searchCondition}">
<input type="hidden" name="searchKeyword" value="${searchVO.searchKeyword}">
</form>

<script language="JavaScript">
document.getElementsByName("prodName")[0].focus();
</script>

</body>
</html>
