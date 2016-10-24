<%@ taglib uri='http://www.sds.samsung.com/tags' prefix='anyframe' %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><anyframe:message code="restrictedtimes.ui.title.timeexclusiondetail" /></title>

<script language="javascript" src="<c:url value='/js/CommonScript.js'/>"></script>

<jsp:include page="/common/jquery-include.jsp" />
<jsp:include page="/common/jstree-include.jsp" />
<jsp:include page="/common/jquery-autocomplete-include.jsp" />

<script type="text/javascript" src="<c:url value='/validator.do'/>"></script>
<validator:javascript formName="restrictedTimes" staticJavascript="false" xhtml="true" cdata="false"/>

<script type="text/javascript">
<!--
	jQuery(document).ready( function(){
		$('[name=addItem]').click( function(){
				//if(!validateRestrictedTimes(document.timesresourcesexclusion)){
					//return;
				//}
				if (!validateInput()) return;
			    document.timesresourcesexclusion.action="<c:url value='/restriction/timeexclusion/save.do?'/>";
			    document.timesresourcesexclusion.submit();
		});
	
		$('[name=updateItem]').click( function(){
				//if(!validateRestrictedTimes(document.timesresourcesexclusion)){
					//return;
				//}
				if (!validateInput()) return;
			    document.timesresourcesexclusion.action="<c:url value='/restriction/timeexclusion/save.do?'/>";
			    document.timesresourcesexclusion.submit();
		});
	
		$('[name=deleteItem]').click( function(){
				if(confirm('<anyframe:message code="restrictedtimes.ui.alert.confirmtodelete" />')) {
				    document.timesresourcesexclusion.action="<c:url value='/restriction/timeexclusion/deleteFromDetail.do?'/>";
				    document.timesresourcesexclusion.submit();
				}
		});
	
		$('[name=movetoback]').click( function(){
				location.href="<c:url value='/restriction/timeexclusion/list.do?'/>";
		});
	
		/* Mapping Resources */
		$("[name=selectTimeId]").click( function() {
			window.open("<c:url value='/restriction/timeexclusion/listPopUp.do?' />", 'timesList', 'height=370, width=800');
		});
	
		/* Delete Role */
		$("[name=deleteRole]").click( function() {
			var boxLength = document.timesresourcesexclusion.roleId.length;
			arrSelected = new Array();
			var count = 0;
			var count2 = 0;
			var count3 = 0;
			var del_value = "";
			for(i = 0 ; i < boxLength ; i++) {
				if(document.timesresourcesexclusion.roleId.options[i].selected) {
					arrSelected[count] = document.timesresourcesexclusion.roleId.options[i].value;
				}
				count++;
			}
			
			var x;
			for(i = 0 ; i < boxLength ; i++) {
				for(x = 0 ; x < arrSelected.length ; x++) {
					if(document.timesresourcesexclusion.roleId.options[i].value == arrSelected[x]) {
						var selectedText = document.timesresourcesexclusion.roleId.options[i].text;
						var selectedValue = document.timesresourcesexclusion.roleId.options[i].value;
						
						if(count3 == 0) {
							del_value = document.timesresourcesexclusion.roleId.options[i].value;
						}
						else {
							del_value = del_value + "," + document.timesresourcesexclusion.roleId.options[i].value;
						}
						
						document.timesresourcesexclusion.roleId.options[i] = null;
						count3++;
					}
					count2++;
				}
				boxLength = document.timesresourcesexclusion.roleId.length;
			}
		});
	});

	function validateInput() {
		if(document.timesresourcesexclusion.timeId.value == "" || document.timesresourcesexclusion.resourceId.value == "") {
			alert("Time ID and Resource ID are required field.");
			return false;
		}
	    var boxLength = document.timesresourcesexclusion.roleId.length;
	    var obj = document.timesresourcesexclusion.roleId.options;
	    
		if(boxLength != 0) {
			for (i = 0; i < boxLength; i++) {
				obj[i].selected = true;
			}
		} else {
			alert("At least, one roleId in roles list must be selected.");
			return false;
		}
		return true;
	}
	
	function timeIdChanged() {
		$.getJSON("<c:url value='/restriction/timeexclusion/findrolebytimeresource.do?timeId='/>" 
					+ document.timesresourcesexclusion.timeId.value
					+ "&resourceId=" + document.timesresourcesexclusion.resourceId.value,
				function(data) {
				document.timesresourcesexclusion.roleId.length = 0; // initialize selectbox
				$.each(data.roles, function(entryIndex, entry) {
					var boxLength = document.timesresourcesexclusion.roleId.length;
					var newOption = new Option(entry['roleName'], entry['roleId'], false, false);
					document.timesresourcesexclusion.roleId.options[boxLength] = newOption;
				});
		});
	}
