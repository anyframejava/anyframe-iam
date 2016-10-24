<%@ taglib uri='http://www.sds.samsung.com/tags' prefix='anyframe' %>
<%@ include file="/common/taglibs.jsp"%> 
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<html>
<head>	
<script type="text/javascript" src="<c:url value='/js/global.js'/>"></script>
<script language="javascript" src="<c:url value='/js/CommonScript.js'/>"></script>

<jsp:include page="/common/jstree-include.jsp" />
<jsp:include page="/common/jqgrid-include.jsp" />
<jsp:include page="/common/jqueryui-include.jsp" />

<script language="JavaScript">
<!--
	function fncAddUsers() {
		var isAvailable = document.usersForm.isAvailable;
		if(isAvailable.value == "false"){
			alert("<anyframe:message code='user.ui.alert.savebeforeconfirm' />");
			return;
		}
		// Form validation
		if(FormValidation(document.usersForm) != false) {
		    document.usersForm.action="<c:url value='/users/add.do?'/>";
		    var boxLength = document.usersForm.roleId.length;
		    var obj = document.usersForm.roleId.options;
		    
			if(boxLength != 0) {
				for (i = 0; i < boxLength; i++) {
					obj[i].selected = true;
				}
			}
		    document.usersForm.submit();
		}
	}
	
	function fncUpdateUsers() {
		// Form validation
		if(FormValidation(document.usersForm) != false) {
		    document.usersForm.action="<c:url value='/users/update.do?'/>";
		    var boxLength = document.usersForm.roleId.length;
		    var obj = document.usersForm.roleId.options;
		    
			if(boxLength != 0) {
				for (i = 0; i < boxLength; i++) {
					obj[i].selected = true;
				}
			}
		    document.usersForm.submit();
		}
	}
	function fncDeleteUsers(){
		if(confirmDelete('users')) {
		    document.usersForm.action="<c:url value='/users/deleteFromDetail.do?'/>";
		    document.usersForm.submit();
		}
	}
	function resetData() {
		document.usersForm.reset();
	}
	
	function selectGroup() {
		var pop = 0;
		url = "<c:url value='/groups/list.do?'/>";
		pop = window.open(url,"GroupTree","top=100, left=200, width=318, height=510,scrollbars=yes,resizable=no");
		pop.focus();
	}
	function setGroupValue(groupId, groupName) {
		document.usersForm.groupId.value = groupId;
		document.usersForm.groupName.value = groupName;
	}

	function moveToBack(){
		location.href="<c:url value='/userdetail/list.do?' />";
	}

	function checkforId() {
		var pop = 0;
		var userId = document.usersForm.userId;
		url = "<c:url value='/userdetail/duplicationconfirm.do?'/>" + "&userId=" + userId.value;
		pop = window.open(url, "CheckUserID","top=100, left=200, width=300, height=200,scrollbars=no,resizable=no");
		pop.focus();
		 
	}

	function changeIdcheck() {
		var isAvailable = document.usersForm.isAvailable;
		isAvailable.value = "false";
	}

	function setUserId(userId) {
		var textbox1 = document.usersForm.userId;
		var textbox2 = document.usersForm.isAvailable;
		textbox1.value = userId;
		textbox2.value = true;
	}

	$(function () {
		$("#role").tree({
			data	: {
			type	: "json",
			method	: "POST",
			url		: "<c:url value='/roles/listData.do?' />",
			async	: true,
			async_data : function (NODE) { return { id : $(NODE).attr("id") || "0" }; }
		},
		ui	: {
			theme_name : "classic",
			context : false
		},
		callback	: {
			ondblclk	: function(NODE,TREE_OBJ) {
	        	addrole($(NODE).children("a:visible").text(), NODE.id);
			},
			error 		: function(TEXT){
				var userId = document.usersForm.userId.value;
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
	});

	function addrole(selectedText,selectedValue) {
		var boxLength = document.usersForm.roleId.length;
		var isNew = true;
		
		if(boxLength != 0) {
			for(var i = 0 ; i < boxLength ; i++) {
				thisitem = document.usersForm.roleId.options[i].text;
				if(thisitem == selectedText) {
					isNew = false;
					break;
				}
			}
		} 
	        
		if(isNew) {
			newoption = new Option(selectedText, selectedValue, false, false);
			document.usersForm.roleId.options[boxLength] = newoption;
		}
	}

	jQuery(document).ready( function(){
		$("[name=deleteRole]").click( function() {
			var boxLength = document.usersForm.roleId.length;
			arrSelected = new Array();
			var count = 0;
			var count2 = 0;
			var count3 = 0;
			var del_value = "";
			for(i = 0 ; i < boxLength ; i++) {
				if(document.usersForm.roleId.options[i].selected) {
					arrSelected[count] = document.usersForm.roleId.options[i].value;
				}
				count++;
			}
			
			var x;
			for(i = 0 ; i < boxLength ; i++) {
				for(x = 0 ; x < arrSelected.length ; x++) {
					if(document.usersForm.roleId.options[i].value == arrSelected[x]) {
						var selectedText = document.usersForm.roleId.options[i].text;
						var selectedValue = document.usersForm.roleId.options[i].value;
						
						if(count3 == 0) {
							del_value = document.usersForm.roleId.options[i].value;
						}
						else {
							del_value = del_value + "," + document.usersForm.roleId.options[i].value;
						}
						
						document.usersForm.roleId.options[i] = null;
						count3++;
					}
					count2++;
				}
				boxLength = document.usersForm.roleId.length;
			}
		});
	});
//-->
</script>
<style type="text/css">
body {
	background-color: #FFFFFF;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>
</head>

<body text="#000000">
<!--************************** begin of contents *****************************-->
<table width="572" border="0" cellspacing="0" cellpadding="0">
	<!-- Begin of Title -->
  	<tr>
		<td valign="top">
	    	<table width="572" border="0" cellspacing="0" cellpadding="0" style="margin-top: 13px;">
				<tr>
					<td class="title" style="padding-left:21px">
						<c:if test="${empty users.userId}">
							<anyframe:message code='user.ui.title.adduserinfo' />
							<c:set var="readonly" value="false"/>
						</c:if>
						<c:if test="${not empty users.userId}">
							<anyframe:message code='user.ui.title.updateuserinfo' />
							<c:set var="readonly" value="true"/>
						</c:if>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<!-- End of Title -->

	<!-- Begin of Input field -->
	<tr>
	    <td style="padding-top: 5px;">
			<form:form commandName="users" method="post" action="users.do" id="usersForm" name="usersForm">
				<table width="572" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" >
					<tr><td height="2" colspan="4" bgcolor="#A2BACE"></td></tr>
					<tr>
						<td width="130" class="tdHead"><anyframe:message code='user.ui.label.userid' /></td>
					  	<td bgcolor="#B6CDE4" width="1"></td>
						<td width="141" class="tdin">
							<form:input path="userId" id="userId" cssClass="ct_input_g" cssErrorClass="text medium error" readonly="${readonly}" onchange="javascript:changeIdcheck();" maxlength="20"/>
								<c:if test="${empty users.userId}">
			          				<c:set var="readonly" value="false"/>
							 	</c:if>
			        		<form:errors path="userId" />
					    	<input type="hidden" name="isAvailable" value="false">
					  	</td>
					  	<td width="320" align="left">
							<c:if test="${empty users.userId}">
					  			<table height="19" border="0" cellpadding="0" cellspacing="0">
					                <tr>
					                  <td width="18"><img src="<c:url value='/images/btn/btn_checkid.gif'/>" width="21" height="19"></td>
					                  <td background="<c:url value='/images/btn/bg_btnsmall.gif'/>" class="smallBtn"><a href="javascript:checkforId();" name="selectGroup"><anyframe:message code='user.ui.link.checkid' /></a></td>
					                  <td width="9" align="right"><img src="<c:url value='/images/btn/btn_tail.gif'/>" width="9" height="19"></td>
					                </tr>
					            </table>
							</c:if>
			      		</td>
					</tr>		
					<tr><td height="1" colspan="4" bgcolor="#D6D6D6"></td></tr>
					<tr>
					  	<td class="tdHead"><anyframe:message code='user.ui.label.username' /></td>
						<td bgcolor="#B6CDE4" width="1"></td>
						<td colspan="2" class="tdin">									        
				        	<form:input path="userName" id="userName" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="50"/>&nbsp;<form:errors path="userName" />
				    	</td>
					</tr>
					<tr><td height="1" colspan="4" bgcolor="#D6D6D6"></td></tr>
					<tr>
					  	<td class="tdHead"><anyframe:message code='user.ui.label.groupname' /></td>
					  	<td bgcolor="#B6CDE4" width="1"></td>
						<td class="tdin">
							<input type="text" name="groupName" class="ct_input_g" value="${groups.groupName}" required readonly="readonly" onClick="javascript:selectGroup();">
						  	<input type="hidden" name="groupId" value="${groups.groupId}">
						</td>
						<td align="left">
						  	<a href="javascript:selectGroup();" id="button_links" name="selectGroup" class="searchBtn"></a>
						</td>
					</tr>
					<tr><td height="1" colspan="4" bgcolor="#D6D6D6"></td></tr>
					<tr>
					  	<td class="tdHead"><anyframe:message code='user.ui.label.enabled' /></td>
					  	<td bgcolor="#B6CDE4" width="1"></td>
						<td colspan="2" class="tdin">
					        <form:select path="enabled" id="enabled" cssClass="ct_input_g">
					        	<form:option value="Y"><anyframe:message code='user.ui.selectbox.proved' /></form:option>
					        	<form:option value="N"><anyframe:message code='user.ui.selectbox.disproved' /></form:option>
					        </form:select>			
				    	</td>
					</tr>
					<tr><td height="1" colspan="4" bgcolor="#D6D6D6"></td></tr>
					<tr>
			  		  	<td class="tdHead"><anyframe:message code="user.ui.label.roleslist" /></td>
			    		<td bgcolor="#B6CDE4" width="1"></td>
			    	  	<td colspan="2"> 	
							<table width="100%" class="tablemargin" height="195" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" >
								<tr height="25">
								  <td width="26" height="25" background="<c:url value='/images/bg_treel.gif'/>" style="padding-left:8px"><div id="menuopen"><a class="openBtn" title="Open Branch" href="javascript:$.tree_reference('role').open_all();">Open</a></div></td>
							  	  <td width="186" height="25" background="<c:url value='/images/bg_treer.gif'/>"><div id="menuclose"><a class="closeBtn" title="Close Branch" href="javascript:$.tree_reference('role').close_all();">Close</a></div></td>
									<td width="5"></td>
									<td align="left">
										<table height="22" border="0" cellpadding="0" cellspacing="0">
											<tr>
									            <td width="18"><img src="<c:url value='/images/btn/btn_delete.gif'/>" width="18" height="22"></td>
									            <td background="<c:url value='/images/btn/bg_btn.gif'/>" height="22" class="boldBtn"><a href="#" name="deleteRole"><anyframe:message code='restrictedtimes.ui.btn.removeroles' /></a></td>
									            <td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td valign="top" colspan="2">
										<div id="role" class="demo" style="overflow:auto;height:167px;width:220px;border:1px solid #c3daf9;">
											<span><anyframe:message code='user.ui.tree.span'/></span>
										</div>
									</td>
									<td></td>
									<td valign="top">
										<div id="roles" class="demo" style="overflow:auto; height:170px;width:188px;">
											<select id="roleId" name="roleId" multiple="multiple" class="selbox" style="overflow:auto; height:170px;width:188px;border:1px solid #c3daf9;">
												<c:forEach var="role" items="${roles}">
													<option value="${role.roleId}">${role.roleName}</option>
												</c:forEach>
											</select>
										</div>
									</td>
								</tr>
							</table>
			        	</td>
					</tr>
					<tr><td height="1" colspan="4" bgcolor="#B6CDE4"></td></tr>	
				</table>
				<input type="hidden" name="rootPath" value="<c:url value='/'/>"/>
			</form:form>
		</td>
	</tr>
	<!-- End of Input field -->

	<!-- Begin of Buttons -->
	<tr>
	    <td valign="top">
			<table width="572" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" style="margin-top:5px;">
				<tr>
					<td align="right">
						<table width="100" height="22" border="0" cellpadding="0" cellspacing="0">
							<tr>		
								<c:if test="${empty users.userId}">
								<td width="82%" align="right" class="tdBtnRight">	
									<table height="22" border="0" cellpadding="0" cellspacing="0" class="tdBtnRight">
			              				<tr>
			                				<td width="18"><img src="<c:url value='/images/btn/btn_add.gif'/>" width="18" height="22"></td>
			                				<td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="javascript:fncAddUsers();" name="addUser"><fmt:message key="button.add"/></a></td>
			                				<td width="10" align="right" background="<c:url value='/images/btn/bg_btn.gif'/>" ><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
			              				</tr>
			           				 </table>
			           			</td>
		           				</c:if>
								<c:if test="${not empty users.userId}">
								<td width="6%" align="right" class="tdBtnRight">
									<table height="22" border="0" cellpadding="0" cellspacing="0" class="tdBtnRight">
			              				<tr>
							                <td width="18"><img src="<c:url value='/images/btn/update.gif'/>" width="18" height="22"></td>
							                <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="javascript:fncUpdateUsers();" name="updateUser"><fmt:message key="button.update"/></a></td>
							                <td width="10" align="right" background="<c:url value='/images/btn/bg_btn.gif'/>" ><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
							            </tr>
			            			</table>
								</td>
		            			</c:if>
								<c:if test="${not empty users.userId}">
								<td width="6%" align="right" class="tdBtnRight">
									<table height="22" border="0" cellpadding="0" cellspacing="0" class="tdBtnRight">
								          <tr>
								            <td width="18"><img src="<c:url value='/images/btn/btn_delete.gif'/>" width="18" height="22"></td>
								            <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="javascript:fncDeleteUsers();" name="deleteUser"><fmt:message key="button.delete"/></a></td>
								            <td width="10" align="right" background="<c:url value='/images/btn/bg_btn.gif'/>" ><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
								          </tr>
				        			</table>
			        			</td>
			        			</c:if>
							  	<td width="6%" align="right"  class="tdBtnRight">
									<table height="22" border="0" cellpadding="0" cellspacing="0" class="tdBtnRight">
							          <tr>
							            <td width="18"><img src="<c:url value='/images/btn/btn_cancel.gif'/>" width="18" height="22"></td>
							            <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="javascript:moveToBack();" name="resetData"><fmt:message key="button.cancel"/></a></td>
							            <td width="10" align="right" background="<c:url value='/images/btn/bg_btn.gif'/>" ><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
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
	<!-- End of Buttons -->
</table>
<!--************************** end of contents *****************************-->
</body>
</html>