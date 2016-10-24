<%@ taglib uri='http://www.sds.samsung.com/tags' prefix='anyframe' %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><anyframe:message code="user.ui.title.userlist" /></title>

<jsp:include page="/common/jstree-include.jsp" />
<jsp:include page="/common/jqgrid-include.jsp" />
<jsp:include page="/common/jqueryui-include.jsp" />

<style type="text/css">
.selected {
	background: url(../images/content/tab_menu1.gif) no-repeat 5px;
	background-position:0 0px;
	color: #000000;
	font-weight: bold;
}
body {
	background-color: #E9ECF1;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>

<script type="text/javascript">
<!--
	$(function() {
		var parentNode = "";
		var jqSearchForm = document.searchForm;
		$("#tree_group").tree(
		{
			data : {
				type : "json",
				method : "POST",
				url : "<c:url value='/groups/listData.do?'/>",
				async : true,
				async_data : function(NODE) {
					return {
						id : $(NODE).attr("id") || "0"
					}
				}
			},
			ui : {
				theme_name : "classic"
			},
			callback : {
				onselect : function(NODE, TREE_OBJ) {
					jqSearchForm.groupId.value = NODE.id;

					if(jqSearchForm.tabInfo.value == "groupTab"){
						frame01.location.href = "<c:url value='/groups/get.do?&groupId=' />" + NODE.id;
					}
					else
						frame01.location.href = "<c:url value='/userdetail/list.do?' />";
				},
				onrename : function(NODE, LANG, TREE_OBJ, RB) {
					if (parentNode == "") {
						$.post(
								"<c:url value='/groups/update.do?'/>",
								{
									groupId : NODE.id,
									groupName : $(NODE).children("a:visible").text()
								}, function() {
								});
					} else {
						$.post(
								"<c:url value='/groups/add.do?'/>",
								{
									groupId : "",
									groupName : $(NODE).children("a:visible").text(),
									parentGroup : parentNode
								}, function() {
								});

						parentNode	= "";
					}
				},
				oncreate : function(NODE, REF_NODE, TYPE,
						TREE_OBJ, RB) {
					parentNode = $(NODE).parents("li:eq(0)").attr("id");
				},
				beforedelete : function(NODE) {
					return confirm("<anyframe:message code='user.ui.alert.confirmtodelete' />");
				},
				ondelete : function(NODE, TREE_OBJ, RB) {
					$.post(
							"<c:url value='/groups/delete.do?'/>",
							{
								groupId : NODE.id
							}, function() {
							});
				},
				error 		: function(TEXT){
					if(TEXT.match('parsererror') != null){
						location.href = "<c:url value='/login/relogin.do?inputPage=/users/list.do'/>";
						return;
					}
					alert(TEXT);
				}
			}
		});

		$('#groupTab').addClass('selectedTab');

		
		$('#groupTab').bind('click', function(){
			$('td').removeClass('selectedTab');
			$('#groupTab').addClass('selectedTab');
			moveToGroupTab();
		});
		$('#usersTab').bind('click', function(){
			$('td').removeClass('selectedTab');
			$('#usersTab').addClass('selectedTab');
			moveToUsersTab();
		});
	});
//-->
</script>
<script type="text/javascript">
<!--
function moveToUsersTab(){
	var tabInfo = document.searchForm.tabInfo;
	var groupId = document.searchForm.groupId;
	tabInfo.value = "usersTab";
	frame01.location.href = "<c:url value='/userdetail/list.do?&groupId=' />" + groupId.value;
}
function moveToGroupTab(){
	var tabInfo = document.searchForm.tabInfo;
	var groupId = document.searchForm.groupId;
	tabInfo.value = "groupTab";
	frame01.location.href = "<c:url value='/groups/get.do?&groupId=' />" + groupId.value;
}
//-->
</script>
</head>

<body>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" >
  <tr valign="top">
    <td width="10" height="50%" style="padding-top:6px"></td>
    <td width="224" style="padding-top:6px"><table width="220" height="100%" border="0" cellpadding="0" cellspacing="0">
      <tr height="29">
        <td width="26" height="25" background="<c:url value='/images/bg_treel.gif'/>" style="padding-left:8px"><div id="menuopen"> <a class="openBtn" title="Open Branch" href="javascript:$.tree_reference('tree_group').open_all()">Open</a></div></td>
        <td width="188" height="25" align="left" background="<c:url value='/images/bg_treer.gif'/>" ><div id="menuclose"><a class="closeBtn" title="Close Branch" href="javascript:$.tree_reference('tree_group').close_all();">Close</a></div></td>
      </tr>
      <tr height="400">
        <td height="100%" colspan="2" valign="top" style="margin-top:10px">
		<div id="tree_group" class="demo" style="overflow: auto; height: 415px; width: 218px; border: 1px solid #C9CFDD;"> <span>
          <anyframe:message code='user.ui.tree.span' />
        </span></div>
		</td>
      </tr>
    </table></td>
    <td align="left"><div id="grid" class="demo" style="overflow: auto; height: 100%; width: 100%; padding-top:6px;">
      <form name="searchForm">
        <input type="hidden" name="groupId">
        <input type="hidden" name="tabInfo" value="groupTab">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr height="30">
            <td height="30" valign="bottom" background="<c:url value='/images/content/bg_tab.gif'/>" style="padding-left:10px"><table height="24" border="0" cellpadding="0" cellspacing="0">
              <tr height="21">
                <td width="134" height="27" align="center" valign="bottom"  background="<c:url value='/images/content/bg_tab_menu2.gif'/>" bgcolor="#EDEDED"
							 id="groupTab" ><a href="#"><anyframe:message code='user.ui.tab.group' /></a> </td>
                <td width="145" height="21" align="center" valign="bottom"  background="<c:url value='/images/content/bg_tab_menu3.gif'/>"
							 id="usersTab"><a href="#"><anyframe:message code='user.ui.tab.user' /></a> </td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td valign="top" id="group" style="padding-left:10px"><iframe src="<c:url value='/groups/get.do?' />" 
					width="645" height="434" frameborder="0" name="frame01" id="frame01"></iframe></td>
          </tr>
        </table>
      </form>
    </div></td>
  </tr>
  
  <tr valign="top">
    <td height="50%" colspan="3" valign="top" ><table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#E9ECF1">
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