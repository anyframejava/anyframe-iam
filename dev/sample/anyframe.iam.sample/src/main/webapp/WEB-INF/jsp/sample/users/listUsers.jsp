<%@ include file="/sample/common/taglibs.jsp"%>
<html>
<head>
    <%@ include file="/sample/common/meta.jsp" %>
    <title><fmt:message key="usersList.title"/></title>
	<meta name="heading" content="<fmt:message key='usersList.heading'/>"/>    
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/sample/css/displaytag.css'/>" />     
	<link rel="stylesheet" href="<c:url value='/sample/css/admin.css'/>" type="text/css">
	
    <script type="text/javascript" src="<c:url value='/sample/javascript/prototype.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/sample/javascript/global.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/sample/javascript/CommonScript.js'/>"></script>    
	<script type="text/javascript">
	function fncAddUsersView() {
		document.location.href="<c:url value='/users.do?method=addView'/>";
	}
		
	function fncSearchUsers(arg) {
	   	document.searchForm.action="<c:url value='/users.do?method=list'/>";
	   	document.searchForm.submit();						
	}		
	</script>
</head>

<body bgcolor="#ffffff" text="#000000">
<!--************************** begin of contents *****************************-->
<!-- begin of title -->
<table width="100%" height="37" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="<c:url value='/sample/images/ct_ttl_img01.gif'/>" width="15" height="37"></td>
		<td background="<c:url value='/sample/images/ct_ttl_img02.gif'/>" width="100%" style="padding-left:10px;" >
			<table width="100%" border="0" cellspacing="0" cellpadding="0" style=" width : 941px; height : 20px;">
				<tr>
					<td width="93%" class="ct_ttl01">Search List of Users</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="<c:url value='/sample/images/ct_ttl_img03.gif'/>" width="12" height="37"></td>
	</tr>
</table>
<!-- end of title -->     
<!-- begin of search -->
<form:form commandName="search" method="post" name="searchForm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;vertical-align: center;">
	<tr>
		<td align="right">
			<form:select path="searchCondition" id="searchCondition" cssClass="ct_input_g" cssStyle="width:114px;">
				<form:option value="All">ALL</form:option>
				<form:option value="userId"><anyframe:message code="users.userId"/></form:option>     
				<form:option value="password"><anyframe:message code="users.password"/></form:option>
				<form:option value="userName"><anyframe:message code="users.userName"/></form:option>
			</form:select>
			<form:input path="searchKeyword" id="searchKeyword" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="255"/>
		</td>
		<td align="right" width="80">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23"><img src="<c:url value='/sample/images/ct_btnbg01.gif'/>" width="17" height="23"></td>
					<td background="<c:url value='/sample/images/ct_btnbg02.gif'/>" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncSearchUsers('1');">Search</a>
					</td>
					<td width="14" height="23"><img src="<c:url value='/sample/images/ct_btnbg03.gif'/>" width="14" height="23"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</form:form>
<!-- end of search -->

<c:set var="export" value="false"/>
<iam:access hasPermission="${iam:getPermissionMask(\"DOWNLOAD\")}" viewResourceId="downloadUser">
<c:set var="export" value="true"/>
</iam:access>

<display:table name="usersList" class="table" requestURI="users.do" id="usersList" export="${export}" partialList="true" size="${size}" pagesize="${pagesize}" style="margin-top:10px;">
    <display:column property="userId" sortable="true" href="users.do?method=get" media="html" paramId="userId" paramProperty="userId" titleKey="users.userId"/>
    <display:column property="createDate" sortable="true" titleKey="users.createDate" maxLength="20" />
    <display:column property="enabled" sortable="true" titleKey="users.enabled" maxLength="20" />
    <display:column property="modifyDate" sortable="true" titleKey="users.modifyDate" maxLength="20" />
    <display:column property="password" sortable="true" titleKey="users.password" maxLength="20" />
    <display:column property="userName" sortable="true" titleKey="users.userName" maxLength="20" />
    <display:setProperty name="paging.banner.group_size" value="${pageunit}" />
	<display:setProperty name="export.csv.filename"><fmt:message key="usersList.title"/>.csv</display:setProperty>
    <display:setProperty name="export.excel.filename"><fmt:message key="usersList.title"/>.xls</display:setProperty>
    <display:setProperty name="export.xml.filename"><fmt:message key="usersList.title"/>.xml</display:setProperty>
    <display:setProperty name="export.pdf.filename"><fmt:message key="usersList.title"/>.pdf</display:setProperty>    
</display:table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="right">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23"><img src="<c:url value='/sample/images/ct_btnbg01.gif'/>" width="17" height="23"></td>
					<td background="<c:url value='/sample/images/ct_btnbg02.gif'/>" class="ct_btn01" style="padding-top:3px;"><a href="javascript:fncAddUsersView();"><fmt:message key="button.add"/></a></td>
					<td width="14" height="23"><img src="<c:url value='/sample/images/ct_btnbg03.gif'/>" width="14" height="23"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<script type="text/javascript">
    //highlightTableRows("usersList");
    document.searchForm.searchKeyword.focus();    
</script> 
</body>
</html>