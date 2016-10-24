<%@ page contentType="text/html; charset=euc-kr" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.sds.emp.common.EmpUtil" %>
<%@ page import="com.sds.emp.purchase.services.DeliveryCompanyVO" %>
<%@ page import="com.sds.emp.sale.services.ProductVO" %>
<%@ page import="com.sds.emp.sale.services.SearchVO" %>
<%@ page import="com.sds.emp.code.services.CodeVO" %>

<%
	ProductVO productVO = (ProductVO)request.getAttribute("productVO");
	SearchVO searchVO = (SearchVO)request.getAttribute("searchVO");	

	ArrayList deliveryCompanyList = (ArrayList)request.getAttribute("deliveryCompanyList");
	if (deliveryCompanyList == null) deliveryCompanyList = new ArrayList();
	
	ArrayList paymentOptionList = (ArrayList)request.getAttribute("paymentOptionList");
	if (paymentOptionList == null) paymentOptionList = new ArrayList();
	
%>
 

<html>
<head>
<title>Register Purchase</title>
<meta http-equiv="content-type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="<c:url value='/sample/css/admin.css'/>" type="text/css">

<script language="javascript" src="<c:url value='/sample/javascript/calendar.js'/>"></script>
<script language="javascript" src="<c:url value='/sample/javascript/CommonScript.js'/>"></script>
<script language="JavaScript">
<!--
function fncAddPurchase() {
	// Form Validation
	if(FormValidation(document.detailForm) != false) {		
	    document.detailForm.action="<c:url value='/empaddpurchase.do'/>";
	    document.detailForm.submit();
	} 
}

