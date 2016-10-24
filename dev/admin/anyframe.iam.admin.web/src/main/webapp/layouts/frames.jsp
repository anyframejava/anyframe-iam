<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>IAM Admin Web</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>

<frameset rows="79,*" cols="*" frameborder="NO" border="0" framespacing="0">
  <frame src="<c:url value='/layouts/top.jsp'/>" name="topFrame" scrolling="NO" noresize >
  <frameset rows="*" cols="164,*" framespacing="0" frameborder="NO" border="0">
    <frame src="<c:url value='/layouts/left.jsp'/>" name="leftFrame" scrolling="NO" noresize>
    <frame src="<c:url value='/layouts/welcome.jsp'/>" name="rightFrame"  scrolling="auto">
  </frameset>
</frameset>
<noframes><body>

</body></noframes>
</html>
