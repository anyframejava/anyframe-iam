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
	jQuery(document).ready(	function() {
		
		/* Upload File */
		$("[name=submit]").click( function() {
			var fileName = $("#filedata").val();
			var isValidExtention = typecheck(fileName, "xls");

			if(!isValidExtention){
				alert("파일을 첨부 하지 않았거나\n지원하지 않는 형태의 확장자를 가지고 있습니다.");
				return;
			}
			
			document.resourceGrid.action="<c:url value='/dataupload/uploadfile.do?'/>";
			document.resourceGrid.submit();
		});

		/* Cancel Button */
		$("[name=cancel]").click( function() {
			location.href="<c:url value='/dataupload/dataupload.do?'/>";
		});

		jQuery("#grid2").jqGrid({
			sortable : true,
			url : "<c:url value='/dataupload/validationlist.do?' />",
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
				sortable : false,
				width : 10,
				hidden : true
			}, {
				name : 'sheetName',
				index : 'sheetName',
				sortable : false,
				width : 80
			}, {
				name : 'rowNum',
				index : 'rowNum',
				sortable : false,
				width : 80
			}, {
				name : 'errorMessage',
				index : 'errorMessage',
				sortable : false,
				formatter : currencyFmatter,
				width : 250
			} ],
			width : 790,
			height : 250,
			forceFit : true,
			multiselect : false,
			pager : jQuery('#pager2'),
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

		function currencyFmatter(cellvalue, options, rowObject ){
				return "<b><font color='red'>" + cellvalue + "</font></b>";
		}

		function typecheck(path, typ) { 
		    var lastidx = -1; 
		    lastidx = path.lastIndexOf('.'); 
		    var extension = path.substring(lastidx+1, path.length); 
		  
		    if((lastidx != -1) && (extension.toLowerCase() == typ)) 
		        return true; 
		    return false; 
		}


});

function setFileName() {
	var fileName = document.resourceGrid.fileName;
	var filedata = document.resourceGrid.filedata.value;
	fileName.value = filedata;
}
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
		<table width="796" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;" >
			<tr height="32">
				<td style="padding-left: 3px;">
					<table height="25" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="200">
								<a href="<c:url value='/excel/IAMexcelSample.xls'/>"><b>Excel Layout Example</b></a>
							</td>
							<td width="156"></td>
							
							<td width="440" align="right">
								<table>
									<tr>
										<td class="tdin">
											<input type="text" id="fileName" name="fileName" size="25" class="ct_input_g" readonly="readonly"/>
										</td>
										<td class="tdin" width="80">
											<DIV style="OVERFLOW: hidden; WIDTH: 75px; HEIGHT: 23px"> 
											<DIV style="FILTER: alpha(opacity=0); OVERFLOW: hidden; WIDTH: 75px; POSITION: absolute; HEIGHT: 23px; opacity: 0">
											<INPUT id="filedata" style="FONT-SIZE: 13px; RIGHT: 0px; CURSOR: pointer; POSITION: absolute; TOP: 0px" type="file" name="filedata" onchange="javascript:setFileName();"> 
											</DIV>
											<table height="22" border="0" cellpadding="0" cellspacing="0">
										  		<tr>											
													<td width="18"><img src="<c:url value='/images/btn/btn_search_2.gif'/>" width="18" height="23"></td>
													<td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="search">Search</a></td>
													<td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="23"></td>
												</tr>
											</table>
											</DIV>
										</td>
										<td>
											<table height="22" border="0" cellpadding="0" cellspacing="0">
										  		<tr>											
													<td width="18"><img src="<c:url value='/images/btn/btn_add.gif'/>" width="18" height="23"></td>
													<td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="submit">Submit</a></td>
													<td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="23"></td>
												</tr>
											</table>	
										</td>
										<td>
											<table height="22" border="0" cellpadding="0" cellspacing="0">
										  		<tr>											
													<td width="18"><img	src="<c:url value='/images/btn/btn_cancel.gif'/>" width="18" height="23"></td>
													<td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="cancel">Cancel</a></td>
													<td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="23"></td>
												</tr>
											</table>	
										</td>
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