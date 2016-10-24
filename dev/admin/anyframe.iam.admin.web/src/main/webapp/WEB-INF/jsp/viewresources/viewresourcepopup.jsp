<%@ taglib uri='http://www.sds.samsung.com/tags' prefix='anyframe' %>
<%@ include file="/common/taglibs.jsp"%> 
<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><anyframe:message code="viewresource.ui.title.viewresources" /></title>

<jsp:include page="/common/jstree-include.jsp" />
<jsp:include page="/common/jqueryui-include.jsp" />
<jsp:include page="/common/jqgrid-include.jsp" />

<script type="text/javascript">
jQuery(document).ready(
	function() {

		jQuery("#grid").jqGrid( 
		{
			sortable: true,
			url: "<c:url value='/viewresources/listData.do?' />",
			mtype:'GET',
			datatype : "json",
			colNames : [ "<anyframe:message code='viewresource.ui.grid.col.viewresourceid' />", "<anyframe:message code='viewresource.ui.grid.col.category' />", "<anyframe:message code='viewresource.ui.grid.col.viewname' />", "<anyframe:message code='viewresource.ui.grid.col.description' />", "<anyframe:message code='viewresource.ui.grid.col.viewinfo' />"],
			jsonReader: {
		        repeatitems: false
		    },
			colModel : [ 
			{
				key : true,
				name : 'viewResourceId',
				index : 'viewResourceId',
				width : 100,
				sorttype : 'text'				
			}, {
				name : 'category',
				index : 'category',
				sorttype : 'text',
				width : 100
			}, {
				name : 'viewName',
				index : 'viewName',
				sorttype : 'text',
				width : 120
			}, {
				name : 'description',
				index : 'description',
				sorttype : 'text',
				width : 320
			}, {
				name : 'viewInfo',
				index : 'viewInfo',
				sorttype : 'text',
				width : 80
			} ],
			width : 747,
			height : 350,
			forceFit:true,
			pager : jQuery('#pager'),
			sortname : 'viewResourceId',
			sortorder: 'asc',
			rowNum : 20,
			rowList : [ 10, 20, 30 ],
			viewrecords : true,

			/*loadError: function(xhr,st,err) {
				if(st == "parsererror" && xhr.responseText.match('<title>Login</title>') != null) {									
					location.href = "<c:url value='/login/relogin.do?inputPage=/resources/list.do'/>";
					return;
				}
				alert("Type: "+st+ "\nErr: "+ xhr.responseText +"\n Response: "+ xhr.status + " "+xhr.statusText); 
			},*/

		    ondblClickRow: function(rowid) {
				var rowData = jQuery("#grid").getRowData(rowid);
				opener.document.viewmapping.viewResourceId.value = rowData.viewResourceId;
				self.close();
		    }
		});
				
		/* Close Resource */
		$("[name=closeWindow]").click( function() {
			self.close();
		});

		/* Search Resource */
		$("[name=searchResource]").click( function() {
			jQuery("#grid2").setPostData({searchCondition:$("#searchCondition").val(), searchKeyword:$("#searchKeyword").val() });
			jQuery("#grid2").setGridParam({url:"<c:url value='/viewresources/listData.do?' />"}).trigger("reloadGrid");
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
  <tr>
    <td style="padding-left:10px">
<form name="resourceGrid">
  <div id="documentation" class="demo" style="overflow:auto; height:460px;width:800px;">
    <table width="800" border="0" cellpadding="0" cellspacing="0"  style="margin-top: 10px;" >
	<tr height="30">
		<td width="41">
			<select id="searchCondition" class="selbox">
				<option value="viewResourceId"><anyframe:message code="viewresource.ui.label.viewresourceid" /></option>
				<option value="category"><anyframe:message code="viewresource.ui.label.category" /></option>
				<option value="viewName"><anyframe:message code="viewresource.ui.label.viewname" /></option>
				<option value="viewInfo"><anyframe:message code="viewresource.ui.label.viewinfo" /></option>
			</select>
		</td>
		<td width="120" class="tdpadding">
			<input type="text" id="searchKeyword" size="20" class="ct_input_g">
		</td>
		<td width="108" class="tdpadding">
			<a href="#" name="searchResource" class="searchBtn"><anyframe:message code="user.ui.btn.search" /></a>		
		</td>
		<td width="490" align="right">
			<table>
				<tr>
					<td>
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
<table width="800" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
		<table id="grid" class="scroll" cellpadding="0" cellspacing="0"></table>
		<div id="pager" class="scroll" style="text-align: center;"></div>
		</td>
	</tr>
</table>
</div>
</form>	</td>
  </tr>
</table>
</body>
</html>
