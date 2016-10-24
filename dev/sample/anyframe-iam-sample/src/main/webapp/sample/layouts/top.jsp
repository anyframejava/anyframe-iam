<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<title>IAM Sample</title>
	<script type="text/javascript">
	function fncLogOut() {
		if(confirm("Do you want to log out?")) {
			document.logoutForm.target="_top";
			document.logoutForm.action="<c:url value='/j_spring_security_logout'/>";
	    	document.logoutForm.submit();	
    	}
	}
	</script>
	<link href="<c:url value='/sample/css/left.css'/>" rel="stylesheet" type="text/css">
</head>

<body topmargin="0" leftmargin="0">
<form name="logoutForm">
	<input type="hidden" name="loflag" value="LO">
</form>
<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td height="30" bgcolor="#8A8BE3" valign="middle"><center><font face="arial" color="#FFFFFF" size="4">IAM Sample</font></center></td>
	</tr>
	<tr><td height="10" align="right" bgcolor="#8A8BE3"></td></tr>
	<tr>
		<td height="20" align="right" background="<c:url value='/sample/images/img_bg.gif'/>">
			<table width="200" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="56">
						<a href="javascript:fncLogOut();">          
							<img src="<c:url value='/sample/images/btn_logout.gif'/>" width="56" height="16">
						</a>  
          </td>
				</tr>
			</table>
		</td>
	</tr>			
</table>
</body>
</html>
