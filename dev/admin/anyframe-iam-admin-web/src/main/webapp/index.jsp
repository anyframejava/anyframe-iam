<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
<title>IAM Admin Web</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>

<!-- c:set property="flag" value="${flag == '' ? 'LO' : flag }" / -->

<c:choose>
	<c:when test="${param.flag == 'L'}">
    	<jsp:forward page="/layouts/frames.jsp"/>
    </c:when>
    <c:otherwise>
    	<jsp:forward page="/common/login.do"/>	   
    </c:otherwise>
</c:choose>
    
</body>
</html>