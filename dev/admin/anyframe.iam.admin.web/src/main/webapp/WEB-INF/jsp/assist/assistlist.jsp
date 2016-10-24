<%@ taglib uri='http://www.sds.samsung.com/tags' prefix='anyframe' %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><anyframe:message code="resourcereload.ui.title.resourcegather" /></title>
<script language="javascript" src="<c:url value='/js/CommonScript.js'/>"></script>

<jsp:include page="/common/jquery-include.jsp" />

<script language="javascript">
<!--
function reload() {
	if(confirm('<anyframe:message code="resourcereload.ui.alert.confirmtogather" />')) {
		document.getElementById("transfer").style.visibility = "visible";

		$.ajax({
			type: 'POST',
			url: '<c:url value="/admin/assist/resourceAssist.do"/>',
			dataType: 'json',
			success: function(msg){
				document.getElementById("transfer").style.visibility= "hidden";
				document.location.href = "<c:url value='/common/complete.jsp'/>";
			},
			error: function(msg){
				document.getElementById("transfer").style.visibility= "hidden";
				document.write(msg.responseText);
			}
		});
	}
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
                <td width="145" height="27" align="center" valign="bottom"  background="<c:url value='/images/content/tab_menu1.gif'/>" bgcolor="#EDEDED" class="blkbold">Res. Gathering</td>
              </tr>
    	</table></td>
	</tr>
	<!--- END : Tab menu ------>
	<tr>
		<td style="padding-left:10px">
			<form action="<c:url value="/admin/assist/resourceAssist.do"/>" method="post" id="reloadAssist" name="reloadAssist">
			<table width="792" border="0" cellspacing="0" cellpadding="0">
				<!-- Begin Title -->
				<tr>
					<td valign="top">
				    	<table width="792" border="0" cellspacing="0" cellpadding="0" style="margin-top: 13px;">
							<tr>
								<td class="title" style="padding-left:21px"><anyframe:message code="resourcereload.ui.title.resourcegather" /></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
				    <td style="padding-top: 5px;">
						<table width="792" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" >
							<tr><td height="2" colspan="3" bgcolor="#A2BACE"></td></tr>
							
							<tr>
								<td class="tdHead"><anyframe:message code="resourcereload.ui.label.targetserver" /></td>
								<td bgcolor="#D6D6D6" width="1"></td>
								<td class="tdin">									        
									<select id="beanid" name="beanid" class="selbox" style="overflow:auto; border:1px solid #c3daf9;">
										<c:forEach var="list" items="${beanlist}">
											<option value="${list.beanId}">${list.serverUrl}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr><td height="1" colspan="3" bgcolor="#B6CDE4"></td></tr>
						</table>
					</td>
				</tr>
				<tr>
				    <td>
						<table width="792" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" style="margin-top:10px;">
							<tr>
								<td align="right">
									<table height="22" border="0" cellpadding="0" cellspacing="0">
										<tr>
										  	<td style="padding-left: 3px;">
												<table height="22" border="0" cellpadding="0" cellspacing="0">
													<tr>
														<td width="18"><img src="<c:url value='/images/btn/update.gif'/>" width="18" height="22"></td>
														<td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="javascript:reload();" name="movetoback"><anyframe:message code="resourcereload.ui.btn.gather" /></a></td>
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
		</td>
	</tr>
	<tr>
		<td width="10">&nbsp;</td>
	    <td colspan="1" valign="top">
			<div id="transfer" style="visibility:hidden;">
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
			</div>
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
</body>
</html>
