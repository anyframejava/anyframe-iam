<%@ include file="/sample/common/taglibs.jsp"%>
<html>   
<head>
    <%@ include file="/sample/common/meta.jsp" %>
    <title><fmt:message key="usersDetail.title"/></title>
    <meta name="heading" content="<fmt:message key='usersDetail.heading'/>"/>   
	<link rel="stylesheet" href="<c:url value='/sample/css/admin.css'/>" type="text/css">        
    <script type="text/javascript" src="<c:url value='/sample/javascript/global.js'/>"></script>	
    <script type="text/javascript" src="<c:url value='/sample/javascript/prototype.js'/>"></script>    
	<script type="text/javascript" src="<c:url value='/sample/javascript/CommonScript.js'/>"></script>
	<script type="text/javascript">
	<!--
	function fncAddUsers() {
		// Form validation
		if(FormValidation(document.usersForm) != false) {		    
		    document.usersForm.action="<c:url value='/users.do?method=add'/>";
		    document.usersForm.submit();
		} 
	}
	
	function fncUpdateUsers() {
		// Form validation
		if(FormValidation(document.usersForm) != false) {
		    document.usersForm.action="<c:url value='/users.do?method=update'/>";
		    document.usersForm.submit();
		}
	}
	
	function fncDeleteUsers(){	
		if(confirmDelete('users')) {
		    document.usersForm.action="<c:url value='/users.do?method=delete'/>";
		    document.usersForm.submit();
		}	    
	}
	function resetData() {
		document.usersForm.reset();
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

				 	<c:if test="${empty users.userId}">
				 	Add Users Information
				 	<c:set var="readonly" value="false"/>
					</c:if>
			
				    <c:if test="${not empty users.userId}">	
					Update Users Information
					<c:set var="readonly" value="true"/>				 
					</c:if>					 				 
				</td>
			</tr>
		</table>
		</td>
		<td width="12" height="37"><img	src="<c:url value='/sample/images/ct_ttl_img03.gif'/>" width="12" height="37"></td>
	</tr>
</table>

<form:form commandName="users" method="post" action="users.do" id="usersForm" name="usersForm">
<form:errors path="*" cssClass="error" element="div"/>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 13px;">

		<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
		<tr>		
			<td width="104" class="ct_td"><anyframe:message code="users.userId"/></td><td bgcolor="D6D6D6" width="1"></td>
			<td class="ct_write01">
				<form:input path="userId" id="userId" cssClass="ct_input_g" cssErrorClass="text medium error" readonly="${readonly}"/>
			*
			</td>			
		</tr>
		<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
		<tr>
			<td width="104" class="ct_td">
			
			<anyframe:message code="users.createDate"/></td><td bgcolor="D6D6D6" width="1"></td>
			<td class="ct_write01">									        
	        <form:input path="createDate" id="createDate" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="8"/>
			</td>
		</tr>
		<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
		<tr>
			<td width="104" class="ct_td">
			
			<anyframe:message code="users.enabled"/></td><td bgcolor="D6D6D6" width="1"></td>
			<td class="ct_write01">									        
	        <form:input path="enabled" id="enabled" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="1"/>
			</td>
		</tr>
		<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
		<tr>
			<td width="104" class="ct_td">
			
			<anyframe:message code="users.modifyDate"/></td><td bgcolor="D6D6D6" width="1"></td>
			<td class="ct_write01">									        
	        <form:input path="modifyDate" id="modifyDate" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="8"/>
			</td>
		</tr>
		<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
		<tr>
			<td width="104" class="ct_td">
			
			<anyframe:message code="users.password"/></td><td bgcolor="D6D6D6" width="1"></td>
			<td class="ct_write01">									        
	        <form:input path="password" id="password" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="50"/>
	        *
			</td>
		</tr>
		<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
		<tr>
			<td width="104" class="ct_td">
			
			<anyframe:message code="users.userName"/></td><td bgcolor="D6D6D6" width="1"></td>
			<td class="ct_write01">									        
	        <form:input path="userName" id="userName" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="50"/>
	        *
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
					<c:if test="${empty users.userId}">	
						<td width="17" height="23"><img src="<c:url value='/sample/images/ct_btnbg01.gif'/>" width="17" height="23"></td>
						<td background="<c:url value='/sample/images/ct_btnbg02.gif'/>" class="ct_btn01" style="padding-top:3px;"><a href="javascript:fncAddUsers();"><fmt:message key="button.add"/></a></td>
						<td width="14" height="23"><img src="<c:url value='/sample/images/ct_btnbg03.gif'/>" width="14" height="23"></td>
						<td width="30"></td>					
        			</c:if>
        					
					<c:if test="${not empty users.userId}">
						<td width="17" height="23"><img src="<c:url value='/sample/images/ct_btnbg01.gif'/>" width="17" height="23"></td>
						<td background="<c:url value='/sample/images/ct_btnbg02.gif'/>" class="ct_btn01" style="padding-top:3px;"><a href="javascript:fncUpdateUsers();"><fmt:message key="button.update"/></a></td>
						<td width="14" height="23"><img src="<c:url value='/sample/images/ct_btnbg03.gif'/>" width="14" height="23"></td>					
						<td width="30"></td>
						
						<td width="17" height="23"><img src="<c:url value='/sample/images/ct_btnbg01.gif'/>" width="17" height="23"></td>
						<td background="<c:url value='/sample/images/ct_btnbg02.gif'/>" class="ct_btn01" style="padding-top:3px;"><a href="javascript:fncDeleteUsers();"><fmt:message key="button.delete"/></a></td>
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

</body>
</html>