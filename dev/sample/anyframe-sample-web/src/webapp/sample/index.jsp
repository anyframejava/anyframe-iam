<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<%@ page import="com.sds.emp.common.EmpUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>eMarketPlace</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<%
	String flag = EmpUtil.null2str((String)request.getParameter("flag"),"L");
%>
<frameset rows="48,*" cols="*" frameborder="NO" border="0" framespacing="0">
  <frame src="sample/layouts/top.jsp" name="topFrame" scrolling="NO" noresize >
  <frameset rows="*" cols="160,*" framespacing="0" frameborder="NO" border="0">
    <frame src="<c:url value='/sample/layouts/left.jsp'/>" name="leftFrame" scrolling="NO" noresize>
    <frame src="<% if(flag.equals("L")) {%>
    	welcome.do
    <%} else {%> 
    	empadduser.do
    <%}%>
    	" name="rightFrame"  scrolling="auto">
  </frameset>
</frameset>
<noframes><body>

</body></noframes>
</html>
