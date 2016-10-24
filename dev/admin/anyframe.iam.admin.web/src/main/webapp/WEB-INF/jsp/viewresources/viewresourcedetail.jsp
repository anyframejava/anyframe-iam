<%@ taglib uri='http://www.anyframejava.org/tags' prefix='anyframe'%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="validator"
	uri="http://www.springmodules.org/tags/commons-validator"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><anyframe:message
	code="viewresource.ui.title.viewresources" /></title>

<script language="javascript" src="<c:url value='/js/CommonScript.js'/>"></script>

<jsp:include page="/common/jquery-include.jsp" />

<script type="text/javascript" src="<c:url value='/validator.do'/>"></script>
<validator:javascript formName="viewResources" staticJavascript="false"
	xhtml="true" cdata="false" />

<script type="text/javascript">
	jQuery(document)
			.ready(function() {

				// button click event
					$('[name=updateResource]')
							.click(
									function() {
										if (!validateViewResources(document.viewResources)) {
											return;
										}
										parent.updateView(document.getElementById("viewResourceId").value, 
												document.getElementById("viewName").value, 
												document.getElementById("category").value, 
												document.getElementById("description").value, 
												document.getElementById("viewInfo").value,
												document.getElementById("viewType").value,
												document.getElementById("visible").value);
									});
					$('[name=deleteResource]')
							.click(
									function() {
											parent.deleteView(document.getElementById("viewResourceId").value);
									});
				});

	function checkforId() {
		var pop = 0;
		var viewResourceId = document.viewResources.viewResourceId;
		url = "<c:url value='/viewresources/duplicationconfirm.do?'/>"
				+ "&viewResourceId=" + viewResourceId.value;
		pop = window
				.open(url, "CheckID",
						"top=100, left=200, width=300, height=200,scrollbars=no,resizable=no");
		pop.focus();
	}

	function changeIdcheck() {
		var isAvailable = document.viewResources.isAvailable;
		isAvailable.value = "false";
	}

	function setviewResourceId(viewResourceId) {
		var textbox1 = document.viewResources.viewResourceId;
		var textbox2 = document.viewResources.isAvailable;
		textbox1.value = viewResourceId;
		textbox2.value = true;
	}
