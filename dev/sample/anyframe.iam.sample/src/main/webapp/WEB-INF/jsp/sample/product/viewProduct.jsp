<%@ include file="/sample/common/taglibs.jsp"%>
<html>   
<head>
    <%@ include file="/sample/common/meta.jsp" %>
    <title><fmt:message key="productDetail.title"/></title>
    <meta name="heading" content="<fmt:message key='productDetail.heading'/>"/>   
	<link rel="stylesheet" href="<c:url value='/sample/css/admin.css'/>" type="text/css">        
    <script type="text/javascript" src="<c:url value='/sample/javascript/global.js'/>"></script>	
    <script type="text/javascript" src="<c:url value='/sample/javascript/prototype.js'/>"></script>    
	<script type="text/javascript" src="<c:url value='/sample/javascript/CommonScript.js'/>"></script>
	<script type="text/javascript">
	<!--
	function fncAddProduct() {
		// Form validation
		if(FormValidation(document.productForm) != false) {		    
            setDefaultDate(document.productForm.regDate);   
		    document.productForm.action="<c:url value='/product.do?method=add'/>";
		    document.productForm.submit();
		} 
	}
	
	function fncUpdateProduct() {
		// Form validation
		if(FormValidation(document.productForm) != false) {
            setDefaultDate(document.productForm.regDate);   
		    document.productForm.action="<c:url value='/product.do?method=update'/>";
		    document.productForm.submit();
		} 
	}
	
	function fncDeleteProduct(){	
		if(confirmDelete('product')) {
		    document.productForm.action="<c:url value='/product.do?method=delete'/>";
		    document.productForm.submit();
		}	    
	}
	
	function resetData() {
		document.productForm.reset();
	}
	-->
	</script>         
</head>

<body bgcolor="#ffffff" text="#000000">
<!--************************** begin of contents *****************************-->

<!-- begin of title -->
<table width="100%" height="37" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="<c:url value='/sample/images/ct_ttl_img01.gif'/>" width="15" height="37"></td>
		<td background="<c:url value='/sample/images/ct_ttl_img02.gif'/>" width="100%" style="padding-left: 10px;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="93%" class="ct_ttl01">		

				 	<c:if test="${empty product.prodNo}">
				 	Add Product Information
				 	<c:set var="readonly" value="false"/>
					</c:if>
			
				    <c:if test="${not empty product.prodNo}">	
					Update Product Information
					<c:set var="readonly" value="true"/>				 
					</c:if>					 				 
				</td>
			</tr>
		</table>
		</td>
		<td width="12" height="37"><img	src="<c:url value='/sample/images/ct_ttl_img03.gif'/>" width="12" height="37"></td>
	</tr>
</table>

