<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/common/taglibs.jsp"%> 
<html>
<head>
<title>IAM Admin Web</title>

<jsp:include page="/common/jquery-include.jsp" />

<script language="JavaScript">
<!--
function fncLogOut() {
	if(confirm("Do you want to log out?")) {
		document.logoutForm.target="_top";
	    document.logoutForm.action="<c:url value='/j_spring_security_logout'/>";
	    document.logoutForm.submit();	
    }
}

function changeSystemName(frm) {

	var systemName = frm.value;
	$.post("<c:url value='/common/changeSystemName.do'/>", {systemName:systemName}, function(data){
	});
	parent.frames['rightFrame'].document.location.href="<c:url value='/layouts/welcome.jsp'/>";
	
}
//-->
</script>
<!-- for firefox topmargin compatibility -->
<style type="text/css">
<!--
*{
padding:0;
margin:0;
}
-->
</style>
<link href="<c:url value='/css/left.css'/>" rel="stylesheet" type="text/css">
</head>
<body topmargin="0" leftmargin="0">
<div id="frame_top">
<form name="logoutForm">
	<input type="hidden" name="loflag" value="LO">
</form>
<table width="100%" height="79" border="0" cellpadding="0" cellspacing="0" background="<c:url value='/images/bg_topframe.gif'/>">
  <tr>
    <td width="227" height="49" align="left" background="<c:url value='/images/bg_logo.gif'/>"><img src="<c:url value='/images/logo.gif'/>" width="369" height="49"></td>
    <td align="right" background="<c:url value='/images/bg_logo.gif'/>"><img src="<c:url value='/images/img_top.gif'/>" width="557" height="49"></td>
  </tr>
  <tr>
    <td height="30" colspan="2" align="right"><table width="380" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td align="right" style="padding-right:8px">
        	IAM에 오신것을 환영합니다!
        	<select name="systemName" onchange="javascript:changeSystemName(this);"  class="ct_input_g">
        		<c:forEach var="item" items="${systemNames }">
					<option value="${item }">${item }</option>
        		</c:forEach>	
        	</select>
        </td>
        <td width="62" colspan="2" align="right" style="padding-right:18px"><a href="javascript:fncLogOut();"> <img src="<c:url value='/images/btn_logout.gif'/>" alt="로그아웃" width="62" height="16" border="0" ></a></td>
        <!-- td width="76" align="right" style="padding-right:18px"><a href="#"><img src="<c:url value='/images/btn_add.gif'/>" alt="사용자 추가" width="72" height="16" border="0"></a></td -->
      </tr>
    </table></td>
  </tr>
</table>
</div>
</body>
</html>
