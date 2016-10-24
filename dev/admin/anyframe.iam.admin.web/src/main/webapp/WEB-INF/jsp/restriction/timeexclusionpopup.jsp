<%@ taglib uri='http://www.anyframejava.org/tags' prefix='anyframe' %>
<%@ include file="/common/taglibs.jsp"%> 
<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><anyframe:message code="restrictedtimes.ui.title.timeexclusionlist" /></title>

<jsp:include page="/common/jquery-include.jsp" />
<jsp:include page="/common/jqgrid-include.jsp" />

<script type="text/javascript">
<!--
jQuery(document).ready( function() {
		jQuery("#grid").jqGrid( 
		{
			sortable: true,
			url: "<c:url value='/restriction/timeresource/listData.do?' />",
			mtype:'GET',
			datatype : "json",
			colNames : [ 
						'<anyframe:message code="restrictedtimes.ui.grid.compkey" />',
						'<anyframe:message code="restrictedtimes.ui.grid.timeid" />',
						'<anyframe:message code="restrictedtimes.ui.grid.resourceid" />',
						'<anyframe:message code="restrictedtimes.ui.grid.timetype" />', 
						'<anyframe:message code="restrictedtimes.ui.grid.startdate" />', 
						'<anyframe:message code="restrictedtimes.ui.grid.starttime" />', 
						'<anyframe:message code="restrictedtimes.ui.grid.enddate" />', 
						'<anyframe:message code="restrictedtimes.ui.grid.endtime" />', 
						'<anyframe:message code="restrictedtimes.ui.grid.description" />' 
						],
			jsonReader: {
		        repeatitems: false
		    },
			colModel : [{
				key : true,
				name : 'compKey',
				index : 'compKey',
				sorttype : 'text',
				hidden : true,
				width : 0
			}, { 
				name : 'timeId',
				index : 'timeId',
				sorttype : 'text',
				width : 30
			}, { 
				name : 'resourceId',
				index : 'resourceId',
				sorttype : 'text',
				width : 40
			}, { 
				name : 'timeType',
				index : 'timeType',
				sorttype : 'text',
				width : 35
			}, {
				name : 'startDate',
				index : 'startDate',
				sorttype : 'text',
				width : 35
			}, {
				name : 'startTime',
				index : 'startTime',
				sorttype : 'text',
				width : 35
			}, {
				name : 'endDate',
				index : 'endDate',
				sorttype : 'text',
				width : 30
			}, {
				name : 'endTime',
				index : 'endTime',
				sorttype : 'text',
				width : 30
			}, {
				name : 'description',
				index : 'description',
				sorttype : 'text',
				width : 40
			} ],
			width : 777,
			height : 250,
			forceFit:true,
			pager : jQuery('#pager'),
			sortname : 'timeId',
			sortorder: 'asc',
			rowNum : 20,
			rowList : [ 10, 20, 30 ],
			viewrecords : true,

			loadError: function(xhr,st,err) {
				if(st == "parsererror" && xhr.responseText.match('<title>Login</title>') != null) {									
					location.href = "<c:url value='/login/relogin.do?inputPage=/restriction/timeexclusion/list.do'/>";
					return;
				}
				alert("Type: "+st+ "\nErr: "+ xhr.responseText +"\n Response: "+ xhr.status + " "+xhr.statusText); 
			},

		    ondblClickRow: function(rowid) {
				var rowData = jQuery("#grid").getRowData(rowid);
				opener.document.timesresourcesexclusion.timeId.value = rowData.timeId;
				opener.document.timesresourcesexclusion.resourceId.value = rowData.resourceId;
				opener.document.timesresourcesexclusion.timeType.value = rowData.timeType;
				opener.document.timesresourcesexclusion.startDate.value = rowData.startDate;
				opener.document.timesresourcesexclusion.startTime.value = rowData.startTime;
				opener.document.timesresourcesexclusion.endDate.value = rowData.endDate;
				opener.document.timesresourcesexclusion.endTime.value = rowData.endTime;
				opener.document.timesresourcesexclusion.description.value = rowData.description;
				opener.timeIdChanged();
				window.close();
		    }
		});
		
		/* Button Function Start (Resource CRUD) */

		/* Search Resource */
		$("[name=searchItems]").click( function() {
			jQuery("#grid").setPostData({searchCondition:$("#searchCondition").val(), searchKeyword:$("#searchKeyword").val(), searchType:$("#searchType").val()});
			jQuery("#grid").setGridParam({url:"<c:url value='/restriction/timeresource/listData.do?' />"}).trigger("reloadGrid");
		});

		/* Close the window */
		$('[name=closeWindow]').click( function(){
			window.close();
		});

		/* auto click by enter key */
		$("#searchKeyword").keypress(function (e) {
			if (e.which == 13)
				$("[name=searchItems]").trigger("click");
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
<body>
<form name="restrictionGrid" onsubmit="return false">
<div id="documentation" class="demo" style="overflow:auto; height:360px;width:780px;">
<table width="780" border="0" cellpadding="0" cellspacing="0">
	<tr height="30">
		<td width="41">
			<select id="searchCondition" class="selbox">
				<option value="timeId"><anyframe:message code="restrictedtimes.ui.selectbox.timeid" /></option>
				<option value="resourceId"><anyframe:message code="restrictedtimes.ui.selectbox.resourceid" /></option>
			</select>
		</td>
		<td width="120" class="tdpadding">
			<input type="text" id="searchKeyword" size="20" class="ct_input_g">
		</td>
		<td width="41" class="tdpadding">
			<select id="searchType" class="selbox">
				<option value="<anyframe:message code="restrictedtimes.ui.selectbox.timetype.all" />"><anyframe:message code="restrictedtimes.ui.selectbox.timetype.all" /></option>
				<option value="<anyframe:message code="restrictedtimes.ui.selectbox.timetype.crash" />"><anyframe:message code="restrictedtimes.ui.selectbox.timetype.crash" /></option>
				<option value="<anyframe:message code="restrictedtimes.ui.selectbox.timetype.improve" />"><anyframe:message code="restrictedtimes.ui.selectbox.timetype.improve" /></option>
				<option value="<anyframe:message code="restrictedtimes.ui.selectbox.timetype.daily" />"><anyframe:message code="restrictedtimes.ui.selectbox.timetype.daily" /></option>
				<option value="<anyframe:message code="restrictedtimes.ui.selectbox.timetype.weekend" />"><anyframe:message code="restrictedtimes.ui.selectbox.timetype.weekend" /></option>
				<option value="<anyframe:message code="restrictedtimes.ui.selectbox.timetype.holiday" />"><anyframe:message code="restrictedtimes.ui.selectbox.timetype.holiday" /></option>
			</select> 
		</td>
		<td width="89" class="tdpadding">
			<a href="#"  name="searchItems" class="searchBtn"><anyframe:message code="user.ui.btn.search" /></a></td>
		<td width="1"></td>

		<td width="488" align="right">
			<table height="22" border="0" cellpadding="0" cellspacing="0">
				<tr>
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
<table width="780" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
		<table id="grid" class="scroll" cellpadding="0" cellspacing="0"></table>
		<div id="pager" class="scroll" style="text-align: center;"></div>
		</td>
	</tr>
</table>
</div>
</form>
</body>
</html>