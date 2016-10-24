<%@ taglib uri='http://www.sds.samsung.com/tags' prefix='anyframe' %>
<%@ include file="/common/taglibs.jsp"%> 
<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><anyframe:message code="restrictedtimes.ui.title.restrictedtimeslist" /></title>

<jsp:include page="/common/jquery-include.jsp" />
<jsp:include page="/common/jqgrid-include.jsp" />

<script type="text/javascript">
<!--
jQuery(document).ready( function() {
	jQuery("#grid").jqGrid( {
		sortable: true,
		url: "<c:url value='/restriction/listData.do?' />",
		mtype:'GET',
		datatype : "json",
		colNames : [ '<anyframe:message code="restrictedtimes.ui.grid.timeid" />', '<anyframe:message code="restrictedtimes.ui.grid.timetype" />', '<anyframe:message code="restrictedtimes.ui.grid.startdate" />', '<anyframe:message code="restrictedtimes.ui.grid.starttime" />', '<anyframe:message code="restrictedtimes.ui.grid.enddate" />', '<anyframe:message code="restrictedtimes.ui.grid.endtime" />', '<anyframe:message code="restrictedtimes.ui.grid.description" />' ],
		jsonReader: {
	        repeatitems: false
	    },
		colModel : [ {
			key : true,
			name : 'timeId',
			index : 'timeId',
			sorttype : 'text',
			width : 40
		}, {
			name : 'timeType',
			index : 'timeType',
			sorttype : 'text',
			width : 50
		}, {
			name : 'startDate',
			index : 'startDate',
			sorttype : 'text',
			width : 40
		}, {
			name : 'startTime',
			index : 'startTime',
			sorttype : 'text',
			width : 30
		}, {
			name : 'endDate',
			index : 'endDate',
			sorttype : 'text',
			width : 40
		}, {
			name : 'endTime',
			index : 'endTime',
			sorttype : 'text',
			width : 40
		}, {
			name : 'description',
			index : 'description',
			sorttype : 'text',
			width : 100
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
				location.href = "<c:url value='/login/relogin.do?inputPage=/restriction/list.do'/>";
				return;
			}
			alert("Type: "+st+ "\nErr: "+ xhr.responseText +"\n Response: "+ xhr.status + " "+xhr.statusText); 
		},

	    ondblClickRow: function(rowid) {
		    location.href = "<c:url value='/restriction/get.do?&timeId=' />" + rowid;
	    }
	});
//	jQuery("#grid").jqGrid('navGrid','#pager',{edit:false,add:false,del:false,search:false});
	
	/* Button Function Start (Resource CRUD) */
	
	/* DELETE */
	$('[name=removeItems]').click( function(){

		var rowNum;
		var rowData;
		var rowArray = new Array();
		rowNum = new String(jQuery("#grid").getGridParam('selarrrow'));
		rowNumList = rowNum.split(",");

		if(rowNum == null || rowNum ==""){
			alert("<anyframe:message code='restrictedtimes.ui.alert.noselectedrow' />");
			return false;
		} else {
			if(confirm("<anyframe:message code='restrictedtimes.ui.alert.confirmtodelete' />")){
				for(var i = 0 ; i < rowNumList.length ; i++){
					rowData = jQuery("#grid").getRowData(rowNumList[i]);
					rowArray[i] = rowData.timeId;
					jQuery("#grid").delRowData(rowData.timeId);
				}
				jQuery("#grid").setPostData({timeId:rowArray});
				jQuery("#grid").setGridParam({url:"<c:url value='/restriction/delete.do?' />"}).trigger("reloadGrid");
				jQuery("#grid").setGridParam({url:"<c:url value='/restriction/listData.do?' />"});
			}
		}
	});

	/* Add */
	$("[name=addItem]").click( function() {
		location.href = "<c:url value='/restriction/addView.do?' />";
	});

	/* Search Resource */
	$("[name=searchItems]").click( function() {
		jQuery("#grid").setPostData({searchCondition:$("#searchCondition").val(), searchKeyword:$("#searchKeyword").val(), searchType:$("#searchType").val()});
		jQuery("#grid").setGridParam({url:"<c:url value='/restriction/listData.do?' />"}).trigger("reloadGrid");
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
    <!--- START : Tab menu ------>
    <td height="30" valign="bottom" background="<c:url value='/images/content/bg_tab.gif'/>" style="padding-left:10px">
      <table height="24" border="0" cellpadding="0" cellspacing="0">
              <tr height="21">
                <td width="145" height="27" align="center" valign="bottom"  background="<c:url value='/images/content/tab_menu1.gif'/>" bgcolor="#EDEDED" class="blkbold">Time List </td>
              </tr>
    </table></td>
	<!--- END : Tab menu ------>
  </tr>
  <tr>
    <td style="padding-left:10px">
	<form name="restrictionGrid" onsubmit="return false">
<div id="documentation" class="demo" style="overflow:auto; height:460px;width:800px;">
<table width="800" border="0" cellpadding="0" cellspacing="0" style="margin-top: 10px;">
	<tr height="30">
		<td width="84" class="sky_h3">
			<img src="<c:url value='/images/arrow.gif'/>" width="13" height="13" align="absmiddle"> <anyframe:message code="restrictedtimes.ui.selectbox.timeid" />:<input type="hidden" id="searchCondition" value="timeId" readonly></td>
		<td width="120"  class="tdpadding"><input type="text" id="searchKeyword" size="20" class="ct_input_g"></td>
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
		<td width="183" class="tdpadding">
			<a href="#"  name="searchItems" class="searchBtn"><anyframe:message code="restrictedtimes.ui.btn.search" /></a>
		</td>
		<td width="377" align="right">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
	                <td class="tdpadding">
	                	<table height="22" border="0" cellpadding="0" cellspacing="0">
	          				<tr>
	            				<td width="18"><img src="<c:url value='/images/btn/btn_add.gif'/>" width="18" height="22"></td>
	            				<td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="addItem"><anyframe:message code="resource.ui.btn.add" /></a></td>
	            				<td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
	         				</tr>
	      				</table>
	      			</td>
	                <td class="tdpadding">
	                	<table height="20" border="0" cellpadding="0" cellspacing="0">
	          				<tr>
	            				<td width="18"><img src="<c:url value='/images/btn/btn_delete.gif'/>" width="18" height="22"></td>
					            <td height="22" background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="removeItems"><anyframe:message code="restrictedtimes.ui.btn.delete" /></a></td>
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
		<div id="pager" class="scroll" style="text-align: center;"></div>		</td>
	</tr>
</table>
</div>
</form></td>
  </tr>
  
  <tr>
    <td colspan="2" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#E9ECF1">
        
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