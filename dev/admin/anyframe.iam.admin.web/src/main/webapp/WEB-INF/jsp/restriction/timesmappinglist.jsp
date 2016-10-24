<%@ taglib uri='http://www.sds.samsung.com/tags' prefix='anyframe' %>
<%@ include file="/common/taglibs.jsp"%> 
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><anyframe:message code="restrictedtimes.ui.title.timesmapping" /></title>

<jsp:include page="/common/jstree-include.jsp" />
<jsp:include page="/common/jqgrid-include.jsp" />
<jsp:include page="/common/jqueryui-include.jsp" />
<script type="text/javascript">
<!--
	function moveToTimeRole(){
		var tabInfo = document.f1.tabInfo;
		tabInfo.value = "timeRoleTab";
		frame01.location.href = "<c:url value='/restriction/timerole/list.do?' />";
	}

	function moveToTimeResource(){
		var tabInfo = document.f1.tabInfo;
		tabInfo.value = "timeResourceTab";
		frame01.location.href = "<c:url value='/restriction/timeresource/list.do?' />";
	}

	function moveToTimeExclusion(){
		var tabInfo = document.f1.tabInfo;
		tabInfo.value = "timeExclusionTab";
		frame01.location.href = "<c:url value='/restriction/timeexclusion/list.do?' />";
	}
//-->
</script>

<script type="text/javascript">
<!--
	$(function () {
		$('#timeRoleTab').addClass('selectedTab');
		
		$('#timeRoleTab').bind('click', function(){
			$('td').removeClass('selectedTab');
			$('#timeRoleTab').addClass('selectedTab');
			moveToTimeRole();
		});
		$('#timeResourceTab').bind('click', function(){
			$('td').removeClass('selectedTab');
			$('#timeResourceTab').addClass('selectedTab');
			moveToTimeResource();
		});
		$('#timeExclusionTab').bind('click', function(){
			$('td').removeClass('selectedTab');
			$('#timeExclusionTab').addClass('selectedTab');
			moveToTimeExclusion();
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
	background-color: #E9ECF1;
}
-->
</style></head>
<body>	
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
	<tr valign="top">
	  	<td width="10" align="left" style="padding-top:6px;">&nbsp;</td>
		<td align="left" style="padding-top:5px;">
			<form name="f1">
				<input type="hidden" name="tabInfo" value="timeRoleTab">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" >
					<tr height="30">
						<td height="30" valign="bottom" background="<c:url value='/images/content/bg_tab.gif'/>" style="padding-left:10px">
							<table height="24" border="0" cellpadding="0" cellspacing="0">
			            		<tr height="25">
			              			<td width="134" height="27" align="center" valign="bottom"  background="<c:url value='/images/content/bg_tab_menu2.gif'/>" bgcolor="#EDEDED"
										id="timeRoleTab" >
										<a href="javascript:moveTotimeRoleTab();">Time-Role </a>						  			</td>
			              			<td width="134" height="27" align="center" valign="bottom"   background="<c:url value='/images/content/bg_tab_menu3.gif'/>" bgcolor="#EDEDED"
								 		id="timeResourceTab" >
										<a href="#">Time-Resource</a>						  			</td>
			              			<td width="145" height="27" align="center" valign="bottom"  background="<c:url value='/images/content/bg_tab_menu3.gif'/>" bgcolor="#EDEDED"
										id="timeExclusionTab" >
										<a href="#">Time-Exclustion </a>						  			</td>
			            		</tr>
			          		</table></td>
		        	</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
					  <td style="padding-left:10px;padding-top:10px"><iframe src="<c:url value='/restriction/timerole/list.do?' />" width="800" height="445" frameborder="0" scrolling="no" name="frame01" id="frame01"></iframe></td>
					</tr>
				</table>
			</form>
		</td>
	</tr>
	<tr valign="top">
  <td colspan="2" align="left" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#E9ECF1">
  <tr>
    <td height="10" bgcolor="#ffffff"></td>
  </tr>
  <tr>
    <td height="1" bgcolor="#C9CFDD"></td>
  </tr>
  <tr>
    <td valign="top" bgcolor="#E9ECF1"><div id="footSub"></div></td>
  </tr>
</table></td>
  </tr>
</table>
</body>
</html>