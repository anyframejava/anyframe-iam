<%@ taglib uri='http://www.sds.samsung.com/tags' prefix='anyframe' %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><anyframe:message code="resource.ui.title.resourcedetail" /></title>

<script type="text/javascript" src="<c:url value='/js/CommonScript.js'/>"></script>

<jsp:include page="/common/jstree-include.jsp" />
<jsp:include page="/common/jqueryui-include.jsp" />
<jsp:include page="/common/jqgrid-include.jsp" />
<jsp:include page="/common/jquery-autocomplete-include.jsp" />

<script type="text/javascript" src="<c:url value='/validator.do'/>"></script>
<validator:javascript formName="securedResources" staticJavascript="false" xhtml="true" cdata="false"/>

<script type="text/javascript">
<!--
jQuery(document).ready( function(){

	// button click event
	$('[name=addResource]').click( function(){
		if(!validateSecuredResources(document.resources)){
			return;
		}
	    document.resources.action="<c:url value='/resources/add.do?'/>";
	    document.resources.submit();
	});
	$('[name=updateResource]').click( function(){
		if(!validateSecuredResources(document.resources)){
			return;
		}
	    document.resources.action="<c:url value='/resources/update.do?'/>";
	    document.resources.submit();
	});
	$('[name=deleteResource]').click( function(){
		if(confirm('<anyframe:message code="resource.ui.alert.confirmtodelete" />')) {
		    document.resources.action="<c:url value='/resources/deleteFromDetail.do?'/>";
		    document.resources.submit();
		}
	});
	$('[name=movetoback]').click( function(){
		location.href="<c:url value='/resources/list.do?'/>";
	});
});

// resource Type 변경 시 UI 변경 이벤트
function changeType(frm){
	var patternArea = document.getElementById("patternArea");
	var inputArea = document.getElementById("inputArea");
	patternArea.removeChild(inputArea);

	// Method 로 변경 될 경우
	if(frm.value == "method"){
		var ihtml = "<div id='inputArea'>";
		ihtml += "<input id='methodPackage' size='10' onblur='completePattern(this);' class='ct_input_g'></input>&nbsp;.&nbsp;";
		ihtml += "<input id='methodClass' size='10' onblur='completePattern(this);' class='ct_input_g' onfocus='isFilled(this);' onkeydown='searchWithParam(this);'></input>&nbsp;.&nbsp;";
		ihtml += "<input id='methodMethod' size='10' onblur='completePattern(this);' class='ct_input_g' onfocus='isFilled(this);' onkeydown='searchWithParam(this);'></input>";
		ihtml += "</div>";	

		patternArea.innerHTML = ihtml;

		$("#methodPackage").autocomplete(
				"<c:url value='/candidateSecuredResources/getPackagesList.do' />", {
					width : 200,
					selectFirst:true,
					mustMatch:true,
					autoFill:true,
					scroll: true
				}
		);

		$("#methodClass").autocomplete(
				"<c:url value='/candidateSecuredResources/getClassesList.do' />", {
					width : 200,
					selectFirst:true,
					mustMatch:true,
					autoFill:true,
					scroll: true
				}
		);

		$("#methodMethod").autocomplete(
				"<c:url value='/candidateSecuredResources/getMethodList.do' />", {
					width : 200,
					selectFirst:true,
					mustMatch:true,
					autoFill:true,
					scroll: true
				}
		);
		$("#methodPackage").focus();
	} 

	// URL로 변경 시 
	else if(frm.value =="url"){
		var ihtml = "<div id='inputArea'>";
		ihtml += "<input id='requestMapping' size='20' onblur='completePattern(this);' class='ct_input_g' value='/'>&nbsp;?&nbsp;";
		ihtml += "<input id='urlPattern_param' size='20' onblur='completePattern(this);' class='ct_input_g' onfocus='isFilled(this);' onkeydown='searchWithParam(this);'>";
		ihtml += "</div>";	

		patternArea.innerHTML = ihtml;

		$("#requestMapping").autocomplete(
				"<c:url value='/candidateSecuredResources/getUrlMappingList.do' />", {
					width : 200,
					selectFirst:true,
					mustMatch:true,
					autoFill:true,
					scroll: true
				}
		);

		$("#urlPattern_param").autocomplete(
				"<c:url value='/candidateSecuredResources/getParameterList.do' />", {
					width : 200,
					selectFirst:true,
					mustMatch:true,
					autoFill:true,
					scroll: true
				}
		);

		$("#requestMapping").focus();	
	} 

	// Point Cut 으로 변경 시
	else if(frm.value=="pointcut"){
		var ihtml = "<div id='inputArea'>";
		ihtml += "<input id='pointCut' size='40' onblur='completePattern(this);' class='ct_input_g'></input>";
		ihtml += "</div>";	

		patternArea.innerHTML = ihtml;

		$("#pointCut").autocomplete(
				"<c:url value='/candidateSecuredResources/getPointCutList.do' />", {
					width : 500,
					selectFirst:true,
					mustMatch:true,
					autoFill:true,
					scroll: true
				}
		);
		$("#pointCut").focus();
	}

	var resoucePattern = document.resources.resourcePattern;
	resourcePattern.value = "";
}