</script>
<style type="text/css">
<!--
body {
	background-color: #FFFFFF;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
</head>

<body text="#000000">
<form:form commandName="viewResources" method="post"
	action="viewResources.do" id="viewResources" name="viewResources">
	<!-- Begin input field -->
	<table width="572" border="0" cellpadding="0" cellspacing="0"
		bgcolor="#FFFFFF" style="margin-top: 13px;">
		<tr>
			<td height="2" colspan="3" bgcolor="#A2BACE"></td>
		</tr>
		<tr>
			<td width="150" class="tdHead"><anyframe:message
				code="viewresource.ui.label.viewresourceid" /></td>
			<td bgcolor="#B6CDE4" width="1"></td>
			<td class="tdin"><form:input path="viewResourceId"
				id="viewResourceId" cssClass="ct_input_g"
				cssErrorClass="text medium error" maxlength="50"
				readonly="true" size="30" />&nbsp;<form:errors
				path="viewResourceId" cssClass="error" /></td>
		</tr>
		<tr>
			<td height="1" colspan="3" bgcolor="#D6D6D6"></td>
		</tr>

		<tr>
			<td class="tdHead"><anyframe:message
				code="viewresource.ui.label.viewname" /></td>
			<td bgcolor="#D6D6D6" width="1"></td>
			<td class="tdin"><form:input path="viewName" id="viewName"
				cssClass="ct_input_g" cssErrorClass="text medium error"
				maxlength="50" size="30" />&nbsp;<form:errors path="viewName"
				cssClass="error" /></td>
		</tr>
		<tr>
			<td height="1" colspan="3" bgcolor="#D6D6D6"></td>
		</tr>

		<tr>
			<td class="tdHead"><anyframe:message
				code="viewresource.ui.label.category" /></td>
			<td bgcolor="#D6D6D6" width="1"></td>
			<td class="tdin"><form:input path="category" id="category"
				cssClass="ct_input_g" cssErrorClass="text medium error"
				maxlength="255" size="55" />&nbsp;<form:errors path="category"
				cssClass="error" /></td>
		</tr>
		<tr>
			<td height="1" colspan="3" bgcolor="#D6D6D6"></td>
		</tr>


		<tr>
			<td class="tdHead"><anyframe:message
				code="viewresource.ui.label.description" /></td>
			<td bgcolor="#D6D6D6" width="1"></td>
			<td class="tdin"><form:input path="description" id="description"
				cssClass="ct_input_g" cssErrorClass="text medium error"
				maxlength="255" size="55" />&nbsp;<form:errors path="description"
				cssClass="error" /></td>
		</tr>
		<tr>
			<td height="1" colspan="3" bgcolor="#D6D6D6"></td>
		</tr>

		<tr>
			<td class="tdHead"><anyframe:message
				code="viewresource.ui.label.viewtype" /></td>
			<td bgcolor="#D6D6D6" width="1"></td>
			<td class="tdin">
			<select name="systemName" disabled="disabled" class="ct_input_g">
				<c:forEach var="item" items="${systemNames }">
					<option value="${item }">${item }</option>
				</c:forEach>			
			</select>
			
		</tr>
		<tr>
			<td height="1" colspan="3" bgcolor="#D6D6D6"></td>
		</tr>
		
		<tr>
			<td class="tdHead"><anyframe:message
				code="viewresource.ui.label.viewtype" /></td>
			<td bgcolor="#D6D6D6" width="1"></td>
			<td class="tdin">
			<form:select path="viewType" id="viewType"
				cssClass="ct_input_g" cssErrorClass="text medium error">
				<form:option value="menu"><anyframe:message
				code="viewresource.ui.selbox.viewtype.menu" /></form:option>	
				<form:option value="button"><anyframe:message
				code="viewresource.ui.selbox.viewtype.button" /></form:option>	
			</form:select>
			<form:errors path="viewType" /></td>
		</tr>
		<tr>
			<td height="1" colspan="3" bgcolor="#D6D6D6"></td>
		</tr>

		<tr>
			<td class="tdHead"><anyframe:message
				code="viewresource.ui.label.visible" /></td>
			<td bgcolor="#D6D6D6" width="1"></td>
			<td class="tdin">
			<form:select path="visible" id="visible"
				cssClass="ct_input_g" cssErrorClass="text medium error">
				<form:option value="Y"><anyframe:message
				code="viewresource.ui.selbox.visible.y" /></form:option>	
				<form:option value="N"><anyframe:message
				code="viewresource.ui.selbox.visible.n" /></form:option>
			</form:select>
			<form:errors path="visible" /></td>
		</tr>
		<tr>
			<td height="1" colspan="3" bgcolor="#D6D6D6"></td>
		</tr>


		<tr>
			<td class="tdHead"><anyframe:message
				code="viewresource.ui.label.viewinfo" /></td>
			<td bgcolor="#D6D6D6" width="1"></td>
			<td class="tdin"><form:input path="viewInfo" id="viewInfo"
				cssClass="ct_input_g" cssErrorClass="text medium error"
				maxlength="255" size="55" />&nbsp;<form:errors path="viewInfo"
				cssClass="error" /></td>
		</tr>
		<tr>
			<td height="1" colspan="3" bgcolor="#D6D6D6"></td>
		</tr>
	</table>
	<!-- End of Input Field -->

	<!-- Begin Button Field -->
	<table width="572" border="0" cellpadding="0" cellspacing="0"
		style="margin-top: 10px;">
		<tr>
			<td align="right">
			<table width="100" height="22" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<c:if test="${not empty viewResources.viewResourceId}">
						<td width="6%" align="right" class="tdBtnRight">
						<table height="22" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="18"><img
									src="<c:url value='/images/btn/update.gif'/>" width="18"
									height="22"></td>
								<td background="<c:url value='/images/btn/bg_btn.gif'/>"
									class="boldBtn"><a href="#" name="updateResource"><fmt:message
									key="button.update" /></a></td>
								<td width="10" align="right"><img
									src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10"
									height="22"></td>
							</tr>
						</table>
						</td>
						<td width="6%" align="right" class="tdBtnRight">
						<table height="22" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="18"><img
									src="<c:url value='/images/btn/btn_delete.gif'/>" width="18"
									height="22"></td>
								<td background="<c:url value='/images/btn/bg_btn.gif'/>"
									class="boldBtn"><a href="#" name="deleteResource"><fmt:message
									key="button.delete" /></a></td>
								<td width="10" align="right"><img
									src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10"
									height="22"></td>
							</tr>
						</table>
						</td>
					</c:if>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<!-- End of Button Field -->
</form:form>
</body>
</html>