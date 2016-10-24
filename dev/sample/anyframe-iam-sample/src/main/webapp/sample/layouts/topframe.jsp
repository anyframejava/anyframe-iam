<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<title>IAM Sample</title>
	<script type="text/javascript">
	function go_top(){
		document.topFrameForm.target = "_top";
		document.topFrameForm.action = "<c:url value='/login.jsp'/>";;	
		document.topFrameForm.submit();
	}
	</script>
</head>
<body bgcolor="#ffffff" onload="javascript:go_top();">
<form name="topFrameForm"></form>
</body>
</html>
