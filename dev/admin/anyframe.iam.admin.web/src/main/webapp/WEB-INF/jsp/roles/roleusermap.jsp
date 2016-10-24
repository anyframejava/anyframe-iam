<%@ taglib uri='http://www.anyframejava.org/tags' prefix='anyframe' %>
<%@ include file="/common/taglibs.jsp"%> 
<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><anyframe:message code="roleuser.ui.title.userlist" /></title>

<jsp:include page="/common/jquery-include.jsp" />
<jsp:include page="/common/jqgrid-include.jsp" />

<script type="text/javascript">
<!--
jQuery(document).ready( function() {
	var roleId = parent.document.f1.roleId.value;

	// Left Grid Component
	jQuery("#gridLeft").jqGrid({
		sortable: true,
		url:"<c:url value='/authorities/listData.do?&roleId=' />" + roleId,
		mtype:'POST',
		datatype: 'json',
		colNames:['<anyframe:message code="roleuser.ui.grid.userid" />', '<anyframe:message code="roleuser.ui.grid.username" />', '<anyframe:message code="roleuser.ui.grid.usergroup" />'],
		jsonReader: {
	        repeatitems: false
	    },
		colModel: [
		           {key: true, name: 'userId', index:'userId', sorttype : 'text', width:80},
		           {name: 'userName', index:'userName', sorttype : 'text', width:80},
		           {name: 'groupName', index: 'groupName', sorttype : 'text', width: 80}
		           ],
		width:240,
		height:295,
		multiselect: true,
		loadComplete:function(){},
		pager : jQuery('#pagerLeft'),
		sortorder: 'asc',
		sortname : 'userId',
		rowNum : 20,
		caption : '<anyframe:message code="roleuser.ui.grid.notalocaption" />',
		rowList : [ 10, 20, 30 ],
		viewrecords : true,
		loadError: function(xhr,st,err) {
			if(st == "parsererror" && xhr.responseText.match('<title>Login</title>') != null) {									
				location.href = "<c:url value='/login/relogin.do?inputPage=/roleusermapping/addView.do?&roleId='/>" + roleId;
				return;
			}
			alert("Type: "+st+ "\nErr: "+ xhr.responseText +"\n Response: "+ xhr.status + " "+xhr.statusText); 
		}
	});

	// Right Grid Component
	jQuery("#gridRight").jqGrid({
		sortable: true,
		url:"<c:url value='/authorities/existListData.do?&roleId='/> " + roleId,
		mtype: 'POST',
		datatype:'json',
		colNames:['<anyframe:message code="roleuser.ui.grid.userid" />', '<anyframe:message code="roleuser.ui.grid.username" />', '<anyframe:message code="roleuser.ui.grid.usergroup" />'],
		jsonReader: {
	        repeatitems: false
	    },
		colModel: [
		           {key: true, name: 'userId', index:'userId', sorttype : 'text', width:80},
		           {name: 'userName', index:'userName', width:80, sorttype : 'text'},
		           {name: 'groupName', index: 'groupName', width: 80, sorttype : 'text'}
		           ],
		width:240,
		height:295,
		multiselect : true,
		loadComplete:function(){},
		pager : jQuery('#pagerRight'),
		sortname : 'userId',
		sortorder: 'asc',
		rowNum : 20,
		caption : '<anyframe:message code="roleuser.ui.grid.alocaption" />',
		rowList : [ 10, 20, 30 ],
		viewrecords : true,
		loadError: function(xhr,st,err) { 
			if(st == "parsererror" && xhr.responseText.match('<title>Login</title>') != null) {									
				location.href = "<c:url value='/login/relogin.do?inputPage=/roleusermapping/addView.do?&roleId='/>" + roleId;
				return;
			}
			alert("Type: "+st+ "\nErr: "+ xhr.responseText +"\n Response: "+ xhr.status + " "+xhr.statusText); 
		}
	});
	
	/* toLeft Button */
	$('[name=toLeft]').click( function() {
		var rowNum;
		var rowData;
		var roleId = parent.document.f1.roleId.value;
		rowNum = new String(jQuery("#gridRight").getGridParam('selarrrow'));
		rowNumList = rowNum.split(",");
		var rowDataArray = new Array();
		if(rowNum == null || rowNum ==""){
			alert("<anyframe:message code='roleuser.ui.alert.norow' />");
			return false;
		} else{
			for(var i = 0 ; i < rowNumList.length ; i++){
				rowData = jQuery("#gridRight").getRowData(rowNumList[i]);
				rowDataArray[i] = rowData.userId;
				jQuery("#gridRight").delRowData(rowData.userId);
			}
			jQuery.ajaxSettings.traditional = true;
			$.post("<c:url value='/authorities/delete.do' />", {roleId:roleId, userId:rowDataArray}, function(data){
				jQuery("#gridLeft").trigger("reloadGrid");
		    });
		}
	});

	/* toRight Button */
	$('[name=toRight]').click( function() {
		var rowNum;
		var rowData;
		//var roleId = parent.document.f1.roleId.value;
		rowNum = new String(jQuery("#gridLeft").getGridParam('selarrrow'));
		rowNumList = rowNum.split(",");
		var rowDataArray = new Array();
		if(rowNum == null || rowNum ==""){
			alert("<anyframe:message code='roleuser.ui.alert.norow' />");
			return false;
		} else{
			if(roleId==""){
				alert("<anyframe:message code='roleuser.ui.alert.norole' />");
				return false;
			}
			for(var i = 0 ; i < rowNumList.length ; i++){
				rowData = jQuery("#gridLeft").getRowData(rowNumList[i]);
				rowDataArray[i] = rowData.userId;
				jQuery("#gridLeft").delRowData(rowData.userId);
			}
			jQuery.ajaxSettings.traditional = true;
			$.post("<c:url value='/authorities/add.do?' />", {roleId:roleId, userId:rowDataArray}, function(data){
		    	jQuery("#gridRight").trigger("reloadGrid");
		    });
		}
	});

	/* search Button */
	$("[name=searchUserLeft]").click( function() {
		jQuery("#gridLeft").setPostData({searchCondition:$("#searchConditionLeft").val(), searchKeyword:$("#searchKeywordLeft").val()});//, roleId:roleId});
		jQuery("#gridLeft").trigger("reloadGrid");
	});

	$("[name=searchUserRight]").click( function() {
		jQuery("#gridRight").setPostData({searchCondition:$("#searchConditionRight").val(), searchKeyword:$("#searchKeywordRight").val()});//, roleId:roleId});
		jQuery("#gridRight").trigger("reloadGrid");
	});

	/* auto click by enter key */
	$("#searchKeywordLeft").keypress(function (e) {
		if (e.which == 13){
			$("[name=searchUserLeft]").trigger("click");
			return false;
		}
	});

	/* auto click by enter key */
	$("#searchKeywordRight").keypress(function (e) {
		if (e.which == 13){
			$("[name=searchUserRight]").trigger("click");
			return false;
		}
	});
});
//-->
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
-->
</style></head>
<body>

