<%@ taglib uri='http://www.anyframejava.org/tags' prefix='anyframe' %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><anyframe:message code="viewmapping.ui.title" /></title>
<script language="javascript" src="<c:url value='/js/CommonScript.js'/>"></script>

<jsp:include page="/common/jquery-include.jsp" />

<script language="javascript">
<!--
var index = 0;
var deletedRow = 0;

jQuery(document).ready( function(){	
	$("#checkall").click(function() {
		if ($("#checkall:checked").length > 0) {
			$('input:checkbox[name=idx]:not(checked)').attr("checked", "checked");
		} else {
			$('input:checkbox[name=idx]:checked').attr("checked", "");
		}
	});

	var all = jQuery("[name=idx]");
	index = all.length;
});


function movetoBack(){
	location.href="<c:url value='/viewresourcesmapping/list.do?' />";
}

function insertRow(flag) {
	var rows = jQuery("#rolemappinglist tr[ID^=role]");	
		
	// jQuery append 구문 안쪽에서는 적용 안됨 
	var roleselectbox	= '	<select class="selbox" name="roleid" id="roleid" onchange="javascript:roleChanged(this, ' + index + ')">' +
						'		<option value=""><anyframe:message code="viewresource.ui.selbox.selectrole" /></option>' + 
						'		<c:forEach var="list" items="${rolelist}">' +			
						'			<option value="${list.roleId}">${list.roleName}</option>' +
						'		</c:forEach>' +
						'	</select>' +
						'	<input type=\"hidden\" name=\"userName\" id=\"userName\">';

	var groupselectbox	= '	<select class="selbox" name="roleid" id="roleid" onchange="javascript:groupChanged(this, ' + index + ')">' +
						'		<option value=""><anyframe:message code="viewresource.ui.selbox.selectgroup" /></option>' +
						'		<c:forEach var="list" items="${grouplist}">' +			
						'			<option value="${list.groupId}">${list.groupName}</option>' +
						'		</c:forEach>' +
						'	</select>' +
						'	<input type=\"hidden\" name=\"userName\" id=\"userName\">';

	var permissionlist	= '<c:forEach var="list" items="${permissionlist}">' +
						'	<td bgcolor="#B6CDE4" width="1"></td>' +
						'	<td class="tdin"><input type="checkbox" name="${fn:toLowerCase(list.permissionName)}" value="0"></td>' +
						'</c:forEach>';
							
	if(flag == "ROLE") {
		jQuery("#rolemappinglist").append(			
			"<tr id=\"role" + index + "\">"
			+"	<td class=\"tdHeadGrey\"><input type=\"checkbox\" name=\"idx\" id=\"idx\" value=\"" + index + "\"><input type=\"hidden\" name=\"refType\" id=\"refType\" value=\"ROLE\"></td>"
			+"	<td bgcolor=\"#B6CDE4\" width=\"1\"></td>"
			+"	<td class=\"tdHeadGrey\" colspan=\"2\">"
			+ roleselectbox
			+"	</td>"

			+ permissionlist

			+"</tr>"
			+"<tr><td height=\"1\" colspan=\"${fn:length(permissionlist)*2+4}\" bgcolor=\"#D6D6D6\"></td></tr>"
		);
	} else if(flag == "GROUP") {
		jQuery("#rolemappinglist").append(
			"<tr id=\"role" + index + "\">"
			+"	<td class=\"tdHeadGrey\"><input type=\"checkbox\" name=\"idx\" id=\"idx\" value=\"" + index + "\"><input type=\"hidden\" name=\"refType\" id=\"refType\" value=\"GROUP\"></td>"
			+"	<td bgcolor=\"#B6CDE4\" width=\"1\"></td>"
			+"	<td class=\"tdHeadGrey\" colspan=\"2\">"
			+ groupselectbox
			+"	</td>"

			+ permissionlist

			+"</tr>"
			+"<tr><td height=\"1\" colspan=\"${fn:length(permissionlist)*2+4}\" bgcolor=\"#D6D6D6\"></td></tr>"
		);
	} else if(flag == "USER") {
		jQuery("#rolemappinglist").append(			
			"<tr id=\"role" + index + "\">"
			+"	<td class=\"tdHeadGrey\"><input type=\"checkbox\" name=\"idx\" id=\"idx\" value=\"" + index + "\"><input type=\"hidden\" name=\"refType\" id=\"refType\" value=\"USER\"></td>"
			+"	<td bgcolor=\"#B6CDE4\" width=\"1\"></td>"
			+"	<td width=\"15\" class=\"tdHeadGrey\"><input type=\"text\" name=\"userName\" id=\"userName\" size=\"15\" class=\"ct_input_g\" readonly=\"readonly\"><input type=\"hidden\" name=\"roleid\" id=\"roleid\"></td>"
			+"	<td style=\"padding-left: 0px\" class=\"tdHeadGrey\" align=\"left\"><a href=\"javascript\:findUserByName(" + rows.length + ");\" id=\"findUserId\" name=\"findUserId\" class=\"searchBtn\"><anyframe\:message code='user.ui.link.select' /></a></td>"

			+ permissionlist

			+"</tr>"
			+"<tr><td height=\"1\" colspan=\"${fn:length(permissionlist)*2+4}\" bgcolor=\"#D6D6D6\"></td></tr>"
		);
	}

	index = index + 1;
}

