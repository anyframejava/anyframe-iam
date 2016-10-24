<%@ include file="/sample/common/taglibs.jsp"%>
<html>
<head>
	<title>IAM Sample</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<!-- c:set property="flag" value="${flag == '' ? 'LO' : flag }" / -->
<c:choose>
	<c:when test="${param.flag == 'L'}">
    	<jsp:forward page="/sample/layouts/frames.jsp"/>
    </c:when>
    <c:otherwise>
    	<jsp:forward page="/common/login.do"/>	   
    </c:otherwise>
</c:choose>
</body>
</html>