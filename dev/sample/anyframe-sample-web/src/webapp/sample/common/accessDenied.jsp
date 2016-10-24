<%@ page import="org.springframework.security.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.Authentication" %>
<%@ page import="org.springframework.security.ui.AccessDeniedHandlerImpl" %> 

<%@ page isErrorPage="true"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ page import="com.sds.emp.common.EmpUtil" %>
<%@ page import="java.lang.String" %>
<%
  	boolean authenticateFail = false;
  	if(request.getAttribute("authenticateFail")!=null && !request.getAttribute("authenticateFail").toString().equals("")){
		authenticateFail = true;
  	}
  
  	boolean authFail = false;
  	if(request.getAttribute("authFail")!=null && !request.getAttribute("authFail").toString().equals("")){
		authFail = true;
  	}  
  	
  	String target = EmpUtil.null2str(request.getAttribute("target"));
  	target = target.equals("") ? "_top" : target;  	
%>
<html>
<head>
<title>Error</title>
<link rel="stylesheet" href="<c:url value='/sample/css/admin.css'/>" type="text/css">
</head>
<script language="javascript">
function fncGoAfterErrorPage(){
	if('<%=authenticateFail%>' == 'true' ){
		document.dummyForm.target="_top";
		document.dummyForm.action = "<c:url value='/empaftererrorpage.do'/>";
		document.dummyForm.submit();
	}else if('<%=authFail%>' == 'true'){
		document.dummyForm.target="<%=target%>";
		document.dummyForm.action = "<c:url value='/empaftererrorpage.do'/>";
		document.dummyForm.submit();
	}else{
		//document.location.href = "<c:url value='/empaftererrorpage.do'/>";
		history.back(-2);
	}
}
</script>
<body bgcolor="#ffffff" text="#000000">
<!-- 타이틀 시작 -->
<table width="100%" height="37" border="0" cellpadding="0"
	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="<c:url value='/sample/images/ct_ttl_img01.gif'/>" width="15" height="37"></td>
		<td background="<c:url value='/sample/images/ct_ttl_img02.gif'/>" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">Ocurred Error!!!</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="<c:url value='/sample/images/ct_ttl_img03.gif'/>" width="12" height="37"></td>
	</tr>
</table>
<!-- 타이틀 끝 -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
	<tr>
		<td width="747">
			<table width="370" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="5">
						<img src="<c:url value='/sample/images/empty.gif'/>" width="1" height="120">
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td align="center">
			<table width="375" height="153" border="0" cellpadding="0" cellspacing="0" background="<c:url value='/sample/images/fail.jpg'/>">
				<tr>
					<td height="153">
						<table width="375" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="26">&nbsp;</td>
								<td width="132">									
									<%= request.getAttribute(AccessDeniedHandlerImpl.SPRING_SECURITY_ACCESS_DENIED_EXCEPTION_KEY)%>
									<%		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
											if (auth != null) { %>
									<%      } %>								
								</td>
								<td width="20">&nbsp;</td>
								<td>
                                	<table border="0" cellspacing="0" cellpadding="0">
                                    	<tr> 
											<td width="17" height="23"><img src="<c:url value='/sample/images/ct_btnbg01.gif'/>" width="17" height="23"></td>
											<td background="<c:url value='/sample/images/ct_btnbg02.gif'/>" class="ct_btn01" style="padding-top:3px;"><a href="javascript:fncGoAfterErrorPage();">확인</a></td>
											<td width="14" height="23"><img src="<c:url value='/sample/images/ct_btnbg03.gif'/>" width="14" height="23"></td>
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
	<tr>
		<td align="center"></td>
	</tr>
</table>
<form name="dummyForm"></form> 
</body>
</html>