<%@ taglib uri='http://www.anyframejava.org/tags' prefix='anyframe' %>
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
		var roleId = parent.document.f1.roleId.value;
		var isNested = document.resourceGrid.isNested;
		var url;
		 if("Y" == isNested.value) {
			url = "<c:url value='/securedresourcesroles/listDataWithLevel.do?&roleId=' />" + roleId;
		} else {
			url = "<c:url value='/securedresourcesroles/listData.do?&roleId=' />" + roleId;
		}

		jQuery("#grid2").jqGrid({
			sortable: true,
			url: "<c:url value='/securedresourcesroles/listData.do?&roleId=' />" + roleId,
			mtype:'POST',
			datatype : "json",
			colNames : [ 
						'<anyframe:message code="roleresource.ui.grid.sortorder" />', 
						'<anyframe:message code="roleresource.ui.grid.resourceid" />', 
						'<anyframe:message code="roleresource.ui.grid.resourcename" />',
						'<anyframe:message code="roleresource.ui.grid.roleId" />', 
						'<anyframe:message code="roleresource.ui.grid.roleName" />', 
						'<anyframe:message code="roleresource.ui.grid.resourcepattern" />', 
						'<anyframe:message code="roleresource.ui.grid.resourcetype" />' ],
			jsonReader: {
		        repeatitems: false
		    },
			colModel : [ {
				name : 'sortOrder',
				index : 'sortOrder',
				sorttype : 'int', 
				align : 'right',
				sorttype : 'int',
				formatter : 'integer',
				width : 30
			}, {
				key : true,
				name : 'resourceId',
				index : 'resourceId',
				sorttype : 'text',
				width : 70
			}, {
				name : 'resourceName',
				index : 'resourceName',
				sorttype : 'text',
				width : 90
			}, {
				name : 'roleId',
				index : 'roleId',
				sorttype : 'text',
				width : 70
			}, {
				name : 'roleName',
				index : 'roleName',
				sorttype : 'text',
				width : 60
			}, {
				name : 'resourcePattern',
				index : 'resourcePattern',
				sorttype : 'text',
				width : 150
			}, {
				name : 'resourceType',
				index : 'resourceType',
				sorttype : 'text',
				width : 60
			} ],
			width : 615,
			height : 315,
			multiselect : true,
			pager : jQuery('#pager2'),
			sortname : 'resourceId',
			sortorder: 'asc',
			rowNum : 20,
			rowList : [ 10, 20, 30 ],
			viewrecords : true,
			loadError: function(xhr,st,err) {
				if(st == "parsererror" && xhr.responseText.match('<title>Login</title>') != null) {									
					location.href = "<c:url value='/login/relogin.do?inputPage=/securedresourcesroles/addView.do?&roleId='/>" + roleId;
					return;
				}
				alert("Type: "+st+ "\nErr: "+ xhr.responseText +"\n Response: "+ xhr.status + " "+xhr.statusText); 
			}
		});
		jQuery("#grid2").jqGrid('gridResize',{minWidth:350,maxWidth:800,minHeight:80, maxHeight:350});

		/* Button Function Start (Resource CRUD) */
		
		/* DELETE Resource */
		$('[name=removeResource]').click( function(){
			var rowNum;
			var rowData;
			var rowArray = new Array();
			var roleId = document.resourceGrid.roleId.value;
			rowNum = new String(jQuery("#grid2").getGridParam('selarrrow'));
			rowNumList = rowNum.split(",");

			if(roleId == "" || roleId == null){
				alert("Select Role first");
				return;
			}
			
			if(rowNum == "" || rowNum == null){
				alert("<anyframe:message code='roleresource.ui.alert.noselectedrow' />");
				return false;
			} else {
				if(confirm("<anyframe:message code='roleresource.ui.alert.confirmtodelete' />")){
					for(var i = 0 ; i < rowNumList.length ; i++){
						rowData = jQuery("#grid2").getRowData(rowNumList[i]);
						rowArray[i] = rowData.resourceId;
						jQuery("#grid2").delRowData(rowData.resourceId);
					}
					jQuery.ajaxSettings.traditional = true;
					if("Y" == document.resourceGrid.isNested.value)
						url = "<c:url value='/securedresourcesroles/listDataWithLevel.do?&roleId=' />" + roleId;
					else
						url = "<c:url value='/securedresourcesroles/listData.do?&roleId=' />" + roleId;
					$.post("<c:url value='/securedresourcesroles/delete.do?' />", {resourceIds:rowArray, roleId:roleId}, function(data){
				    	jQuery("#grid2").trigger("reloadGrid");
					});
				}
			}
		});

		/* Mapping Resources */
		$("[name=addResource]").click( function() {
			window.open("<c:url value='/securedresourcesroles/listPopUp.do' />", 'resourceList', 'height=400, width=700');
		});

		/* Search Resource */
		$("[name=searchResource]").click( function() {
			var levelCondition = document.resourceGrid.levelCondition;
				if(levelCondition.value == "parent"){
					jQuery("#grid2").setPostData({searchCondition:$("#searchCondition").val(), searchKeyword:$("#searchKeyword").val(), searchType:$("#searchType").val()});
					jQuery("#grid2").setGridParam({url:"<c:url value='/securedresourcesroles/listData.do?&roleId=' />" + roleId}).trigger("reloadGrid");
				}else{
					jQuery("#grid2").setPostData({searchCondition:$("#searchCondition").val(), searchKeyword:$("#searchKeyword").val(), searchType:$("#searchType").val()});
					jQuery("#grid2").setGridParam({url:"<c:url value='/securedresourcesroles/listDataWithLevel.do?&roleId=' />" + roleId}).trigger("reloadGrid");
				}
		});

		/* auto click by enter key */
		$("#searchKeyword").keypress(function (e) {
			if (e.which == 13)
				$("[name=searchResource]").trigger("click");
		});
	});

	// Has Error in IE
	function reloadGrid(roleId, tempArray){
		var rowDataArray = new Array();
		for(i = 0 ; i < tempArray.length ; i++)
			rowDataArray[i] = tempArray[i];
		jQuery.ajaxSettings.traditional = true;
		if("Y" == document.resourceGrid.isNested.value)
			url = "<c:url value='/securedresourcesroles/listDataWithLevel.do?&roleId=' />" + roleId;
		else
			url = "<c:url value='/securedresourcesroles/listData.do?&roleId=' />" + roleId;
		$.post("<c:url value='/securedresourcesroles/add.do?' />", {roleId:roleId, resourceId:rowDataArray}, function(data){
	    	jQuery("#grid2").trigger("reloadGrid");
	    });
	}

	function changeRole(obj){
		var roleId = document.resourceGrid.roleId.value;
		var levelCondition = document.resourceGrid.levelCondition;
		if(obj.value == "parent"){
			levelCondition.value="parent";
			jQuery("#grid2").setPostData({searchCondition:$("#searchCondition").val(), searchKeyword:$("#searchKeyword").val(), searchType:$("#searchType").val()});
			jQuery("#grid2").setGridParam({url:"<c:url value='/securedresourcesroles/listData.do?&roleId=' />" + roleId}).trigger("reloadGrid");
		}
		else{
			levelCondition.value="child";
			jQuery("#grid2").setPostData({searchCondition:$("#searchCondition").val(), searchKeyword:$("#searchKeyword").val(), searchType:$("#searchType").val()});
			jQuery("#grid2").setGridParam({url:"<c:url value='/securedresourcesroles/listDataWithLevel.do?&roleId=' />" + roleId}).trigger("reloadGrid");
		}
	}