//-->
</script>

<script type="text/javascript">
<!--
	$(function () {
		$("#role").tree({
			data	: {
				type	: "json",
				async	: true,
				opts 	: {
					method	: "POST",
					url		: "<c:url value='/roles/listData.do?' />"
				}
			},
			ui	: {
				theme_name : "apple"
			},
			types : {
				"default" : {
					draggable : false
				}
			},
			callback	: {
				beforedata	: function(NODE, TREE_OBJ) { 
					return {
						id : $(NODE).attr("id") || "0",
						roleName : document.getElementById("roleName").value,
						searchClickYn : document.getElementById("searchClickYn").value
					} 
				},
				ondblclk	: function(NODE,TREE_OBJ) {
	        		addrole($.tree.focused().get_text(NODE), NODE.id);
				},
				error 		: function(TEXT){
					var userId = document.timesresourcesexclusion.userId.value;
					if(TEXT.match('parsererror') != null){
						if(userId == "" || userId == null){
							location.href = "<c:url value='/login/relogin.do?inputPage=/users/addView.do?'/>";
						} else{
							location.href = "<c:url value='/login/relogin.do?inputPage=/users/get.do?&userId='/>" + userId;
						}
						return;
					}
					alert(TEXT);
				}
			}
		});

		$("[name=searchUsers]").click(
				function() {
					document.getElementById("searchClickYn").value = "Y";
					$.tree.focused().refresh();
					document.getElementById("searchClickYn").value = "N";
		});
	});

	function addrole(selectedText,selectedValue) {
		var boxLength = document.timesresourcesexclusion.roleId.length;
		var isNew = true;
		
		if(boxLength != 0) {
			for(var i = 0 ; i < boxLength ; i++) {
				thisitem = document.timesresourcesexclusion.roleId.options[i].text;
				if(thisitem == selectedText) {
					isNew = false;
					break;
				}
			}
		} 

		if(isNew) {
			newoption = new Option(selectedText, selectedValue, false, false);
			document.timesresourcesexclusion.roleId.options[boxLength] = newoption;
		}
	}
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
</style>
</head>

