<%@ taglib uri='http://www.anyframejava.org/tags' prefix='anyframe' %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><anyframe:message code="resourcereload.ui.title.resourcegather" /></title>
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
</style>
</head>
<script language="javascript">
function fncGoAfterPage(){	
	history.back(-2);
}
</script>
<body bgcolor="#ffffff" text="#000000">
<!-- START : Title -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 13px;">
	<tr><td class="errortitle" style="padding-left:28px"><anyframe:message code="resourcereload.ui.title.resourcegather" /></td></tr>
</table>
<!-- END : Title -->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td valign="top" style="padding-top:15px">
			<table width="517" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td id="message" style="padding-left:89px" class="blue_h2"><anyframe:message code="resourcereload.ui.title.resourcegather" /></td>
				</tr>
				<tr>
					<td height="15" background="<c:url value='/images/box_top.gif'/>">&nbsp;</td>
				</tr>
				<tr>
					<td align="center" background="<c:url value='/images/box_bg.gif'/>" class="boxpadding">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="78%" align="center" style="padding-left:10px"><anyframe:message code="resourcereload.ui.message.successtogather" /></td>
								<td width="22%"><img src="<c:url value='/images/icon_complete.gif'/>" width="77" height="77"></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td height="31" background="<c:url value='/images/box_bttm.gif'/>">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td align="center">
			<table height="19" border="0" align="center" cellpadding="0" cellspacing="0" style="margin:10px">
				<tr>
					<td width="18"><img src="<c:url value='/images/btn/btn_checkid.gif'/>" width="21" height="19"></td>
					<td valign="bottom" background="<c:url value='/images/btn/bg_btnsmall.gif'/>" class="boldBtn"><a href="javascript:fncGoAfterPage();" >확인</a></td>
					<td width="9" align="right"><img src="<c:url value='/images/btn/btn_tail.gif'/>" width="9" height="19"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</body>
</html>

