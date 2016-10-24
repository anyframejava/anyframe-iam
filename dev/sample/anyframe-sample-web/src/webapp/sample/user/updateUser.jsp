<%@ page contentType="text/html; charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.lang.String" %> 
<%@ page import="com.sds.emp.common.EmpUtil" %>
<%@ page import="com.sds.emp.user.services.UserVO" %>
<%@ page import="com.sds.emp.code.services.CodeVO" %>

<%
	UserVO userVO = (UserVO)request.getAttribute("userVO");
	if (userVO == null) userVO = new UserVO();
	//ArrayList cellPhoneCodeList = (ArrayList)request.getAttribute("cellPhoneCodeList");
	//if (cellPhoneCodeList == null) cellPhoneCodeList = new ArrayList();
	//ArrayList emailCodeList = (ArrayList)request.getAttribute("emailCodeList");
	//if (emailCodeList == null) emailCodeList = new ArrayList();
	
	String flag = (String)request.getAttribute("flag");
	if (flag == null) flag = "user";
%>
<html>
<head>
<title>Update User Information</title>
<meta http-equiv="content-type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="<c:url value='/sample/css/admin.css'/>" type="text/css">

<script language="javascript" src="<c:url value='/sample/javascript/calendar.js'/>"></script>
<script language="javascript" src="<c:url value='/sample/javascript/CommonScript.js'/>"></script>
<script language="JavaScript">
<!--
function fncUpdateUser() {
	// Form validation
	if(FormValidation(document.detailForm) != false) {
		
	    document.detailForm.action="<c:url value='/empupdateuser.do'/>";
	    //document.detailForm.action="<c:url value='/empuser.do?act=updateUser'/>";
	    document.detailForm.submit();
	} 
}

function resetData() {
	document.detailForm.reset();
}
-->
</script>


</head>

<body bgcolor="#ffffff" text="#000000">

<form name="detailForm"  method="post">
<input type="hidden" name="name" value="abc"/>

<table width="100%" height="37" border="0" cellpadding="0"
	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="<c:url value='/sample/images/ct_ttl_img01.gif'/>" width="15" height="37"></td>
		<td background="<c:url value='/sample/images/ct_ttl_img02.gif'/>" width="100%" style="padding-left:10px;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="93%" class="ct_ttl01">Update User Information</td>
				<td width="20%" align="right">&nbsp;</td>
			</tr>
		</table>
		</td>
		<td width="12" height="37"><img src="<c:url value='/sample/images/ct_ttl_img03.gif'/>" width="12" height="37"></td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:13px;">
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">ID <img src="<c:url value='/sample/images/ct_icon_red.gif'/>" width="3" height="3" align="absmiddle"></td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<input type="text" name="userId" class="ct_input_bg" style="width:100px; height:19px" value="<%= userVO.getUserId() %>" readonly required fieldTitle="ID" maxLength="20" char="s">
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">Name <img src="<c:url value='/sample/images/ct_icon_red.gif'/>" width="3" height="3" align="absmiddle"></td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<input type="text" name="userName" class="ct_input_g" style="width:100px; height:19px" value="<%= userVO.getUserName() %>" required fieldTitle="Name" maxLength="50" char="sn">
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
</table>

<!-- remain search condition of search UI previously-->
<input type="hidden" name="pageIndex" value="${searchVO.pageIndex}">
<input type="hidden" name="searchCondition" value="${searchVO.searchCondition}">
<input type="hidden" name="searchKeyword" value="${searchVO.searchKeyword}">


<input type="hidden" name="flag" value="<%= flag  %>">
 

<table width="100%" border="0" cellspacing="0" cellpadding="0"
	style="margin-top:10px;">
	<tr>
		<td width="53%">
		
		</td>
		<td align="right">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23"><img src="<c:url value='/sample/images/ct_btnbg01.gif'/>" width="17" height="23"></td>
					<td background="<c:url value='/sample/images/ct_btnbg02.gif'/>" class="ct_btn01" style="padding-top:3px;"><a href="javascript:fncUpdateUser();">Update</a></td>
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
<input type="hidden" name="rootPath" value="<c:url value='/'/>"/>
</form>

</body>
</html>
