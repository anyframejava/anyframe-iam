<%@ taglib uri='http://www.anyframejava.org/tags' prefix='anyframe' %>
<%@ include file="/common/taglibs.jsp"%> 
<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><anyframe:message code="roleresource.ui.title.resourcelist" /></title>

<jsp:include page="/common/jquery-include.jsp" />
<jsp:include page="/common/jqgrid-include.jsp" />

<script type="text/javascript">
<!--
	jQuery(document).ready(	function() {
		jQuery("#grid2").jqGrid( {
			sortable: true,
			url: "<c:url value='/restriction/timeresource/listUnmappedResourceData.do?timeId=' />" + opener.document.restrictedtimes.timeId.value,
			mtype:'GET',
			datatype : "json",
			colNames : [ '<anyframe:message code="roleresource.ui.grid.resourceid" />', '<anyframe:message code="roleresource.ui.grid.resourcename" />', '<anyframe:message code="roleresource.ui.grid.sortorder" />', '<anyframe:message code="roleresource.ui.grid.resourcepattern" />', '<anyframe:message code="roleresource.ui.grid.resourcetype" />' ],
			jsonReader: {
		        repeatitems: false
		    },
			colModel : [ {
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
				name : 'sortOrder',
				index : 'sortOrder',
				sorttype : 'int', 
				align : 'right',
				sorttype : 'int',
				formatter : 'integer',
				width : 60
			},{
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
			width : 563,
			height : 260,
			multiselect : true,
			pager : jQuery('#pager2'),
			sortname : 'resourceId',
			sortorder: 'asc',
			rowNum : 20,
			rowList : [ 10, 20, 30 ],
			viewrecords : true,
			loadError: function(xhr,st,err) {
		    	if(st == "parsererror" && xhr.responseText.match('<title>Login</title>') != null) {									
					opener.location.href = "<c:url value='/login/relogin.do?inputPage=/restriction/timeresource/listResourcePopUp.do'/>";
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
			var timeId = opener.document.restrictedtimes.timeId.value;
			rowNum = new String(jQuery("#grid2").getGridParam('selarrrow'));
			rowNumList = rowNum.split(",");
			var rowDataArray = new Array();
			if(rowNum == null || rowNum ==""){
				alert("<anyframe:message code='roleresource.ui.alert.noselectedrow' />");
				return false;
			} else {
				if(timeId=="")
				{
					alert("<anyframe:message code='roleresource.ui.alert.noselectedrole' />");
					return false;
				}
				for(var i = 0 ; i < rowNumList.length ; i++){
					rowData = jQuery("#grid2").getRowData(rowNumList[i]);
					rowDataArray[i] = rowData.resourceId;
					jQuery("#grid2").delRowData(rowData.resourceId);
				}
			    window.opener.reloadGrid(timeId, rowDataArray);
				window.close();
			}
		});

		/* Search Resource */
		$("[name=searchResource]").click( function() {
			jQuery("#grid2").setPostData({timeId:opener.document.restrictedtimes.timeId.value, searchCondition:$("#searchCondition").val(), searchKeyword:$("#searchKeyword").val(), searchType:$("#searchType").val()});
			jQuery("#grid2").setGridParam({url:"<c:url value='/restriction/timeresource/listUnmappedResourceData.do?' />"}).trigger("reloadGrid");
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
</style></head>
<body>
<form name="resourceGrid" onsubmit="return false">
<table width="620">
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
				<option value="URL"><anyframe:message code='roleresource.ui.selectbox.resourcetype.url' /></option>
				<option value="Method"><anyframe:message code='roleresource.ui.selectbox.resourcetype.method' /></option>
				<option value="PointCut"><anyframe:message code='roleresource.ui.selectbox.resourcetype.pointcut' /></option>
			</select>
		</td> 
		<td width="80">
			<a href="#" name="searchResource" class="searchBtn"><anyframe:message code="user.ui.btn.search" /></a></td>
		</td>
		<td width="260" align="right">
			<table height="22" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td style="padding-left: 3px;">
						<table height="22" border="0" cellpadding="0" cellspacing="0">
              				<tr>
                				<td width="18"><img src="<c:url value='/images/btn/btn_add.gif'/>" width="18" height="22"></td>
                				<td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="mappingResource"><anyframe:message code="roleresource.ui.btn.add" /></a></td>
                				<td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
              				</tr>
           				 </table>
           			</td>
					<td style="padding-left: 3px;">
						<table height="22" border="0" cellpadding="0" cellspacing="0">
			              <tr>
			                <td width="18"><img src="<c:url value='/images/btn/btn_cancel.gif'/>" width="18" height="22"></td>
			                <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="closeWindow"><anyframe:message code="restrictedtimes.ui.btn.close" /></a></td>
			                <td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
			              </tr>
			            </table>
					</td>
				</tr>
			</table>		
		</td>
	</tr>
</table>
<table width="620">
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