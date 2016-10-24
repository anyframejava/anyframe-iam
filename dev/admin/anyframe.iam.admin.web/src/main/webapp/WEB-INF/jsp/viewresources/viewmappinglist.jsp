<%@ taglib uri='http://www.sds.samsung.com/tags' prefix='anyframe' %>
<%@ include file="/common/taglibs.jsp"%> 
<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><anyframe:message code="viewresource.ui.title.viewmapping" /></title>

<jsp:include page="/common/jstree-include.jsp" />
<jsp:include page="/common/jqueryui-include.jsp" />
<jsp:include page="/common/jqgrid-include.jsp" />

<script type="text/javascript">
jQuery(document).ready(
	function() {

		jQuery("#grid2").jqGrid( 
		{
			sortable: true,
			url: "<c:url value='/viewresourcesmapping/listData.do?' />",
			mtype:'GET',
			datatype : "json",
			colNames : [ '<anyframe:message code="restrictedtimes.ui.grid.compkey" />', "<anyframe:message code='viewresource.ui.label.viewresourceid' />", "<anyframe:message code='viewresource.ui.label.reftype' />", "<anyframe:message code='viewresource.ui.label.viewname' />", "<anyframe:message code='viewresource.ui.label.category' />", "<anyframe:message code='viewresource.ui.label.refid' />", "<anyframe:message code='viewresource.ui.label.permissions' />"],
			jsonReader: {
		        repeatitems: false
		    },
			colModel : [ 
			{
				key : true,
				name : 'compKey',
				index : 'compKey',
				sorttype : 'text',
				hidden : true,
				width : 30
			},	{
				name : 'viewResourceId',
				index : 'viewResourceId',
				width : 100,
				sorttype : 'text'		
			}, {
				name : 'refType',
				index : 'refType',
				sorttype : 'text',
				width : 100
			}, {
				name : 'viewName',
				index : 'viewName',
				sorttype : 'text',
				width : 80
			}, {
				name : 'category',
				index : 'category',
				sorttype : 'text',
				width : 80
			}, {
				key : true,
				name : 'refId',
				index : 'refId',
				sorttype : 'text',
				width : 100
			}, {
				name : 'permissions',
				index : 'permissions',
				sorttype : 'text',
				width : 200
			} ],
			width : 790,
			height : 350,
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
					location.href = "<c:url value='/login/relogin.do?inputPage=/viewresourcesmapping/list.do'/>";
					return;
				}
				alert("Type: "+st+ "\nErr: "+ xhr.responseText +"\n Response: "+ xhr.status + " "+xhr.statusText); 
			},

		    ondblClickRow: function(rowid) {
				var rowData = jQuery("#grid2").getRowData(rowid);
				location.href = "<c:url value='/viewresourcesmapping/get.do?&viewResourceId=' />" + rowData.viewResourceId;
		    }
		});
		jQuery("#grid2").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false,search:false});
		
		/* Button Function Start (Resource CRUD) */
		
		/* DELETE Mapping */
		$('[name=removeMapping]').click( function(){

			var rowNum;
			var rowData;
			var viewID = new Array();
			var refId = new Array();
			rowNum = new String(jQuery("#grid2").getGridParam('selarrrow'));
			rowNumList = rowNum.split(",");

			if(rowNum == null || rowNum ==""){
				alert("<anyframe:message code='viewresource.ui.alert.noselectedrow' />");
				return false;
			} else {
				if(confirm("<anyframe:message code='viewresource.ui.alert.confirmtodelete' />")){
				for(var i = 0 ; i < rowNumList.length ; i++){
					rowData = jQuery("#grid2").getRowData(rowNumList[i]);
					viewID[i] = rowData.viewResourceId;
					refId[i] = rowData.refId;
					jQuery("#grid2").delRowData(rowData.compKey);
				}
				jQuery("#grid2").setPostData({viewResourceId:viewID, refId:refId});
				jQuery("#grid2").setGridParam({url:"<c:url value='/viewresourcesmapping/delete.do?' />"}).trigger("reloadGrid");
				jQuery("#grid2").setGridParam({url:"<c:url value='/viewresourcesmapping/listData.do?' />"});
								}
							}
						});

		/* Add Mapping */
		$("[name=addMapping]").click( function() {
			location.href = "<c:url value='/viewresourcesmapping/addView.do?' />";
		});

		/* Search Mapping */
		$("[name=searchMapping]").click( function() {
			jQuery("#grid2").setPostData({searchCondition:$("#searchCondition").val(), searchKeyword:$("#searchKeyword").val(), type:$("#type").val() });
			jQuery("#grid2").setGridParam({url:"<c:url value='/viewresourcesmapping/listData.do?' />"}).trigger("reloadGrid");
			});
	});
