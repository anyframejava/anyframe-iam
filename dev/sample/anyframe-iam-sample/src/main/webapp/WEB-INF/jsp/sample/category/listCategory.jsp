<%@ include file="/sample/common/taglibs.jsp"%>
<html>
<head>
    <%@ include file="/sample/common/meta.jsp" %>
    <title><fmt:message key="categoryList.title"/></title>
	<meta name="heading" content="<fmt:message key='categoryList.heading'/>"/>    
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/sample/css/displaytag.css'/>" />     
	<link rel="stylesheet" href="<c:url value='/sample/css/admin.css'/>" type="text/css">
	
    <script type="text/javascript" src="<c:url value='/sample/javascript/prototype.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/sample/javascript/global.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/sample/javascript/CommonScript.js'/>"></script>    
	<script type="text/javascript">
	function fncAddCategoryView() {
		document.location.href="<c:url value='/category.do?method=addView'/>";
	}
		
	function fncSearchCategory(arg) {
	   	document.searchForm.action="<c:url value='/category.do?method=list'/>";
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
					<td width="93%" class="ct_ttl01">Search List of Category</td>
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
				<form:option value="categoryNo"><anyframe:message code="category.categoryNo"/></form:option>     
				<form:option value="categoryName"><anyframe:message code="category.categoryName"/></form:option>
			</form:select>
			<form:input path="searchKeyword" id="searchKeyword" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="255"/>
		</td>
		<td align="right" width="80">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23"><img src="<c:url value='/sample/images/ct_btnbg01.gif'/>" width="17" height="23"></td>
					<td background="<c:url value='/sample/images/ct_btnbg02.gif'/>" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncSearchCategory('1');">Search</a>
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
<iam:access hasPermission="${iam:getPermissionMask(\"DOWNLOAD\")}" viewName="downloadCategory">
<c:set var="export" value="true"/>
</iam:access>

<display:table name="categoryList" class="table" requestURI="category.do" id="categoryList" export="${export}" partialList="true" size="${size}" pagesize="${pagesize}" style="margin-top:10px;">
	<display:column property="categoryNo" sortable="true" href="category.do?method=get" media="html" paramId="categoryNo" paramProperty="categoryNo" titleKey="category.categoryNo"/>
	<display:column property="categoryDesc" sortable="true" titleKey="category.categoryDesc" maxLength="20" />
	<display:column property="categoryName" sortable="true" titleKey="category.categoryName" maxLength="20" />
	<display:column property="modifyDate" sortable="true" titleKey="category.modifyDate" maxLength="20" />
	<display:column property="modifyId" sortable="true" titleKey="category.modifyId" maxLength="20" />
	<display:column property="regDate" sortable="true" titleKey="category.regDate" maxLength="20" />
	<display:column property="regId" sortable="true" titleKey="category.regId" maxLength="20" />
	<display:column property="useYn" sortable="true" titleKey="category.useYn" maxLength="20" />
	<display:setProperty name="paging.banner.group_size" value="${pageunit}" />
	<display:setProperty name="export.csv.filename"><fmt:message key="categoryList.title"/>.csv</display:setProperty>
	<display:setProperty name="export.excel.filename"><fmt:message key="categoryList.title"/>.xls</display:setProperty>
	<display:setProperty name="export.xml.filename"><fmt:message key="categoryList.title"/>.xml</display:setProperty>
	<display:setProperty name="export.pdf.filename"><fmt:message key="categoryList.title"/>.pdf</display:setProperty>
</display:table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="right">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23"><img src="<c:url value='/sample/images/ct_btnbg01.gif'/>" width="17" height="23"></td>
					<td background="<c:url value='/sample/images/ct_btnbg02.gif'/>" class="ct_btn01" style="padding-top:3px;"><a href="javascript:fncAddCategoryView();"><fmt:message key="button.add"/></a></td>
					<td width="14" height="23"><img src="<c:url value='/sample/images/ct_btnbg03.gif'/>" width="14" height="23"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<script type="text/javascript">
	//highlightTableRows("categoryList");
	document.searchForm.searchKeyword.focus();    
</script>
 
</body>
</html>