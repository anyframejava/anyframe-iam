<%@ page contentType="text/html; charset=euc-kr" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ page import="com.sds.emp.common.EmpUtil" %>
<%@ page import="com.sds.emp.sale.services.ProductVO" %>
<%@ page import="com.sds.emp.sale.services.SearchVO" %>

<%
	ProductVO productVO = (ProductVO)request.getAttribute("productVO");
	SearchVO searchVO = (SearchVO)request.getAttribute("searchVO");
%>

<html>
<head>
<title>Search Detail Information of Product</title>
<meta http-equiv="content-type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="<c:url value='/sample/css/admin.css'/>" type="text/css">

<script language="javascript" src="<c:url value='/sample/javascript/CommonScript.js'/>"></script>
<script language="JavaScript">
<!--
function fncListProductSearch() {
	document.detailForm.action="<c:url value='/emplistproductsearch.do'/>";
	document.detailForm.submit();
}

function fncAddPurchaseView() { 
   	document.detailForm.action="<c:url value='/empaddpurchase.do'/>";
   	document.detailForm.submit();		
}

function resetData() {
	document.detailForm.reset();
}

function resizeImgWidth(imgObj, width) {
    var oldWidth = imgObj.width;
    var oldHeight = imgObj.height;
    if (oldWidth > width) {
        var newWidth = width;
        var newHeight = Math.round(oldHeight * newWidth / oldWidth);
        imgObj.wdith = newWidth;
        imgObj.height = newHeight;
    }
}
-->
</script>


</head>

<body bgcolor="#ffffff" text="#000000">

<form name="detailForm" enctype="multipart/form-data">
<input type="hidden" name="prodNo" value="<%= productVO.getProdNo() %>" />
<!--************************** begin of contents *****************************-->

<!-- begin of title -->
<table width="100%" height="37" border="0" cellpadding="0"
	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="<c:url value='/sample/images/ct_ttl_img01.gif'/>" width="15" height="37"></td>
		<td background="<c:url value='/sample/images/ct_ttl_img02.gif'/>" width="100%" style="padding-left:10px;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="93%" class="ct_ttl01">Search Detail Information of Product</td>
				<td width="20%" align="right">&nbsp;</td>
			</tr>
		</table>
		</td>
		<td width="12" height="37"><img src="<c:url value='/sample/images/ct_ttl_img03.gif'/>" width="12" height="37"></td>
	</tr>
</table>
<!-- end of title -->

<!-- begin of detail table -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:13px;">
	<tr>
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>
				<tr>
					<td width="104" class="ct_write">Product No. </td>
					<td bgcolor="D6D6D6" width="1"></td>
					<td class="ct_write01">
						<%= productVO.getProdNo() %>
					</td>
				</tr>
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>
				<tr>
					<td width="104" class="ct_write">Product Name </td>
					<td bgcolor="D6D6D6" width="1"></td>
					<td class="ct_write01">
			   			<%= productVO.getProdName() %>
					</td>
				</tr>
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>
				<tr>
					<td width="104" class="ct_write">Product Classification</td>
					<td bgcolor="D6D6D6" width="1"></td>
					<td class="ct_write01">
						<%= productVO.getCategoryNo() %>
					</td>
				</tr>
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>	
				<tr>
					<td width="104" class="ct_write">Manufactured Date</td>
					<td bgcolor="D6D6D6" width="1"></td>
					<td class="ct_write01">
						<%= productVO.getManufactureDay() %>
					</td>
				</tr>
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>
				<tr>
					<td width="104" class="ct_write">Detail Information</td>
					<td bgcolor="D6D6D6" width="1"></td>
					<td class="ct_write01">
						<%= EmpUtil.null2str(productVO.getProdDetail()).replaceAll("\n", "<br>") %>
					</td>
				</tr>					
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>	
				<tr>
					<td width="104" class="ct_write">Sellable Quantity</td>
					<td bgcolor="D6D6D6" width="1"></td>
					<td class="ct_write01">
						<%= productVO.getSellQuantity() %>
					</td>
				</tr>
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>		
				<tr>
					<td width="104" class="ct_write">Price</td>
					<td bgcolor="D6D6D6" width="1"></td>
					<td class="ct_write01">
						<%= productVO.getSellAmount() %>
					</td>
				</tr>
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>	
				<tr>
					<td width="104" class="ct_write">A/S</td>
					<td bgcolor="D6D6D6" width="1"></td>
					<td class="ct_write01">
						<%= ("Y".equals(productVO.getAsYn()) ? "Possible" : "Impossible")%>					
					</td>
				</tr> 
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>
			</table>
		</td>
		
		<td width="300" valign="high">
			<div style="width:300px;overflow:hidden">
				<img src="<%= EmpUtil.null2str(productVO.getImageFile()) %>" alt="Product Image" border="0" onError="this.width=0;" onLoad="resizeImgWidth(this,250)">
			</div>
		</td>
	</tr>				
</table>
<!-- end of table -->

<!-- remain search condition of search UI previously-->
<input type="hidden" name="searchCondition" value="<%= EmpUtil.null2str(searchVO.getSearchCondition()) %>">
<input type="hidden" name="searchKeyword" value="<%= EmpUtil.null2str(searchVO.getSearchKeyword()) %>">
<input type="hidden" name="pageIndex" value="<%= searchVO.getPageIndex() %>">
<input type="hidden" name="searchAsYn" value="<%= searchVO.getSearchAsYn() %>">

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
					<td background="<c:url value='/sample/images/ct_btnbg02.gif'/>" class="ct_btn01" style="padding-top:3px;"><a href="javascript:fncListProductSearch();">List</a></td>
					<td width="14" height="23"><img src="<c:url value='/sample/images/ct_btnbg03.gif'/>" width="14" height="23"></td>

					<td width="30"></td>
										
					<td width="17" height="23"><img src="<c:url value='/sample/images/ct_btnbg01.gif'/>" width="17" height="23"></td>
					<td background="<c:url value='/sample/images/ct_btnbg02.gif'/>" class="ct_btn01" style="padding-top:3px;"><a href="javascript:fncAddPurchaseView();">Purchase</a></td>
					<td width="14" height="23"><img src="<c:url value='/sample/images/ct_btnbg03.gif'/>" width="14" height="23"></td>
					
				</tr>
			</table>
		</td>
	</tr>
</table>
<!-- end of button -->

</form>

</body>
</html>