//self input ckeck box를 이용한 resource pattern read only 제어
function  changeReadOnly(frm){
	var resourcePattern = document.resources.resourcePattern;
	if(frm.checked){
		resourcePattern.readOnly=false;
		resourcePattern.focus();
	}else
		resourcePattern.readOnly=true;
}

//자동완성 기능 이용시 package 나 class를 먼저 작성 후 method를 검색 하도록 유도
//마찬가지로 URL 에서도 주소를 먼저 완성 후 parameter 검색 하도록 focus 유도
function isFilled(frm){
	if(frm == document.getElementById('urlPattern_param')){
		var requestMapping = document.getElementById('requestMapping');
		if(requestMapping.value == "" || requestMapping.value == null){
			requestMapping.focus();
		}
	} else if(frm == document.getElementById('methodMethod')){
		var methodClass = document.getElementById('methodClass');
		if(methodClass.value == "" || methodClass.value == null){
			methodClass.focus();
		}
	} else if(frm == document.getElementById('methodClass')){
		var methodPackage = document.getElementById('methodPackage');
		if(methodPackage.value == "" || methodPackage.value == null){
			methodPackage.focus();
		}
	}
}

// package 검색 이후 method 검색 시 package 명을 추가 검색 조건으로 부여하는 이벤트
// 마찬가지로 requestMapping 검색 이후 parameter 검색 시 추가 조건 부여
function searchWithParam(frm){
	if(frm == document.getElementById('methodClass')){
		var packages = document.getElementById('methodPackage');
		$("#methodClass").setOptions({
			extraParams:{packages:packages.value}
		});
	}else if(frm == document.getElementById('methodMethod')){
		var packages = document.getElementById('methodPackage');
		var classes = document.getElementById('methodClass');
		$("#methodMethod").setOptions({
			extraParams:{packages:packages.value, classes:classes.value}
		});
	}else if(frm == document.getElementById('urlPattern_param')){
		var requestMapping = document.getElementById('requestMapping');
		$("#urlPattern_param").setOptions({
			extraParams:{requestMapping:requestMapping.value}
		});
	}
}

