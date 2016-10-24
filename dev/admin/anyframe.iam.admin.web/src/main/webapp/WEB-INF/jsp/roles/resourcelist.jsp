<%@ taglib uri='http://www.sds.samsung.com/tags' prefix='anyframe' %>
<%@ include file="/common/taglibs.jsp"%> 
<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><anyframe:message code="roleresource.ui.title.resourcelist" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">	

<jsp:include page="/common/jquery-include.jsp" />
<jsp:include page="/common/jqgrid-include.jsp" />

<script type="text/javascript">
<!--
jQuery(document).ready( function() {
   	var roleId = opener.document.resourceGrid.roleId.value;
	jQuery("#grid2").jqGrid( {
		sortable: true,
		url: "<c:url value='/securedresourcesroles/listDataUnmapped.do?&roleId=' />" + roleId,
		mtype:'GET',
		datatype : "json",
		colNames : [ '<anyframe:message code="roleresource.ui.grid.sortorder" />', '<anyframe:message code="roleresource.ui.grid.resourceid" />', '<anyframe:message code="roleresource.ui.grid.resourcename" />', '<anyframe:message code="roleresource.ui.grid.resourcepattern" />', '<anyframe:message code="roleresource.ui.grid.resourcetype" />' ],
		jsonReader: {
	        repeatitems: false
	    },
		colModel : [ 
		{
			name : 'sortOrder',
			index : 'sortOrder',
			sorttype : 'int', 
			align : 'right',
			sorttype : 'int',
			formatter : 'integer',
			width : 60
		}, {
			key : true,
			name : 'resourceId',
			index : 'resourceId',
			width : 80,
			sorttype : 'text'
		}, {
			name : 'resourceName',
			index : 'resourceName',
			width : 120,
			sorttype : 'text'
		}, {
			name : 'resourcePattern',
			index : 'resourcePattern',
			width : 300,
			sorttype : 'text'
		}, {
			name : 'resourceType',
			index : 'resourceType',
			width : 100,
			sorttype : 'text'
		} ],
		width : 615,
		height : 230,
		multiselect : true,
		pager : jQuery('#pager2'),
		sortname : 'sortOrder',
		sortorder: 'asc',
		rowNum : 20,
		rowList : [ 10, 20, 30 ],
		viewrecords : true,
		loadError: function(xhr,st,err) {
	    	if(st == "parsererror" && xhr.responseText.match('<title>Login</title>') != null) {									
				opener.location.href = "<c:url value='/login/relogin.do?inputPage=/securedresourcesroles/addView.do?&roleId='/>" + roleId;
				window.close();
				return;
			}
			alert("Type: "+st+ "\nErr: "+ xhr.responseText +"\n Response: "+ xhr.status + " "+xhr.statusText); 
		}
	});
	
	/* Button Function Start (Resource CRUD) */
	
	/* Close the window */
	$('[name=closeWindow]').click( function(){
		window.close();
	});

	/* Mapping Resources */
	$("[name=mappingResource]").click( function() {
		var rowNum;
		var rowData;
		var roleId = opener.document.resourceGrid.roleId.value;
		rowNum = new String(jQuery("#grid2").getGridParam('selarrrow'));
		rowNumList = rowNum.split(",");
		var rowDataArray = new Array();
		if(rowNum == null || rowNum ==""){
			alert("<anyframe:message code='roleresource.ui.alert.noselectedrow' />");
			return false;
		} else {
			if(roleId=="")
			{
				alert("<anyframe:message code='roleresource.ui.alert.noselectedrole' />");
				return false;
			}
			for(var i = 0 ; i < rowNumList.length ; i++){
				rowData = jQuery("#grid2").getRowData(rowNumList[i]);
				rowDataArray[i] = rowData.resourceId;
				jQuery("#grid2").delRowData(rowData.resourceId);
			}
		    window.opener.reloadGrid(roleId, rowDataArray);
			window.close();
		}
	});

	/* Search Resource */
	$("[name=searchResource]").click( function() {
		var isMapped = "Y";
		jQuery("#grid2").setPostData({searchCondition:$("#searchCondition").val(), searchKeyword:$("#searchKeyword").val(), searchType:$("#searchType").val(),isMapped:isMapped});
		jQuery("#grid2").setGridParam({url:"<c:url value='/securedresourcesroles/listDataUnmapped.do?' />"}).trigger("reloadGrid");
	});

	/* auto click by enter key */
	$("#searchKeyword").keypress(function (e) {
		if (e.which == 13)
			$("[name=searchResource]").trigger("click");
	});
});
//-->
</script>
<style type="text/css">
<!--
body {
	background-color: #FFFFFF;
}
-->
</style>
</head>
<body marginheight="0">
<form name="resourceGrid" onSubmit="return false">
<table width="620" height="44" border="0"
			cellpadding="0" cellspacing="0">
			<tr>
				<td height="44" align="left" class="checkResource"
					style="padding-left: 38px">Resource List </td>
			</tr>
  </table>
<table width="620" border="0" cellpadding="0" cellspacing="0" style="margin-top:5px">
	<tr>
		<td width="100">
			<select id="searchCondition" class="selbox">
				<option value="resourceName"><anyframe:message code='roleresource.ui.selectbox.resourcename' /></option>
				<option value="resourceId"><anyframe:message code='roleresource.ui.selectbox.resourceid' /></option>
				<option value="resourcePattern"><anyframe:message code='roleresource.ui.selectbox.resourcepattern' /></option>
			</select>
		</td>
		<td width="100">
			<input type="text" id="searchKeyword" size="15" class="ct_input_g">
		</td>
		<td width="80">
			<select id="searchType" class="selbox">
				<option value="All"><anyframe:message code='roleresource.ui.selectbox.resourcetype.all' /></option>
				<option value="url"><anyframe:message code='roleresource.ui.selectbox.resourcetype.url' /></option>
				<option value="method"><anyframe:message code='roleresource.ui.selectbox.resourcetype.method' /></option>
				<option value="pointcut"><anyframe:message code='roleresource.ui.selectbox.resourcetype.pointcut' /></option>
			</select>
		</td> 
		<td width="80">
			<a href="#" name="searchResource" class="searchBtn"><anyframe:message code="user.ui.btn.search" /></a>		
		</td>
		<td width="260" align="right">
			<table>
				<tr>
					<td>
					  <table height="22" border="0" cellpadding="0" cellspacing="0">
			              <tr>
			                <td width="18"><img src="<c:url value='/images/btn/btn_add.gif'/>" width="18" height="22"></td>
			                <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="mappingResource"><anyframe:message code="roleresource.ui.btn.allocation" /></a></td>
			                <td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
			              </tr>
		              </table>
		            </td>
		            <td>
				    	<table height="22" border="0" cellpadding="0" cellspacing="0">
				          <tr>
				            <td width="18"><img src="<c:url value='/images/btn/btn_delete.gif'/>" width="18" height="22"></td>
				            <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="closeWindow"><anyframe:message code="roleresource.ui.btn.close" /></a></td>
				            <td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
				          </tr>
			        	</table>
		        	</td>
	        	</tr>
	        </table>		
		</td>
	</tr>
</table>
<table width="630" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
		<table id="grid2" class="scroll" cellpadding="0" cellspacing="0"></table>
		<div id="pager2" class="scroll" style="text-align: center;"></div>
		</td>
	</tr>
</table>
</form>
</body>
</html>