<body text="#000000">
<!--  Begin of Contents -->
<table width="796" border="0" cellspacing="0" cellpadding="0">
	<!-- Begin Title -->
	<tr>
		<td valign="top">
	    	<table width="796" border="0" cellspacing="0" cellpadding="0" style="margin-top: 5px;">
				<tr>
					<td class="title" style="padding-left:21px">
						<c:if test="${empty timesresourcesexclusion.id.timeId}">
						 	<anyframe:message code="restrictedtimes.ui.title.timeexclusionadd" />
						 	<c:set var="readonly" value="false" />
						</c:if>
						<c:if test="${not empty timesresourcesexclusion.id.timeId}">
							<anyframe:message code="restrictedtimes.ui.title.timeexclusionupdate" />
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
			<form:form commandName="timesresourcesexclusion" method="post" id="timesresourcesexclusion" name="timesresourcesexclusion">
				<table width="796" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" >
					<tr><td height="2" colspan="8" bgcolor="#A2BACE"></td></tr>
					<tr>
						<td width="120" class="tdHead"><anyframe:message code="restrictedtimes.ui.label.timeid" /></td>
					  	<td bgcolor="#B6CDE4" width="2"></td>
						<td width="140" class="tdin">
							<form:input path="id.timeId" id="timeId" readonly="true" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="10" size="20"/>&nbsp;<form:errors path="id.timeId" cssClass="error" /></td>
						<td bgcolor="#D6D6D6" width="1"></td>
						<td width="120" class="tdHead"><anyframe:message code="restrictedtimes.ui.label.resourceid" /></td>
						<td bgcolor="#D6D6D6" width="1"></td>
						<td width="145" class="tdin">
							<form:input path="id.resourceId" id="resourceId" readonly="true" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="10" size="20"/>&nbsp;<form:errors path="id.resourceId" cssClass="error" /></td>
					  	<td width="260" align="left">
					  		<a href="#"  name="selectTimeId" class="searchBtn"><anyframe:message code="user.ui.btn.search" /></a></td>					  	
					</tr>		
					<tr><td height="1" colspan="8" bgcolor="#D6D6D6"></td></tr>
					<tr>
					  	<td class="tdHead"><anyframe:message code="restrictedtimes.ui.label.timetype" /></td>
						<td bgcolor="#D6D6D6" width="2"></td>
						<td colspan="6" class="tdin">
				        <form:select path="restrictedTimes.timeType" id="timeType" cssClass="ct_input_g" disabled="true">
				        	<form:option value="crash"><anyframe:message code="restrictedtimes.ui.selectbox.timetype.crash" /></form:option>
				        	<form:option value="improve"><anyframe:message code="restrictedtimes.ui.selectbox.timetype.improve" /></form:option>
				        	<form:option value="daily"><anyframe:message code="restrictedtimes.ui.selectbox.timetype.daily" /></form:option>
				        	<form:option value="weekend"><anyframe:message code="restrictedtimes.ui.selectbox.timetype.weekend" /></form:option>
				        	<form:option value="holiday"><anyframe:message code="restrictedtimes.ui.selectbox.timetype.holiday" /></form:option>
				        </form:select><form:errors path="restrictedTimes.timeType" />								        
				    	</td>
					</tr>
					<tr><td height="1" colspan="8" bgcolor="#D6D6D6"></td></tr>
					<tr>
					  	<td class="tdHead">
						<anyframe:message code="restrictedtimes.ui.label.startdate" /></td>
						<td bgcolor="#D6D6D6" width="2"></td>
						<td colspan="6" class="tdin">
							<form:input path="restrictedTimes.startDate" id="startDate" readonly="true" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="8" size="20" />&nbsp;<form:errors path="restrictedTimes.startDate" cssClass="error" />
						</td>
					</tr>
					<tr><td height="1" colspan="8" bgcolor="#D6D6D6"></td></tr>
					<tr>
					  	<td class="tdHead">
						<anyframe:message code="restrictedtimes.ui.label.starttime" /></td>
						<td bgcolor="#D6D6D6" width="2"></td>
						<td colspan="6" class="tdin">
							<form:input path="restrictedTimes.startTime" id="startTime" readonly="true" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="6" size="20" />&nbsp;<form:errors path="restrictedTimes.startTime" cssClass="error" />
						</td>
					</tr>
					<tr><td height="1" colspan="8" bgcolor="#D6D6D6"></td></tr>
					<tr>
					  	<td class="tdHead">
						<anyframe:message code="restrictedtimes.ui.label.enddate" /></td>
						<td bgcolor="#D6D6D6" width="2"></td>
						<td colspan="6" class="tdin">
				        <form:input path="restrictedTimes.endDate" id="endDate" readonly="true" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="8" size="20" />&nbsp;<form:errors path="restrictedTimes.endDate" cssClass="error" />
						</td>
					</tr>
					<tr><td height="1" colspan="8" bgcolor="#D6D6D6"></td></tr>
					<tr>
					  	<td class="tdHead">
						<anyframe:message code="restrictedtimes.ui.label.endtime" /></td>
						<td bgcolor="#D6D6D6" width="2"></td>
						<td colspan="6" class="tdin">
				        <form:input path="restrictedTimes.endTime" id="endTime" readonly="true" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="6" size="20" />&nbsp;<form:errors path="restrictedTimes.endTime" cssClass="error" />
						</td>
					</tr>
					<tr><td height="1" colspan="8" bgcolor="#D6D6D6"></td></tr>
					<tr>
					  	<td class="tdHead">
						<anyframe:message code="restrictedtimes.ui.label.description" /></td>
						<td bgcolor="#D6D6D6" width="2"></td>
						<td colspan="6" class="tdin">
						<form:input path="restrictedTimes.description" id="description" readonly="true" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="100" size="50" />&nbsp;<form:errors path="restrictedTimes.description" cssClass="error" />
						</td>
					</tr>
					<tr><td height="1" colspan="8" bgcolor="#D6D6D6"></td></tr>
					<tr>
					  	<td class="tdHead"><anyframe:message code="restrictedtimes.ui.label.roleslist" /></td>
						<td bgcolor="#D6D6D6" width="2"></td>
						<td colspan="6" >
						    <table width="668" border="0" cellpadding="0" cellspacing="0" class="tablemargin">
						      <tr height="25">
						        <td width="26" background="<c:url value='/images/bg_treel.gif'/>" style="padding-left:8px"><div id="menuopen"><a class="openBtn" title="Open Branch" a href="javascript:$.tree.focused().open_all();">Open</a></div></td>
						        <td width="26" background="<c:url value='/images/bg_treer3.gif'/>"><div id="menuclose"><a class="closeBtn" title="Close Branch" a href="javascript:$.tree.focused().close_all();">Close</a></div></td>
						        <td width="100" align="left" background="<c:url value='/images/bg_treer.gif'/>" >
									<div id="inputArea">
										<input id="roleName" size="20" class='ct_input_g'>
										<input id="searchClickYn" type="hidden" value="N">
										<script type="text/javascript">
											$("#roleName").autocomplete(
												"<c:url value='/roles/getRoleNameList.do' />", {
												width : 200,
												selectFirst:true,
												mustMatch:true,
												autoFill:true,
												scroll: true
												}
											);
										</script>
									</div>
								</td>
								<td width="130" height="25" align="left" background="<c:url value='/images/bg_treer.gif'/>"><a href="#"  name="searchUsers" class="searchBtn"><anyframe:message code="user.ui.btn.search" /></a></td>
						        <td width="386" align="right" >
									<table height="22" border="0" cellpadding="0" cellspacing="0">
								          <tr>
								            <td width="18"><img src="<c:url value='/images/btn/btn_delete.gif'/>" width="18" height="22"></td>
								            <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="deleteRole"><anyframe:message code='restrictedtimes.ui.btn.removeroles' /></a></td>
								            <td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
								          </tr>
							    	</table>		
						    	</td>
						      </tr>
						      
						      <tr>
						        <td colspan="4" align="left" valign="top">
						        	<div id="role" class="demo" style="overflow:auto;height:137px;width:282px;border:1px solid #c3daf9;">
						        		<span><anyframe:message code='user.ui.tree.span'/></span>
						        		<input id="roleName" size="20" class='ct_input_g'>
										<input id="searchClickYn" type="hidden" value="N">
						        	</div>
						        </td>
						        <td valign="top" class="tdin"><div id="roles" class="demo" style="overflow:auto; height:142px;width:370px;">
						          <select id="roleId" name="roleId" multiple="multiple" class="selbox" style="overflow:auto; height:142px;width:370px;border:1px solid #c3daf9;">
						            <c:forEach var="role" items="${roles}">
						              <option value="${role.roleId}">${role.roleName}</option>
						            </c:forEach>
						          </select>
						        </span></div></td>
						      </tr>
					      </table>
						</td>
					</tr>
					<tr><td height="1" colspan="8" bgcolor="#B6CDE4"></td></tr>	
			</table>
			</form:form>
		</td>
	</tr>
	<!-- End of Input Field -->



	<!-- Begin Button Field -->
	<tr>
		<td>
			<table width="796" border="0" cellspacing="0" cellpadding="0" style="padding-top:5px">
				<tr>
					<td width="63%">
					</td>
					<td align="right">
						<table width="100%" height="22" border="0" cellpadding="0" cellspacing="0">
							<tr><c:if test="${empty timesresourcesexclusion.id.timeId}">		
								<td width="50%" align="right" class="tdBtnRight">
									<table height="22" border="0" cellpadding="0" cellspacing="0">
			              				<tr>
			                				<td width="18"><img src="<c:url value='/images/btn/btn_add.gif'/>" width="18" height="22"></td>
			                				<td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="addItem"><fmt:message key="button.add"/></a></td>
			                				<td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
			              				</tr>
			           				 </table>
		           			  </td> </c:if>
			
								<c:if test="${not empty timesresourcesexclusion.id.timeId}"><td width="17%" align="right" class="tdBtnRight" >
									<table height="22" border="0" cellpadding="0" cellspacing="0">
			              				<tr>
							                <td width="18"><img src="<c:url value='/images/btn/update.gif'/>" width="18" height="22"></td>
							                <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="updateItem"><fmt:message key="button.update"/></a></td>
							                <td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
							            </tr>
			            			</table>
							  </td></c:if>
							  <c:if test="${not empty timesresourcesexclusion.id.timeId}">
								<td width="16%" align="right" class="tdBtnRight">
									<table height="22" border="0" cellpadding="0" cellspacing="0">
								          <tr>
								            <td width="18"><img src="<c:url value='/images/btn/btn_delete.gif'/>" width="18" height="22"></td>
								            <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="deleteItem"><fmt:message key="button.delete"/></a></td>
								            <td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
								          </tr>
				        			</table>
		        			  </td></c:if>
							  	<td width="17%" align="right" class="tdBtnRight">
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
</table>
</body>
</html>