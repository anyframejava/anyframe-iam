<%@ page isErrorPage="true"%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.lang.String" %>
<%
  	boolean authenticateFail = false;
  	if(request.getAttribute("authenticateFail")!=null && !request.getAttribute("authenticateFail").toString().equals("")){
		authenticateFail = true;
  	}
  
  	boolean authFail = false;
  	if(request.getAttribute("authFail")!=null && !request.getAttribute("authFail").toString().equals("")){
		authFail = true;
  	}  
  	
  	String target = (String)request.getAttribute("target");
  	if (target==null) target = "";
  	target = target.equals("") ? "_top" : target;
%>

<html>
<head>
<title>Error</title>
<link rel="stylesheet" href="<c:url value='/css/admin.css'/>" type="text/css">
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style></head>
<script language="javascript">
function fncGoAfterErrorPage(){
	if('<%=authenticateFail%>' == 'true' ){
		document.dummyForm.target="_top";
		document.dummyForm.action = "<c:url value='/empaftererrorpage.do'/>";
		document.dummyForm.submit();
	}else if('<%=authFail%>' == 'true'){
		document.dummyForm.target="<%=target%>";
		document.dummyForm.action = "<c:url value='/empaftererrorpage.do'/>";
		document.dummyForm.submit();
	}else{
		document.location.href = "<c:url value='/empaftererrorpage.do'/>";
		history.back(-2);
	}
}
</script>
<body bgcolor="#ffffff" text="#000000">

<h1>${command}</h1>
<!-- START : Title -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 13px;">
  <tr>
    <td class="errortitle" style="padding-left:28px">Reload Authorization.</td>
  </tr>
</table><!-- END : Title -->
<form name="dummyForm">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td valign="top" style="padding-top:15px">
	<table width="517" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td id="message" style="padding-left:89px" class="blue_h2">Data Transporting </td>
  </tr>
  <tr>
    <td height="15" background="<c:url value='/images/box_top.gif'/>">&nbsp;</td>
  </tr>
  <tr>
    <td align="center" background="<c:url value='/images/box_bg.gif'/>" class="boxpadding"> <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="78%" align="center" style="padding-left:10px">${exception.message}</td>
    <td width="22%"><img src="<c:url value='/images/icon_complete.gif'/>" width="77" height="77"></td>
  </tr>
</table>
</td>
  </tr>
  <tr>
    <td height="31" background="<c:url value='/images/box_bttm.gif'/>">&nbsp;</td>
  </tr>
</table>
<table height="19" border="0" align="center" cellpadding="0" cellspacing="0" style="margin:10px">
					                <tr>
					                  <td width="18"><img src="<c:url value='/images/btn/btn_checkid.gif'/>" width="21" height="19"></td>
					                  <td valign="bottom" background="<c:url value='/images/btn/bg_btnsmall.gif'/>" class="boldBtn"><a href="javascript:fncGoAfterErrorPage();" >확인</a></td>
					                  <td width="9" align="right"><img src="<c:url value='/images/btn/btn_tail.gif'/>" width="9" height="19"></td>
					                </tr>
      </table>
    </form> 
</body>
</html>

