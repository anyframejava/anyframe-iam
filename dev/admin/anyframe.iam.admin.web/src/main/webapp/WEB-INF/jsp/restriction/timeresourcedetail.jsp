<%@ taglib uri='http://www.anyframejava.org/tags' prefix='anyframe' %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><anyframe:message code="restrictedtimes.ui.title.timeresourcedetail" /></title>

<script language="javascript" src="<c:url value='/js/CommonScript.js'/>"></script>

<jsp:include page="/common/jquery-include.jsp" />
<jsp:include page="/common/jqgrid-include.jsp" />

<script type="text/javascript" src="<c:url value='/validator.do'/>"></script>
<validator:javascript formName="restrictedTimes" staticJavascript="false" xhtml="true" cdata="false"/>

<script type="text/javascript">
<!--
	jQuery(document).ready( function(){
		$('[name=movetoback]').click( function(){
				location.href="<c:url value='/restriction/timeresource/list.do?'/>";
		});
	
		/* Mapping Resources */
		$("[name=selectTimeId]").click( function() {
			window.open("<c:url value='/restriction/timeresource/listPopUp.do?' />", 'timesList', 'height=370, width=850');
		});
	});

	function timeIdChanged(){
		jQuery("#grid_timeresource").setPostData({searchKeyword:document.restrictedtimes.timeId.value});
		jQuery("#grid_timeresource").setGridParam({url:"<c:url value='/restriction/timeresource/listResourceData.do?' />"}).trigger("reloadGrid");
	}
//-->
</script>

<script type="text/javascript">
<!--
	jQuery(document).ready( function() {
		jQuery("#grid_timeresource").jqGrid( {
			url: "<c:url value='/restriction/timeresource/listResourceData.do?&searchKeyword=' />" + document.restrictedtimes.timeId.value,
			mtype:'POST',
			datatype : "json",
			colNames : [ 
						'<anyframe:message code="roleresource.ui.grid.sortorder" />', 
						'<anyframe:message code="roleresource.ui.grid.resourceid" />', 
						'<anyframe:message code="roleresource.ui.grid.resourcename" />',
						'System Name',
						'<anyframe:message code="roleresource.ui.grid.resourcepattern" />', 
						'<anyframe:message code="roleresource.ui.grid.resourcetype" />' 
						],
			jsonReader: {
		        repeatitems: false
		    },
			colModel : [ 
			{
				name : 'sortOrder',
				index : 'sortOrder',
				sorttype : 'int', 
				align : 'right',
				sorttype : 'int',
				formatter : 'integer',
				width : 60
			}, {
				key : true,
				name : 'resourceId',
				index : 'resourceId',
				sorttype : 'text',
				width : 70
			}, {
				name : 'resourceName',
				index : 'resourceName',
				sorttype : 'text',
				width : 90
			}, {
				name : 'systemName',
				index : 'systemName',
				sorttype : 'text',
				width : 90
			}, {
				name : 'resourcePattern',
				index : 'resourcePattern',
				sorttype : 'text',
				width : 100
			}, {
				name : 'resourceType',
				index : 'resourceType',
				sorttype : 'text',
				width : 60
			} ],
			width : 790,
			height : 130,
			multiselect : true,
			pager : jQuery('#pager_timeresource'),
			sortname : 'resourceId',
			sortorder: 'asc',
			rowNum : 20,
			rowList : [ 10, 20, 30 ],
			viewrecords : true,
			loadError: function(xhr,st,err) {
				if(st == "parsererror" && xhr.responseText.match('<title>Login</title>') != null) {									
					location.href = "<c:url value='/login/relogin.do?inputPage=/restriction/timeresource/addView.do?&timeId='/>" + document.restrictedtimes.timeId.value;
					return;
				}
				alert("Type: "+st+ "\nErr: "+ xhr.responseText +"\n Response: "+ xhr.status + " "+xhr.statusText); 
			}
		});

		/* Button Function Start (Resource CRUD) */
		
		/* DELETE Resource */
		$('[name=removeResource]').click( function(){
			var rowNum;
			var rowData;
			var rowArray = new Array();
			rowNum = new String(jQuery("#grid_timeresource").getGridParam('selarrrow'));
			rowNumList = rowNum.split(",");

			if(rowNum == null || rowNum ==""){
				alert("<anyframe:message code='roleresource.ui.alert.noselectedrow' />");
				return false;
			} else {
				if(confirm("<anyframe:message code='roleresource.ui.alert.confirmtodelete' />")){
					for(var i = 0 ; i < rowNumList.length ; i++){
						rowData = jQuery("#grid_timeresource").getRowData(rowNumList[i]);
						rowArray[i] = rowData.resourceId;
						jQuery("#grid_timeresource").delRowData(rowData.resourceId);
					}
					//jQuery("#grid_timeresource").setPostData({resourceIds:rowArray, timeId:document.restrictedtimes.timeId.value});
					//jQuery("#grid_timeresource").setGridParam({url:"<c:url value='/restriction/timeresource/deleteResourceFromDetail.do?' />"}).trigger("reloadGrid");
					//jQuery("#grid_timeresource").setGridParam({url:"<c:url value='/restriction/timeresource/listResourceData.do?&searchKeyword=' />" + document.restrictedtimes.timeId.value});
					$.post("<c:url value='/restriction/timeresource/deleteResourceFromDetail.do?' />", {resourceIds:rowArray, timeId:document.restrictedtimes.timeId.value}, function(data){
				    	jQuery("#grid_timeresource").trigger("reloadGrid");
				    });
				}
			}
		});

		/* Mapping Resources */
		$("[name=addResource]").click( function() {
			if (document.restrictedtimes.timeId.value == "")
			{
				alert("Please select time id.");
				return;
			}
			window.open("<c:url value='/restriction/timeresource/listResourcePopUp.do?' />", 'resourceList', 'height=370, width=640');
		});
	});

	function reloadGrid(timeId, tempArray){
		var rowDataArray = new Array();
		for(i = 0 ; i < tempArray.length ; i++)
			rowDataArray[i] = tempArray[i];
		//jQuery("#grid_timeresource").setPostData({timeId:timeId, resourceId:rowDataArray, searchKeyword:timeId});
	    //jQuery("#grid_timeresource").setGridParam({url:"<c:url value='/restriction/timeresource/add.do?' />"}).trigger("reloadGrid");
		
		jQuery.ajaxSettings.traditional = true;
		$.post("<c:url value='/restriction/timeresource/add.do?' />", {timeId:timeId, resourceId:rowDataArray, searchKeyword:timeId}, function(data){
	    	jQuery("#grid_timeresource").trigger("reloadGrid");
	    });
		alert("<anyframe:message code='roleresource.ui.alert.allocationsuccess' />");
	}
