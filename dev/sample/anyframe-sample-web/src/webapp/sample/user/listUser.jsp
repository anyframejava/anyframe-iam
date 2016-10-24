<%@ page contentType="text/html; charset=euc-kr" %>
<%@ taglib uri='/WEB-INF/anyframe-page.tld' prefix='emp' %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="anyframe.common.Page" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.lang.String" %>
<%@ page import="com.sds.emp.common.EmpUtil" %>
<%@ page import="com.sds.emp.user.services.UserVO" %>
<%@ page import="com.sds.emp.user.services.SearchVO" %>
<%
	Page resultPage = (Page)request.getAttribute("page");
	ArrayList resultList = (ArrayList)resultPage.getList();	
	SearchVO searchVO=(SearchVO)request.getAttribute("searchVO");
	String keyword = EmpUtil.null2str(searchVO.getSearchKeyword());
	String condition = EmpUtil.null2str(searchVO.getSearchCondition());
	
	int pageSize = ((Integer)request.getAttribute("pageSize")).intValue();
%>
<html>
<head>
<title>Search List of User</title>
<meta http-equiv="content-type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="<c:url value='/sample/css/admin.css'/>" type="text/css">

<script language="javascript" src="<c:url value='/sample/javascript/CommonScript.js'/>"></script>
<script language="JavaScript">
<!--
function fncAddUserView() {
    document.listForm.action="<c:url value='/empadduser.do'/>";
    document.listForm.submit();
}

function fncGetUserList(arg) {

	// Form validation
	if(FormValidation(document.listForm) != false) {
		if(arg == "1")
			document.listForm.pageIndex.value = 1;
	   	document.listForm.action="<c:url value='/emplistuser.do'/>";
	   	document.listForm.submit();		
	}
}

function fncGetUser(userId) {
	document.listForm.userId.value = userId;
   	document.listForm.action="<c:url value='/empgetadminuser.do'/>";
   	document.listForm.submit();		
}
-->
</script>

</head>

<body bgcolor="#ffffff" text="#000000">

<form name="listForm" action="<c:url value='/emplistuser.do'/>">


<table width="100%" height="37" border="0" cellpadding="0"
	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="<c:url value='/sample/images/ct_ttl_img01.gif'/>" width="15" height="37"></td>
		<td background="<c:url value='/sample/images/ct_ttl_img02.gif'/>" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">Search List of User</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="<c:url value='/sample/images/ct_ttl_img03.gif'/>" width="12" height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value="0" <%= (condition.equals("0") ? "selected" : "")%> >User ID</option>
				<option value="1" <%= (condition.equals("1") ? "selected" : "")%> >User Name</option>
			</select>
			<input type="text" name="searchKeyword" value="<%= keyword %>" class="ct_input_g" style="width:200px; height:19px" fieldTitle="Keyword" maxLength="40" char="s" >
			&nbsp;&nbsp;
		</td>
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23"><img src="<c:url value='/sample/images/ct_btnbg01.gif'/>" width="17" height="23"></td>
					<td background="<c:url value='/sample/images/ct_btnbg02.gif'/>" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetUserList('1');">Search</a>
					</td>
					<td width="14" height="23"><img src="<c:url value='/sample/images/ct_btnbg03.gif'/>" width="14" height="23"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">User ID</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">User Name</td>
		<td class="ct_line02"></td>
	</tr>
	<tr>
		<td colspan="6" bgcolor="808285" height="1"></td>
	</tr>
	<% 	int currentPageIndex = resultPage.getCurrentPage();
		int no = (currentPageIndex - 1) * pageSize;
		
		for(int i=0; i<resultList.size(); i++) {
			UserVO userVO = (UserVO)resultList.get(i);
	%>	
		<tr class="ct_list_pop">
			<td align="center"><%=++no%></td>
			<td></td>
			<td align="left"><a href="javascript:fncGetUser('<%= userVO.getUserId() %>');"><%= userVO.getUserId() %></a></td>
			<td></td>
			<td align="left"><%= userVO.getUserName() %></td>
			<td></td>
		</tr>
		<tr>
			<td colspan="6" bgcolor="D6D7D6" height="1"></td>
		</tr>
	<% } %>
	
</table>
<!-- selected userId -->
<input type="hidden" name="userId" >

<table width="100%" border="0" cellspacing="0" cellpadding="0"
	style="margin-top:10px;">
	<tr>
		<td align="center">
             <input type="hidden" name="pageIndex" value="<%=resultPage.getCurrentPage()%>">
             <emp:pagenavigator linkUrl="javascript:fncGetUserList(2);" pages="<%=resultPage%>" formName="listForm"
              firstImg="${pageContext.request.contextPath}/sample/images/ct_btn_pre.gif" 
              prevImg="${pageContext.request.contextPath}/sample/images/ct_btn_pre01.gif" 
              lastImg="${pageContext.request.contextPath}/sample/images/ct_btn_next.gif" 
              nextImg="${pageContext.request.contextPath}/sample/images/ct_btn_next01.gif" />		
    	</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"
	style="margin-top:10px;">
	<tr>
		<td align="right">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23"><img src="<c:url value='/sample/images/ct_btnbg01.gif'/>" width="17" height="23"></td>
					<td background="<c:url value='/sample/images/ct_btnbg02.gif'/>" class="ct_btn01" style="padding-top:3px;"><a href="javascript:fncAddUserView();">Add</a></td>
					<td width="14" height="23"><img src="<c:url value='/sample/images/ct_btnbg03.gif'/>" width="14" height="23"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>

</form>
<script language="JavaScript">
document.listForm.searchKeyword.focus();
</script>
</body>
</html>
