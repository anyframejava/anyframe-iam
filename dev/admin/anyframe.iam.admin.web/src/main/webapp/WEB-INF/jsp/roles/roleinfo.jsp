<%@ taglib uri='http://www.sds.samsung.com/tags' prefix='anyframe' %>
<%@ include file="/common/taglibs.jsp"%> 
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Role Information</title>

<jsp:include page="/common/jstree-include.jsp" />
<jsp:include page="/common/jqgrid-include.jsp" />
<jsp:include page="/common/jqueryui-include.jsp" />

<script type="text/javascript" src="<c:url value='/validator.do'/>"></script>
<validator:javascript formName="roles" staticJavascript="false" xhtml="true" cdata="false"/>

<script type="text/javascript">
<!--
jQuery(document).ready(
		function(){

			$('[name=addRole]').click(
					function(){
						alert("add Role");
					});
			
			$('[name=updateRole]').click(
					function(){
						if(!validateRoles(document.roles)){
							return;
						}
						document.roles.action="<c:url value='/roleinformation/update.do?'/>";
						document.roles.submit();
					});
			
			$('[name=deleterole]').click(
					function(){
						if(confirm('<anyframe:message code="resource.ui.alert.confirmtodelete" />')) {
							alert("delete role");

							/* role deletion 이후 refresh 기능 필요 */
						}
					});

			$('[name=resetinfo]').click(
					function(){
						alert("reset");
					});
			
		});
//-->
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
</style></head>
<body>
<c:if test="${empty roles.roleId }">

<c:set var="readonly" value="false" />
</c:if>
<c:if test="${not empty roles.roleId}">

<c:set var="readonly" value="true" />
</c:if>
<!-- Input Field  -->

<form:form commandName="roles" method="post" action="roleinfomation.do" id="roles" name="roles">
	<table width="572" border="0" cellspacing="0" cellpadding="0" style="margin-top: 13px;">
		<tr><td height="2" colspan="3" bgcolor="#A2BACE"></td></tr>
		<tr>
			<td width="134" class="tdHead"><anyframe:message code="roleinfo.ui.label.roleid" /></td><td width="1" bgcolor="#B6CDE4"></td>
			<td class="tdin">
				<form:input path="roleId" id="roleId" cssClass="ct_input_g" cssErrorClass="text medium error" readonly="${readonly}" maxlength="10"/>&nbsp;<form:errors path="roleId" />
			</td>
		</tr>		
		<tr><td height="1" colspan="3" bgcolor="#D8D8D8"></td></tr>
		<tr>
			<td width="134" class="tdHead"><anyframe:message code="roleinfo.ui.label.rolename" /></td><td bgcolor="#D6D6D6" width="1"></td>
			<td class="tdin">									        
	        	<form:input path="roleName" id="roleName" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="50"/>&nbsp;<form:errors path="roleName" />
			</td>
		</tr>
		<tr><td height="1" colspan="3" bgcolor="#D8D8D8"></td></tr>
		<tr>
			<td width="134" class="tdHead"><anyframe:message code="roleinfo.ui.label.description" /></td><td bgcolor="#D6D6D6" width="1"></td>
			<td class="tdin">									        
	        	<form:input path="description" id="description" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="100" size="61" />&nbsp;<form:errors path="description" />
			</td>
		</tr>
		<tr><td height="1" colspan="3" bgcolor="#B6CDE4"></td></tr>	
		<tr><td height="10" colspan="3" bgcolor="#FFFFFF"></td></tr>	
	</table>

<!-- Begin Button Field -->
<table width="572" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="right" class="tdBtnRight">
			<c:if test="${not empty roles.roleId}">
				<table height="22" border="0" cellpadding="0" cellspacing="0">
	              <tr>
	                <td width="18"><img src="<c:url value='/images/btn/update.gif'/>" width="18" height="22"></td>
	                <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="updateRole"><fmt:message key="button.update"/></a></td>
	                <td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
	              </tr>
	            </table>
			    <!--table height="22" border="0" cellpadding="0" cellspacing="0">
	              <tr>
	                <td width="18"><img src="<c:url value='/images/btn/btn_delete.gif'/>" width="18" height="22"></td>
	                <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="deleteRole"><fmt:message key="button.delete"/></a></td>
	                <td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
	              </tr>
	            </table-->			
       	</c:if>
		</td>
	</tr>
</table>
<!-- End of Button Field -->

</form:form>
</body>
</html>