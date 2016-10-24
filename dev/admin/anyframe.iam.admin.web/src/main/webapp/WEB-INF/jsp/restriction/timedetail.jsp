<%@ taglib uri='http://www.anyframejava.org/tags' prefix='anyframe' %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><anyframe:message code="restrictedtimes.ui.title.restrictedtimesdetail" /></title>

<script language="javascript" src="<c:url value='/js/CommonScript.js'/>"></script>

<jsp:include page="/common/jquery-include.jsp" />

<script type="text/javascript" src="<c:url value='/validator.do'/>"></script>
<validator:javascript formName="restrictedTimes" staticJavascript="false" xhtml="true" cdata="false"/>

<script type="text/javascript">
<!--
jQuery(document).ready( function(){
	$("#startDate").datepicker({ 
		dateFormat: 'yymmdd',
		showButtonPanel: true
	});
	
	$("#endDate").datepicker({ 
		dateFormat: 'yymmdd',
		showButtonPanel: true
	});
	if(document.restrictedtimes.startDate.value!=""){
		var year = document.restrictedtimes.startDate.value.substr(0,4);
		var month = document.restrictedtimes.startDate.value.substr(4,2) - 1;
		var day = document.restrictedtimes.startDate.value.substr(6,2);
		$("#endDate").datepicker('option', 'minDate', new Date(year, month, day));
	}
	
	$('[name=addItem]').click( function(){
		if(!validateRestrictedTimes(document.restrictedtimes)){
			return;
		}
	    document.restrictedtimes.action="<c:url value='/restriction/add.do?'/>";
	    document.restrictedtimes.submit();
	});
	
	$('[name=updateItem]').click( function(){
		if(!validateRestrictedTimes(document.restrictedtimes)){
			return;
		}
	    document.restrictedtimes.action="<c:url value='/restriction/update.do?'/>";
	    document.restrictedtimes.submit();
	});

	$('[name=deleteItem]').click( function(){
		if(confirm('<anyframe:message code="restrictedtimes.ui.alert.confirmtodelete" />')) {
		    document.restrictedtimes.action="<c:url value='/restriction/deleteFromDetail.do?'/>";
		    document.restrictedtimes.submit();
		}
	});

	$('[name=movetoback]').click( function(){
		location.href="<c:url value='/restriction/list.do?'/>";
	});
});

function isCrash(frm){
	var startTime = document.restrictedtimes.startTime;
	var endTime = document.restrictedtimes.endTime;
	if(frm.value == "crash"){
		startTime.value = "000000";
		endTime.value = "235959";
		startTime.readOnly = true;
		endTime.readOnly = true;
	}else{
		startTime.readOnly = false;
		endTime.readOnly = false;
	}
}

function changeEndDate(frm){
	var year = frm.value.substr(0,4);
	var month = frm.value.substr(4,2) - 1;
	var day = frm.value.substr(6,2);
	$("#endDate").datepicker('option', 'minDate', new Date(year, month, day));
}

function endDateFirst(frm){
	if(document.restrictedtimes.startDate.value=="" || document.restrictedtimes.startDate.value==null){
		alert("<anyframe:message code='restrictedtimes.ui.alert.enddatefirst' />");
		frm.value="";
		return;
	}
}
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

