<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>IAM Admin Web Menu</title>
<link href="<c:url value='/css/left.css'/>" rel="stylesheet" type="text/css">
<jsp:include page="/common/jstree-include.jsp" />

<script ID="clientEventHandlersJS" LANGUAGE="javascript">
<!--
jQuery(document).ready( function(){
	$("[id^=Main]").click( function() {
		// Change - + images on main menu
		var idx = this.id.substr(4,1);
		if ($("[id^=sub" + idx + "]").is(':hidden'))
			$("[id^=Main" + idx + "] > td").attr("background","<c:url value='/images/left/bg_menu.gif'/>");
		else
			$("[id^=Main" + idx + "] > td").attr("background","<c:url value='/images/left/bg_menu_folding.gif'/>");
		// Toggle sub menus in main menu
		$("[id^=sub" + idx + "]").slideToggle("fast");
	});
});
//-->
</script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>

</head>
<body background="<c:url value='/images/left/imgLeftBg.gif'/>">
<!-- div class="navitop" style="height:40px"></div-->
<table width="164" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="9" background="<c:url value='/images/left/menu_topbg2.gif'/>" ></td>
  </tr>
  <tr id=Main1 >
    <td height="29" background="<c:url value='/images/left/bg_menu.gif'/>" class="mainMenu" style="padding:9px 2px 4px 20px">User Management</td>
  </tr>
  <tr id="sub1-1" style="display:">
    <td height="24" width="148" background="<c:url value='/images/left/bg_mainnav.gif'/>" class="mainNav" style="padding:7px 2px 4px 20px"><a href="<c:url value='/users/list.do'/>" target="rightFrame" >User List.</a></td>
  </tr>
  <tr id=Main2 >
    <td height="29" background="<c:url value='/images/left/bg_menu.gif'/>" class="mainMenu" style="padding:9px 2px 4px 20px">Roles Management</td>
  </tr>
  <tr id="sub2-1" style="display:">
    <td height="24" width="141" background="<c:url value='/images/left/bg_mainnav.gif'/>" class="mainNav" style="padding:7px 2px 4px 20px"><a href="<c:url value='/roles/list.do'/>" target="rightFrame">Roles List.</a></td>
  </tr>
  <tr id=Main3>
    <td height="29" id="mainMenu" background="<c:url value='/images/left/bg_menu.gif'/>" class="mainMenu" style="padding:9px 2px 4px 20px">Resource Management</td>
  </tr>
  <tr id="sub3-1" style="display:">
    <td height="24" width="141" background="<c:url value='/images/left/bg_mainnav.gif'/>" class="mainNav" style="padding:7px 2px 4px 20px"><a href="<c:url value='/resources/list.do'/>" target="rightFrame">Resource List.</a></td>
  </tr>
  <tr id="sub3-2" style="display:">
    <td height="1" width="250" background="<c:url value='/images/left/bg_line.gif'/>"></td>
  </tr>
  <tr id="sub3-3" style="display:">
    <td height="24" width="141" background="<c:url value='/images/left/bg_mainnav.gif'/>" class="mainNav" style="padding:7px 2px 4px 20px"><a href="<c:url value='/viewresources/list.do'/>" target="rightFrame">View List.</a></td>
  </tr>
  <tr id="sub3-4" style="display:">
    <td height="1" width="250" background="<c:url value='/images/left/bg_line.gif'/>"></td>
  </tr>
  <tr id="sub3-5" style="display:">
    <td height="24" width="141" background="<c:url value='/images/left/bg_mainnav.gif'/>" class="mainNav" style="padding:7px 2px 4px 20px"><a href="<c:url value='/viewresourcesmapping/list.do'/>" target="rightFrame">View Mapping.</a></td>
  </tr>
  <tr id=Main4>
    <td height="29" id="mainMenu" background="<c:url value='/images/left/bg_menu.gif'/>" class="mainMenu" style="padding:9px 2px 4px 20px">Restriction Management</td>
  </tr>
  <tr id="sub4-1" style="display:">
    <td height="24" width="141" background="<c:url value='/images/left/bg_mainnav.gif'/>" class="mainNav" style="padding:7px 2px 4px 20px"><a href="<c:url value='/restriction/list.do'/>" target="rightFrame" >Restricted Times List.</a></td>
  </tr>
  <tr id="sub4-2" style="display:">
    <td height="1" width="250" background="<c:url value='/images/left/bg_line.gif'/>"></td>
  </tr>
  <tr id="sub4-3" style="display:">
    <td height="24" width="141" background="<c:url value='/images/left/bg_mainnav.gif'/>" class="mainNav" style="padding:7px 2px 4px 20px; border-bottom:1px; border-color:#CCCCCC"><a href="<c:url value='/restriction/timesmapping/list.do'/>" target="rightFrame" >Times Mapping.</a></td>
  </tr>
  <tr id=Main5>
    <td height="29" id="mainMenu" background="<c:url value='/images/left/bg_menu.gif'/>" class="mainMenu" style="padding:9px 2px 4px 20px">System Management</td>
  </tr>
  <tr id="sub5-1" style="display:">
    <td height="24" width="141" background="<c:url value='/images/left/bg_mainnav.gif'/>" class="mainNav" style="padding:7px 2px 4px 20px"><a href="<c:url value='/admin/reload/reloadlist.do'/>" target="rightFrame" >Resources Reload.</a></td>
  </tr>
  <tr id="sub5-2" style="display:">
    <td height="1" width="250" background="<c:url value='/images/left/bg_line.gif'/>"></td>
  </tr>
  <tr id="sub5-3" style="display:">
    <td height="25" width="141" background="<c:url value='/images/left/bg_mainnav.gif'/>" class="mainNav" style="padding:7px 2px 4px 20px"><a href="<c:url value='/admin/assist/assistlist.do'/>" target="rightFrame" >Resources Gathering.</a></td>
  </tr>
</table>
<div class="banner" align="center"></div>
</body>
</html>
