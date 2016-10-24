<%@ taglib uri='http://www.anyframejava.org/tags' prefix='anyframe' %>
<%@ include file="/common/taglibs.jsp"%> 
<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><anyframe:message code="resource.ui.title.resourcelist" /></title>

<jsp:include page="/common/jquery-include.jsp" />
<jsp:include page="/common/jqgrid-include.jsp" />

<script type="text/javascript">
<!--
jQuery(document).ready( function() {
	jQuery("#grid2").jqGrid( {
		sortable: true,
		url: "<c:url value='/resources/listData.do?' />",
		mtype:'GET',
		datatype : "json",
		colNames : [ 
		    		'<anyframe:message code="resource.ui.grid.sortorder" />', 
		    		'<anyframe:message code="resource.ui.grid.resourceid" />', 
		    		'<anyframe:message code="resource.ui.grid.resourcename" />', 
		    		'<anyframe:message code="resource.ui.grid.resourcepattern" />', 
		    		'<anyframe:message code="resource.ui.grid.resourcetype" />' 
		    		],
		jsonReader: {
	        repeatitems: false
	    },
		colModel : [ 
		{
			name : 'sortOrder',
			index : 'sortOrder',
			width : 60,
			sorttype : 'int', 
			align : 'right',
			formatter : 'integer'
			
		}, {
			key : true,
			name : 'resourceId',
			index : 'resourceId',
			sorttype : 'text',
			width : 80
		}, {
			name : 'resourceName',
			index : 'resourceName',
			sorttype : 'text',
			width : 160
		}, {
			name : 'resourcePattern',
			index : 'resourcePattern',
			sorttype : 'text',
			width : 320
		}, {
			name : 'resourceType',
			index : 'resourceType',
			sorttype : 'text',
			width : 80
		} ],
		width : 790,
		height : 350,
		forceFit:true,
		multiselect : true,
		pager : jQuery('#pager2'),
		sortname : 'sortOrder',
		sortorder: 'asc',
		rowNum : 20,
		rowList : [ 10, 20, 30 ],
		viewrecords : true,

		loadError: function(xhr,st,err) {
			if(st == "parsererror" && xhr.responseText.match('<title>Login</title>') != null) {									
				location.href = "<c:url value='/login/relogin.do?inputPage=/resources/list.do'/>";
				return;
			}
			alert("Type: "+st+ "\nErr: "+ xhr.responseText +"\n Response: "+ xhr.status + " "+xhr.statusText); 
		},

	    ondblClickRow: function(rowid) {
		    location.href = "<c:url value='/resources/get.do?&resourceId=' />" + rowid;
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
			alert("<anyframe:message code='resource.ui.alert.noselectedrow' />");
			return false;
		} else {
			if(confirm("<anyframe:message code='resource.ui.alert.confirmtodelete' />")){
				for(var i = 0 ; i < rowNumList.length ; i++){
					rowData = jQuery("#grid2").getRowData(rowNumList[i]);
					rowArray[i] = rowData.resourceId;
					jQuery("#grid2").delRowData(rowData.resourceId);
				}
				jQuery.ajaxSettings.traditional = true;
				//jQuery("#grid2").setPostData({resourceId:rowArray});
				//jQuery("#grid2").setGridParam({url:"<c:url value='/resources/delete.do?' />"}).trigger("reloadGrid");
				//jQuery("#grid2").setGridParam({url:"<c:url value='/resources/listData.do?' />"});
				$.post("<c:url value='/resources/delete.do?' />", {resourceId:rowArray}, function(data){
			    	jQuery("#grid2").trigger("reloadGrid");
			    });
			}
		}
	});

	/* Add Resource */
	$("[name=addResource]").click( function() {
		location.href = "<c:url value='/resources/addView.do?' />";
	});

	/* Search Resource */
	$("[name=searchResource]").click( function() {
		jQuery("#grid2").setPostData({searchCondition:$("#searchCondition").val(), searchKeyword:$("#searchKeyword").val(), searchType:$("#searchType").val()});
		jQuery("#grid2").setGridParam({url:"<c:url value='/resources/listData.do?' />"}).trigger("reloadGrid");
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
                <td width="145" height="27" align="center" valign="bottom"  background="<c:url value='/images/content/tab_menu1.gif'/>" bgcolor="#EDEDED" class="blkbold">Resource List </td>
              </tr>
    </table></td>
  </tr>
  <!--- END : Tab menu ------>
  <tr>
    <td style="padding-left:10px">
<form name="resourceGrid" onsubmit="return false">
  <div id="documentation" class="demo" style="overflow:auto; height:460px;width:800px;">
    <table width="800" border="0" cellpadding="0" cellspacing="0"  style="margin-top: 10px;" >
	<tr height="30">
		<td width="41">
			<select id="searchCondition" class="selbox">
				<option value="resourceName"><anyframe:message code="resource.ui.selectbox.resourcename" /></option>
				<option value="resourceId"><anyframe:message code="resource.ui.selectbox.resourceid" /></option>
				<option value="resourcePattern"><anyframe:message code="resource.ui.selectbox.resourcepattern" /></option>
			</select>
		</td>
		<td width="120" class="tdpadding">
			<input type="text" id="searchKeyword" size="20" class="ct_input_g">
		</td>
		<td width="41" class="tdpadding">
			<select id="searchType" class="selbox">
				<option value="ALL"><anyframe:message code="resource.ui.selectbox.resourcetype.all" /></option>
				<option value="url"><anyframe:message code="resource.ui.selectbox.resourcetype.url" /></option>
				<option value="method"><anyframe:message code="resource.ui.selectbox.resourcetype.method" /></option>
				<option value="pointcut"><anyframe:message code="resource.ui.selectbox.resourcetype.pointcut" /></option>
			</select> 
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
			                <td width="18"><img src="<c:url value='/images/btn/btn_add.gif'/>" width="18" height="22"></td>
			                <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="addResource"><anyframe:message code="resource.ui.btn.add" /></a></td>
			                <td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
			              </tr>
			            </table>
		            </td>
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