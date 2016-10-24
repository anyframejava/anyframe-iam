<%@ taglib uri='http://www.sds.samsung.com/tags' prefix='anyframe' %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><anyframe:message code="viewresource.ui.title.viewresources" /></title>

<script language="javascript" src="<c:url value='/js/CommonScript.js'/>"></script>

<jsp:include page="/common/jstree-include.jsp" />
<jsp:include page="/common/jqueryui-include.jsp" />
<jsp:include page="/common/jqgrid-include.jsp" />
<jsp:include page="/common/jquery-autocomplete-include.jsp" />

<script type="text/javascript" src="<c:url value='/validator.do'/>"></script>
<validator:javascript formName="viewResources" staticJavascript="false" xhtml="true" cdata="false"/>

<script type="text/javascript">
jQuery(document).ready(
		function(){

			// button click event
			$('[name=addResource]').click(
					function(){
						var isAvailable = document.viewResources.isAvailable;
						if(isAvailable.value == "false"){
							alert("<anyframe:message code='user.ui.alert.savebeforeconfirm' />");
							return;
						}
						
						if(!validateViewResources(document.viewResources)){
							return;
						}
					    document.viewResources.action="<c:url value='/viewresources/add.do?'/>";
					    document.viewResources.submit();
					});
			$('[name=updateResource]').click(
					function(){
						if(!validateViewResources(document.viewResources)){
							return;
						}
					    document.viewResources.action="<c:url value='/viewresources/update.do?'/>";
					    document.viewResources.submit();
					});
			$('[name=deleteResource]').click(
					function(){
						if(confirm('<anyframe:message code="resource.ui.alert.confirmtodelete" />')) {
						    document.viewResources.action="<c:url value='/viewresources/deleteFromDetail.do?'/>";
						    document.viewResources.submit();
						}
					});
			$('[name=movetoback]').click(
					function(){
						location.href="<c:url value='/viewresources/list.do?'/>";
					});
		});