// input box 가 focus 를 잃었을 때 patten field 를 완성 시키는 event
function  completePattern(frm){
	var resourcePattern = document.resources.resourcePattern;

	// Package . class . method 의 형태로 만들어준다.
	// class 나 method 가 공란일 경우 append 하지 않는다. 
	if(frm == document.getElementById('methodPackage')
			&& document.getElementById('methodPackage').value != ""){
		resourcePattern.value = frm.value;
		if(document.getElementById('methodClass').value != ""){
			resourcePattern.value += "." + document.getElementById('methodClass').value;
			if(document.getElementById('methodMethod').value != ""){
				resourcePattern.value += "." + document.getElementById('methodMethod').value;
			}
		}
	} else if(frm == document.getElementById('methodClass') 
			&& document.getElementById('methodClass').value != ""){
		resourcePattern.value = document.getElementById('methodPackage').value
		 + "." +frm.value;
		if(document.getElementById('methodMethod').value != ""){
			resourcePattern.value += "." + document.getElementById('methodMethod').value;
		}
	} else if(frm == document.getElementById('methodMethod')
			&& document.getElementById('methodMethod').value != ""){
		resourcePattern.value = document.getElementById('methodPackage').value
		 + "." + document.getElementById('methodClass').value + "." + frm.value;
	}

	// execute(*    pattern  )  의 형태로 만들어준다.
	else if(frm == document.getElementById('pointCut')
			&& document.getElementById('pointCut').value != ""){
		resourcePattern.value = "execution(* " + frm.value + " )";
	}

	// \A  URL_PATTERN  ?  PARAMETER  .*\Z 의 형태로 만들어준다.
	else if(frm == document.getElementById('requestMapping')
			&& document.getElementById('requestMapping').value != ""){

		var changedURL = "";
		var url = frm.value;
		for(i = 0 ; i < url.length ; i++){
			if("." == url.charAt(i)){
				changedURL += "\\.";
			} else{
				changedURL += url.charAt(i);
			}
		}
		resourcePattern.value = "\\A" + changedURL;
		
		if(document.getElementById('urlPattern_param').value != ""){

			var beanid = document.resources.beanid.value;
			var parameter = document.getElementById('urlPattern_param').value;
			

			resourcePattern.value += "\\?.*" + beanid + "=" + parameter;
		}
		resourcePattern.value += ".*\\Z";
	} else if(frm == document.getElementById('urlPattern_param')
			&& document.getElementById('urlPattern_param').value != ""){

		var beanid = document.resources.beanid.value;
		var url = document.getElementById('requestMapping').value;
		var changedURL = "";
		for(i = 0 ; i < url.length ; i++){
			if("." == url.charAt(i)){
				changedURL += "\\.";
			} else{
				changedURL += url.charAt(i);
			}
		}

		resourcePattern.value = "\\A" + changedURL + "\\?.*" + beanid + "=" + frm.value + ".*\\Z";
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

<body text="#000000">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td width="10" rowspan="3">&nbsp;</td>
    <td height="6"></td>
  </tr>
  <tr>
    <td height="30" valign="bottom" background="<c:url value='/images/content/bg_tab.gif'/>" style="padding-left:10px">
      <table height="24" border="0" cellpadding="0" cellspacing="0">
              <tr height="21">
                <td width="145" height="27" align="center" valign="bottom"  background="<c:url value='/images/content/tab_menu1.gif'/>" bgcolor="#EDEDED" class="blkbold">Resource List </td>
              </tr>
    </table></td>
  </tr>
  <tr>
    <td>
	<!--  Begin of Contents -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 13px; margin-left:10px">
	<!-- begin of title -->
	<tr>
		<td valign="top">
	    	<table width="796" border="0" cellspacing="0" cellpadding="0">
				<tr>	
				    <td class="title" style="padding-left:21px">
				    	<c:if test="${empty resources.resourceId}">
							<anyframe:message code="resource.ui.title.resourceadd" />
							<c:set var="readonly" value="false" />
						</c:if>
						<c:if test="${not empty resources.resourceId}">	
							<anyframe:message code="resource.ui.title.resourceupdate" />
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
	    <td style="padding-top: 5px;">
			<form:form commandName="resources" method="post" action="resources.do" id="resources" name="resources">
			<table width="796" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" >
				<tr><td height="2" colspan="3" bgcolor="#A2BACE"></td></tr>

				<c:if test="${not empty resources.resourceId}">
				<tr>
					<td width="150" class="tdHead"><anyframe:message code="resource.ui.label.resourceid" /></td>
					<td bgcolor="#B6CDE4" width="1"></td>
					<td class="tdin">
						<form:input path="resourceId" id="resourceId" cssClass="ct_input_g" cssErrorClass="text medium error" readonly="true" maxlength="10"/>&nbsp;<form:errors path="resourceId" cssClass="error" />
					</td>
				</tr>		
				<tr><td height="1" colspan="3" bgcolor="#D6D6D6"></td></tr>
				</c:if>
				<tr>
					<td class="tdHead">
					  <anyframe:message code="resource.ui.label.resourcename" /></td>
					<td bgcolor="#B6CDE4" width="1"></td>
					<td class="tdin">									        
			        	<form:input path="resourceName" id="resourceName" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="50"/>&nbsp;<form:errors path="resourceName" cssClass="error" /></td>
				</tr>
				<tr><td height="1" colspan="3" bgcolor="#D6D6D6"></td></tr>
				<tr>
					<td class="tdHead">
						<anyframe:message code="resource.ui.label.resourcetype" /></td>
					<td bgcolor="#B6CDE4" width="1"></td>
					<td class="tdin">									        
			        <form:select path="resourceType" id="resourceType" cssClass="ct_input_g" onchange="javascript:changeType(this);">
			        	<form:option value="url"><anyframe:message code="resource.ui.selectbox.resourcetype.url" /></form:option>
			        	<form:option value="method"><anyframe:message code="resource.ui.selectbox.resourcetype.method" /></form:option>
			        	<form:option value="pointcut"><anyframe:message code="resource.ui.selectbox.resourcetype.pointcut" /></form:option>
			        </form:select><form:errors path="resourceType" /></td>
				</tr>
				<tr><td height="1" colspan="3" bgcolor="#D6D6D6"></td></tr>
				<tr>
					<td class="tdHead">
						<anyframe:message code="resource.ui.label.resourcepattern" /></td>
					<td bgcolor="#B6CDE4" width="1"></td>

					<td class="tdin" id="patternArea">
					<input type="hidden" value="<c:out value='${beanid }' />" name="beanid">								        
					<!-- resource information 수정 시 resourceType 에 따른 UI 변경 -->
					<c:if test="${resources.resourceType == 'url' || resources.resourceType == null || resources.resourceType == ''}">	
						<div id="inputArea">
							<input id="requestMapping" size="20" onblur="completePattern(this);" class='ct_input_g' value="/">&nbsp;?&nbsp;
							<input id="urlPattern_param" size="20" onfocus="isFilled(this);" class='ct_input_g' onkeydown="searchWithParam(this);" onblur="completePattern(this);">
							<script type="text/javascript">
								$("#requestMapping").autocomplete(
										"<c:url value='/candidateSecuredResources/getUrlMappingList.do' />", {
											width : 200,
											selectFirst:true,
											mustMatch:true,
											autoFill:true,
											scroll: true
										}
								);
			
								$("#urlPattern_param").autocomplete(
										"<c:url value='/candidateSecuredResources/getParameterList.do' />", {
											width : 200,
											selectFirst:true,
											mustMatch:true,
											autoFill:true,
											scroll: true
										}
								);
							</script>
						</div>		
					</c:if>
		
					<c:if test="${resources.resourceType == 'method'}">	
						<div id='inputArea'>
							<input id='methodPackage' size='10' onblur="completePattern(this);" class='ct_input_g'></input>&nbsp;.&nbsp;
							<input id='methodClass' size='10' onblur="completePattern(this);" class='ct_input_g' onfocus='isFilled(this);' onkeydown="searchWithParam(this);"></input>&nbsp;.&nbsp;
							<input id='methodMethod' size='10' onblur="completePattern(this);" class='ct_input_g' onfocus='isFilled(this);' onkeydown="searchWithParam(this);" onblur="completePattern(this);"></input>
							<script type="text/javascript">
								$("#methodPackage").autocomplete(
										"<c:url value='/candidateSecuredResources/getPackagesList.do' />", {
											width : 200,
											selectFirst:true,
											mustMatch:true,
											autoFill:true,
											scroll: true
										}
								);
			
								$("#methodClass").autocomplete(
										"<c:url value='/candidateSecuredResources/getClassesList.do' />", {
											width : 200,
											selectFirst:true,
											mustMatch:true,
											autoFill:true,
											scroll: true
										}
								);
			
								$("#methodMethod").autocomplete(
										"<c:url value='/candidateSecuredResources/getMethodList.do' />", {
											width : 200,
											selectFirst:true,
											mustMatch:true,
											autoFill:true,
											scroll: true
										}
								);
							</script>
						</div>		
					</c:if>
		
					<c:if test="${resources.resourceType == 'pointcut'}">	
						<div id='inputArea'>
							<input id='pointCut' size='40' onblur="completePattern(this);" class='ct_input_g'></input>
							<script type="text/javascript">
								$("#pointCut").autocomplete(
										"<c:url value='/candidateSecuredResources/getPointCutList.do' />", {
											width : 500,
											selectFirst:true,
											mustMatch:true,
											autoFill:true,
											scroll: true
										}
								);
							</script>
						</div>	
					</c:if>
					</td>
				</tr>
				<tr><td height="1" colspan="3" bgcolor="#D6D6D6"></td></tr>
				<tr>
					<!-- td tag is omitted by upper td's rowspan -->
					<td class="tdHead">
						<anyframe:message code="resource.ui.label.resourcepattern" /> Detail</td>
					<td bgcolor="#B6CDE4" width="1"></td>
					<td class="tdin">									        
						<form:input path="resourcePattern" id="resourcePattern" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="300" size="40" readonly="true" />&nbsp;<form:errors path="resourcePattern" cssClass="error" />
						<input type="checkbox" name="isSelfInput" onchange="changeReadOnly(this);" "${checked }"><anyframe:message code="resource.ui.selectbox.selfinput" />
					</td>
				</tr>
				<tr><td height="1" colspan="3" bgcolor="#D6D6D6"></td></tr>
				<tr>
					<td class="tdHead">
						<anyframe:message code="resource.ui.label.description" /></td>
					<td bgcolor="#B6CDE4" width="1"></td>
					<td class="tdin">									        
			        <form:input path="description" id="description" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="100" size="61" />&nbsp;<form:errors path="description" cssClass="error" /></td>
				</tr>
				<tr><td height="1" colspan="3" bgcolor="#D6D6D6"></td></tr>
				<tr>
					<td class="tdHead">
						<anyframe:message code="resource.ui.label.sortorder" /></td>
					<td bgcolor="#B6CDE4" width="1"></td>
					<td class="tdin">									        
				        <form:input path="sortOrder" id="description" cssClass="ct_input_g" cssErrorClass="text medium error" maxlength="5" size="10"
				        onkeypress="if(event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;"  />&nbsp;<form:errors path="sortOrder" cssClass="error" />
					</td>
				</tr>
				<tr><td height="1" colspan="3" bgcolor="#B6CDE4"></td></tr>	
			</table>
			</form:form>
		</td>
	</tr>
	<!-- End of Input Field -->
	
	<!-- Begin Button Field -->
	<tr>
	    <td valign="top">
			<table width="796" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" style="margin-top:10px;">
				<tr>
					<td align="right">
						<table width="100" height="22" border="0" cellpadding="0" cellspacing="0">
							<tr>
							<c:if test="${empty resources.resourceId}">		
								<td width="82%" align="right" class="tdBtnRight">	
									<table height="22" border="0" cellpadding="0" cellspacing="0">
			              				<tr>
			                				<td width="18"><img src="<c:url value='/images/btn/btn_add.gif'/>" width="18" height="22"></td>
			                				<td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="addResource"><fmt:message key="button.add"/></a></td>
			                				<td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
			              				</tr>
			           				 </table>
		           				  </td></c:if>
								<c:if test="${not empty resources.resourceId}">
								<td width="6%" align="right" class="tdBtnRight">
									<table height="22" border="0" cellpadding="0" cellspacing="0">
			              				<tr>
							                <td width="18"><img src="<c:url value='/images/btn/update.gif'/>" width="18" height="22"></td>
							                <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="updateResource"><fmt:message key="button.update"/></a></td>
							                <td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
							            </tr>
			            			</table>
			            			</td></c:if>	
								<c:if test="${not empty resources.resourceId}">
								<td width="6%" align="right"  class="tdBtnRight">
									<table height="22" border="0" cellpadding="0" cellspacing="0">
								          <tr>
								            <td width="18"><img src="<c:url value='/images/btn/btn_delete.gif'/>" width="18" height="22"></td>
								            <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="deleteResource"><fmt:message key="button.delete"/></a></td>
								            <td width="10" align="right"><img src="<c:url value='/images/btn/btn_tailb.gif'/>" width="10" height="22"></td>
								          </tr>
				        			</table>
				        			</td></c:if>	
							  	<td width="6%" align="right"  class="tdBtnRight">
									<table height="22" border="0" cellpadding="0" cellspacing="0">
							          <tr>
							            <td width="18"><img src="<c:url value='/images/btn/btn_cancel.gif'/>" width="18" height="22"></td>
							            <td background="<c:url value='/images/btn/bg_btn.gif'/>" class="boldBtn"><a href="#" name="movetoback"><fmt:message key="button.cancel"/></a></td>
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
	<!-- End of Button Field -->
</table></td>
  </tr>
  <tr>
    <td colspan="2">
      <table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#E9ECF1">
        <tr>
          <td height="10" bgcolor="#ffffff"></td>
      </tr>
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