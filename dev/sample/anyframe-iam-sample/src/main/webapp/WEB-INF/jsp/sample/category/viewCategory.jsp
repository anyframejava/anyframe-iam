<%@ include file="/sample/common/taglibs.jsp"%>
<html>   
<head>
    <%@ include file="/sample/common/meta.jsp" %>
    <title><fmt:message key="categoryDetail.title"/></title>
    <meta name="heading" content="<fmt:message key='categoryDetail.heading'/>"/>   
	<link rel="stylesheet" href="<c:url value='/sample/css/admin.css'/>" type="text/css">        
    <script type="text/javascript" src="<c:url value='/sample/javascript/global.js'/>"></script>	
    <script type="text/javascript" src="<c:url value='/sample/javascript/prototype.js'/>"></script>    
	<script type="text/javascript" src="<c:url value='/sample/javascript/CommonScript.js'/>"></script>
	<script type="text/javascript">
	<!--
	function fncAddCategory() {
		// Form validation
		if(FormValidation(document.categoryForm) != false) {		    
            setDefaultDate(document.categoryForm.modifyDate);   
            setDefaultDate(document.categoryForm.regDate);   
		    document.categoryForm.action="<c:url value='/category.do?method=add'/>";
		    document.categoryForm.submit();
		} 
	}
	
	function fncUpdateCategory() {
		// Form validation
		if(FormValidation(document.categoryForm) != false) {
            setDefaultDate(document.categoryForm.modifyDate);   
            setDefaultDate(document.categoryForm.regDate);   
		    document.categoryForm.action="<c:url value='/category.do?method=update'/>";
		    document.categoryForm.submit();
		} 
	}
	
	function fncDeleteCategory(){	
		if(confirmDelete('category')) {
		    document.categoryForm.action="<c:url value='/category.do?method=delete'/>";
		    document.categoryForm.submit();
		}	    
	}
	
	function resetData() {
		document.categoryForm.reset();
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

				 	<c:if test="${empty category.categoryNo}">
				 	Add Category Information
				 	<c:set var="readonly" value="false"/>
					</c:if>
			
				    <c:if test="${not empty category.categoryNo}">	
					Update Category Information
					<c:set var="readonly" value="true"/>				 
					</c:if>					 				 
				</td>
			</tr>
		</table>
		</td>
		<td width="12" height="37"><img	src="<c:url value='/sample/images/ct_ttl_img03.gif'/>" width="12" height="37"></td>
	</tr>
</table>

<form:form commandName="category" method="post" action="category.do" id="categoryForm" name="categoryForm">
<form:errors path="*" cssClass="error" element="div"/>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 13px;">

		<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
		<tr>		
			<td width="104" class="ct_td"><anyframe:message code="category.categoryNo"/></td><td bgcolor="D6D6D6" width="1"></td>
			<td class="ct_write01">
				<form:input path="categoryNo" id="categoryNo" cssClass="ct_input_g" cssErrorClass="text medium error" readonly="${readonly}"/>
			*
			</td>			
		</tr>
		<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
		<tr>
			<td width="104" class="ct_td">
			
			<anyframe:message code="category.categoryDesc"/></td><td bgcolor="D6D6D6" width="1"></td>
			<td class="ct_write01">									        
	        <form:input path="categoryDesc" id="categoryDesc" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="100"/>
			</td>
		</tr>
		<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
		<tr>
			<td width="104" class="ct_td">
			
			<anyframe:message code="category.categoryName"/></td><td bgcolor="D6D6D6" width="1"></td>
			<td class="ct_write01">									        
	        <form:input path="categoryName" id="categoryName" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="50"/>
	        *
			</td>
		</tr>
		<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
		<tr>
			<td width="104" class="ct_td">
			
			<anyframe:message code="category.modifyDate"/></td><td bgcolor="D6D6D6" width="1"></td>
			<td class="ct_write01">									        
	        <form:input path="modifyDate" id="modifyDate" cssClass="ct_input_g" size="11"/>
	        <a href="javascript:show_calendar(document.categoryForm.rootPath.value,'document.categoryForm.modifyDate', document.categoryForm.modifyDate.value)" id="iconCalendarmodifyDate"><img src="<c:url value='/sample/images/ct_icon_date.gif'/>" width="16" height="18" border="0" align="middle"></a>
			</td>
		</tr>
		<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
		<tr>
			<td width="104" class="ct_td">
			
			<anyframe:message code="category.modifyId"/></td><td bgcolor="D6D6D6" width="1"></td>
			<td class="ct_write01">									        
	        <form:input path="modifyId" id="modifyId" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="20"/>
			</td>
		</tr>
		<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
		<tr>
			<td width="104" class="ct_td">
			
			<anyframe:message code="category.regDate"/></td><td bgcolor="D6D6D6" width="1"></td>
			<td class="ct_write01">									        
	        <form:input path="regDate" id="regDate" cssClass="ct_input_g" size="11"/>
	        <a href="javascript:show_calendar(document.categoryForm.rootPath.value,'document.categoryForm.regDate', document.categoryForm.regDate.value)" id="iconCalendarregDate"><img src="<c:url value='/sample/images/ct_icon_date.gif'/>" width="16" height="18" border="0" align="middle"></a>
			</td>
		</tr>
		<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
		<tr>
			<td width="104" class="ct_td">
			
			<anyframe:message code="category.regId"/></td><td bgcolor="D6D6D6" width="1"></td>
			<td class="ct_write01">									        
	        <form:input path="regId" id="regId" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="20"/>
			</td>
		</tr>
		<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
		<tr>
			<td width="104" class="ct_td">
			
			<anyframe:message code="category.useYn"/></td><td bgcolor="D6D6D6" width="1"></td>
			<td class="ct_write01">									        
	        <form:input path="useYn" id="useYn" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="1"/>
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
					<c:if test="${empty category.categoryNo}">	
						<td width="17" height="23"><img src="<c:url value='/sample/images/ct_btnbg01.gif'/>" width="17" height="23"></td>
						<td background="<c:url value='/sample/images/ct_btnbg02.gif'/>" class="ct_btn01" style="padding-top:3px;"><a href="javascript:fncAddCategory();"><fmt:message key="button.add"/></a></td>
						<td width="14" height="23"><img src="<c:url value='/sample/images/ct_btnbg03.gif'/>" width="14" height="23"></td>
						<td width="30"></td>					
        			</c:if>
        					
					<c:if test="${not empty category.categoryNo}">
						<td width="17" height="23"><img src="<c:url value='/sample/images/ct_btnbg01.gif'/>" width="17" height="23"></td>
						<td background="<c:url value='/sample/images/ct_btnbg02.gif'/>" class="ct_btn01" style="padding-top:3px;"><a href="javascript:fncUpdateCategory();"><fmt:message key="button.update"/></a></td>
						<td width="14" height="23"><img src="<c:url value='/sample/images/ct_btnbg03.gif'/>" width="14" height="23"></td>					
						<td width="30"></td>
						
						<td width="17" height="23"><img src="<c:url value='/sample/images/ct_btnbg01.gif'/>" width="17" height="23"></td>
						<td background="<c:url value='/sample/images/ct_btnbg02.gif'/>" class="ct_btn01" style="padding-top:3px;"><a href="javascript:fncDeleteCategory();"><fmt:message key="button.delete"/></a></td>
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