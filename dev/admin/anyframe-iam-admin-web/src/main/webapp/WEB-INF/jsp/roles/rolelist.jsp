<%@ taglib uri='http://www.anyframejava.org/tags' prefix='anyframe' %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><anyframe:message code="role.ui.title.rolemapping" /></title>

<jsp:include page="/common/jquery-include.jsp" />
<jsp:include page="/common/jstree-include.jsp" />
<jsp:include page="/common/jquery-autocomplete-include.jsp" />

<script type="text/javascript">
<!--
	$(function () {
		var	parentNode	= "";
		var jqSearchForm = document.f1;
		var roleId = jqSearchForm.roleId;
		$("#role").tree({
			data	: {
				type	: "json",
				async	: true,
				opts	: {
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
			plugins : {
				contextmenu : { }
			},
			callback	: {
				beforedata	: function(NODE, TREE_OBJ) {
					return {
						id : $(NODE).attr("id") || "0",
						roleName : document.getElementById("roleName").value,
						searchClickYn : document.getElementById("searchClickYn").value
					};
				},
				onselect	: function(NODE, TREE_OBJ) {
					var selectedRoleId = document.f1.roleId;
					selectedRoleId.value = NODE.id; 
					roleId.value = NODE.id;
					if(jqSearchForm.tabInfo.value == "resource"){
						frame01.location.href = "<c:url value='/securedresourcesroles/addView.do?&roleId=' />" + NODE.id;
					}
					else if(jqSearchForm.tabInfo.value == "userGroup"){
						frame01.location.href = "<c:url value='/rolegroupmapping/addView.do?&roleId=' />" + NODE.id;
					}
					else if(jqSearchForm.tabInfo.value == "roleInfoTab"){
						frame01.location.href = "<c:url value='/roleinformation/get.do?&roleId=' />" + NODE.id;
					}
					else {
						frame01.location.href = "<c:url value='/roleusermapping/addView.do?&roleId=' />" + NODE.id;
					}
				},
				onrename	: function(NODE,LANG,TREE_OBJ,RB) {
					var roleName = $.tree.focused().get_text(NODE);
					if( parentNode == "" ) {
						$.post(
							"<c:url value='/roles/update.do?' />",
							{
								roleId : NODE.id, 
								roleName : roleName,
								description : document.getElementById("description").value
							},function(data){
								frame01.location.href = "<c:url value='/roleinformation/get.do?&roleId=' />" + NODE.id;
							}
						);
					} else {
						$('td').removeClass('selectedTab');
						$('#roleInfoTab').addClass('selectedTab');
						
						var tabInfo = document.f1.tabInfo;
						tabInfo.value = "roleInfoTab";
						
						$.post(
							"<c:url value='/roles/add.do?' />",
							{
								roleId : roleId,
								roleName : roleName,
								childRole : parentNode
							},function(data){
								NODE.id = roleId;
								frame01.location.href = "<c:url value='/roleinformation/get.do?&roleId=' />" + roleId;
							}
						);
						var documentRoleId = document.f1.roleId;
						documentRoleId.value = roleId;
						parentNode	= "";
					}
				},
				oncreate	: function(NODE,REF_NODE,TYPE,TREE_OBJ,RB) {
					parentNode = $(NODE).parents("li:eq(0)").attr("id");

					$.getJSON(
							"<c:url value='/roles/getRoleId.do'/>", function(data) {
								roleId = data.roleId;
						});
				},
				beforedelete : function (NODE) {
					return confirm("<anyframe:message code='role.ui.tree.alert.confirm' />");
				},
				ondelete	: function(NODE, TREE_OBJ,RB) {
					$.post("<c:url value='/roles/delete.do?' />",{roleId:NODE.id},function(){});

					var deletedRoleId = document.f1.roleId;
					deletedRoleId.value = "";
				},
				error 		: function(TEXT){
					if(TEXT.match('parsererror') != null){
						location.href = "<c:url value='/login/relogin.do?inputPage=/roles/list.do'/>";
						return;
					}
					alert(TEXT);
				}
			}
		});
		$('#roleInfoTab').addClass('selectedTab');
		
		$('#resourceListTab').bind('click', function(){
			$('td').removeClass('selectedTab');
			$('#resourceListTab').addClass('selectedTab');
			moveToResource();
		});
		
		$('#userGroupTab').bind('click', function(){
			$('td').removeClass('selectedTab');
			$('#userGroupTab').addClass('selectedTab');
			moveToUserGroup();
		});
		
		$('#usersTab').bind('click', function(){
			$('td').removeClass('selectedTab');
			$('#usersTab').addClass('selectedTab');
			moveToUsers();
		});
		
		$('#roleInfoTab').bind('click', function(){
			$('td').removeClass('selectedTab');
			$('#roleInfoTab').addClass('selectedTab');
			moveToRoleInfo();
		});

		$("[name=searchUsers]").click( function() {
			document.getElementById("searchClickYn").value = "Y";
			$.tree.focused().refresh();
			document.getElementById("searchClickYn").value = "N";
		});

		/* auto click by enter key */
		$("#roleName").keypress(function (e) {
			if (e.which == 13)
				$("[name=searchUsers]").trigger("click");
		});
	});

	function moveToResource() {
		var roleId = document.f1.roleId;
		var isChangedForm = window.frames.frame01;
		if(document.f1.tabInfo.value=="userGroup" && roleId.value!="" && isChangedForm.document.usersForm.isChanged.value == "changed") {
			if(confirm("<anyframe:message code='role.ui.alert.save' />")) {
				frame01.saveRole();
			}
		}
		
		var tabInfo = document.f1.tabInfo;
		tabInfo.value = "resource";
		
		frame01.location.href = "<c:url value='/securedresourcesroles/addView.do?&roleId=' />" + roleId.value;
	}

	function moveToUserGroup() {
		var tabInfo = document.f1.tabInfo;
		var roleId = document.f1.roleId;
		tabInfo.value = "userGroup";
		frame01.location.href = "<c:url value='/rolegroupmapping/addView.do?&roleId=' />" + roleId.value;
	}

	function moveToUsers() {
		var roleId = document.f1.roleId;
		var isChangedForm = window.frames.frame01;
		if(document.f1.tabInfo.value=="userGroup" && roleId.value!="" && isChangedForm.document.usersForm.isChanged.value == "changed") {
			if(confirm("<anyframe:message code='role.ui.alert.save' />")) {
				frame01.saveRole();
			}
		}

		var tabInfo = document.f1.tabInfo;
		tabInfo.value = "users";
		frame01.location.href = "<c:url value='/roleusermapping/addView.do?&roleId=' />" + roleId.value;
	}

	function moveToRoleInfo() {
		var roleId = document.f1.roleId;
		var isChangedForm = window.frames.frame01;
		
		if(document.f1.tabInfo.value=="userGroup" && roleId.value!="" && isChangedForm.document.usersForm.isChanged.value == "changed") {
			if(confirm("<anyframe:message code='role.ui.alert.save' />")) {
				frame01.saveRole();
			}
		}

		var tabInfo = document.f1.tabInfo;
		tabInfo.value = "roleInfoTab";
		frame01.location.href = "<c:url value='/roleinformation/get.do?&roleId=' />" + roleId.value;
	}

	function updateRole(roleId, roleName, description) {
		document.getElementById("description").value = description;
		$.tree.focused().rename("#" + roleId, roleName);
		document.getElementById("description").value = "";
	}

	function deleteRole(roleId) {
		$.tree.focused().remove("#" + roleId);
	}
//-->
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
<body>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" >
	<tr valign="top">
	    <td width="10" height="50%" style="padding-top:6px"></td>
	   	<td width="224" style="padding-top:6px">
	   		<table width="220" height="100%" border="0" cellpadding="0" cellspacing="0">
	     		<tr height="29">
	       			<td width="26" height="25" background="<c:url value='/images/bg_treel.gif'/>" style="padding-left:8px"><div id="menuopen" ><a class="openBtn" title="Open Branch" href="javascript:$.tree.focused().open_all();"><anyframe:message code="role.ui.tree.openbranch" />Open</a></div></td>
	       			<td width="26" height="25" align="left" background="<c:url value='/images/bg_treer.gif'/>" ><div id="menuclose"><a class="closeBtn" title="Close Branch" href="javascript:$.tree.focused().close_all();"><anyframe:message code="role.ui.tree.closebranch" />Close</a></div></td>
	       			<td width="100" height="25" align="left" background="<c:url value='/images/bg_treer.gif'/>" >
						<div id="inputArea">
							<input id="roleName" size="20" class='ct_input_g'>
							<input id="searchClickYn" type="hidden" value="N">
							<input id="description" type="hidden" value="">
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
					<td width="62" height="25" align="left" background="<c:url value='/images/bg_treerrr3.gif'/>"><a href="#"  name="searchUsers" class="searchBtn"><anyframe:message code="user.ui.btn.search" /></a></td>
				</tr>
	      		<tr height="400">
	        		<td height="100%" colspan="4" valign="top" style="margin-top:10px">
	        			<div id="documentation">
	          				<div id="role" class="demo" style="overflow:auto; height:410px;width:218px;border:1px solid #C9CFDD;">
	          					<span><anyframe:message code="role.ui.tree.span" /></span>
							</div>
						</div>
					</td>
	      		</tr>
			</table>
		</td>
		<td align="left" style="padding-top:6px;">
		<form name="f1">
			<input type="hidden" name="roleId">
			<input type="hidden" name="tabInfo" value="roleInfoTab">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" >
				<tr height="30">
					<td height="30" valign="bottom" background="<c:url value='/images/content/bg_tab.gif'/>" style="padding-left:10px">
						<table height="24" border="0" cellpadding="0" cellspacing="0">
							<tr height="21">
								<td width="134" height="27" align="center" valign="bottom" background="<c:url value='/images/content/bg_tab_menu2.gif'/>" bgcolor="#EDEDED" 
									id="roleInfoTab" ><a href="javascript:moveToRoleInfo();"><anyframe:message code='role.ui.link.role' /></a></td>
								<td width="134" height="27" align="center" valign="bottom" background="<c:url value='/images/content/bg_tab_menu3.gif'/>" bgcolor="#EDEDED" 
									id="resourceListTab" ><a href="javascript:moveToResource();"><anyframe:message code='role.ui.link.resource' /></a></td>
								<td width="134" height="27" align="center" valign="bottom" background="<c:url value='/images/content/bg_tab_menu3.gif'/>" bgcolor="#EDEDED" 
									id="userGroupTab" ><a href="javascript:moveToUserGroup();"><anyframe:message code='role.ui.link.usergroup' /></a></td>
								<td width="145" height="21" align="center" valign="bottom" background="<c:url value='/images/content/bg_tab_menu3.gif'/>"  
									id="usersTab" ><a href="javascript:moveToUsers();"><anyframe:message code='role.ui.link.users' /></a></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td style="padding-left:10px">
						<iframe src="<c:url value='/roleinformation/get.do?' />" width="630" height="415" frameborder="0" scrolling="no" name="frame01" id="frame01"></iframe>
					</td>
				</tr>
			</table>
		</form>
		</td>
	</tr>
	<tr valign="top">
		<td height="50%" colspan="3" valign="top" >
			<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#E9ECF1">
				<tr><td height="10" bgcolor="#ffffff"></td></tr>
				<tr><td height="1" bgcolor="#C9CFDD"></td></tr>
				<tr><td valign="top" bgcolor="#E9ECF1"><div id="footSub"></div></td></tr>
			</table>
		</td>
	</tr>
</table>
</body>
</html>