//-->
</script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style></head>

<body bgcolor="#ffffff" text="#000000">
<!--  Begin of Contents -->
<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" >
	<tr>
	    <td height="6"></td>
  	</tr>
 	<!-- Begin Title -->
  	<tr>
    	<td align="left">
    		<table width="796" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
      			<tr>
					<td class="title" style="padding-left:21px">
						<c:if test="${empty restrictedtimes.timeId}">
						 	<anyframe:message code="restrictedtimes.ui.title.timeresourceadd" />
						 	<c:set var="readonly" value="false" />
						</c:if> 
						<c:if test="${not empty restrictedtimes.timeId}">	
							<anyframe:message code="restrictedtimes.ui.title.timeresourceupdate" />
							<c:set var="readonly" value="true" />
						</c:if>
					</td>
				</tr>
			</table>
		</td>
	</tr>
 	<!-- End of Title -->

    <!-- Begin input field -->
  	<tr>
	  	<td>
			<form:form commandName="restrictedtimes" method="post" id="restrictedtimes" name="restrictedtimes">
				<table width="796" border="0" cellspacing="0" cellpadding="0" style="margin-top: 5px;">
						<tr><td height="2" colspan="4" bgcolor="#A2BACE"></td></tr>
						<tr>
							<td width="144" class="tdHead"><anyframe:message code="restrictedtimes.ui.label.timeid" /></td>
							<td bgcolor="#D6D6D6" width="1"></td>			
							<td width="144" class="tdin">
								<form:input path="timeId" id="timeId" readonly="true" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="10"/>&nbsp;<form:errors path="timeId" cssClass="error" />
							</td>
							<td width="474" align="left">
								<a href="#" name="selectTimeId" class="searchBtn"><anyframe:message code='user.ui.link.select' /></a>
							</td>
						</tr>		
						<tr><td height="1" colspan="4" bgcolor="#D6D6D6"></td></tr>
						<tr>
							<td class="tdHead">
							<anyframe:message code="restrictedtimes.ui.label.timetype" /></td>
							<td bgcolor="#D6D6D6" width="1"></td>
							<td colspan="2" class="tdin">	
					        <form:select path="timeType" id="timeType" cssClass="ct_input_g" disabled="true">
					        	<form:option value="crash"><anyframe:message code="restrictedtimes.ui.selectbox.timetype.crash" /></form:option>
					        	<form:option value="improve"><anyframe:message code="restrictedtimes.ui.selectbox.timetype.improve" /></form:option>
					        	<form:option value="daily"><anyframe:message code="restrictedtimes.ui.selectbox.timetype.daily" /></form:option>
					        	<form:option value="weekend"><anyframe:message code="restrictedtimes.ui.selectbox.timetype.weekend" /></form:option>
					        	<form:option value="holiday"><anyframe:message code="restrictedtimes.ui.selectbox.timetype.holiday" /></form:option>
					        </form:select><form:errors path="timeType" />								        
							</td>
						</tr>
						<tr><td height="1" colspan="4" bgcolor="#D6D6D6"></td></tr>
						<tr>
							<td class="tdHead">
							<anyframe:message code="restrictedtimes.ui.label.startdate" /></td>
							<td bgcolor="#D6D6D6" width="1"></td>
							<td colspan="2" class="tdin">			
								<form:input path="startDate" id="startDate" readonly="true" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="8" size="20" />&nbsp;<form:errors path="startDate" cssClass="error" />
							</td>
						</tr>
						<tr><td height="1" colspan="4" bgcolor="#D6D6D6"></td></tr>
						<tr>
							<td class="tdHead">
							<anyframe:message code="restrictedtimes.ui.label.starttime" /></td>
							<td bgcolor="#D6D6D6" width="1"></td>			
							<td colspan="2" class="tdin">
					        <form:input path="startTime" id="startTime" readonly="true" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="6" size="20" />&nbsp;<form:errors path="startTime" cssClass="error" />
							</td>
						</tr>
						<tr><td height="1" colspan="4" bgcolor="#D6D6D6"></td></tr>	
						<tr>
							<td class="tdHead">
							<anyframe:message code="restrictedtimes.ui.label.enddate" /></td>
							<td bgcolor="#D6D6D6" width="1"></td>
							<td colspan="2" class="tdin">
					        <form:input path="endDate" id="endDate" readonly="true" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="8" size="20" />&nbsp;<form:errors path="endDate" cssClass="error" />
							</td>
						</tr>
						<tr><td height="1" colspan="4" bgcolor="#D6D6D6"></td></tr>	
						<tr>
							<td class="tdHead">
							<anyframe:message code="restrictedtimes.ui.label.endtime" /></td>
							<td bgcolor="#D6D6D6" width="1"></td>
							<td colspan="2" class="tdin">
					        <form:input path="endTime" id="endTime" readonly="true" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="6" size="20" />&nbsp;<form:errors path="endTime" cssClass="error" />
							</td>
						</tr>
						<tr><td height="1" colspan="4" bgcolor="#D6D6D6"></td></tr>	
						<tr>
							<td class="tdHead">
							<anyframe:message code="restrictedtimes.ui.label.description" /></td>
							<td bgcolor="#D6D6D6" width="1"></td>
							<td colspan="2" class="tdin">
					        <form:input path="description" id="description" readonly="true" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="100" size="50" />&nbsp;<form:errors path="description" cssClass="error" />
							</td>
						</tr>
						<tr><td height="1" colspan="4" bgcolor="#B6CDE4"></td></tr>
						<tr>
						  	<td colspan="4">
								<form name="resourceGrid">
								<table width="796" border="0" cellpadding="0" cellspacing="0">
								  <tr>
								    <td><table id="grid_timeresource" class="scroll" cellpadding="0" cellspacing="0">
								    </table>
								        <div id="pager_timeresource" class="scroll" style="text-align: center;"></div></td>
								  </tr>
								</table>
								</form>
							</td>
						</tr>
				</table>
			</form:form>
		</td>
	</tr>
	<!-- End of Input Field -->


	<tr>
	  	<td valign="top">
			<!-- Begin Button Field -->
			<table width="796" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" style="margin-top:5px;">
							<tr>
								<td align="right">
									<table width="796" height="22" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="72%" align="right" class="tdBtnRight">	
												<table width="129" height="22" border="0" cellpadding="0" cellspacing="0">
						              				<tr>
						                				<td width="18"><img src="<c:url value='/images/btn/btn_add.gif'/>" width="18" height="22"></td>
						                				<td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="addResource" ><anyframe:message code="restrictedtimes.ui.btn.addresources" /></a></td>
						                				<td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
						              				</tr>
				           				  		</table>
				           				  	</td>
											<c:if test="${not empty restrictedtimes.timeId}">
											<td width="20%" align="right"  class="tdBtnRight">
												<table width="155" height="22" border="0" cellpadding="0" cellspacing="0">
											          <tr>
											            <td width="18"><img src="<c:url value='/images/btn/btn_delete.gif'/>" width="18" height="22"></td>
											            <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="removeResource"><anyframe:message code="restrictedtimes.ui.btn.removeresources" /></a></td>
											            <td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
											          </tr>
						        			  	</table>
						        			</td>
											</c:if>	
										  	<td width="6%" align="right"  class="tdBtnRight">
												<table height="22" border="0" cellpadding="0" cellspacing="0">
										          <tr>
										            <td width="18"><img src="<c:url value='/images/btn/btn_cancel.gif'/>" width="18" height="22"></td>
										            <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="movetoback"><anyframe:message code="restrictedtimes.ui.btn.back" /></a></td>
										            <td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
										          </tr>
				        				  		</table>
				        				  	</td>
										</tr>
							      </table> </td>
						  </tr>
			  </table>
			<!-- End of Button Field -->
		</td>
	</tr>
</table>

</body>
</html>