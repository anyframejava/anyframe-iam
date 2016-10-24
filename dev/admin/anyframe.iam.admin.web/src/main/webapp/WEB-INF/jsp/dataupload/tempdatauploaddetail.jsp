<%@ taglib uri='http://www.anyframejava.org/tags' prefix='anyframe' %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><anyframe:message code="resource.ui.title.resourcelist" /></title>

<jsp:include page="/common/jquery-include.jsp" />

<script type="text/javascript">
	jQuery(document)
			.ready(
					function() {

						jQuery("#grid2")
								.jqGrid(
										{
											sortable : true,
											url : "<c:url value='dataupload/validationlist.do?' />",
											mtype : 'GET',
											datatype : "json",
											colNames : [ 'ID', 'Sheet Name',
													'Row Num', 'Error Message' ],
											jsonReader : {
												repeatitems : false
											},
											colModel : [ {
												key : true,
												name : 'id',
												index : 'id',
												width : 10,
												hidden : true,
												sorttype : 'text'
											}, {
												name : 'sheetName',
												index : 'sheetName',
												sorttype : 'text',
												width : 80
											}, {
												name : 'rowNum',
												index : 'rowNum',
												sorttype : 'text',
												width : 80
											}, {
												name : 'errorMessage',
												index : 'errorMessage',
												sorttype : 'text',
												width : 250
											} ],
											width : 790,
											height : 250,
											forceFit : true,
											multiselect : true,
											pager : jQuery('#pager2'),
											sortname : 'id',
											sortorder : 'asc',
											rowNum : 20,
											rowList : [ 10, 20, 30 ],
											viewrecords : true,

											loadError : function(xhr, st, err) {
												if (st == "parsererror"
														&& xhr.responseText
																.match('<title>Login</title>') != null) {
													location.href = "<c:url value='/login/relogin.do?inputPage=/resources/list.do'/>";
													return;
												}
												alert("Type: " + st + "\nErr: "
														+ xhr.responseText
														+ "\n Response: "
														+ xhr.status + " "
														+ xhr.statusText);
											}
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
</style>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0"
	bgcolor="#FFFFFF">
	<tr>
		<td width="10" rowspan="3">&nbsp;</td>
		<td height="6"></td>
	</tr>
	<!--- START : Tab menu ------>
	<tr>
		<td height="30" valign="bottom"
			background="<c:url value='/images/content/bg_tab.gif'/>"
			style="padding-left: 10px">
		<table height="24" border="0" cellpadding="0" cellspacing="0">
			<tr height="21">
				<td width="145" height="27" align="center" valign="bottom"
					background="<c:url value='/images/content/tab_menu1.gif'/>"
					bgcolor="#EDEDED" class="blkbold">File Upload</td>
			</tr>
		</table>
		</td>
	</tr>
	<!--- END : Tab menu ------>
	<tr>
		<td style="padding-left: 10px">
		<form name="resourceGrid" id="resourceGrid" method="post"
			action="<c:url value='/dataupload/uploadfile.do?' />"
			enctype="multipart/form-data">

		<div id="documentation" class="demo"
			style="overflow: auto; height: 460px; width: 800px;">
		<table width="796" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="title" style="padding-left: 21px">Upload File
				(*.xls)</td>
			</tr>
			<tr>
				<td style="padding-left: 3px;">
				<table height="22" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><input type="submit" value="Submit" /></td>
						<td width="18"><img
							src="<c:url value='/images/btn/btn_cancel.gif'/>" width="18"
							height="22"></td>
						<td background="<c:url value='/images/btn/bg_btn.gif'/>"
							class="boldBtn"><a href="#" name="movetoback">Cancel</a></td>
						<td width="10" align="right"><img
							src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10"
							height="22"></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td class="tdin"><input id="filedata" name="filedata"
					type="file" /></td>
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
		</form>
		</td>
	</tr>
	<tr>
		<td colspan="2" valign="top">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bgcolor="#E9ECF1">

			<tr>
				<td height="1" bgcolor="#C9CFDD"></td>
			</tr>
			<tr>
				<td valign="top" bgcolor="#E9ECF1">
				<div id="footSub"></div>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>