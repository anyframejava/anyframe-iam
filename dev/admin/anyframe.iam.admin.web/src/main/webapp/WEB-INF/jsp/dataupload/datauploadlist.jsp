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
		url: "<c:url value='/dataupload/listData.do?' />",
		mtype:'GET',
		datatype : "json",
		colNames : ['File ID', 'File Name', 'Create Date', 'Work Date'],
		jsonReader: {
	        repeatitems: false
	    },
		colModel : [ 
		{
			key : true,
			name : 'fileId',
			index : 'fileId',
			width : 60,
			sorttype : 'text'
		}, {
			name : 'fileName',
			index : 'fileName',
			sorttype : 'text',
			formatter : currencyFmatter,
			width : 80
		}, {
			name : 'createDate',
			index : 'createDate',
			sorttype : 'text',
			width : 80
		}, {
			name : 'workDate',
			index : 'workDate',
			sorttype : 'text',
			width : 80
		}],
		width : 790,
		height : 350,
		forceFit:true,
		pager : jQuery('#pager2'),
		sortname : 'fileId',
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
		}
	});

	function currencyFmatter(cellvalue, options, rowObject ){

		var targetURL = "<c:url value='/dataupload/downloadexcel.do?&fileId='/>" + options.rowId;
		cellvalue = "<a href=" + targetURL + "><b>" + cellvalue + "</b></a>";
		
		return cellvalue;
	}
		
	/* Remove TempUsers */
	$("[name=moveBack]").click( function() {
		location.href="<c:url value='/excel/excelimportview.do?'/>";
	});

	/* Upload File */
	$("[name=upload]").click( function() {
		document.resourceGrid.action="<c:url value='/dataupload/datauploaddetail.do?'/>";
		document.resourceGrid.submit();
	});

	/* Apply File */
	$("[name=apply]").click( function() {
		var fileId = jQuery("#grid2").jqGrid('getGridParam', 'selrow');
		if(fileId == null || fileId ==""){
			alert("No selected Rows");
			return false;
		} 
		
		if(confirm('Do you want to apply this file to DB?')) {

			jQuery.ajaxSettings.traditional = true;
			$.ajax({
				type: 'POST',
				url: '<c:url value="/dataupload/doapply.do"/>',
				data:{
					fileId : fileId
				},
				dataType: 'json',
				success: function(msg){
					alert("작업을 성공적으로 수행 했습니다.\n기존의 데이터는 Snapshot File로 저장 되었습니다.");
					jQuery("#grid2").setGridParam({url:"<c:url value='/dataupload/listData.do?' />"}).trigger("reloadGrid");
				},
				error: function(msg){
					alert("작업 도중 문제가 발생 했습니다.");
					jQuery("#grid2").setGridParam({url:"<c:url value='/dataupload/listData.do?' />"}).trigger("reloadGrid");
				}
			});
		}
	});

	/* Snapshot File */
	$("[name=snapshot]").click( function() {
		if(confirm("Do you want to make Snapshot file?")){
			$.post("<c:url value='/dataupload/dosnapshot.do'/>", function(data){
				jQuery("#grid2").trigger("reloadGrid");
			});
		}
	});

	/* Search Resource */
	$("[name=searchResource]").click( function() {
		jQuery("#grid2").setPostData({searchCondition:$("#searchCondition").val(), searchKeyword:$("#searchKeyword").val(), searchType:$("#searchType").val()});
		jQuery("#grid2").setGridParam({url:"<c:url value='/dataupload/listData.do?' />"}).trigger("reloadGrid");
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
                <td width="145" height="27" align="center" valign="bottom"  background="<c:url value='/images/content/tab_menu1.gif'/>" bgcolor="#EDEDED" class="blkbold">Data Upload </td>
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
				<option value="fileName">File Name</option>
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
			                <td width="18"><img src="<c:url value='/images/btn/btn_add.gif'/>" width="18" height="22"></td>
			                <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="upload">Upload</a></td>
			                <td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
			              </tr>
			            </table>
		            </td>
					<td>
					  <table height="22" border="0" cellpadding="0" cellspacing="0">
			              <tr>
			                <td width="18"><img src="<c:url value='/images/btn/btn_save.gif'/>" width="18" height="22"></td>
			                <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="snapshot">Snapshot</a></td>
			                <td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
			              </tr>
			            </table>
		            </td>
		            <td>
				    	<table height="22" border="0" cellpadding="0" cellspacing="0">
				          <tr>
				            <td width="18"><img src="<c:url value='/images/btn/btn_save.gif'/>" width="18" height="22"></td>
				            <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="apply">Apply</a></td>
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