function check_receiptYn(checkReceiptYn) {
	if (checkReceiptYn.checked) {
		document.detailForm.receiptYn.value = "Y";
	} else {
		document.detailForm.receiptYn.value = "N";
	}
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

function resetData() {
	document.detailForm.reset();
}
-->
</script>


</head>

<body bgcolor="#ffffff" text="#000000">
 
<form name="detailForm" method="post" enctype="multipart/form-data"> 
<input type="hidden" name="sellerId" value="<%=productVO.getSellerId()%>"/>
<!--************************** begin of contents *****************************-->

<!-- begin of title -->
<table width="100%" height="37" border="0" cellpadding="0"
	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="<c:url value='/sample/images/ct_ttl_img01.gif'/>" width="15" height="37"></td>
		<td background="<c:url value='/sample/images/ct_ttl_img02.gif'/>" width="100%" style="padding-left:10px;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="93%" class="ct_ttl01">Register Purchase</td>
				<td width="20%" align="right">&nbsp;</td>
			</tr>
		</table>
		</td>
		<td width="12" height="37"><img src="<c:url value='/sample/images/ct_ttl_img03.gif'/>" width="12" height="37"></td>
	</tr>
</table>
<!-- end of title -->

<!-- detail information of product -->
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
						<%= EmpUtil.null2str(productVO.getProdNo()) %>
						<input type="hidden" name="prodNo" value="<%=productVO.getProdNo()%>">
					</td>
				</tr>
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>
				<tr>
					<td width="104" class="ct_write">Product Name</td>
					<td bgcolor="D6D6D6" width="1"></td>
					<td class="ct_write01" colspan="2">
						<%= EmpUtil.null2str(productVO.getProdName()) %>
					</td>
				</tr>
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>
				<tr>
					<td width="104" class="ct_write">Product Classification</td>
					<td bgcolor="D6D6D6" width="1"></td>
					<td class="ct_write01">
						<%= EmpUtil.null2str(productVO.getCategoryNo()) %>
					</td>
				</tr>
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>
				<tr>
					<td width="104" class="ct_write">Manufactured Date</td>
					<td bgcolor="D6D6D6" width="1"></td>
					<td class="ct_write01">
						<%= EmpUtil.toDateStr(productVO.getManufactureDay()) %>
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
					    <%= EmpUtil.null2str(productVO.getSellQuantity()) %>
					</td>
				</tr>
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>
				<tr>
					<td width="104" class="ct_write">Price</td>
					<td bgcolor="D6D6D6" width="1"></td>
					<td class="ct_write01">
						<%= EmpUtil.null2str(productVO.getSellAmount()) %>
					</td>
				</tr>
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>
				<tr>
					<td width="104" class="ct_write">A/S</td>
					<td bgcolor="D6D6D6" width="1"></td>
					<td class="ct_write01">
						<%= "Y".equals( EmpUtil.null2str(productVO.getAsYn()) ) ? "Possible" : "Impossible" %>
					</td>
				</tr>
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>
			</table>
		</td>
			
		<td width="300">
			<div style="width:300px;overflow:hidden">
				<img src="<%= EmpUtil.null2str(productVO.getImageFile()) %>" alt="Image of Product" border="0" onError="this.width=0;" onLoad="resizeImgWidth(this,250)">
			</div>
		</td>
	</tr> 
</table>

<!-- begin of table -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:13px;">
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
  	    <td width="104" class="ct_write">Receiver <img src="<c:url value='/sample/images/ct_icon_red.gif'/>" width="3" height="3" align="absmiddle"></td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<input type="text" name="receiverId" class="ct_input_g" style="width:150px; height:19px" fieldTitle="Receiver" required value="" maxLength="20" char="s">
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
  	    <td width="104" class="ct_write">Telephone <img src="<c:url value='/sample/images/ct_icon_red.gif'/>" width="3" height="3" align="absmiddle"></td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<input type="text" name="receiverPhone" class="ct_input_g" style="width:150px; height:19px" fieldTitle="Telephone" required maxLength="13" valCheck="PHONE">
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>	
	<tr>
	<td width="104" class="ct_write">Hope Date for Delivery</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<input type="text" name=dlvyExpectDay class="ct_input_g" style="width:100px; height:19px" value="" fieldTitle="Hope Date for Delivery" maxLength="8" valCheck="DATE" >
			<a href="javascript:show_calendar(document.detailForm.rootPath.value, 'document.detailForm.dlvyExpectDay', document.detailForm.dlvyExpectDay.value)" id="iconCalendar"><img src="<c:url value='/sample/images/ct_icon_date.gif'/>" width="16" height="18" border="0" align="absmiddle"></a>
		</td>
	</tr>	
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
  	    <td width="104" class="ct_write">Address <img src="<c:url value='/sample/images/ct_icon_red.gif'/>" width="3" height="3" align="absmiddle"></td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<input type="text" name="dlvyAddr" class="ct_input_g" style="width:150px; height:19px"  fieldTitle="Address" required  maxLength="100" char="s">
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">Delivery Company <img src="<c:url value='/sample/images/ct_icon_red.gif'/>" width="3" height="3" align="absmiddle"></td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<select name="dlvyCompNo" class="ct_input_g" style="width:100px" required fieldTitle="Delivery Company" >
			<%  for(int i=0; i < deliveryCompanyList.size(); i++) { 
					DeliveryCompanyVO deliveryCompanyVO = (DeliveryCompanyVO)deliveryCompanyList.get(i);
			%>
				<option value="<%= deliveryCompanyVO.getDlvyCompNo() %>" ><%= deliveryCompanyVO.getDlvyCompName() %></option>
			<% } %>
			</select>
		</td>
	</tr>	
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">Matters to be attended to delivery</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<textarea name="dlvyRequest" cols="50" rows="5"></textarea>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
  	    <td width="104" class="ct_write">Ways of Approval </td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<%  for(int i=0; i < paymentOptionList.size(); i++) { 
					CodeVO codeVO = (CodeVO)paymentOptionList.get(i); 
			%>
				<%= codeVO.getCodeName() %><input type="radio" name="paymentOption" value="<%= codeVO.getCode() %>" <%=(i==0)?"checked":""%> %> &nbsp;&nbsp;&nbsp;				
			<% } %>
		
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">Receipt</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01"> 
			<input type="checkbox" name="checkReceiptYn" checked onChange="check_receiptYn(this)"> Issue a Receipt.
			<input type="hidden" name="receiptYn" value="Y">
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
</table>
<!-- end of table -->

<!-- remain search condition of search UI previously-->
<input type="hidden" name="searchCondition" value="<%= EmpUtil.null2str(searchVO.getSearchCondition()) %>">
<input type="hidden" name="searchKeyword" value="<%= EmpUtil.null2str(searchVO.getSearchKeyword()) %>">
<input type="hidden" name="pageIndex" value="<%= searchVO.getPageIndex() %>">
<input type="hidden" name="searchAsYn" value="<%= productVO.getAsYn() %>">

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
					<td background="<c:url value='/sample/images/ct_btnbg02.gif'/>" class="ct_btn01" style="padding-top:3px;"><a href="javascript:fncAddPurchase();">Register</a></td>
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
</form>

</body>
</html>