<form:form commandName="product" method="post" action="product.do" id="productForm" name="productForm">
<form:errors path="*" cssClass="error" element="div"/>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 13px;">

		<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
		<tr>		
			<td width="104" class="ct_td"><anyframe:message code="product.prodNo"/></td><td bgcolor="D6D6D6" width="1"></td>
			<td class="ct_write01">
				<form:input path="prodNo" id="prodNo" cssClass="ct_input_g" cssErrorClass="text medium error" readonly="${readonly}"/>
			*
			</td>			
		</tr>
		<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
		<tr>
			<td width="104" class="ct_td">
			
			<anyframe:message code="product.asYn"/></td><td bgcolor="D6D6D6" width="1"></td>
			<td class="ct_write01">									        
	        <form:input path="asYn" id="asYn" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="1"/>
			</td>
		</tr>
		<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
		<tr>
			<td width="104" class="ct_td">
			
			<anyframe:message code="product.imageFile"/></td><td bgcolor="D6D6D6" width="1"></td>
			<td class="ct_write01">									        
	        <form:input path="imageFile" id="imageFile" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="100"/>
			</td>
		</tr>
		<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
		<tr>
			<td width="104" class="ct_td">
			
			<anyframe:message code="product.manufactureDay"/></td><td bgcolor="D6D6D6" width="1"></td>
			<td class="ct_write01">									        
	        <form:input path="manufactureDay" id="manufactureDay" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="8"/>
			</td>
		</tr>
		<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
		<tr>
			<td width="104" class="ct_td">
			
			<anyframe:message code="product.prodDetail"/></td><td bgcolor="D6D6D6" width="1"></td>
			<td class="ct_write01">									        
	        <form:input path="prodDetail" id="prodDetail" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="200"/>
			</td>
		</tr>
		<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
		<tr>
			<td width="104" class="ct_td">
			
			<anyframe:message code="product.prodName"/></td><td bgcolor="D6D6D6" width="1"></td>
			<td class="ct_write01">									        
	        <form:input path="prodName" id="prodName" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="100"/>
	        *
			</td>
		</tr>
		<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
		<tr>
			<td width="104" class="ct_td">
			
			<anyframe:message code="product.regDate"/></td><td bgcolor="D6D6D6" width="1"></td>
			<td class="ct_write01">									        
	        <form:input path="regDate" id="regDate" cssClass="ct_input_g" size="11"/>
	        <a href="javascript:show_calendar(document.productForm.rootPath.value,'document.productForm.regDate', document.productForm.regDate.value)" id="iconCalendarregDate"><img src="<c:url value='/sample/images/ct_icon_date.gif'/>" width="16" height="18" border="0" align="middle"></a>
			</td>
		</tr>
		<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
		<tr>
			<td width="104" class="ct_td">
			
			<anyframe:message code="product.sellAmount"/></td><td bgcolor="D6D6D6" width="1"></td>
			<td class="ct_write01">									        
	        <form:input path="sellAmount" id="sellAmount" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="255"/>
			</td>
		</tr>
		<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
		<tr>
			<td width="104" class="ct_td">
			
			<anyframe:message code="product.sellQuantity"/></td><td bgcolor="D6D6D6" width="1"></td>
			<td class="ct_write01">									        
	        <form:input path="sellQuantity" id="sellQuantity" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="255"/>
			</td>
		</tr>
		<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
		<tr>
			<td width="104" class="ct_td">
			
			<anyframe:message code="product.sellerId"/></td><td bgcolor="D6D6D6" width="1"></td>
			<td class="ct_write01">									        
	        <form:input path="sellerId" id="sellerId" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="20"/>
			</td>
		</tr>
	<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>	
</table>

<input type="hidden" name="rootPath" value="<c:url value='/'/>"/>

<!-- begin of button -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	style="margin-top:10px;">
	<tr>
		<td width="53%">
		
		</td>
		<td align="right">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>		
					<c:if test="${empty product.prodNo}">	
						<td width="17" height="23"><img src="<c:url value='/sample/images/ct_btnbg01.gif'/>" width="17" height="23"></td>
						<td background="<c:url value='/sample/images/ct_btnbg02.gif'/>" class="ct_btn01" style="padding-top:3px;"><a href="javascript:fncAddProduct();"><fmt:message key="button.add"/></a></td>
						<td width="14" height="23"><img src="<c:url value='/sample/images/ct_btnbg03.gif'/>" width="14" height="23"></td>
						<td width="30"></td>					
        			</c:if>
        					
					<c:if test="${not empty product.prodNo}">
						<td width="17" height="23"><img src="<c:url value='/sample/images/ct_btnbg01.gif'/>" width="17" height="23"></td>
						<td background="<c:url value='/sample/images/ct_btnbg02.gif'/>" class="ct_btn01" style="padding-top:3px;"><a href="javascript:fncUpdateProduct();"><fmt:message key="button.update"/></a></td>
						<td width="14" height="23"><img src="<c:url value='/sample/images/ct_btnbg03.gif'/>" width="14" height="23"></td>					
						<td width="30"></td>
						
						<td width="17" height="23"><img src="<c:url value='/sample/images/ct_btnbg01.gif'/>" width="17" height="23"></td>
						<td background="<c:url value='/sample/images/ct_btnbg02.gif'/>" class="ct_btn01" style="padding-top:3px;"><a href="javascript:fncDeleteProduct();"><fmt:message key="button.delete"/></a></td>
						<td width="14" height="23"><img src="<c:url value='/sample/images/ct_btnbg03.gif'/>" width="14" height="23"></td>
						<td width="30"></td>
        			</c:if>
													
					<td width="17" height="23"><img src="<c:url value='/sample/images/ct_btnbg01.gif'/>" width="17" height="23"></td>
					<td background="<c:url value='/sample/images/ct_btnbg02.gif'/>" class="ct_btn01" style="padding-top:3px;"><a href="javascript:resetData();"><fmt:message key="button.cancel"/></a></td>
					<td width="14" height="23"><img src="<c:url value='/sample/images/ct_btnbg03.gif'/>" width="14" height="23"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>

</form:form>

	<script language="javascript" src="<c:url value='/sample/javascript/calendar.js'/>"></script>	
</body>
</html>