//-->
</script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	height:100%;
	background-color: #FFFFFF;
}
-->
</style></head>
<body>
<form name="resourceGrid" onsubmit="return false">
<input type="hidden" value="<c:out value='${param.roleId}'/>" name="roleId">
<input type="hidden" value="parent" name="levelCondition">
<table width="620" border="0" cellpadding="0" cellspacing="0" style="margin-top: 13px;">
	<tr height="30">
		<td width="41" align="left">
			<select id="searchCondition" class="selbox">
				<option value="resourceName"><anyframe:message code='roleresource.ui.selectbox.resourcename' /></option>
				<option value="resourceId"><anyframe:message code='roleresource.ui.selectbox.resourceid' /></option>
				<option value="resourcePattern"><anyframe:message code='roleresource.ui.selectbox.resourcepattern' /></option>
	  		</select>
  	  </td>
		<td width="67" style="padding-left: 3px;">
			<input type="text" id="searchKeyword" size="10" class="ct_input_g">
  	  </td>
		<td width="47" style="padding-left: 3px;">
			<select id="searchType" class="selbox">
				<option value="All"><anyframe:message code='roleresource.ui.selectbox.resourcetype.all' /></option>
				<option value="url"><anyframe:message code='roleresource.ui.selectbox.resourcetype.url' /></option>
				<option value="method"><anyframe:message code='roleresource.ui.selectbox.resourcetype.method' /></option>
				<option value="pointcut"><anyframe:message code='roleresource.ui.selectbox.resourcetype.pointcut' /></option>
			</select>
		</td>
		<td width="38" align="left" id="button_links" style="padding-left: 3px;">
			<a href="#" name="searchResource" class="searchBtn"><anyframe:message code="user.ui.btn.search" /></a>
		</td>
		<td width="150" align="left">
			<c:if test="${isOracle == true}">
				<select id="roleinfo" class="selbox" onChange="changeRole(this)">
					<option value="parent"><anyframe:message code="roleresource.ui.selectbox.roleInfo.parent" /></option>
					<option value="child"><anyframe:message code="roleresource.ui.selectbox.roleInfo.child" /></option>
				</select>
				<input type="hidden" name="isNested" value="Y" />
			</c:if>
			<c:if test="${ isOracle == false}">
				<input type="hidden" name="isNested" value="N" />
			</c:if>
	  </td>
		<td width="277" align="right">
			<table>
				<tr>
					<td>
					  <table height="22" border="0" cellpadding="0" cellspacing="0">
			              <tr>
			                <td width="18"><img src="<c:url value='/images/btn/btn_add.gif'/>" width="18" height="22"></td>
			                <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="addResource"><anyframe:message code="roleresource.ui.btn.add" /></a></td>
			                <td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
			              </tr>
		              </table>
		            </td>
		            <td>
				    	<table height="22" border="0" cellpadding="0" cellspacing="0">
				          <tr>
				            <td width="18"><img src="<c:url value='/images/btn/btn_delete.gif'/>" width="18" height="22"></td>
				            <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="removeResource"><anyframe:message code="roleresource.ui.btn.remove" /></a></td>
				            <td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
				          </tr>
			        	</table>
		        	</td>
	        	</tr>
	        </table>
	  </td>
	</tr>
</table>
<table width="572" height="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td valign="top">
		<table id="grid2" class="scroll" cellpadding="0" cellspacing="0"></table>
		<div id="pager2" class="scroll" style="text-align: center;"></div>
	  </td>
	</tr>
</table>
</form>
</body>
</html>