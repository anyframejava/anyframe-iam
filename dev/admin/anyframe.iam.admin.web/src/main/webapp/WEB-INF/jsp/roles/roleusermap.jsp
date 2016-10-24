<%@ taglib uri='http://www.sds.samsung.com/tags' prefix='anyframe' %>
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
	var callbackvar = document.f1.callbackvar;
	// Grid Component
	jQuery("#grid2").jqGrid({
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
		height:285,
		multiselect: true,
		loadComplete:function(){
			jQuery("#grid3").jqGrid({
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
				height:285,
				multiselect : true,
				loadComplete:function(){
					if(callbackvar.value=="toRight"){
						jQuery("#grid2").setGridParam({url:"<c:url value='/authorities/listData.do?&roleId=' />" + roleId}).trigger("reloadGrid");
					}
			    },
				pager : jQuery('#pager3'),
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
			
			if(callbackvar.value=="toLeft"){
				jQuery("#grid3").setPostData({roleId:roleId});
				jQuery("#grid3").setGridParam({url:"<c:url value='/authorities/existListData.do?'/>"}).trigger("reloadGrid");
			} 
	    },
		pager : jQuery('#pager2'),
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

	/* Button */
	
	/* toLeft Button */
	$('[name=toLeft]').click( function() {
		var rowNum;
		var rowData;
		var roleId = parent.document.f1.roleId.value;
		callbackvar.value = "toLeft";
		rowNum = new String(jQuery("#grid3").getGridParam('selarrrow'));
		rowNumList = rowNum.split(",");
		var rowDataArray = new Array();
		if(rowNum == null || rowNum ==""){
			alert("<anyframe:message code='roleuser.ui.alert.norow' />");
			return false;
		} else{
			for(var i = 0 ; i < rowNumList.length ; i++){
				rowData = jQuery("#grid3").getRowData(rowNumList[i]);
				rowDataArray[i] = rowData.userId;
			}
			jQuery("#grid2").setPostData({roleId:roleId, userId:rowDataArray});
			jQuery("#grid2").setGridParam({url:"<c:url value='/authorities/delete.do?' />"}).trigger("reloadGrid");
			jQuery("#grid2").setGridParam({url:"<c:url value='/authorities/listData.do?&roleId=' />" + roleId});
		}
	});

	/* toRight Button */
	$('[name=toRight]').click( function() {
		var rowNum;
		var rowData;
		callbackvar.value = "toRight";
		rowNum = new String(jQuery("#grid2").getGridParam('selarrrow'));
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
				rowData = jQuery("#grid2").getRowData(rowNumList[i]);
				rowDataArray[i] = rowData.userId;
			}
			jQuery("#grid3").setPostData({roleId:roleId, userId:rowDataArray});
			jQuery("#grid3").setGridParam({url:"<c:url value='/authorities/add.do?' />"}).trigger("reloadGrid");
			jQuery("#grid3").setGridParam({url:"<c:url value='/authorities/existListData.do?'/> "});
		}
	});

	/* search Button */
	$("[name=searchUser]").click( function() {
		callbackvar.value = "";
		jQuery("#grid2").setPostData({searchCondition:$("#searchCondition").val(), searchKeyword:$("#searchKeyword").val()});
		jQuery("#grid2").setGridParam({url:"<c:url value='/authorities/listData.do?' />"}).trigger("reloadGrid");
	});

	$("[name=searchUser2]").click( function() {
		callbackvar.value = "";
		jQuery("#grid3").setPostData({searchCondition:$("#searchCondition2").val(), searchKeyword:$("#searchKeyword2").val()});
		jQuery("#grid3").setGridParam({url:"<c:url value='/authorities/existListData.do?&roleId=' />" + roleId}).trigger("reloadGrid");
	});

	/* auto click by enter key */
	$("#searchKeyword").keypress(function (e) {
		if (e.which == 13)
			$("[name=searchUser]").trigger("click");
	});

	/* auto click by enter key */
	$("#searchKeyword2").keypress(function (e) {
		if (e.which == 13)
			$("[name=searchUser2]").trigger("click");
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
<input type="hidden" name="callbackvar">
<table width="572" border="0" cellpadding="0" cellspacing="0" style="margin-top: 13px;">
	<tr>
		<td width="41" height="30">
			<select name="searchCondition" id="searchCondition" class="selbox">
			<option value="userName"><anyframe:message code='roleuser.ui.selectbox.username' /></option>
			<option value="userId"><anyframe:message code='roleuser.ui.selectbox.userid' /></option>
			</select>
		</td>
		<td width="93" style="padding-left: 3px;">
			<input type="text" name="searchKeyword" id="searchKeyword" size="15" class="ct_input_g">
		</td>
		<td width="123" style="padding-left: 3px;">
			<a href="#" name="searchUser" class="searchBtn"><anyframe:message code="user.ui.btn.search" /></a>			
		</td>
		
		<td width="69">&nbsp;		</td>

		<td width="41">
			<select name="searchCondition2" id="searchCondition2" class="selbox">
			<option value="userName"><anyframe:message code='roleuser.ui.selectbox.username' /></option>
			<option value="userId"><anyframe:message code='roleuser.ui.selectbox.userid' /></option>
			</select>
		</td>
		<td width="93" style="padding-left: 3px;">
			<input type="text" name="searchKeyword2" id="searchKeyword2" size="15" class="ct_input_g">
		</td>
		<td width="112" style="padding-left: 3px;">
			<a href="#" name="searchUser2" class="searchBtn"><anyframe:message code="user.ui.btn.search" /></a>		
		</td>
	</tr>
	<tr>
		<td colspan="3" valign="top" >
			<table id="grid2" class="scroll" cellpadding="0" cellspacing="0"></table>
			<div id="pager2" class="scroll" style="text-align: center;"></div>
		</td>
		<td width="69" valign="middle" align="center">
			<div id="shiftBtn"><a class="outBtn" name="toRight" href="#">out</a></div>
			<div id="shiftBtn"><a class="inBtn" name="toLeft" href="#">in</a></div>
		</td>
		<td colspan="3" valign="top">
			<table id="grid3" class="scroll" cellpadding="0" cellspacing="0"></table>
			<div id="pager3" class="scroll" style="text-align: center;"></div>
		</td>
	</tr>
</table>
</form>
</body>
</html>