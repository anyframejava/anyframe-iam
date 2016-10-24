<%@ taglib uri='http://www.anyframejava.org/tags' prefix='anyframe' %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><anyframe:message code="resourcereload.ui.title.resourcereload" /></title>
<script language="javascript" src="<c:url value='/js/CommonScript.js'/>"></script>

<jsp:include page="/common/jquery-include.jsp" />
<jsp:include page="/common/jqgrid-include.jsp" />

<script language="javascript">
<!--
jQuery(document).ready( function() {
	jQuery("#grid").jqGrid( {
		sortable: true,
		url: "<c:url value='/admin/reload/listData.do?' />",
		mtype:'GET',
		datatype : "json",
		colNames : [ 
		    		'Bean ID', 
		    		'System Name',
		    		'Target App' 
		],
		jsonReader: {
	        repeatitems: false
	    },
		colModel : [ {
			key : true,
			name : 'beanId',
			index : 'beanId',
			hidden : true,
			width : 0
		}, {
			name : 'systemName',
			index : 'systemName',
			width : 120
		}, {
			name : 'serverUrl',
			index : 'serverUrl',
			width : 250
		} ],
		width : 787,
		height : 150,
		pager : jQuery('#pager'),
		forceFit:true,
		multiselect : true,
		viewrecords : true,

		loadError: function(xhr,st,err) {
			if(st == "parsererror" && xhr.responseText.match('<title>Login</title>') != null) {									
				location.href = "<c:url value='/login/relogin.do?inputPage=/restriction/list.do'/>";
				return;
			}
			alert("Type: "+st+ "\nErr: "+ xhr.responseText +"\n Response: "+ xhr.status + " "+xhr.statusText); 
		}
	});
	
	/* Button Function Start (Resource CRUD) */
	
	$('[name=requestMap]').click( function(){
		var rowNum;
		var rowData;
		var rowArray = new Array();
		rowNum = new String(jQuery("#grid").getGridParam('selarrrow'));
		rowNumList = rowNum.split(",");

		if(rowNum == null || rowNum ==""){
			alert("No selected Rows");
			return false;
		} else {
			for(var i = 0 ; i < rowNumList.length ; i++){
				rowData = jQuery("#grid").getRowData(rowNumList[i]);
				rowArray[i] = rowData.beanId;
			}
			jQuery.ajaxSettings.traditional = true;
			$.ajax({
				type: 'POST',
				url: '<c:url value="/admin/reload/reloadMaps.do"/>',
				data:
				{ 
					beanid : rowArray,
					reloadmaps : 'maps'
				},
				dataType: 'json',
				success: function(msg){
					document.getElementById("transfer").style.visibility= "hidden";
					alert('<anyframe:message code="resourcereload.ui.alert.successreload" />');
				},
				error: function(msg){
					document.getElementById("transfer").style.visibility= "hidden";
					document.write(msg.responseText);
				}
			});
		}
	});
	
	$("[name=restrictedTimes]").click( function() {
		var rowNum;
		var rowData;
		var rowArray = new Array();
		rowNum = new String(jQuery("#grid").getGridParam('selarrrow'));
		rowNumList = rowNum.split(",");

		if(rowNum == null || rowNum ==""){
			alert("No selected Rows");
			return false;
		} else {
			for(var i = 0 ; i < rowNumList.length ; i++){
				rowData = jQuery("#grid").getRowData(rowNumList[i]);
				rowArray[i] = rowData.beanId;
			}
			jQuery.ajaxSettings.traditional = true;
			$.ajax({
				type: 'POST',
				url: '<c:url value="/admin/reload/reloadTimes.do"/>',
				data:
				{ 
					beanid : rowArray,
					reloadtimes : 'times'
				},
				dataType: 'json',
				success: function(msg){
					document.getElementById("transfer").style.visibility= "hidden";
					alert('<anyframe:message code="resourcereload.ui.alert.successreload" />');
				},
				error: function(msg){
					document.getElementById("transfer").style.visibility= "hidden";
					document.write(msg.responseText);
				}
			});
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
                <td width="145" height="27" align="center" valign="bottom"  background="<c:url value='/images/content/tab_menu1.gif'/>" bgcolor="#EDEDED" class="blkbold">Auth. Reload</td>
              </tr>
    	</table></td>
	</tr>
	<!--- END : Tab menu ------>
	<tr>
		<td style="padding-left:10px">
			<form action="<c:url value="/admin/reload/resourceReload.do"/>" method="post" id="reloadResource" name="reloadResource">
				<div id="documentation" class="demo" style="overflow:auto; height:300px;width:800px;">
					<table width="793" border="0" cellpadding="0" cellspacing="0">
						<tr height="30">
							<td width="400">
							</td>
							<td width="330" align="right">
								<table>
									<tr>
										<td>
										  <table height="22" border="0" cellpadding="0" cellspacing="0">
								              <tr>
								                <td width="18"><img src="<c:url value='/images/btn/update.gif'/>" width="18" height="22"></td>
								                <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="requestMap">Request Mapping</a></td>
								                <td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
								              </tr>
								            </table>
							            </td>
							            <td>
									    	<table height="22" border="0" cellpadding="0" cellspacing="0">
										        <tr>
											        <td width="18"><img src="<c:url value='/images/btn/update.gif'/>" width="18" height="22"></td>
											        <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="restrictedTimes">Restricted Times</a></td>
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
			</form>
		</td>
  	</tr>
	<tr valign="top">
		<td height="50%" colspan="2" valign="top" >
			<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#E9ECF1">
				<tr><td height="10" bgcolor="#ffffff"></td></tr>
				<tr><td height="1" bgcolor="#C9CFDD"></td></tr>
				<tr><td valign="top" bgcolor="#E9ECF1"><div id="footSub"></div></td></tr>
			</table>
		</td>
	</tr>
</table>

<div id="transfer" style="visibility:hidden;">
	<table>
		<tr>
			<td width="10">&nbsp;</td>
		    <td colspan="1" valign="top">
				<table width="802" height="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td valign="top" style="padding-top:15px">
							<table width="517" border="0" align="center" cellpadding="0" cellspacing="0">
								<tr>
									<td id="message" style="padding-left:89px" class="blue_h2">Please Wait! </td>
								</tr>
								<tr>
									<td height="15" background="<c:url value='/images/box_top.gif'/>">&nbsp;</td>
								</tr>
								<tr>
									<td align="center" background="<c:url value='/images/box_bg.gif'/>" class="boxpadding">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td align="center"><img src="<c:url value='/images/transport.gif'/>" width="428" height="72"></td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td height="31" background="<c:url value='/images/box_bttm.gif'/>">&nbsp;</td>
								</tr>
						  </table>
						</td>
					</tr>
				</table>
			</td>
	  	</tr>
	</table>
</div>
</body>
</html>