function findUserByName(index) {
	var pop = 0;
	var username = $("[name=userName]");
	url = "<c:url value='/viewresourcesmapping/finduser.do?'/>" + "&userName=" + username[index].value + "&index=" + index;	
	pop = window.open(url, "FindUsersByName","top=100, left=200, width=640, height=450,scrollbars=no,resizable=no");
	pop.focus();
}

function setUserInfo(userId, userName, index) {
	var roleid = $("[name=roleid]");
	var username = $("[name=userName]");

	for(i = 0 ; i < index ; i++){
		if(roleid[i].value == userId){
			alert("<anyframe:message code='viewresource.ui.alert.useridalreadyselected' />");
			return;
		}
	}
	roleid[index].value = userId;
	username[index].value = userName;
}

function roleChanged(frm, num){
	var roleid = $("[name=roleid]");
	var username = $("[name=userName]");

	for(i = 0 ; i < index - deletedRow ; i++){
		if(roleid[i] != frm){
			if(roleid[i].value == frm.value && frm.value != ""){
				alert("<anyframe:message code='viewresource.ui.alert.roleidalreadyselected' />");
				frm.value = "";
				return;
			}
		}
	}
}

function groupChanged(frm, num){
	var roleid = $("[name=roleid]");
	var username = $("[name=userName]");
	
	for(i = 0 ; i < index - deletedRow ; i++){
		if(roleid[i] != frm){
			if(frm.value != "" && (frm.value == roleid[i].value)){
				alert("<anyframe:message code='viewresource.ui.alert.groupidalreadyselected' />");
				frm.value = "";
				return;
			}
		}
	}
}

function selectviewmapping() {
	var pop = 0;
	var username = $("[name=userName]");
	url = "<c:url value='/viewresourcesmapping/viewresourcelist.do?'/>";	
	pop = window.open(url, "FindUsersByName","top=100, left=200, width=860, height=500,scrollbars=no,resizable=no");
	pop.focus();
}

function goSubmit() {
	var viewResourceId = document.viewmapping.viewResourceId;

	if(viewResourceId.value == "" || viewResourceId.value == null){
		alert("<anyframe:message code='viewresource.ui.alert.requiredviewid' />");
		return;
	}

	var permission = new Array();
	var validPer = new Array();
	var perLength;
	var roleid = $("[name=roleid]");
	var username = $("[name=userName]");
	
	for(var i = 0 ; i < roleid.length ; i++){
		if(roleid[i].value == "" || roleid[i].value == null){
			alert("<anyframe:message code='viewresource.ui.alert.refernotnull' />");
			return;
		}
	}
	
	<c:forEach var="list" items="${permissionlist}" varStatus="status">
		var index = ${status.count} - 1;
		permission[index] = $("[name=${fn:toLowerCase(list.permissionName)}]");
		perLength = permission[index].length;
		if(perLength == 0){
			alert("<anyframe:message code='viewresource.ui.alert.atleastonerow' />");
			return;
		}
		
		for(var i = 0 ; i < perLength ; i++) {
			if(permission[index][i].checked == false ) {
				permission[index][i].value = "0";
				permission[index][i].checked = true;
			} else {
				permission[index][i].value = "${list.permissionMask}";
				validPer[i] = true;
			}
		}		
	</c:forEach>
	
	for(var i = 0 ; i < perLength ; i++){
		if(validPer[i] == true){
			
		} else {
			alert("<anyframe:message code='viewresource.ui.alert.atleastonepermission' />");
			return;
		}
	}
	
	document.viewmapping.action = '<c:url value="/viewresourcesmapping/save.do"/>';
	document.viewmapping.submit();
}

