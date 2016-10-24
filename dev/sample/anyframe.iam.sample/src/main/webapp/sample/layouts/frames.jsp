<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<title>IAM Sample</title>
	<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>

<frameset rows="68,*" cols="*" frameborder="NO" border="0" framespacing="0">
	<frame src="<c:url value='sample/layouts/top.jsp'/>" name="topFrame" scrolling="NO" noresize >
	<frameset rows="*" cols="160,*" framespacing="0" frameborder="NO" border="0">
		<frame src="<c:url value='/sample/layouts/left.jsp'/>" name="leftFrame" scrolling="NO" noresize>
		<frame src="<c:url value='/sample/layouts/welcome.jsp'/>" name="rightFrame"  scrolling="auto">
	</frameset>
</frameset>
<noframes>
<body>
</body>
</noframes>
</html>
