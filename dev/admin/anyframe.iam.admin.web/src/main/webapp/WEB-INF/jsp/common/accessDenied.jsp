<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Error</title>
<link rel="stylesheet" href="<c:url value='/css/admin.css'/>" type="text/css">
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style></head>

<body bgcolor="#ffffff" text="#000000">

<!-- START : Title -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 13px;">
	<tr>
		<td class="errortitle" style="padding-left:28px">Ocurred Error!!!</td>
	</tr>
</table>
<!-- END : Title -->
<form name="dummyForm">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td valign="top" style="padding-top:15px">
			<table width="517" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td id="error" style="padding-left:89px" class="blue_h2">Occurred Error! </td>
				</tr>
				<tr>
					<td height="15" background="<c:url value='/images/box_top.gif'/>">&nbsp;</td>
				</tr>
				<tr>
					<td height="14" align="center" background="<c:url value='/images/box_bg.gif'/>" class="boxpadding">Access is Denied!</td>
				</tr>
				<tr>
					<td height="31" background="<c:url value='/images/box_bttm.gif'/>">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</form> 
</body>
</html>
