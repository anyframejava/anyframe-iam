<%@ taglib uri='http://www.sds.samsung.com/tags' prefix='anyframe' %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Group List</title>

<jsp:include page="/common/jquery-include.jsp" />
<jsp:include page="/common/jstree-include.jsp" />
<jsp:include page="/common/jquery-autocomplete-include.jsp" />

<script type="text/javascript">
	$(function () {
		$("#group").tree({
			data	: {
				type	: "json",
				async	: true,
				opts 	: {
					method	: "POST",
					url		: "<c:url value='/groups/listData.do'/>"
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
						groupName : document.getElementById("groupName").value,
						searchClickYn : document.getElementById("searchClickYn").value
					}
				},
				onselect	: function(NODE,TREE_OBJ) {
					opener.setGroupValue(NODE.id, $.tree.focused().get_text(NODE));
					self.close();
				},
				error 		: function(TEXT){
					alert(TEXT);
				}
			}
		});

		$("[name=searchUsers]").click( function() {
			document.getElementById("searchClickYn").value = "Y";
			$.tree.focused().refresh();
			document.getElementById("searchClickYn").value = "N";
		});
	});
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

a.search { color:green !important; }
-->
</style>
</head>
<body>
<table width="300" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="center">
			<table width="285" height="44" border="0" align="center" cellpadding="0" cellspacing="0" >
				<tr>
					<td height="44" align="left" class="checkGrouptitle" style="padding-left:38px"><anyframe:message code='user.ui.label.groupname' /></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td style="padding-left:9px;padding-top:6px">
			<table width="284" border="0" cellpadding="0" cellspacing="0">
				<tr height="30">
					<!---START: Tree Btn ------>
					<td width="26" height="25"  background="<c:url value='/images/bg_treel.gif'/>" style="padding-left:8px"><div id="menuopen"> <a class="openBtn" title="Open Branch" href="javascript:$.tree.focused().open_all();">Open</a></div></td>
					<td width="26" background="<c:url value='/images/bg_treer2.gif'/>"><div id="menuclose"><a class="closeBtn" title="Close Branch" href="javascript:$.tree.focused().close_all();">Close</a></div></td>
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
					<td width="125" height="25" align="left" background="<c:url value='/images/bg_treer.gif'/>"><a href="#"  name="searchUsers" class="searchBtn"><anyframe:message code="user.ui.btn.search" /></a></td>
					<!---END: Tree Btn ------>
				</tr>
				<tr height="400">
					<td colspan="4">
						<div id="documentation">
							<div id="group" class="demo" style="overflow:auto; height:420px;width:282px;border:1px solid #c3daf9;">
								<span><anyframe:message code="role.ui.tree.span" /></span>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</body>
</html>