</script>
<style type="text/css">
<!--
body {
	background-color: #E9ECF1;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style></head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td width="10" rowspan="3">&nbsp;</td>
    <td height="6"></td>
  </tr>
  <!--- START : Tab menu ------>
  <tr>
    <td height="30" valign="bottom" background="<c:url value='/images/content/bg_tab.gif'/>" style="padding-left:10px">
      <table height="24" border="0" cellpadding="0" cellspacing="0">
              <tr height="21">
                <td width="145" height="27" align="center" valign="bottom"  background="<c:url value='/images/content/tab_menu1.gif'/>" bgcolor="#EDEDED" class="blkbold">
                <anyframe:message code='viewresource.ui.tab.viewmapping' /></td>
              </tr>
    </table></td>
  </tr>
  <!--- END : Tab menu ------>
  <tr>
    <td style="padding-left:10px">
<form name="resourceGrid">
  <div id="documentation" class="demo" style="overflow:auto; height:460px;width:800px;">
    <table width="800" border="0" cellpadding="0" cellspacing="0"  style="margin-top: 10px;" >
	<tr height="30">
		<td width="41">
			<select id="searchCondition" class="selbox">
				<option value="viewResourceId"><anyframe:message code='viewresource.ui.selbox.viewresourceid' /></option>
				<option value="category"><anyframe:message code='viewresource.ui.selbox.category' /></option>
				<option value="viewName"><anyframe:message code='viewresource.ui.selbox.viewname' /></option>
				<option value="refId"><anyframe:message code='viewresource.ui.selbox.refid' /></option>
				<option value="permissions"><anyframe:message code='viewresource.ui.selbox.permissions' /></option>
			</select>
		</td>
		<td width="120" class="tdpadding">
			<input type="text" id="searchKeyword" size="20" class="ct_input_g">
		</td>
		<td width="41" class="tdpadding">
			<select id="type" class="selbox">
				<option value="All"><anyframe:message code='viewresource.ui.selbox.all' /></option>
				<option value="USER"><anyframe:message code='viewresource.ui.selbox.user' /></option>
				<option value="ROLE"><anyframe:message code='viewresource.ui.selbox.role' /></option>
			</select> 
		</td>
		<td width="108" class="tdpadding">
			<a href="#" name="searchMapping" class="searchBtn"><anyframe:message code="user.ui.btn.search" /></a>		
		</td>
		<td width="490" align="right">
			<table>
				<tr>
					<td>
					  <table height="22" border="0" cellpadding="0" cellspacing="0">
			              <tr>
			                <td width="18"><img src="<c:url value='/images/btn/btn_add.gif'/>" width="18" height="22"></td>
			                <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="addMapping"><anyframe:message code="resource.ui.btn.add" /></a></td>
			                <td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
			              </tr>
			            </table>
		            </td>
		            <td>
				    	<table height="22" border="0" cellpadding="0" cellspacing="0">
				          <tr>
				            <td width="18"><img src="<c:url value='/images/btn/btn_delete.gif'/>" width="18" height="22"></td>
				            <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="removeMapping"><anyframe:message code="resource.ui.btn.delete" /></a></td>
				            <td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
				          </tr>
			        	</table>
		        	</td>
	        	</tr>
	        </table>			
		</td>
	</tr>
</table>
<table width="800" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
		<table id="grid2" class="scroll" cellpadding="0" cellspacing="0"></table>
		<div id="pager2" class="scroll" style="text-align: center;"></div>
		</td>
	</tr>
</table>
</div>
</form>	</td>
  </tr>
 <tr>
    <td colspan="2" valign="top">
      <table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#E9ECF1">
        
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