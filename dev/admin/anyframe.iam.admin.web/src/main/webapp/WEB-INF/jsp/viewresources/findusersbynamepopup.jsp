<%@ taglib uri='http://www.sds.samsung.com/tags' prefix='anyframe' %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><anyframe:message code="user.ui.title.userlist" /></title>

<jsp:include page="/common/jstree-include.jsp" />
<jsp:include page="/common/jqueryui-include.jsp" />
<jsp:include page="/common/jqgrid-include.jsp" />

<script type="text/javascript">

jQuery(document).ready(
	function() {
		// Grid Component
		jQuery("#grid").jqGrid(
		{
			sortable: true,
			url : "<c:url value='/users/listData.do?'/>",
			mtype : 'POST',
			datatype : "json",
			colNames : [ '<anyframe:message code="user.ui.grid.userid" />', '<anyframe:message code="user.ui.grid.username" />',
			             '<anyframe:message code="user.ui.grid.group" />', '<anyframe:message code="user.ui.grid.enable" />' ],
			jsonReader: {
		        repeatitems: false
		    },
			colModel : [ {
				key : true,
				name : 'userId',
				index : 'userId',
				width : 100
			}, {
				name : 'userName',
				index : 'userName',
				width : 100
			}, {
				name : 'groupName',
				index : 'groupName',
				width : 100
			}, {
				name : 'enabled',
				index : 'enabled',
				width : 70
			} ],
			width : 545,
			height : 335,
			sortname : 'userId',
			sortorder : 'asc',
			pager : jQuery('#pager2'),
			rowNum : 20,
			rowList : [ 10, 20, 30 ],
			viewrecords : true,
			/*
			loadError: function(xhr,st,err) {
				if(st == "parsererror" && xhr.responseText.match('<title>Login</title>') != null) {									
					location.href = "<c:url value='/login/relogin.do?inputPage=/userdetail/list.do?&groupId='/>" + groupId;
					return;
				}
				alert("Type: "+st+ "\nErr: "+ xhr.responseText +"\n Response: "+ xhr.status + " "+xhr.statusText); 
			},
			*/
			ondblClickRow : function(rowid) {
				var index = "${index}";				
				var rowData = jQuery("#grid").getRowData(rowid);
				opener.setUserInfo(rowData.userId,rowData.userName,index);
				self.close();
			}
		});

		/* Search Users */
		$("[name=searchUsers]").click(
			function() {
				var jqSearchForm = document.searchForm;
				jQuery("#grid").setPostData(
				{
					searchCondition : jqSearchForm.searchCondition.value,
					searchKeyword : jqSearchForm.searchKeyword.value
				});
				jQuery("#grid").setGridParam(
				{
					url : "<c:url value='/users/listData.do?'/>"
				}).trigger("reloadGrid");
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
	height:100%;
	background-color: #FFFFFF;
}
-->
</style></head>
<body height="100%">
<div id="grid2" class="demo" style="overflow: auto; height:100%; width: 624px;margin-top: 13px;" >
<form name="searchForm" onSubmit="return false"><input type="hidden" name="groupId">
<table width="572" border="0" cellpadding="0" cellspacing="0" >
	<tr height="30">
		<td width="56">
			<select name="searchCondition" id="searchSelect" class="selbox">
				<option value="userName"><anyframe:message code="user.ui.selectbox.username" /></option>
				<option value="userId"><anyframe:message code="user.ui.selectbox.userid" /></option>
			</select>		</td>
			<td width="93" style="padding-left: 3px;">
			<input type="text" name="searchKeyword" size="15" class="ct_input_g" id="searchKeyword">		</td>
			<td width="145" style="padding-left: 3px;">
				<a href="#"  name="searchUsers" class="searchBtn"><anyframe:message code="user.ui.btn.search" /></a></td>
			<td width="207" align="right">
			  <table height="22" border="0" cellpadding="0" cellspacing="0">
	              <tr>
	                <td width="18"></td>
	                <td></td>
	                <td width="10" align="right"></td>
	              </tr>
              </table>
	        </td>
		    <!--td width="71" align="right" style="padding-left:2px">
		    	<table height="22" border="0" cellpadding="0" cellspacing="0">
		          <tr>
		            <td width="18"><img src="<c:url value='/images/btn/btn_update.gif'/>" width="18" height="22"></td>
		            <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="groupChange"><anyframe:message code="user.ui.btn.chggroup" /></a></td>
		            <td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
		          </tr>
		        </table>
	        </td-->
		    <td width="71" align="right" style="padding-left:2px">
		    	<table height="22" border="0" cellpadding="0" cellspacing="0">
		          <tr>
		            <td width="18"></td>
		            <td></td>
		            <td width="10" align="right"></td>
		          </tr>
	        	</table>
	        </td>
		</td>
	</tr>
</table>
<table width="572" height="100%" border="0" cellpadding="0" cellspacing="0" >
	<tr>
		<td>
		<table id="grid" class="scroll" cellpadding="0" cellspacing="0" style="margin-top: 10px;"></table>
		<div id="pager2" class="scroll" style="text-align: center;"></div>
		</td>
	</tr>
</table>
</form>
</div>
</body>
</html>