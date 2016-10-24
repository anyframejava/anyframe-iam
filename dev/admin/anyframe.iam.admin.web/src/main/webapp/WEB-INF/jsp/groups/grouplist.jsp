<%@ taglib uri='http://www.sds.samsung.com/tags' prefix='anyframe' %>
<%@ include file="/common/taglibs.jsp"%> 
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>User List</title>

<jsp:include page="/common/jstree-include.jsp" />
<jsp:include page="/common/jqgrid-include.jsp" />
<jsp:include page="/common/jqueryui-include.jsp" />
<script type="text/javascript">
	$(function () {
		$("#group").tree({
			data	: {
			type	: "json",
			method	: "POST",
			url		: "<c:url value='/groups/listData.do'/>",
			async	: true,
			async_data : function (NODE) { return { id : $(NODE).attr("id") || "0" } }
		},
		ui	: {
			theme_name : "classic",
			context : false
		},
		callback	: {
			onselect	: function(NODE,TREE_OBJ) {
				opener.setGroupValue(NODE.id, $(NODE).children("a:visible").text());
				self.close();
			},
			error 		: function(TEXT){
				alert(TEXT);
			}	 			    					
		}        
		});
	});
</script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-color: #FFFFFF;
}
-->
</style></head>
<body>		
<table width="300" border="0" cellpadding="0" cellspacing="0">
	<tr>
	  <td align="center"><table width="285" height="44" border="0" align="center" cellpadding="0" cellspacing="0" >
      <tr>
        <td height="44" align="left" class="checkGrouptitle" style="padding-left:38px"><anyframe:message code='user.ui.label.groupname' /></td>
      </tr>
    </table></td>
  </tr>
	<tr>
		<td style="padding-left:9px;padding-top:6px">
		<table width="284" border="0" cellpadding="0" cellspacing="0">
			<tr height="30">
			  <td width="26" height="25"  background="<c:url value='/images/bg_treel.gif'/>" style="padding-left:8px"><div id="menuopen"> <a class="openBtn" title="Open Branch" href="javascript:$.tree_reference('group').open_all();">Open</a></div></td>
		    <!---START: Tree Btn ------>
			  <td width="251" background="<c:url value='/images/bg_treer2.gif'/>"><div id="menuclose"><a class="closeBtn" title="Close Branch" href="javascript:$.tree_reference('group').close_all();">Close</a></div></td>
			  <!---END: Tree Btn ------>
		  </tr>
			<tr height="400">
				<td colspan="2">
					<div id="documentation">
						<div id="group" class="demo" style="overflow:auto; height:420px;width:282px;border:1px solid #c3daf9;">
							<span><anyframe:message code="role.ui.tree.span" /></span>
						</div>
					</div>
				</td>
			</tr>
		</table></td>
	</tr>
</table>
</body>
</html>