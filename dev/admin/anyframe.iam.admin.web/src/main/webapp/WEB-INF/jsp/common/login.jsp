<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<html>
<head>
<title>Login</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="<c:url value='/css/admin.css'/>" type="text/css">

<script language="javascript" src="<c:url value='/js/CommonScript.js'/>"></script>
<script language="JavaScript">
function fncLogin() {
	if(FormValidation(document.loginForm) != false) {
	    document.loginForm.action="<c:url value='/j_spring_security_check'/>";
	    document.loginForm.flag.value = "L";
	    document.loginForm.submit();
    }
}
</script>
<style type="text/css">
<!--
body {
	background-color: #FFFFFF;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
</head>

<body>
<div align="center">
	<table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr><td height="5%" width="35"></td></tr>
		<!-- begin of title -->    
		<tr>
      		<td height="95%" align="center" valign="middle">
      			<form name="loginForm"  action="<c:url value='/j_spring_security_check'/>">
		  			<div id="wrapper" align="center">
			  			<div id="main">
						  	<div id="main_img"></div>
						    <table width="598" height="90" border="0" align="center" cellpadding="0" cellspacing="0">
						    	<tr>
						        	<td height="20" colspan="5" style="padding-left:34">&nbsp;</td>
						      	</tr>
						      	<tr>
						        	<td valign="top" style="padding-left:34"><img src="<c:url value='/images/text_login2.gif'/>" width="85" height="23"></td>
						            	<td width="174" align="center" class="blue_h3">IAM에 오신것을 환영합니다!</td>
						            	<td>
						            		<input type='text' name='j_username' <c:if test="${not empty param.login_error}">value='<c:out value="${SPRING_SECURITY_LAST_USERNAME}"/>'</c:if>
						            			class='ct_input_g' style='width:100px; height:19px' required fieldTitle='ID' maxLength='50'>
											<input type="hidden" name="flag">
						            	</td>
						            <td width="104"><input type="password" name="j_password" class="ct_input_g" style="width:100px; height:19px" required fieldTitle="Password" maxLength="50" onKeyPress="if(event.keyCode==13) fncLogin();"></td>
						            <td width="92" style="padding-left:2"><a href="javascript:fncLogin();"><img src="<c:url value='/images/btn_login.gif'/>" width="46" height="18" border="0"></a></td>
								</tr>
								<tr>
						        	<td height="42" colspan="5" valign="top" class="pink_h3" style="padding-left:298">
							        	<c:if test="${not empty param.login_error}">  Your login attempt was not successful, try again.<BR>Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
											<%/*=((AuthenticationException) session.getAttribute(AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY)).getMessage() */%>
							          	</c:if>
						        	</td>
						      	</tr>
							</table>
						</div>
					</div>
					<div id="foot"></div>
  				</form>
  			</td>
    	</tr>
		<!-- end of title -->    
	</table>
</div>
</body>
</html>
<script language="JavaScript">
<!--
	document.loginForm.j_username.focus();
-->
</script>
