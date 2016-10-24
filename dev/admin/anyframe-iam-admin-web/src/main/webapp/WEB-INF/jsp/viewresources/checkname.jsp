<%@ taglib uri='http://www.anyframejava.org/tags' prefix='anyframe'%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Check View Name</title>

<jsp:include page="/common/jquery-include.jsp" />

<script type="text/javascript">
	function closeWindow() {
		var isAvailable = document.checkNameForm.isAvailable;
		var viewName = document.checkNameForm.viewName;
		if (isAvailable.value == "false") {
			window.close();
		} else {
			opener.setViewName(viewName.value);
			window.close();
		}
	}

	function checkName() {
		var viewName = document.checkNameForm.viewName;
		url = "<c:url value='/viewresources/duplicationNameConfirm.do?'/>";
		document.checkNameForm.action = url;
		document.checkNameForm.submit();
	}

	function nameChanged(){
		var isAvailable = document.checkNameForm.isAvailable;
		isAvailable.value = "false";
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

<!-- begin of title -->
<table width="300" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td align="center" valign="top">
		<table width="285" height="44" border="0" align="center"
			cellpadding="0" cellspacing="0">
			<tr>
				<td height="44" align="left" class="checkidtitle"
					style="padding-left: 38px">View Name Check</td>
			</tr>
		</table>
		</td>
	</tr>

	<tr>
		<td align="center" valign="top" class="sky_h3"><!-- begin of title -->
		<c:set var="viewName" value="${viewName}" />
<!-- End of Title -->

<form name="checkNameForm">
<table width="285" border="0" align="center" cellpadding="0"
	cellspacing="0" style="margin-top: 13px;">
	<tr>
		<td height="2" colspan="3" bgcolor="#A2BACE"></td>
	</tr>
	<tr height="30">
		<td width="104" class="tdHead">View Name</td>
		<td width="1" height="45" bgcolor="#D6D6D6"></td>
		<td height="45" class="tdin"><input
			style="width: 90%; height: 15px; padding-top: 5px" class="ct_input_g"
			type="text" name="viewName" value="<c:out value='${viewName }' />"
			maxlength="20" onchange="javascript:nameChanged();"></td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="#B6CDE4"></td>
	</tr>
</table>
<br>
<c:if test="${exist == true}">
	<font color="#FF6E86">View Name Not Available</font>
	<input type="hidden" name="isAvailable" value="false">
</c:if> <c:if test="${exist == false}">
	View Name Available
	<input type="hidden" name="isAvailable" value="true">
</c:if>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" style="margin-top: 23px;">
	<tr>
		<td colspan="3" align="right">
		<table height="22" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="18"><img
					src="<c:url value='/images/btn/btn_check.gif'/>" width="18"
					height="22"></td>
				<td background="<c:url value='/images/btn/bg_btn.gif'/>"
					class="boldBtn"><a href="javascript:checkName();"
					name="checkName">Check Name</a></td>
				<td width="10" align="right"><img
					src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10"
					height="22"></td>
			</tr>
		</table>
		</td>
		<td colspan="3" align="left" style="padding-left: 3px">
		<table height="22" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="18"><img
					src="<c:url value='/images/btn/btn_save.gif'/>" width="18"
					height="22"></td>
				<td background="<c:url value='/images/btn/bg_btn.gif'/>"
					class="boldBtn"><a href="javascript:closeWindow();"
					name="closeWindow"><anyframe:message code='user.ui.link.save' /></a></td>
				<td width="10" align="right"><img
					src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10"
					height="22"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>