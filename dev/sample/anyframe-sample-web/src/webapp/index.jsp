<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="com.sds.emp.common.EmpUtil" %>
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
