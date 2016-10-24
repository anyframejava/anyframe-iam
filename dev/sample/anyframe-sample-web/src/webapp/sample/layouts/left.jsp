<%@ page contentType="text/html; charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>eMarketPlace Menu</title>
<link href="<c:url value='/sample/css/left.css'/>" rel="stylesheet" type="text/css">
<script language="javascript" src="<c:url value='/sample/javascript/common.js'/>"></script>
<script ID="clientEventHandlersJS" LANGUAGE="javascript">
<!--
function window_onload(){

}

// -->
</script>
<style type="text/css">
 .Depth01 { 
		font-size: 14px; 
	color: #ffffff;
	font-weight:bold;
	line-height: 26px;
	text-indent: 15px;
	background-image: url(<c:url value='/sample/images/left/leftMenuDep01Bg.gif'/>);
	background-repeat:no-repeat;
   	}
 
	.Depth02on { 
	font-size: 12px; 
	line-height: 25px;
	color: #505050;
	font-weight:normal;
	text-indent: 19px;
	background-image: url(<c:url value='/sample/images/left/leftMenuDep02_on.gif'/>);
	background-repeat:no-repeat;
  	}
  
	.Depth02off { 
	 font-size: 12px; 
	line-height: 25px;
	color: #505050;
	font-weight:normal;
	text-indent: 19px;
	background-image: url(<c:url value='/sample/images/left/leftMenuDep02_off.gif'/>);
	background-repeat:no-repeat;
  	}

	.Depth03 { 
	font-size: 12px; 
	line-height: 20px;
	color: #505050;
	font-weight:normal;
	text-indent: 14px;
	background-image: url(<c:url value='/sample/images/left/leftMenuDep03Bg.gif'/>);
	background-repeat:no-repeat;
 	}

	.DepthEnd {
background-image: url(<c:url value='/sample/images/left/space_line.gif'/>);
line-height: 5px;
background-repeat:no-repeat;
	}
	
.layer_submenu_bg {
	background-image:  url(<c:url value='/sample/images/left/menuLayerBg.gif'/>);
	background-position: right;
	background-repeat: repeat;
	text-align: center;
</style>

</head>
<body background="<c:url value='/sample/images/left/imgLeftBg.gif'/>" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  onload="return window_onload()">
<Form id="frmMenu">
<table width="159" border="0" cellspacing="0" cellpadding="0">
<tr>
 <td class="Depth01" >eMarketPlace</td>
</tr>

<!--menu 01 line-->
<tr>
<td valign="top"> 
	<table id=Main1 border="0" cellspacing="0" cellpadding="0"  width="159" >
		<tr> 
		<td id="menu1" class="Depth02off"><a href="javascript:toggle(1);" id="plus">User Management</a></td>
		</tr>
	</table>
	
	<div ID="sub1" style="DISPLAY: none">
		<table  border="0" cellspacing="0" cellpadding="0" width="159" >	
		<tr>
		<tr>
		<td class="Depth03"><a href="<c:url value='/empupdateuser.do'/>" target="rightFrame">Personal Info. Mng.</a></td>
		<!-- DispatchAction Test -->
		<!-- <td class="Depth03"><a href="<c:url value='/empuser.do?act=getUser'/>" target="rightFrame">Personal Info. Mng.</a></td> -->
		</tr>
		<tr>
		<td class="Depth03" ><a href="<c:url value='/emplistuser.do'/>" target="rightFrame">User Mng.</a></td>
		<!-- DispatchAction Test -->
		<!-- <td class="Depth03" ><a href="<c:url value='/empuser.do?act=getUserList'/>" target="rightFrame">User Mng.</a></td> -->
		</tr>
		<tr>
		<td class="DepthEnd">&nbsp;</td>
		</tr>
		</table>
	</div>
</td></tr>

<!--menu 02 line-->
<tr><td valign="top"> 
	<table id=Main2  border="0" cellspacing="0" cellpadding="0" width="159" >
		<tr> 
		<td id="menu2" class="Depth02off"><a href="javascript:toggle(2)">Sales Management</a></td>
		</tr>
	</table>
	
	<div ID="sub2" style="DISPLAY: none">
		<table  border="0" cellspacing="0" cellpadding="0" width="159">
		<tr>
		<td class="Depth03"><a href="<c:url value='/empaddproduct.do'/>" target="rightFrame">Regist Product</a></td>
		</tr>
		<tr>
		<td class="Depth03"><a href="<c:url value='/emplistsale.do'/>" target="rightFrame">Sales Mng.</a></td>
		</tr>
		<tr>
		<td class="Depth03"><a href="<c:url value='/empListCategory.do'/>" target="rightFrame">Category Mng.</a></td>
		</tr>
		<tr>
		<td class="DepthEnd">&nbsp;</td>
		</tr>
		</table>
	</div>
</td></tr>

<!--menu 03 line-->
<tr><td valign="top"> 
	<table id=Main3  border="0" cellspacing="0" cellpadding="0" width="159" >
		<tr> 
		<td id="menu3" class="Depth02off"><a href="javascript:toggle(3)">Purchase Management</a></td>
		</tr>
	</table>
	
	<div ID="sub3" style="DISPLAY: none">
		<table  border="0" cellspacing="0" cellpadding="0" width="159">
		<tr>
		<td class="Depth03"><a href="<c:url value='/emplistproductsearch.do'/>" target="rightFrame">Search Product</a></td>
		</tr>
		<tr>
		<td class="Depth03"><a href="<c:url value='/emplistpurchase.do'/>" target="rightFrame">Purchase Mng.</a></td>
		</tr>
		<!-- tr>
		<td class="Depth03"><a href="<c:url value='/emplistdeliverycompany.do'/>" target="rightFrame">Delivery Company Mng.</a></td>
		</tr-->
		<tr>
		<td class="DepthEnd">&nbsp;</td>
		</tr>
		</table>
	</div>
</td></tr>

</table>
</Form>
</body>
</html>
