<%@ taglib uri='http://www.sds.samsung.com/tags' prefix='anyframe' %>
<%@ include file="/common/taglibs.jsp"%> 
<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><anyframe:message code="restrictedtimes.ui.title.timerolelist" /></title>

<jsp:include page="/common/jquery-include.jsp" />
<jsp:include page="/common/jqgrid-include.jsp" />

<script type="text/javascript">
<!--
jQuery(document).ready( function() {
	jQuery("#grid").jqGrid( {
		sortable: true,
		url: "<c:url value='/restriction/timerole/listData.do?' />",
		mtype:'GET',
		datatype : "json",
		colNames : [ '<anyframe:message code="restrictedtimes.ui.grid.compkey" />','<anyframe:message code="restrictedtimes.ui.grid.timeid" />','<anyframe:message code="restrictedtimes.ui.grid.roleid" />','<anyframe:message code="restrictedtimes.ui.grid.timetype" />', '<anyframe:message code="restrictedtimes.ui.grid.startdate" />', '<anyframe:message code="restrictedtimes.ui.grid.starttime" />', '<anyframe:message code="restrictedtimes.ui.grid.enddate" />', '<anyframe:message code="restrictedtimes.ui.grid.endtime" />', '<anyframe:message code="restrictedtimes.ui.grid.description" />' ],
		jsonReader: {
	        repeatitems: false
	    },
		colModel : [{
			key : true,
			name : 'compKey',
			index : 'compKey',
			sorttype : 'text',
			hidden : true,
			width : 30
		}, { 
			name : 'timeId',
			index : 'timeId',
			sorttype : 'text',
			width : 40
		}, { 
			name : 'roleId',
			index : 'roleId',
			sorttype : 'text',
			width : 40
		}, {
			name : 'timeType',
			index : 'timeType',
			sorttype : 'text',
			width : 40
		}, {
			name : 'startDate',
			index : 'startDate',
			sorttype : 'text',
			width : 40
		}, {
			name : 'startTime',
			index : 'startTime',
			sorttype : 'text',
			width : 40
		}, {
			name : 'endDate',
			index : 'endDate',
			sorttype : 'text',
			width : 38
		}, {
			name : 'endTime',
			index : 'endTime',
			sorttype : 'text',
			width : 40
		}, {
			name : 'description',
			index : 'description',
			sorttype : 'text',
			width : 60
		} ],
		width : 790,
		height : 350,
		forceFit:true,
		multiselect : true,
		pager : jQuery('#pager'),
		sortname : 'timeId',
		sortorder: 'asc',
		rowNum : 20,
		rowList : [ 10, 20, 30 ],
		viewrecords : true,

		loadError: function(xhr,st,err) {
			if(st == "parsererror" && xhr.responseText.match('<title>Login</title>') != null) {									
				location.href = "<c:url value='/login/relogin.do?inputPage=/restriction/timerole/list.do'/>";
				return;
			}
			alert("Type: "+st+ "\nErr: "+ xhr.responseText +"\n Response: "+ xhr.status + " "+xhr.statusText); 
		},

	    ondblClickRow: function(rowid) {
		    location.href = "<c:url value='/restriction/timerole/get.do?&timeId=' />" + rowid.substr(0,10);
	    }
	});
	
	/* Button Function Start (Resource CRUD) */
	
	/* DELETE */
	$('[name=removeItems]').click( function(){

		var rowNum;
		var rowData;
		var timeIdArray = new Array();
		var roleIdArray = new Array();
		rowNum = new String(jQuery("#grid").getGridParam('selarrrow'));
		rowNumList = rowNum.split(",");

		if(rowNum == null || rowNum ==""){
			alert("<anyframe:message code='restrictedtimes.ui.alert.noselectedrow' />");
			return false;
		} else {
			if(confirm("<anyframe:message code='restrictedtimes.ui.alert.confirmtodelete' />")){
				for(var i = 0 ; i < rowNumList.length ; i++){
					rowData = jQuery("#grid").getRowData(rowNumList[i]);
					timeIdArray[i] = rowData.timeId;
					roleIdArray[i] = rowData.roleId;
					jQuery("#grid").delRowData(rowData.compKey);
				}
				jQuery.ajaxSettings.traditional = true;
				jQuery("#grid").setPostData({timeId:timeIdArray, roleId:roleIdArray});
				jQuery("#grid").setGridParam({url:"<c:url value='/restriction/timerole/delete.do?' />"}).trigger("reloadGrid");
				jQuery("#grid").setGridParam({url:"<c:url value='/restriction/timerole/listData.do?' />"});
			}
		}
	});

	/* Add */
	$("[name=addItem]").click( function() {
		location.href = "<c:url value='/restriction/timerole/addView.do?' />";
	});

	/* Search Resource */
	$("[name=searchItems]").click( function() {
		jQuery("#grid").setPostData({searchCondition:$("#searchCondition").val(), searchKeyword:$("#searchKeyword").val(), searchType:$("#searchType").val()});
		jQuery("#grid").setGridParam({url:"<c:url value='/restriction/timerole/listData.do?' />"}).trigger("reloadGrid");
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
<form name="restrictionGrid" onsubmit="return false">
<div id="documentation" class="demo" style="overflow:auto; height:450px;width:800px;">
<table width="798" border="0" cellpadding="0" cellspacing="0">
	<tr height="30">
		<td width="41">
			<select id="searchCondition" class="selbox">
				<option value="timeId"><anyframe:message code="restrictedtimes.ui.selectbox.timeid" /></option>
				<option value="roleId"><anyframe:message code="restrictedtimes.ui.selectbox.roleid" /></option>
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
		<td width="94" class="tdpadding">
			<a href="#" name="searchItems" class="searchBtn"><anyframe:message code="user.ui.btn.search" /></a></td>
		</td>
		<td width="1"></td>
		<td width="501" align="right">
			<table>
				<tr>
					<td>
						<table height="22" border="0" cellpadding="0" cellspacing="0">
			              <tr>
			                <td width="18"><img src="<c:url value='/images/btn/btn_add.gif'/>" width="18" height="22"></td>
			                <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="addItem"><anyframe:message code="restrictedtimes.ui.btn.add" /></a></td>
			                <td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
			              </tr>
			            </table>
					</td>
				    <td>
				    	<table height="22" border="0" cellpadding="0" cellspacing="0">
				          <tr>
				            <td width="18"><img src="<c:url value='/images/btn/btn_delete.gif'/>" width="18" height="22"></td>
				            <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="removeItems"><anyframe:message code="restrictedtimes.ui.btn.delete" /></a></td>
				            <td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
				          </tr>
			        	</table>
			        </td>
				</tr>
			</table>		
		</td>
	</tr>
</table>
<table width="798" border="0" cellpadding="0" cellspacing="0">
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