<form name="f1">
<table width="572" border="0" cellpadding="0" cellspacing="0" style="margin-top: 13px;">
	<tr>
		<td width="41" height="30">
			<select name="searchConditionLeft" id="searchConditionLeft" class="selbox">
			<option value="userName"><anyframe:message code='roleuser.ui.selectbox.username' /></option>
			<option value="userId"><anyframe:message code='roleuser.ui.selectbox.userid' /></option>
			</select>
		</td>
		<td width="93" style="padding-left: 3px;">
			<input type="text" name="searchKeywordLeft" id="searchKeywordLeft" size="15" class="ct_input_g">
		</td>
		<td width="123" style="padding-left: 3px;">
			<a href="#" name="searchUserLeft" class="searchBtn"><anyframe:message code="user.ui.btn.search" /></a>			
		</td>
		
		<td width="69">&nbsp;		</td>

		<td width="41">
			<select name="searchConditionRight" id="searchConditionRight" class="selbox">
			<option value="userName"><anyframe:message code='roleuser.ui.selectbox.username' /></option>
			<option value="userId"><anyframe:message code='roleuser.ui.selectbox.userid' /></option>
			</select>
		</td>
		<td width="93" style="padding-left: 3px;">
			<input type="text" name="searchKeywordRight" id="searchKeywordRight" size="15" class="ct_input_g">
		</td>
		<td width="112" style="padding-left: 3px;">
			<a href="#" name="searchUserRight" class="searchBtn"><anyframe:message code="user.ui.btn.search" /></a>		
		</td>
	</tr>
	<tr>
		<td colspan="3" valign="top" >
			<table id="gridLeft" class="scroll" cellpadding="0" cellspacing="0"></table>
			<div id="pagerLeft" class="scroll" style="text-align: center;"></div>
		</td>
		<td width="69" valign="middle" align="center">
			<div id="shiftBtn"><a class="outBtn" name="toRight" href="#">out</a></div>
			<div id="shiftBtn"><a class="inBtn" name="toLeft" href="#">in</a></div>
		</td>
		<td colspan="3" valign="top">
			<table id="gridRight" class="scroll" cellpadding="0" cellspacing="0"></table>
			<div id="pagerRight" class="scroll" style="text-align: center;"></div>
		</td>
	</tr>
</table>
</form>
</body>
</html>