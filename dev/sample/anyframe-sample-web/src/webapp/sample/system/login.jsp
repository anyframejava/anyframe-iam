<%@ page contentType="text/html; charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.sds.emp.common.EmpUtil" %>
<%@ page import="java.lang.String" %>
<%@ page import="org.springframework.security.ui.AbstractProcessingFilter" %>
<%@ page import="org.springframework.security.AuthenticationException" %>

<html>
<head>
<title>Login</title>
<meta http-equiv="content-type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="<c:url value='/sample/css/admin.css'/>" type="text/css">
<%
	String loflag = EmpUtil.null2str((String)request.getParameter("loflag"));
%>
<script language="javascript" src="<c:url value='/sample/javascript/CommonScript.js'/>"></script>
<script language="JavaScript">
function fncLogin() {
	if(FormValidation(document.loginForm) != false) {
	    document.loginForm.action="<c:url value='/j_spring_security_check'/>";
	    document.loginForm.flag.value = "L";
	    document.loginForm.submit();
    }
}

function fncAddUserView() {
    document.loginForm.action="<c:url value='/empadduser.do'/>";
    document.loginForm.flag.value = "N";
    document.loginForm.submit();
}
</script>

</head>

<body bgcolor="#ffffff" text="#000000" >

<form name="loginForm"  action="<c:url value='/j_spring_security_check'/>">
<div align="center">
<TABLE WITH="100%" HEIGHT="100%" BORDER="0" CELLPADDING="0" CELLSPACING="0">
<TR>
<TD ALIGN="CENTER" VALIGN="MIDDLE">

<!--************************** begin of contents *****************************-->

<table width="650" height="390" border="5" cellpadding="0" cellspacing="0" bordercolor="#D6CDB7">
  <tr> 
    <td width="10" height="5" align="left" valign="top" bordercolor="#D6CDB7"><table width="650" height="390" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="305">
            <img src="<c:url value='/sample/images/login.gif'/>" width="305" height="390">
          </td>
          <td width="345" align="left" valign="top" background="<c:url value='/sample/images/login02.gif'/>"><table width="100%" height="220" border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td width="30" height="100">&nbsp;</td>
                <td width="100" height="100">&nbsp;</td>
                <td height="100">&nbsp;</td>
                <td width="20" height="100">&nbsp;</td>
              </tr>
              <tr> 
                <td width="30" height="50">&nbsp;</td>
                <td width="100" height="50"><img src="<c:url value='/sample/images/text_login.gif'/>" width="91" height="32"></td>
                <td height="50">&nbsp;</td>
                <td width="20" height="50">&nbsp;</td>
              </tr>
              <tr> 
                <td width="200" height="50" colspan="4">
								    <c:if test="${not empty param.login_error}">
								      <font color="red">
								        Your login attempt was not successful, try again.<BR><BR>
								        Reason: <%= ((AuthenticationException) session.getAttribute(AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY)).getMessage() %>
								      </font>
								    </c:if>	                
                </td>
              </tr>              
              <tr> 
                <td width="30" height="30">&nbsp;</td>
                <td width="100" height="30"><img src="<c:url value='/sample/images/text_id.gif'/>" width="100" height="30"></td>
                <td height="30">
                  <input type='text' name='j_username' <c:if test="${not empty param.login_error}">value='<c:out value="${SPRING_SECURITY_LAST_USERNAME}"/>'</c:if>
                                       								class='ct_input_g' style='width:180px; height:19px' required fieldTitle='ID' maxLength='50'>          
                  </input>                                       												
          			</td>
                <td width="20" height="30">&nbsp;</td>
              </tr>
              <tr> 
                <td width="30" height="30">&nbsp;</td>
                <td width="100" height="30"><img src="<c:url value='/sample/images/text_pas.gif'/>" width="100" height="30"></td>
                <td height="30">                    
                    <input type="password" name="j_password" class="ct_input_g" style="width:180px; height:19px" required fieldTitle="Password" maxLength="50" onKeyPress="if(event.keyCode==13) fncLogin();">                                        
                </td>
                <td width="20" height="30">&nbsp;</td>
              </tr>
              <tr> 
                <td width="30" height="20">&nbsp;</td>
                <td width="100" height="20">&nbsp;</td>
                <td height="20" align="center">
      				    <!--버튼들어가는 테이블-->
      				    <table width="136" height="20" border="0" cellpadding="0" cellspacing="0">
                          <tr> 
                            <td width="56"><a href="javascript:fncLogin();"><img src="<c:url value='/sample/images/btn_login.gif'/>" width="56" height="20" border="0"></a></td>
                            <td width="10">&nbsp;</td>
                            <td width="70"><a href="javascript:fncAddUserView();"><img src="<c:url value='/sample/images/btn_add.gif'/>" width="70" height="20" border="0"></a></td>
                          </tr>
                        </table></td>
                      <td width="20" height="20">&nbsp;</td>
                    </tr>
                  </table>
                </td>
              </tr>                            
      </table>
      </td>
  </tr>
</table>
</TD></TR>
  
</TABLE>
</div>

<!-- end of title -->
<input type="hidden" name="flag">
</form>
<script language="JavaScript">
document.loginForm.j_username.focus();
</script>
</body>
</html>