function deleteRow() {
	deletedRow++;
	jQuery("#[name=idx]:checked").closest('tr').next().remove();
	jQuery("#[name=idx]:checked").closest('tr').remove();
}

function mask() {
	<c:forEach var="mlist" items="${masklist}" varStatus="mstatus">
	
		var permission = new Array();
		
		<c:forEach var="plist" items="${permissionlist}" varStatus="pstatus">
			var currentpermission = '${mlist}';
			var	rowindex = ${mstatus.count} - 1;		
			
			var index = ${pstatus.count} - 1;	
			var bitOrder = 31 - ${plist.permissionBitOrder};
			
			permission[index] = $("[name=${fn:toLowerCase(plist.permissionName)}]");
			
			if(currentpermission.substring(bitOrder,bitOrder+1) == "1") {
				permission[index][rowindex].checked = true;
			}
		</c:forEach>
	</c:forEach>
}
-->
</script>

</head>
<body text="#000000" onLoad="javascript:mask();">
<form name="viewmapping" id="viewmapping">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
	<tr>
		<td>
			<!--  Begin of Contents -->
			<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 13px; margin-left:10px">
	
			<!-- Begin View Resource ID field -->
				<c:if test="${not empty viewResourceId}">
				<tr>
					<td style="padding-top: 5px;">
						<table width="600" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" >
							<tr><td height="2" colspan="3" bgcolor="#A2BACE"></td></tr>
							<tr>
								<td width="198" class="tdHead"><anyframe:message code="viewmapping.ui.label.viewresourceid" /></td>
								<td bgcolor="#B6CDE4" width="1"></td>
								<td class="tdin">${viewResourceId} <input type="hidden" name="viewResourceId" id="viewResourceId" value="${viewResourceId}" ></td>
							</tr>
							<tr><td height="2" colspan="3" bgcolor="#A2BACE"></td></tr>
						</table>
					</td>
				</tr>
				</c:if>
				<c:if test="${empty viewResourceId}">
				<tr>
					<td style="padding-top: 5px;">
						<table width="600" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" >
							<tr><td height="2" colspan="4" bgcolor="#A2BACE"></td></tr>
							<tr>
								<td width="201" class="tdHead"><anyframe:message code="viewmapping.ui.label.viewresourceid" /></td>
								<td bgcolor="#B6CDE4" width="1"></td>
							  <td class="tdin"><input type="text" name="viewResourceId" id="viewResourceId" size="20" class="ct_input_g" readonly="readonly"></td>
								<td width="434" align="left">
								<a href="javascript:selectviewmapping();" name="selectTimeId" class="searchBtn">
								<anyframe:message code='user.ui.link.select' /></a>
							  </td>
							</tr>
							<tr><td height="2" colspan="4" bgcolor="#A2BACE"></td></tr>
						</table>
					</td>
				</tr>
				</c:if>
			<!-- End View Resource ID filed -->				
			</table>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
	<tr>
		<td width="10" rowspan="3">&nbsp;</td>
		<td height="6"></td>
	</tr>
	<tr>
		<td>
			<!--  Begin of Contents -->
			<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 13px;">
				<tr>
					<td style="padding-top: 5px;">
						<table width="600" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" id="rolemappinglist">
							<tr><td height="2" colspan="${fn:length(permissionlist)*2+4}" bgcolor="#A2BACE"></td></tr>
							<tr>
								<td class="tdHead"><input type="checkbox" name="checkall" id="checkall"></td>
								<td bgcolor="#B6CDE4" width="1"></td>
								<td class="tdHead" colspan="2"><anyframe:message code='viewresource.ui.label.refid' /></td>
								<c:forEach var="list" items="${permissionlist}">
								<td bgcolor="#B6CDE4" width="1"></td>
								<td class="tdHead">${list.permissionName}</td>
								</c:forEach>
							</tr>
							<tr><td height="2" colspan="${fn:length(permissionlist)*2+4}" bgcolor="#A2BACE"></td></tr>
							<c:forEach var="list" items="${mappinglist}" varStatus="status">
								<tr id="role${status.count-1}">
									<td class="tdHeadGrey"><input type="checkbox" name="idx" id="idx" value="${status.count-1}"><input type="hidden" name="refType" id="refType" value="${list.refType}"></td>
									<td bgcolor="#B6CDE4" width="1"></td>
									<td class="tdHeadGrey" colspan="2">${list.id.refId}<input type="hidden" name="userName" id="userName"><input type="hidden" name="roleid" id="roleid" value="${list.id.refId}"></td>
									<c:forEach var="list" items="${permissionlist}">
									<td bgcolor="#B6CDE4" width="1"></td>
									<td class="tdin"><input type="checkbox" name="${fn:toLowerCase(list.permissionName)}" value="1"></td>
									</c:forEach>
								</tr>
								<tr><td height="1" colspan="${fn:length(permissionlist)*2+4}" bgcolor="#D6D6D6"></td></tr>
							</c:forEach>
						</table>
					</td>
				</tr>				
			</table>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
	<tr>
		<td width="10" rowspan="3">&nbsp;</td>
		<td height="6"></td>
	</tr>
	<tr>
		<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" style="margin-top:10px;">
				<tr>
					<td style="padding-top: 5px;">
						<table width="590" height="22" border="0" cellpadding="0" cellspacing="0" style="margin-left:10px">
							<tr>
								<td align="right"  class="tdBtnRight">
									<table height="22" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="18"><img src="<c:url value='/images/btn/btn_add.gif'/>" width="18" height="22"></td>
											<td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="javascript:insertRow('ROLE');" name="movetoback"><anyframe:message code="viewmapping.ui.button.addrole" /></a></td>
											<td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
										</tr>
									</table>
							  </td>
								<td width="12%" align="right"  class="tdBtnRight">
									<table width="97" height="22" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="18"><img src="<c:url value='/images/btn/btn_add.gif'/>" width="18" height="22"></td>
											<td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="javascript:insertRow('GROUP');" name="movetoback"><anyframe:message code="viewmapping.ui.button.addgroup" /></a></td>
											<td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
										</tr>
								  </table>
							  </td>
								<td width="11%" align="right"  class="tdBtnRight">
									<table width="88" height="22" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="18"><img src="<c:url value='/images/btn/btn_add.gif'/>" width="18" height="22"></td>
											<td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="javascript:insertRow('USER');" name="movetoback"><anyframe:message code="viewmapping.ui.button.adduser" /></a></td>
											<td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
										</tr>
								  </table>
							  </td>
								<td width="10%" align="right"  class="tdBtnRight">
									<table width="82" height="22" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="18"><img src="<c:url value='/images/btn/btn_delete.gif'/>" width="18" height="22"></td>
											<td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="javascript:deleteRow();" name="movetoback"><anyframe:message code="viewmapping.ui.button.delrow" /></a></td>
											<td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
										</tr>
								  </table>
							  </td>
								<td width="8%" align="right"  class="tdBtnRight">
									<table width="60" height="22" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="18"><img src="<c:url value='/images/btn/btn_save.gif'/>" width="18" height="22"></td>
											<td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="javascript:goSubmit();" name="movetoback"><fmt:message key="button.save"/></a></td>
											<td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
										</tr>
								  </table>
							  </td>
								<td width="9%" align="right"  class="tdBtnRight">
									<table width="74" height="22" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="18"><img src="<c:url value='/images/btn/btn_cancel.gif'/>" width="18" height="22"></td>
											<td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="javascript:movetoBack();" name="movetoback"><fmt:message key="button.cancel"/></a></td>
											<td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
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

</form>
</body>
</html>	
