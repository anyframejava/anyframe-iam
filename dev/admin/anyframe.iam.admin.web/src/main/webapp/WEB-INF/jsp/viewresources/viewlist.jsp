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
		var parentNode = "";
		var viewResourceId = document.f1.viewResourceId;
		var tabInfo = document.f1.tabInfo;
		$("#view").tree({
			data	: {
				type	: "json",
				async	: true,
				opts	: {
					method	: "POST",
					url		: "<c:url value='/viewresources/listTreeData.do?' />"
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
						viewName : document.getElementById("viewName").value,
						searchClickYn : document.getElementById("searchClickYn").value
					};
				},
				onselect	: function(NODE, TREE_OBJ) {
					var selectedViewId = document.f1.viewResourceId;
					selectedViewId.value = NODE.id; 
					viewResourceId.value = NODE.id;
					if(tabInfo.value == "viewResourceDetail"){
						frame01.location.href = "<c:url value='/viewresources/get.do?&viewResourceId=' />" + NODE.id;
					}
					else if(tabInfo.value == "viewList"){
						frame01.location.href = "<c:url value='/viewresources/list.do?&parentViewResourceId=' />" + NODE.id;
					}
					else if(tabInfo.value == "viewMapping"){
						frame01.location.href = "<c:url value='/viewresourcesmapping/list.do?&parentViewResourceId=' />" + NODE.id;
					}
				},
				onrename	: function(NODE,LANG,TREE_OBJ,RB) {
					var viewName = $.tree.focused().get_text(NODE);
					if( parentNode == "" ) {
						$.post(
							"<c:url value='/viewresources/update.do?' />",
							{
								viewResourceId : NODE.id, 
								viewName : viewName,
								category : document.getElementById("category").value,
								viewInfo : document.getElementById("viewInfo").value,
								description : document.getElementById("description").value,
								viewType : document.getElementById("viewType").value,
								visible : document.getElementById("visible").value
							},function(data){
								frame01.location.href = "<c:url value='/viewresources/get.do?&viewResourceId=' />" + NODE.id;
							}
						);
					} else {
						$('td').removeClass('selectedTab');
						$('#viewResourceDetail').addClass('selectedTab');
						
						var tabInfo = document.f1.tabInfo;
						tabInfo.value = "viewResourceDetail";
						
						$.post(
							"<c:url value='/viewresources/add.do?' />",
							{
								viewResourceId : viewResourceId,
								viewName : viewName,
								childView : parentNode,
								category : viewResourceId + " category here",
								viewInfo : viewResourceId + " information here",
								description : viewResourceId + " description here",
								viewType : "button",
								visible : "Y"
							},function(data){
								NODE.id = viewResourceId;
								frame01.location.href = "<c:url value='/viewresources/get.do?&viewResourceId=' />" + viewResourceId;
							}
						);
						var documentViewResourceId = document.f1.viewResourceId;
						documentViewResourceId.value = viewResourceId;
						parentNode	= "";
					}
				},
				oncreate	: function(NODE,REF_NODE,TYPE,TREE_OBJ,RB) {
					parentNode = $(NODE).parents("li:eq(0)").attr("id");

					$.getJSON(
							"<c:url value='/viewresources/getViewResourceId.do'/>", function(data) {
								viewResourceId = data.viewResourceId;
						});
				},
				beforedelete : function (NODE) {
					return confirm("<anyframe:message code='role.ui.tree.alert.confirm' />");
				},
				ondelete	: function(NODE, TREE_OBJ,RB) {
					$.post("<c:url value='/viewresources/remove.do?' />",{viewResourceId:NODE.id},function(){});

					var deletedViewResourceId = document.f1.viewResourceId;
					deletedViewResourceId.value = "";
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

		$('#viewDetailTab').addClass('selectedTab');
		
		$('#viewListTab').bind('click', function(){
			$('td').removeClass('selectedTab');
			$('#viewListTab').addClass('selectedTab');
			moveToViewList();
		});
		
		$('#viewMappingTab').bind('click', function(){
			$('td').removeClass('selectedTab');
			$('#viewMappingTab').addClass('selectedTab');
			moveToViewMapping();
		});
		
		$('#viewDetailTab').bind('click', function(){
			$('td').removeClass('selectedTab');
			$('#viewDetailTab').addClass('selectedTab');
			moveToViewResourceDetail();
		});
		
		$("[name=searchUsers]").click( function() {
			document.getElementById("searchClickYn").value = "Y";
			$.tree.focused().refresh();
			document.getElementById("searchClickYn").value = "N";
		});

		/* auto click by enter key */
		$("#viewName").keypress(function (e) {
			if (e.which == 13){
				$("[name=searchUsers]").trigger("click");
				return false;
			}
		});
	});

	function moveToViewResourceDetail() {
		var tabInfo = document.f1.tabInfo;
		var viewResourceId = document.f1.viewResourceId;
		tabInfo.value = "viewResourceDetail";
		frame01.location.href = "<c:url value='/viewresources/get.do?&viewResourceId=' />" + viewResourceId.value;
	}
	
	function moveToViewList() {
		var viewResourceId = document.f1.viewResourceId;
		var tabInfo = document.f1.tabInfo;
		tabInfo.value = "viewList";
		frame01.location.href = "<c:url value='/viewresources/list.do?&viewResourceId=' />" + viewResourceId.value;
	}
	
	function moveToViewMapping() {
		var viewResourceId = document.f1.viewResourceId;
		var tabInfo = document.f1.tabInfo;
		tabInfo.value = "viewMapping";
		frame01.location.href = "<c:url value='/viewresourcesmapping/list.do?&viewResourceId=' />" + viewResourceId.value;
	}

	function updateView(viewResourceId, viewName, category, description, viewInfo, viewType, visible){
		document.getElementById("category").value = category;
		document.getElementById("description").value = description;
		document.getElementById("viewInfo").value = viewInfo;
		document.getElementById("viewType").value = viewType;
		document.getElementById("visible").value = visible;
		$.tree.focused().rename("#" + viewResourceId, viewName);
		document.getElementById("category").value = "";
		document.getElementById("description").value = "";
		document.getElementById("viewInfo").value = "";
		document.getElementById("viewType").value = "";
		document.getElementById("visible").value = "";
	}

	function deleteView(viewResourceId){
		$.tree.focused().remove("#" + viewResourceId);
	}

	function refreshTree(){
		$.tree.focused().refresh();
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
							<input id="viewName" size="20" class='ct_input_g'>
							<input id="searchClickYn" type="hidden" value="N">
							<input id="description" type="hidden" value="">
							<input id="category" type="hidden" value="">
							<input id="viewInfo" type="hidden" value="">
							<input id="viewType" type="hidden" value="">
							<input id="visible" type="hidden" value="">
							<script type="text/javascript">
							$("#viewName").autocomplete(
								"<c:url value='/viewresources/getViewNameList.do' />", {
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
	          				<div id="view" class="demo" style="overflow:auto; height:410px;width:218px;border:1px solid #C9CFDD;">
	          					<span><anyframe:message code="role.ui.tree.span" /></span>
							</div>
						</div>
					</td>
	      		</tr>
			</table>
		</td>
		<td align="left" style="padding-top:6px;">
		<form name="f1">
			<input type="hidden" name="viewResourceId">
			<input type="hidden" name="tabInfo" value="viewResourceDetail">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" >
				<tr height="30">
					<td height="30" valign="bottom" background="<c:url value='/images/content/bg_tab.gif'/>" style="padding-left:10px">
						<table height="24" border="0" cellpadding="0" cellspacing="0">
							<tr height="21">
								<td width="134" height="27" align="center" valign="bottom" background="<c:url value='/images/content/bg_tab_menu2.gif'/>" bgcolor="#EDEDED" 
									id="viewDetailTab" ><a href="javascript:moveToViewResourceDetail();"><anyframe:message code="viewresource.ui.tab.viewdetail" /></a></td>
								<td width="134" height="27" align="center" valign="bottom" background="<c:url value='/images/content/bg_tab_menu3.gif'/>" bgcolor="#EDEDED" 
									id="viewListTab" ><a href="javascript:moveToViewList();"><anyframe:message code="viewresource.ui.tab.viewlist" /></a></td>
								<td width="145" height="21" align="center" valign="bottom" background="<c:url value='/images/content/bg_tab_menu3.gif'/>" bgcolor="#EDEDED" 
									id="viewMappingTab" ><a href="javascript:moveToViewMapping();"><anyframe:message code="viewresource.ui.tab.viewmapping" /></a></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td style="padding-left:10px">
						<iframe src="<c:url value='/viewresources/get.do?' />" width="630" height="415" frameborder="0" scrolling="no" name="frame01" id="frame01"></iframe>
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
