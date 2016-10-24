<%@ include file="/sample/common/taglibs.jsp"%>
<html>
<head>
<%@ include file="/sample/common/meta.jsp"%>
<title><fmt:message key="usersList.title" /></title>
<meta name="heading" content="<fmt:message key='usersList.heading'/>" />
<link rel="stylesheet" type="text/css" media="all"
	href="<c:url value='/sample/css/displaytag.css'/>" />
<link rel="stylesheet" href="<c:url value='/sample/css/admin.css'/>"
	type="text/css">

<script type="text/javascript"
	src="<c:url value='/sample/javascript/prototype.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/sample/javascript/global.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/sample/javascript/CommonScript.js'/>"></script>
	
<link type="text/css" href="<c:url value='/sample/css/jquery-ui-1.8.1.custom.css' />" rel="stylesheet" />	
<script type="text/javascript" src="<c:url value='/sample/javascript/jquery/jquery-1.4.2.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/sample/javascript/jquery/jquery-ui-1.8.1.custom.min.js'/>"></script>
<script type="text/javascript">
$(function(){
	// Tabs
	$('#tabs').tabs();
});
</script>		
</head>
<body>
	<div id="tabs">
		<ul>
		
			<li><a href="#tabs-1">1.First</a></li>
			
			<iam:access hasPermission="${iam:getPermissionMask(\"READ\")}" viewName="User View">
				<li><a href="#tabs-2">2.Second</a></li>
			</iam:access>
			
			<iam:access hasPermission="${iam:getPermissionMask(\"READ\")}" viewName="Manage View">
				<li><a href="#tabs-3">3.Third</a></li>
			</iam:access>
			
			<iam:access hasPermission="${iam:getPermissionMask(\"READ\")}" viewName="Admin View">
				<li><a href="#tabs-4">4.Fourth</a></li>
			</iam:access>
			
		</ul>
		
		<div id="tabs-1">
			<a href=""><u>Anonymous Menu</u></a>
		</div>
		
		<iam:access hasPermission="${iam:getPermissionMask(\"READ\")}" viewName="User View">
			<div id="tabs-2">
				<a href=""><font color="GREEN"><u>General User Menu</u></font></a>
				
				<iam:access hasPermission="${iam:getPermissionMask(\"READ\")}" viewName="Manage View">
					<a href=""><font color="BLUE"><u>Manager Menu</u></font></a>
				</iam:access>
			</div>
		</iam:access>
		
		<iam:access hasPermission="${iam:getPermissionMask(\"READ\")}" viewName="Manage View">
			<div id="tabs-3">
				<a href=""><font color="BLUE"><u>Manager Menu</u></font></a>
				
				<iam:access hasPermission="${iam:getPermissionMask(\"READ\")}" viewName="Admin View">
					<a href=""><font color="RED"><u>Administrator Menu</u></font></a>
				</iam:access>
			</div>
		</iam:access>
		
		<iam:access hasPermission="${iam:getPermissionMask(\"READ\")}" viewName="Admin View">
			<div id="tabs-4">
			<u><font color="RED">Administrator Menu</font></u>
			</div>
		</iam:access>
	</div>
	
	<div id="contents">
		<ul>
			<li>1. Contents : For Anonymous Users</li>
			
			<iam:access hasPermission="${iam:getPermissionMask(\"READ\")}" viewName="User View">
				<li><font color="GREEN">2. Contents : For General Users</font></li>
			</iam:access>
			
			<iam:access hasPermission="${iam:getPermissionMask(\"READ\")}" viewName="Manage View">
				<li><font color="BLUE">3. Contents : For Manage</font>r</li>
			</iam:access>
			
			<iam:access hasPermission="${iam:getPermissionMask(\"READ\")}" viewName="Admin View">
				<li><font color="RED">4. Contents : For Administrator</font></li>
			</iam:access>
		</ul>
	</div>
</body>
</html>