function checkforId() {
	var pop = 0;
	var viewResourceId = document.viewResources.viewResourceId;
	url = "<c:url value='/viewresources/duplicationconfirm.do?'/>" + "&viewResourceId=" + viewResourceId.value;
	pop = window.open(url, "CheckID","top=100, left=200, width=300, height=200,scrollbars=no,resizable=no");
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
body {
	background-color: #E9ECF1;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>
</head>

<body text="#000000">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td width="10" rowspan="3">&nbsp;</td>
    <td height="6"></td>
  </tr>
  <tr>
    <td height="30" valign="bottom" background="<c:url value='/images/content/bg_tab.gif'/>" style="padding-left:10px">
      <table height="24" border="0" cellpadding="0" cellspacing="0">
              <tr height="21">
                <td width="145" height="27" align="center" valign="bottom"  background="<c:url value='/images/content/tab_menu1.gif'/>" bgcolor="#EDEDED" class="blkbold">viewResource List </td>
              </tr>
    </table></td>
  </tr>
  <tr>
    <td>
	<!--  Begin of Contents -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 13px; margin-left:10px">
	<!-- begin of title -->
	<tr>
		<td valign="top">
	    	<table width="796" border="0" cellspacing="0" cellpadding="0">
				<tr>	
				    <td class="title" style="padding-left:21px">
				    	<c:if test="${empty viewResources.viewResourceId}">
							<anyframe:message code="viewresource.ui.title.addviewresources" />
							<c:set var="readonly" value="false" />
						</c:if>
						<c:if test="${not empty viewResources.viewResourceId}">	
							<anyframe:message code="viewresource.ui.title.updateviewresources" />
							<c:set var="readonly" value="true" />
						</c:if>
					</td>
				</tr>
			</table>
		</td>	
	</tr>
	<!-- End of Title -->

	<!-- Begin input field -->
	<tr>
	    <td style="padding-top: 5px;">
			<form:form commandName="viewResources" method="post" action="viewResources.do" id="viewResources" name="viewResources">
			<table width="796" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" >
				<tr><td height="2" colspan="4" bgcolor="#A2BACE"></td></tr>

				<tr>
					<td width="150" class="tdHead"><anyframe:message code="viewresource.ui.label.viewresourceid" /></td>
					<td bgcolor="#B6CDE4" width="1"></td>
					<td class="tdin">
						<form:input path="viewResourceId" id="viewResourceId" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="50" readonly="${readonly}" size="30" />&nbsp;<form:errors path="viewResourceId" cssClass="error" />
					</td>
					<td width="320" align="left">
							<c:if test="${empty viewResources.viewResourceId}">
					  			<table height="19" border="0" cellpadding="0" cellspacing="0">
					                <tr>
					                  <td width="18"><img src="<c:url value='/images/btn/btn_checkid.gif'/>" width="21" height="19"></td>
					                  <td background="<c:url value='/images/btn/bg_btnsmall.gif'/>" class="smallBtn"><a href="javascript:checkforId();" name="selectGroup"><anyframe:message code='user.ui.link.checkid' /></a></td>
					                  <td width="9" align="right"><img src="<c:url value='/images/btn/btn_tail.gif'/>" width="9" height="19"></td>
					                </tr>
					            </table>
							</c:if>
						<input type="hidden" name="isAvailable" value="false">
					</td>
				</tr>		
				<tr><td height="1" colspan="4" bgcolor="#D6D6D6"></td></tr>
								
				<tr>
					<td class="tdHead">
						<anyframe:message code="viewresource.ui.label.viewname" />
					</td>
					<td bgcolor="#D6D6D6" width="1"></td>
					<td class="tdin" colspan="2">									        
						<form:input path="viewName" id="viewName" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="50" size="30" />&nbsp;<form:errors path="viewName" cssClass="error" /></td>
				</tr>
				<tr><td height="1" colspan="4" bgcolor="#D6D6D6"></td></tr>
				
				<tr>
					<td class="tdHead">
					  <anyframe:message code="viewresource.ui.label.category" /></td>
					<td bgcolor="#D6D6D6" width="1"></td>
					<td class="tdin" colspan="2">									        
			        	<form:input path="category" id="category" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="255" size="61" />&nbsp;<form:errors path="category" cssClass="error" /></td>
				</tr>
				<tr><td height="1" colspan="4" bgcolor="#D6D6D6"></td></tr>

				
				<tr>
					<td class="tdHead">
						<anyframe:message code="viewresource.ui.label.description" /></td>
					<td bgcolor="#D6D6D6" width="1"></td>
					<td class="tdin" colspan="2">									        
						<form:input path="description" id="description" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="255" size="61" />&nbsp;<form:errors path="description" cssClass="error" />
					</td>
				</tr>
				<tr><td height="1" colspan="4" bgcolor="#D6D6D6"></td></tr>
				
				<tr>
					<td class="tdHead">
						<anyframe:message code="viewresource.ui.label.viewinfo" /></td>
					<td bgcolor="#D6D6D6" width="1"></td>
					<td class="tdin" colspan="2">									        
			        <form:input path="viewInfo" id="viewInfo" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="255" size="61" />&nbsp;<form:errors path="viewInfo" cssClass="error" /></td>
				</tr>
				<tr><td height="1" colspan="4" bgcolor="#D6D6D6"></td></tr>
			</table>
			</form:form>
		</td>
	</tr>
	<!-- End of Input Field -->
	
	<!-- Begin Button Field -->
	<tr>
	    <td valign="top">
			<table width="796" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" style="margin-top:10px;">
				<tr>
					<td align="right">
						<table width="100" height="22" border="0" cellpadding="0" cellspacing="0">
							<tr>
							<c:if test="${empty viewResources.viewResourceId}">		
								<td width="82%" align="right" class="tdBtnRight">	
									<table height="22" border="0" cellpadding="0" cellspacing="0">
			              				<tr>
			                				<td width="18"><img src="<c:url value='/images/btn/btn_add.gif'/>" width="18" height="22"></td>
			                				<td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="addResource"><fmt:message key="button.add"/></a></td>
			                				<td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
			              				</tr>
			           				 </table>
		           				  </td></c:if>
								<c:if test="${not empty viewResources.viewResourceId}">
								<td width="6%" align="right" class="tdBtnRight">
									<table height="22" border="0" cellpadding="0" cellspacing="0">
			              				<tr>
							                <td width="18"><img src="<c:url value='/images/btn/update.gif'/>" width="18" height="22"></td>
							                <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="updateResource"><fmt:message key="button.update"/></a></td>
							                <td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
							            </tr>
			            			</table>
			            			</td></c:if>	
								<c:if test="${not empty viewResources.viewResourceId}">
								<td width="6%" align="right"  class="tdBtnRight">
									<table height="22" border="0" cellpadding="0" cellspacing="0">
								          <tr>
								            <td width="18"><img src="<c:url value='/images/btn/btn_delete.gif'/>" width="18" height="22"></td>
								            <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="deleteResource"><fmt:message key="button.delete"/></a></td>
								            <td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
								          </tr>
				        			</table>
				        			</td></c:if>	
							  	<td width="6%" align="right"  class="tdBtnRight">
									<table height="22" border="0" cellpadding="0" cellspacing="0">
							          <tr>
							            <td width="18"><img src="<c:url value='/images/btn/btn_cancel.gif'/>" width="18" height="22"></td>
							            <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="movetoback"><fmt:message key="button.cancel"/></a></td>
							            <td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
							          </tr>
			        				</table>
			        			</td>
							</tr>
					    </table>
						</td>
				  </tr>
			</table>
		</td>
	</tr>
	<!-- End of Button Field -->
</table></td>
  </tr>
  <tr>
    <td colspan="2">
      <table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#E9ECF1">
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