<body text="#000000">
<!--  Begin of Contents -->
<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td width="10" rowspan="3">&nbsp;</td>
    <td height="6"></td>
  </tr>
  <tr>
    <td height="30" valign="bottom" background="<c:url value='/images/content/bg_tab.gif'/>" style="padding-left:10px">
      <table height="24" border="0" cellpadding="0" cellspacing="0">
              <tr height="21">
                <td width="145" height="27" align="center" valign="bottom"  background="<c:url value='/images/content/tab_menu1.gif'/>" bgcolor="#EDEDED" class="blkbold">Time List  </td>
              </tr>
    </table></td>
  </tr>
  <tr>
    <td><table width="796" border="0" cellspacing="0" cellpadding="0" style="margin-top: 13px; margin-left:10px">
      <tr>
        <!-- Begin Title -->
        <td class="title" style="padding-left:21px"><c:if
					test="${empty restrictedtimes.timeId}">
            <anyframe:message code="restrictedtimes.ui.title.restrictedtimesadd" />
            <c:set var="readonly" value="false" />
          </c:if>
            <c:if test="${not empty restrictedtimes.timeId}">
              <anyframe:message code="restrictedtimes.ui.title.restrictedtimesupdate" />
              <c:set var="readonly" value="true" />
          </c:if></td>
        <!-- End of Title -->
      </tr>
      <tr>
        <td></td>
      </tr>
      <tr>
        <td style="padding-top: 5px;"><!-- Begin input field -->
            <form:form commandName="restrictedtimes" method="post" id="restrictedtimes" name="restrictedtimes">
              <table width="796" border="0" cellspacing="0" cellpadding="0" >
                <tr>
                  <td height="2" colspan="3" bgcolor="#A2BACE"></td>
                </tr>
                <c:if test="${not empty restrictedtimes.timeId}">
                  <tr>
                    <td width="150" class="tdHead"><anyframe:message code="restrictedtimes.ui.label.timeid" /></td>
                    <td bgcolor="#B6CDE4" width="1"></td>
                    <td class="tdin"><form:input path="timeId" id="timeId" cssClass="ct_input_g" cssErrorClass="text medium error" readonly="true" maxlength="10"/>
                      &nbsp;
                      <form:errors path="timeId" cssClass="error" />                    </td>
                  </tr>
                  <tr>
                    <td height="1" colspan="3" bgcolor="#D6D6D6"></td>
                  </tr>
                </c:if>
                <tr>
                  <td class="tdHead"><anyframe:message code="restrictedtimes.ui.label.timetype" /></td>
                  <td bgcolor="#B6CDE4" width="1"></td>
                  <td class="tdin"><form:select path="timeType" id="timeType" cssClass="ct_input_g" onchange="javascript:isCrash(this);">
                      <form:option value="improve">
                        <anyframe:message code="restrictedtimes.ui.selectbox.timetype.improve" />
                      </form:option>
                      <form:option value="daily">
                        <anyframe:message code="restrictedtimes.ui.selectbox.timetype.daily" />
                      </form:option>
                      <form:option value="weekend">
                        <anyframe:message code="restrictedtimes.ui.selectbox.timetype.weekend" />
                      </form:option>
                      <form:option value="holiday">
                        <anyframe:message code="restrictedtimes.ui.selectbox.timetype.holiday" />
                      </form:option>
                      <form:option value="crash">
                        <anyframe:message code="restrictedtimes.ui.selectbox.timetype.crash" />
                      </form:option>
                    </form:select>
                      <form:errors path="timeType" />                  </td>
                </tr>
                
                <tr>
                  <td height="1" colspan="3" bgcolor="#D6D6D6"></td>
                </tr>
                <tr>
					<td class="tdHead">System Name</td>
					<td bgcolor="#B6CDE4" width="1"></td>
					<td class="tdin">
						<select name="systemName" disabled="disabled" class="ct_input_g">
							<c:forEach var="item" items="${systemNames }">
								<option value="${item }">${item }</option>
							</c:forEach>
						</select>
					</td>
                </tr>
                
                <tr>
                  <td height="1" colspan="3" bgcolor="#D6D6D6"></td>
                </tr>
                <tr>
                  <td class="tdHead"><anyframe:message code="restrictedtimes.ui.label.startdate" /></td>
                  <td bgcolor="#B6CDE4" width="1"></td>
                  <td class="tdin"><form:input path="startDate" id="startDate" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="8" size="20" readonly="true" onchange="javascript:changeEndDate(this);" />
                    &nbsp;
                    <form:errors path="startDate" cssClass="error" />                  </td>
                </tr>
                <tr>
                  <td height="1" colspan="3" bgcolor="#D6D6D6"></td>
                </tr>
                <tr>
                  <td class="tdHead"><anyframe:message code="restrictedtimes.ui.label.starttime" /></td>
                  <td bgcolor="#B6CDE4" width="1"></td>
                  <td class="tdin"><form:input path="startTime" id="startTime" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="6" size="20" readonly="${timeTypeReadOnly}" />
                    &nbsp;
                    <form:errors path="startTime" cssClass="error" />                  </td>
                </tr>
                <tr>
                  <td height="1" colspan="3" bgcolor="#D6D6D6"></td>
                </tr>
                <tr>
                  <td class="tdHead"><anyframe:message code="restrictedtimes.ui.label.enddate" /></td>
                  <td bgcolor="#B6CDE4" width="1"></td>
                  <td class="tdin"><form:input path="endDate" id="endDate" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="8" size="20" readonly="true" onchange="javascript:endDateFirst(this);" />
                    &nbsp;
                    <form:errors path="endDate" cssClass="error" />                  </td>
                </tr>
                <tr>
                  <td height="1" colspan="3" bgcolor="#D6D6D6"></td>
                </tr>
                <tr>
                  <td class="tdHead"><anyframe:message code="restrictedtimes.ui.label.endtime" /></td>
                  <td bgcolor="#B6CDE4" width="1"></td>
                  <td class="tdin"><form:input path="endTime" id="endTime" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="6" size="20" readonly="${timeTypeReadOnly}" />
                    &nbsp;
                    <form:errors path="endTime" cssClass="error" />                  </td>
                </tr>
                <tr>
                  <td height="1" colspan="3" bgcolor="#D6D6D6"></td>
                </tr>
                <tr>
                  <td class="tdHead"><anyframe:message code="restrictedtimes.ui.label.description" /></td>
                  <td bgcolor="#B6CDE4" width="1"></td>
                  <td class="tdin"><form:input path="description" id="description" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="100" size="50" />
                    &nbsp;
                    <form:errors path="description" cssClass="error" />                  </td>
                </tr>
                <tr>
                  <td height="1" colspan="3" bgcolor="#B6CDE4"></td>
                </tr>
              </table>
              <!-- Begin Button Field -->
			  <table width="796" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" style="margin-top:10px;">
				<tr>
					<td align="right">
						<table width="100" height="22" border="0" cellpadding="0" cellspacing="0">
							<tr>
							<c:if test="${empty restrictedtimes.timeId}">	
								<td width="82%" align="right" class="tdBtnRight">	
									<table height="22" border="0" cellpadding="0" cellspacing="0">
			              				<tr>
			                				<td width="18"><img src="<c:url value='/images/btn/btn_add.gif'/>" width="18" height="22"></td>
			                				<td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="addItem"><fmt:message key="button.add"/></a></td>
			                				<td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
			              				</tr>
		           				  </table>
	           				  </td></c:if>
								<c:if test="${not empty restrictedtimes.timeId}">
								<td width="6%" align="right" class="tdBtnRight">
									<table height="22" border="0" cellpadding="0" cellspacing="0">
			              				<tr>
							                <td width="18"><img src="<c:url value='/images/btn/update.gif'/>" width="18" height="22"></td>
							                <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="updateItem"><fmt:message key="button.update"/></a></td>
							                <td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
							            </tr>
			            			</table>
		            			  </td></c:if>	
								<c:if test="${not empty restrictedtimes.timeId}">
								<td width="6%" align="right"  class="tdBtnRight">
									<table height="22" border="0" cellpadding="0" cellspacing="0">
								          <tr>
								            <td width="18"><img src="<c:url value='/images/btn/btn_delete.gif'/>" width="18" height="22"></td>
								            <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="deleteItem"><fmt:message key="button.delete"/></a></td>
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
			        				</table></td>
							</tr>
					   </table> </td>
			  </tr>
		  </table>
              <!-- End of Button Field -->
            </form:form>
            <!-- End of Input Field -->        </td>
      </tr>
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
<!-- End of Input Field -->


</body>
</html>