<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<%@ page import="com.sds.emp.common.EmpUtil" %>
<%@ page import="java.lang.String" %>
<html>
<head>
<title>eMarketPlace</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body>
<%
	String flag = EmpUtil.null2str((String)request.getParameter("flag"),"LO");
%>

<% if(flag.equals("L")) {%>
    	<jsp:forward page="/sample/index.jsp"/>	   
    <%} else {%> 
    	<jsp:forward page="/login.do"/>	   
    <%}%>
    
</body>
</html>
