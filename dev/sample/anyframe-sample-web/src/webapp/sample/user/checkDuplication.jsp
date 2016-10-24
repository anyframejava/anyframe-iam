<%@ page contentType="text/html; charset=euc-kr"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.sds.emp.user.services.UserVO"%>
<%@ page import="com.sds.emp.common.EmpUtil"%>
<%
	String displayResult = "";
	UserVO userVO = (UserVO) request.getAttribute("userVO");
	if (userVO == null)
		userVO = new UserVO();
	Boolean duplicated = (Boolean)request.getAttribute("duplicated");
	
	if (duplicated != null) {
		if (duplicated.booleanValue()) {
			displayResult = "this ID is already used.";
		} else {
			displayResult = "You can use this ID.";
		}
	}
%>

<html>
<head>
<title>checkDuplication</title>
<meta http-equiv="content-type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="<c:url value='/sample/css/admin.css'/>"
	type="text/css">

<script language="JavaScript">

</script>
<script language="javascript"
	src="<c:url value='/sample/javascript/CommonScript.js'/>"></script>
<script language="JavaScript">
<!--
function fncCheckDuplication() {
	// Form validation
	if(FormValidation(document.detailForm) != false) {
		
	    document.detailForm.action="<c:url value='/empcheckduplication.do'/>";
	    document.detailForm.submit();
	} 
}

function fncUseId() {
	if(opener) {
		opener.document.detailForm.userId.value = "<%=userVO.getUserId()%>";
	}
	
	window.close();
}
-->
</script>


</head>

<body bgcolor="#ffffff" text="#000000">
<form name="detailForm" method="post"><input type="hidden"
	name="name" value="abc" /> <!--************************** begin of contents *****************************-->

<table width="100%" height="37" border="0" cellpadding="0"
	cellspacing="0">
	<tr>
		<td width="15" height="37"><img
			src="<c:url value='/sample/images/ct_ttl_img01.gif'/>" width="15"
			height="37"></td>
		<td background="<c:url value='/sample/images/ct_ttl_img02.gif'/>"
			width="100%" style="padding-left: 10px;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="93%" class="ct_ttl01">Check ID Duplication</td>
				<td width="20%" align="right">&nbsp;</td>
			</tr>
		</table>
		</td>
		<td width="12" height="37"><img
			src="<c:url value='/sample/images/ct_ttl_img03.gif'/>" width="12"
			height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="32" style="padding-left: 12px;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			style="margin-top: 3px;">
			<tr>
				<td width="8" style="padding-bottom: 3px;"><img
					src="<c:url value='/sample/images/ct_bot_ttl01.gif'/>" width="4"
					height="7"></td>
				<td class="ct_ttl02"><%=displayResult%></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td background="<htm:rewrite page='/sample/images/ct_line_ttl.gif'/>"
			height="1"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"
	style="margin-top: 13px;">
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">ID <img
			src="<c:url value='/sample/images/ct_icon_red.gif'/>" width="3"
			height="3" align="absmiddle"></td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="105"><input type="text" name="userId"
					class="ct_input_g" style="width: 100px; height: 19px"
					value="<%=EmpUtil.null2str(userVO.getUserId())%>" required
					fieldTitle="ID" maxLength="20" char="s"></td>
				<td>
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="4" height="21"><img
							src="<c:url value='/sample/images/ct_btng01.gif'/>" width="4"
							height="21"></td>
						<td align="center"
							background="<c:url value='/sample/images/ct_btng02.gif'/>"
							class="ct_btn" style="padding-top: 3px;"><a
							href="javascript:fncCheckDuplication();">Check ID</a></td>
						<td width="4" height="21"><img
							src="<c:url value='/sample/images/ct_btng03.gif'/>" width="4"
							height="21"></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"
	style="margin-top: 10px;">
	<tr>
		<td align="center">
		<table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<%
				if(duplicated!=null){
					if (!duplicated.booleanValue()) {
				%>
				<td width="17" height="23"><img
					src="<c:url value='/sample/images/ct_btnbg01.gif'/>" width="17"
					height="23"></td>
				<td background="<c:url value='/sample/images/ct_btnbg02.gif'/>"
					class="ct_btn01" style="padding-top: 3px;"><a
					href="javascript:fncUseId();">Use</a></td>
				<td width="14" height="23"><img
					src="<c:url value='/sample/images/ct_btnbg03.gif'/>" width="14"
					height="23"></td>
				<td width="30"></td>
				<%
					}
				%>
				<td width="17" height="23"><img
					src="<c:url value='/sample/images/ct_btnbg01.gif'/>" width="17"
					height="23"></td>
				<td background="<c:url value='/sample/images/ct_btnbg02.gif'/>"
					class="ct_btn01" style="padding-top: 3px;"><a
					href="javascript:window.close();">Close</a></td>
				<td width="14" height="23"><img
					src="<c:url value='/sample/images/ct_btnbg03.gif'/>" width="14"
					height="23"></td>
				<%
					}
				%>
			</tr>
		</table>
		</td>
	</tr>
</table>

</form>

</body>
</html>