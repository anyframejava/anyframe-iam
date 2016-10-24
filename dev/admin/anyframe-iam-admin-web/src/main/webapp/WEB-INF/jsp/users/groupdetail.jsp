<%@ taglib uri='http://www.anyframejava.org/tags' prefix='anyframe' %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Group Information</title>

<jsp:include page="/common/jquery-include.jsp" />

<script type="text/javascript" src="<c:url value='/validator.do'/>"></script>
<validator:javascript formName="groups" staticJavascript="false" xhtml="true" cdata="false"/>
<script type="text/javascript">
<!--
jQuery(document).ready( function() {
	$('[name=updateGroup]').click( function(){
		if(!validateGroups(document.groupsForm)){
			return;
		}
		parent.updateGroup(document.getElementById("groupId").value,document.getElementById("groupName").value);
	});

	$('[name=deleteGroup]').click( function(){
		parent.deleteGroup(document.getElementById("groupId").value);
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
	background-color: #FFFFFF;
}
-->
</style></head>
<body>
<c:set var="readonly" value="true" />
<!-- Input Field  -->

<form:form commandName="groups" method="post" id="groupsForm" name="groupsForm">
<table width="572" border="0" cellspacing="0" cellpadding="0" style="margin-top: 13px;">
	<tr><td height="2" colspan="3" bgcolor="#A2BACE"></td></tr>
	<tr>
		<td width="134" class="tdHead"><anyframe:message code="groupinfo.ui.label.groupid" /></td><td width="1" bgcolor="#B6CDE4"></td>
		<td class="tdin">
			<form:input path="groupId" id="groupId" cssClass="ct_input_g" cssErrorClass="text medium error" readonly="${readonly}" maxlength="10" autocomplete="false" /><form:errors path="groupId" />
		</td>
	</tr>
	<tr><td height="1" colspan="3" bgcolor="#D8D8D8"></td></tr>
	<tr>
		<td width="134" class="tdHead">
			<anyframe:message code="groupinfo.ui.label.groupname" /></td><td bgcolor="#B6CDE4" width="1">
		</td>
		<td class="tdin">									        
			<form:input path="groupName" id="groupName" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="50"/><form:errors path="groupName" />
		</td>
	</tr>
	<tr><td height="1" colspan="3" bgcolor="#B6CDE4"></td></tr>
	<tr><td height="10" colspan="3" bgcolor="#FFFFFF"></td></tr>	
</table>
<!-- Begin Button Field -->
<table width="572" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<c:if test="${not empty groups.groupId}">
		<td width="94%" align="right" class="tdBtnRight">
			<table height="22" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="18"><img src="<c:url value='/images/btn/update.gif'/>" width="18" height="22"></td>
					<td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="updateGroup"><fmt:message key="button.update"/></a></td>
					<td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
				</tr>
			</table>
		</td>
		<td width="6%" align="right" class="tdBtnRight">
			<table height="22" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="18"><img src="<c:url value='/images/btn/btn_delete.gif'/>" width="18" height="22"></td>
					<td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="deleteGroup"><fmt:message key="button.delete"/></a></td>
					<td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
				</tr>
			</table>
		</td>
		</c:if>
	</tr>
</table>
</form:form>
<!-- End of Button Field -->

</body>
</html>
