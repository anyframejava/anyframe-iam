<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.lang.String" %>
<%
String mipid = request.getParameter("mipid");
%>
<HTML>
<HEAD>
<TITLE>MiPlatform Body</TITLE>
<META HTTP-EQUIV="Content-type" CONTENT="text/html;charset=utf-8">
<SCRIPT LANGUAGE="javascript">
<!--
    function fn_run()
    {
      MiPlatformCtrl.Key = "anyframe_demo";
  	  MiPlatformCtrl.DeviceType = "Win32";
      MiPlatformCtrl.VERSION  = "3.2";  
      MiPlatformCtrl.TimeOut = 1800;
      
      MiPlatformCtrl.AutoSize = false;
      MiPlatformCtrl.Retry = 0;
      MiPlatformCtrl.StartXML = "http://" + window.location.host + "<c:url value='/sample/common/sample_ci_main_Win32.xml'/>"
      
      //MiPlatformCtrl.InitUrl = "DefApp::<%=mipid%>";
//      MiPlatformCtrl.SetGlobalVariableValue( "jsessionid", <%=request.getSession().getId()%>);
      MiPlatformCtrl.InitUrl = "<%= mipid %>";
      MiPlatformCtrl.DoRun();
    }

-->  
</SCRIPT>

<SCRIPT LANGUAGE=javascript FOR=MiPlatformCtrl EVENT=LoadCompleted(pDisp,strReqID,strServiceID,strURL)>
	MiPlatformCtrl.SetGlobalVariableValue( "JSESSIONID", "<%=request.getSession().getId()%>"); 
</SCRIPT>	   
                     
</HEAD>

<BODY onload="fn_run()" leftmargin="0" topmargin="0" rightmargin="0">

<object classid="clsid:761C6511-03CE-4B78-ACD8-645CEF3CB713" id="MiPlatformCtrl" width="100%" height="100%" visible="true" >
       <param name="Key" 		value="anyframe_demo">  
       <param name="Version" 	value="3.2">
       <param name="DeviceType"  value="Win32">
</object> 

</BODY>
</HTML>
