<%@ page contentType="text/html; charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.sds.emp.common.EmpUtil" %>
<%@ page import="com.sds.emp.sale.services.SaleVO" %>

<%
	SaleVO saleVO = (SaleVO)request.getAttribute("saleVO");
%>

<html>
<head>
<title>Search Detail Information of Sales</title>
<meta http-equiv="content-type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="<c:url value='/sample/css/admin.css'/>" type="text/css">

<script language="JavaScript">
<!--
function fncUpdateProduct() {
  document.detailForm.action="<c:url value='/empupdateproduct.do'/>";
  document.detailForm.submit();
}
<% // only in case add product, enables update button
   if (EmpUtil.null2str(saleVO.getTranStatusCode()).equals("002")) { %>
	function fncUpdateTranStatusCode() {
	    document.detailForm.action="<c:url value='/empupdatetranstatuscode.do'/>";
	    document.detailForm.submit();
	}
<% } %>


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

<form name="detailForm">
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
				<td width="93%" class="ct_ttl01">Search Detail Information of Sales</td>
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
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">

				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>
				<tr>
					<td width="104" class="ct_write">Product No.</td>
					<td bgcolor="D6D6D6" width="1"></td>
					<td class="ct_write01">
						<%= EmpUtil.null2str(saleVO.getProdNo()) %>
					</td>
				</tr>
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>
				<tr>
					<td width="104" class="ct_write">Product Name</td>
					<td bgcolor="D6D6D6" width="1"></td>
					<td class="ct_write01" colspan="2">
						<%= EmpUtil.null2str(saleVO.getProdName()) %>
					</td>
				</tr>
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>
				<tr>
					<td width="104" class="ct_write">Product Classification</td>
					<td bgcolor="D6D6D6" width="1"></td>
					<td class="ct_write01">
						<%= EmpUtil.null2str(saleVO.getCategoryName()) %>
					</td>
				</tr>
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>
				<tr>
					<td width="104" class="ct_write">Manufactured Date</td>
					<td bgcolor="D6D6D6" width="1"></td>
					<td class="ct_write01">
						<%= EmpUtil.toDateStr(saleVO.getManufactureDay()) %>
					</td>
				</tr>
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>
				<tr>
					<td width="104" class="ct_write">Detail Information</td>
					<td bgcolor="D6D6D6" width="1"></td>
					<td class="ct_write01">
						<%= EmpUtil.null2str(saleVO.getProdDetail()).replaceAll("\n", "<br>") %>
					</td>
				</tr>
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>
				<tr>
					<td width="104" class="ct_write">Sellable Quantity</td>
					<td bgcolor="D6D6D6" width="1"></td>
					<td class="ct_write01">
						<%= EmpUtil.toAmountStr(saleVO.getSellQuantity()) %>
					</td>
				</tr>
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>
				<tr>
					<td width="104" class="ct_write">Price</td>
					<td bgcolor="D6D6D6" width="1"></td>
					<td class="ct_write01">
						<%= EmpUtil.toAmountStr(saleVO.getSellAmount()) %>
					</td>
				</tr>
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>
				<tr>
					<td width="104" class="ct_write">A/S</td>
					<td bgcolor="D6D6D6" width="1"></td>
					<td class="ct_write01">
						<%= EmpUtil.null2str(saleVO.getAsYn()) %>
					</td>
				</tr>
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>
			</table>
		</td>
			
		<td width="300">
			<div style="width:300px;overflow:hidden">
				<img src="<%= EmpUtil.null2str(saleVO.getImageFile()) %>" alt="Product Image" border="0" onError="this.width=0;" onLoad="resizeImgWidth(this,250)">
			</div>
		</td>
	</tr>
	
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:13px;">
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">Buyer</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<%= EmpUtil.null2str(saleVO.getBuyerId()) %> <%= EmpUtil.null2str(saleVO.getBuyerName()) %>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">Receiver</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<%= EmpUtil.null2str(saleVO.getReceiverId()) %> <%= EmpUtil.null2str(saleVO.getReceiverName()) %>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">Telephone</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<%= EmpUtil.null2str(saleVO.getReceiverPhone()) %>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">Hope Date for Delivery</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<%= EmpUtil.toDateStr(saleVO.getDlvyExpectDay()) %>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">Delivery Company</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<%= EmpUtil.null2str(saleVO.getDlvyCompName()) %>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">Matters to be attended to delivery</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<%= EmpUtil.null2str(saleVO.getDlvyRequest()).replaceAll("\n", "<br>") %>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">Ways of Approval</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<%= EmpUtil.null2str(saleVO.getPaymentOptionName()) %>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">Receipt</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<%= EmpUtil.null2str(saleVO.getReceiptYn()) %>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
</table>
<!-- selected prodNo -->
<input type="hidden" name="prodNo" value="<%= EmpUtil.null2str(saleVO.getProdNo()) %>" >
<input type="hidden" name="prodName" value="<%= EmpUtil.null2str(saleVO.getProdName()) %>" >

<!-- search condition -->
<input type="hidden" name="searchTranStatusCode" value="${searchVO.searchTranStatusCode}">
<input type="hidden" name="pageIndex" value="${searchVO.pageIndex}">
<input type="hidden" name="searchCondition" value="${searchVO.searchCondition}">
<input type="hidden" name="searchKeyword" value="${searchVO.searchKeyword}">
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
				
			<% // only in case add product, enables update button
			   if (EmpUtil.null2str(saleVO.getTranStatusCode()).equals("001")) { 
			%>
					<td width="17" height="23"><img src="<c:url value='/sample/images/ct_btnbg01.gif'/>" width="17" height="23"></td>
					<td background="<c:url value='/sample/images/ct_btnbg02.gif'/>" class="ct_btn01" style="padding-top:3px;"><a href="javascript:fncUpdateProduct();">Update</a></td>
					<td width="14" height="23"><img src="<c:url value='/sample/images/ct_btnbg03.gif'/>" width="14" height="23"></td>

					<td width="30"></td>	
			<% } %>

			<% // only in case accept order, enables delivery button
   			   if (EmpUtil.null2str(saleVO.getTranStatusCode()).equals("002")) { 
   			%>
					<td width="17" height="23"><img src="<c:url value='/sample/images/ct_btnbg01.gif'/>" width="17" height="23"></td>
					<td background="<c:url value='/sample/images/ct_btnbg02.gif'/>" class="ct_btn01" style="padding-top:3px;"><a href="javascript:fncUpdateTranStatusCode();">Delivery</a></td>
					<td width="14" height="23"><img src="<c:url value='/sample/images/ct_btnbg03.gif'/>" width="14" height="23"></td>

					<td width="30"></td>							
			<% } %>
					<td width="17" height="23"><img src="<c:url value='/sample/images/ct_btnbg01.gif'/>" width="17" height="23"></td>
					<td background="<c:url value='/sample/images/ct_btnbg02.gif'/>" class="ct_btn01" style="padding-top:3px;"><a href="javascript:resetData();">Cancel</a></td>
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
