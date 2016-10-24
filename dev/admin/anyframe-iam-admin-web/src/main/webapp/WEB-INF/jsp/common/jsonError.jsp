<%@ page isErrorPage="true"%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*,org.anyframe.iam.admin.common.web.JsonIAMException" %>
<%
	String errMsg = ((java.lang.Exception)request.getAttribute("exception")).getMessage();
	response.setStatus(311);
	response.getOutputStream().print(errMsg);
%>