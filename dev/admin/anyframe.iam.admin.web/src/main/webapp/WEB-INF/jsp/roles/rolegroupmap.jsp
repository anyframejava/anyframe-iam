<%@ taglib uri='http://www.sds.samsung.com/tags' prefix='anyframe' %>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%> 
<html>
<head>
	
<jsp:include page="/common/jstree-include.jsp" />
<jsp:include page="/common/jqueryui-include.jsp" />
<jsp:include page="/common/jquery-autocomplete-include.jsp" />

<script type="text/javascript">
<!--
	$(function () {
		var	parentNode	= "";
		var jqSearchForm = document.usersForm;
		$("#group").tree({
			data	: {
				type	: "json",
				async	: true,
				opts 	: {
					method	: "POST",
					url		: "<c:url value='/groups/listData.do?' />"
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
				cookie : { prefix : "rolegroupmap_" }
			},
			
			callback	: {
				beforedata	: function(NODE, TREE_OBJ) {
					return {
						id : $(NODE).attr("id") || "0",
						groupName : document.getElementById("groupName").value,
						searchClickYn : document.getElementById("searchClickYn").value
					}
				},
				onselect	: function(NODE, TREE_OBJ) {
					var sText = jqSearchForm.selectedText;
					sText.value = $.tree.focused().get_text(NODE);
					var sValue = jqSearchForm.selectedValue;
					sValue.value = NODE.id;
				},
				ondblclk	: function(NODE, TREE_OBJ) {
					addrole($.tree.focused().get_text(NODE), NODE.id);
				},
				error 		: function(TEXT){
					var roleId = document.usersForm.roleId.value;
					if(TEXT.match('parsererror') != null){
						location.href = "<c:url value='/login/relogin.do?inputPage=/rolegroupmapping/addView.do?&roleId='/>" + roleId;
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

	function saveRole(){

		var isChanged = document.usersForm.isChanged;
		isChanged.value= "saved";
		
		var boxLength = document.usersForm.groupId.length;
		var obj = document.usersForm.groupId.options;
		var roleId = document.usersForm.roleId.value;
		if(roleId == null || roleId == ""){
			alert("<anyframe:message code='rolegroup.ui.tree.alert.noselectedrole' />");
			return;
		} 
		var groupId = new Array();
		if(boxLength != 0) {
			for(i = 0 ; i < boxLength ; i++) {
				obj[i].selected = true;	
				groupId[i] = document.usersForm.groupId.options[i].value; 
			}
		}
		
		$.post("<c:url value='/rolegroupmapping/add.do?' />", {roleId:roleId, groupId:groupId}, function(data){
			if(data.match('<title>Login</title>') != null){
				location.href = "<c:url value='/login/relogin.do?inputPage=/rolegroupmapping/addView.do?&roleId='/>" + roleId;
				return;
			}
		});
	}

	function addrole(selectedText, selectedValue){
		var boxLength = document.usersForm.groupId.length;
		var isNew = true;

		if(boxLength != 0){
			for(var i = 0 ; i < boxLength ; i++){
				thisitem = document.usersForm.groupId.options[i].text;
				if(thisitem == selectedText) {
					isNew = false;
					break;
				}
			}
		}
		if(isNew) {
			newoption = new Option(selectedText, selectedValue, false, false);
			document.usersForm.groupId.options[boxLength] = newoption;
		}

		var isChanged = document.usersForm.isChanged;
		isChanged.value = "changed";
	}

	function addgroupId(){
		var selectedText = document.usersForm.selectedText.value;
		var selectedValue = document.usersForm.selectedValue.value;
		if(selectedText == "" || selectedValue == ""){
			alert("<anyframe:message code='rolegroup.ui.tree.alert.noselectedrows' />");
			return;
		}
		addrole(selectedText, selectedValue);
	}

	function DelMe() {
		var boxLength = document.usersForm.groupId.length;
		if(boxLength == 0){
			alert("<anyframe:message code='rolegroup.ui.tree.alert.noselectedrows' />");
			return;
		}
		arrSelected = new Array();
		var count = 0;
		var count2 = 0;
		var count3 = 0;
		var del_value = "";
		for(i = 0 ; i < boxLength ; i++) {
			if(document.usersForm.groupId.options[i].selected) {
				arrSelected[count] = document.usersForm.groupId.options[i].value;
			}
			count++;
		}
		
		var x;
		for(i = 0 ; i < boxLength ; i++) {
			for(x = 0 ; x < arrSelected.length ; x++) {
				if(document.usersForm.groupId.options[i].value == arrSelected[x]) {
					var selectedText = document.usersForm.groupId.options[i].text;
					var selectedValue = document.usersForm.groupId.options[i].value;
					
					if(count3 == 0) {
						del_value = document.usersForm.groupId.options[i].value;
					}
					else {
						del_value = del_value + "," + document.usersForm.groupId.options[i].value;
					}
					
					document.usersForm.groupId.options[i] = null;
					count3++;
				}
				count2++;
			}
			boxLength = document.usersForm.groupId.length;
		}

		var isChanged = document.usersForm.isChanged;
		isChanged.value = "changed";
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
</style></head>
<body text="#000000">

<form:form commandName="users" method="post" action="groups.do" id="usersForm" name="usersForm">
<form:errors path="*" cssClass="error" element="div"/>
<table width="572" border="0" cellpadding="0" cellspacing="0" style="margin-top:8px">
	<tr valign="bottom" height="25">
	    <td width="224" valign="bottom" style="padding-top:6px">
    		<table width="220" height="25" border="0" cellpadding="0" cellspacing="0">
      			<tr height="25">
        			<td width="26" height="25" background="<c:url value='/images/bg_treel.gif'/>" style="padding-left:8px"><div id="menuopen" ><a class="openBtn" title="Open Branch" href="javascript:$.tree.focused().open_all();"><anyframe:message code="role.ui.tree.openbranch" />Open</a></div></td>
        			<td width="26" height="25" align="left" background="<c:url value='/images/bg_treer.gif'/>" ><div id="menuclose"><a class="closeBtn" title="Close Branch" href="javascript:$.tree.focused().close_all();"><anyframe:message code="role.ui.tree.closebranch" />Close</a></div></td>
        			<td width="100" height="25" align="left" background="<c:url value='/images/bg_treer.gif'/>" >
						<div id="inputArea">
							<input id="groupName" size="20" class='ct_input_g'>
							<input id="searchClickYn" type="hidden" value="N">
							<script type="text/javascript">
								$("#groupName").autocomplete(
									"<c:url value='/groups/getGroupNameList.do' />", {
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
					<td width="62" height="25" align="left" background="<c:url value='/images/bg_treer.gif'/>"><a href="#"  name="searchUsers" class="searchBtn"><anyframe:message code="user.ui.btn.search" /></a></td>
      			</tr>
    		</table>
		</td>
		<td></td>
		<td width="308" valign="middle" align="right" style="padding-top:0px; padding-right:10px;">
			<table height="22" border="0" cellpadding="0" cellspacing="0">
	              <tr>
	                <td width="18"><img src="<c:url value='/images/btn/btn_add.gif'/>" width="18" height="22"></td>
	                <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="javascript:saveRole();" name="saveRoles"><anyframe:message code="rolegroup.ui.btn.save" /></a></td>
	                <td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
	              </tr>
            </table>
		</td>
	</tr>
	<tr height="400">
		<td height="100%" valign="top" style="margin-top:10px">
			<div id="documentation">
				<table width="218" border="0" cellpadding="0" cellspacing="1" bgcolor="#c3daf9">
					  <tr>
					    <td bgcolor="#FFFFFF">
					    	<div id="group" class="demo" style="overflow:auto; height:370px;width:218px;border:0px ;">
					    		<span><anyframe:message code="rolegroup.ui.tree.span" /></span>
					    	</div>
					    </td>
					  </tr>
				</table>
			</div>
		</td>
		<td>
			<table>
				<tr>
					<td>
						<div id="shiftBtn"><a class="outBtn" name="toRight" href="javascript:addgroupId();">out</a></div>
						<input type="hidden" name="selectedText">
						<input type="hidden" name="selectedValue">
						<input type="hidden" name="isChanged">
						<input type="hidden" name="roleId" value="<c:out value='${param.roleId}'/>">
					</td>
				</tr>
				<tr>
					<td>
						<div id="shiftBtn"><a class="inBtn" name="toLeft" href="javascript:DelMe();">in</a></div>
					</td>
				</tr>
			</table>
		</td>
		<td valign="top">
			<div id="roles" class="demo" style="overflow:auto; height:372px; width:304px;">
				<select id="groupId" name="groupId" class="selbox" multiple="multiple" style="overflow:auto; height:372px; width:304px;border:1px solid #D6D6D6;">
				<c:forEach var="group" items="${groups}">
					<option value="${group.groupId}">${group.groupName}</option>
				</c:forEach>
				</select>
			</div>
		</td>			
	</tr>
</table>
</form:form>
</body>
</html>