<%@ taglib uri='http://www.anyframejava.org/tags' prefix='anyframe' %>
<%@ include file="/common/taglibs.jsp"%> 
<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><anyframe:message code="viewresource.ui.title.viewresources" /></title>

<jsp:include page="/common/jquery-include.jsp" />
<jsp:include page="/common/jqgrid-include.jsp" />

<script type="text/javascript">
<!--
jQuery(document).ready(
	function() {
		var parentViewResourceId = parent.document.f1.viewResourceId.value;
		jQuery("#grid2").jqGrid( 
		{
			sortable: true,
			url: "<c:url value='/viewresources/listData.do?&parentViewResourceId=' />" + parentViewResourceId,
			mtype:'POST',
			datatype : "json",
			colNames : [ 
						"<anyframe:message code='viewresource.ui.grid.col.viewresourceid' />", 
						"<anyframe:message code='viewresource.ui.grid.col.category' />", 
						"<anyframe:message code='viewresource.ui.grid.col.viewname' />", 
						"<anyframe:message code='viewresource.ui.grid.col.description' />", 
						"View Type", 
						"Visible", 
						"<anyframe:message code='viewresource.ui.grid.col.viewinfo' />"],
			jsonReader: {
		        repeatitems: false
		    },
			colModel : [ 
			{
				key : true,
				name : 'viewResourceId',
				index : 'viewResourceId',
				width : 80,
				sorttype : 'text'				
			}, {
				name : 'category',
				index : 'category',
				sorttype : 'text',
				width : 60
			}, {
				name : 'viewName',
				index : 'viewName',
				sorttype : 'text',
				width : 80
			}, {
				name : 'description',
				index : 'description',
				sorttype : 'text',
				width : 150
			}, {
				name : 'viewType',
				index : '',
				sorttype : 'viewType',
				width : 60
			}, {
				name : 'visible',
				index : 'visible',
				sorttype : 'text',
				width : 40
			}, {
				name : 'viewInfo',
				index : 'viewInfo',
				sorttype : 'text',
				width : 60
			} ],
			width : 615,
			height : 322,
			forceFit:true,
			multiselect : true,
			pager : jQuery('#pager2'),
			sortname : 'viewResourceId',
			sortorder: 'asc',
			rowNum : 20,
			rowList : [ 10, 20, 30 ],
			viewrecords : true,

			loadError: function(xhr,st,err) {
				if(st == "parsererror" && xhr.responseText.match('<title>Login</title>') != null) {									
					location.href = "<c:url value='/login/relogin.do?inputPage=/viewresources/list.do'/>";
					return;
				}
				alert("Type: "+st+ "\nErr: "+ xhr.responseText +"\n Response: "+ xhr.status + " "+xhr.statusText); 
			}
		});
		
		/* Button Function Start (Resource CRUD) */
		
		/* DELETE Resource */
		$('[name=removeResource]').click( function(){

			var rowNum;
			var rowData;
			var rowArray = new Array();
			rowNum = new String(jQuery("#grid2").getGridParam('selarrrow'));
			rowNumList = rowNum.split(",");

			if(rowNum == null || rowNum ==""){
				alert("<anyframe:message code='viewresource.ui.alert.noselectedrow' />");
				return false;
			} else {
				// message 정의
				if(confirm("<anyframe:message code='viewresource.ui.alert.confirmtodelete' />")){
					
				for(var i = 0 ; i < rowNumList.length ; i++){
					rowData = jQuery("#grid2").getRowData(rowNumList[i]);
					rowArray[i] = rowData.viewResourceId;
					jQuery("#grid2").delRowData(rowData.viewResourceId);
				}
				
				jQuery.ajaxSettings.traditional = true;
				//jQuery("#grid2").setPostData({viewResourceIds:rowArray, parentViewResourceId:parentViewResourceId});
				//jQuery("#grid2").setGridParam({url:"<c:url value='/viewresources/delete.do?'/>"}).trigger("reloadGrid");
				//jQuery("#grid2").setGridParam({url:"<c:url value='/viewresources/listData.do?'/>"});
				$.post("<c:url value='/viewresources/delete.do?'/>", {viewResourceIds:rowArray, parentViewResourceId:parentViewResourceId}, function(data){
			    	jQuery("#grid2").trigger("reloadGrid");
				});
					
				parent.refreshTree();
								}
							}
						});

		/* Add Resource */
		$("[name=addResource]").click( function() {
			location.href = "<c:url value='/viewresources/addView.do?' />";
		});

		/* Search Resource */
		$("[name=searchResource]").click( function() {
			jQuery("#grid2").setPostData({searchCondition:$("#searchCondition").val(), searchKeyword:$("#searchKeyword").val() });
			jQuery("#grid2").setGridParam({url:"<c:url value='/viewresources/listData.do?' />"}).trigger("reloadGrid");
		});

		/* auto click by enter key */
		$("#searchKeyword").keypress(function (e) {
			if (e.which == 13){
				$("[name=searchResource]").trigger("click");
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
	height:100%;
	background-color: #FFFFFF;
}
-->
</style>
</head>
<body>
<form name="resourceGrid">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td width="10" rowspan="3">&nbsp;</td>
    <td height="6"></td>
  </tr>
  <tr>
    <td>
    <div id="documentation" class="demo" style="overflow:auto; height:460px;width:800px;">
	    <table width="620" border="0" cellpadding="0" cellspacing="0">
		<tr height="30">
			<td width="41">
				<select id="searchCondition" class="selbox">
					<option value="viewResourceId"><anyframe:message code="viewresource.ui.label.viewresourceid" /></option>
					<option value="category"><anyframe:message code="viewresource.ui.label.category" /></option>
					<option value="viewName"><anyframe:message code="viewresource.ui.label.viewname" /></option>
					<option value="viewInfo"><anyframe:message code="viewresource.ui.label.viewinfo" /></option>
				</select>
			</td>
			<td width="67" class="tdpadding">
				<input type="text" id="searchKeyword" size="20" class="ct_input_g">
			</td>
			<td width="38" class="tdpadding">
				<a href="#" name="searchResource" class="searchBtn"><anyframe:message code="user.ui.btn.search" /></a>		
			</td>
			<td width="600" align="right">
				<table>
					<tr>
			            <td>
					    	<table height="22" border="0" cellpadding="0" cellspacing="0">
					          <tr>
					            <td width="18"><img src="<c:url value='/images/btn/btn_delete.gif'/>" width="18" height="22"></td>
					            <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="removeResource"><anyframe:message code="resource.ui.btn.delete" /></a></td>
					            <td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
					          </tr>
				        	</table>
			        	</td>
		        	</tr>
		        </table>			
			</td>
		</tr>
		</table>
		<table width="572" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
				<table id="grid2" class="scroll" cellpadding="0" cellspacing="0"></table>
				<div id="pager2" class="scroll" style="text-align: center;"></div>
				</td>
			</tr>
		</table>
		</div>
</td>
  </tr>
</table>
</form>	
</body>
</html>