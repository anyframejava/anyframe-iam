<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
	<title>IAM Sample Menu</title>
	<link href="<c:url value='/sample/css/left.css'/>" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="<c:url value='/sample/javascript/common.js'/>"></script>
	<script type="text/javascript">
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
<form id="frmMenu">
<table width="159" border="0" cellspacing="0" cellpadding="0">
	<tr><td class="Depth01">Menus</td></tr>
	<!--Users-START-->
	<tr>
		<td valign="top"> 
			<table border="0" cellspacing="0" cellpadding="0" width="159" >
				<tr> 
					<td class="Depth02off"><a href="javascript:toggle(1262671400046)">Users Management</a></td>
				</tr>
			</table>
			<div ID="sub1262671400046" style="DISPLAY: none">
				<table  border="0" cellspacing="0" cellpadding="0" width="159">
					<tr>
						<td class="Depth03"><a href="<c:url value='/users.do?method=list'/>" target="rightFrame">Users List</a></td>
					</tr>
				</table>
			</div>
		</td>
	</tr>
	<!--Users-END-->

	<!--Category-START-->
	<tr>
		<td valign="top"> 
			<table border="0" cellspacing="0" cellpadding="0" width="159" >
				<tr> 
					<td class="Depth02off"><a href="javascript:toggle(1262671587718)">Category Management</a></td>
				</tr>
			</table>
			<div ID="sub1262671587718" style="DISPLAY: none">
				<table  border="0" cellspacing="0" cellpadding="0" width="159">
					<tr>
						<td class="Depth03"><a href="<c:url value='/category.do?method=list'/>" target="rightFrame">Category List</a></td>
					</tr>
				</table>
			</div>
		</td>
	</tr>
	<!--Category-END-->

	<!--Product-START-->
	<tr>
		<td valign="top"> 
			<table border="0" cellspacing="0" cellpadding="0" width="159" >
				<tr> 
					<td class="Depth02off"><a href="javascript:toggle(1262671623437)">Product Management</a></td>
				</tr>
			</table>
			<div ID="sub1262671623437" style="DISPLAY: none">
				<table  border="0" cellspacing="0" cellpadding="0" width="159">
					<tr>
						<td class="Depth03"><a href="<c:url value='/product.do?method=list'/>" target="rightFrame">Product List</a></td>
					</tr>
				</table>
			</div>
		</td>
	</tr>
	<!--Product-END-->
	
	<!--View Management-START-->
	<tr>
		<td valign="top"> 
			<table border="0" cellspacing="0" cellpadding="0" width="159" >
				<tr> 
					<td class="Depth02off"><a href="javascript:toggle(1262671623438)">View</a></td>
				</tr>
			</table>
			<div ID="sub1262671623438" style="DISPLAY: none">
				<table  border="0" cellspacing="0" cellpadding="0" width="159">
					<tr>
						<td class="Depth03"><a href="<c:url value='/viewManagement.do'/>" target="rightFrame">View Management</a></td>
					</tr>
				</table>
			</div>
		</td>
	</tr>
	<!--View Management-END-->
	
	<!--Flex Control-START>
	<tr>
		<td valign="top"> 
			<table border="0" cellspacing="0" cellpadding="0" width="159" >
				<tr> 
					<td class="Depth02off"><a href="javascript:toggle(1262671623439)">Flex Control</a></td>
				</tr>
			</table>
			<div ID="sub1262671623439" style="DISPLAY: none">
				<table  border="0" cellspacing="0" cellpadding="0" width="159">
					<tr>
						<td class="Depth03"><a href="<c:url value='/iam-flex-debug/main.html'/>" target="rightFrame">Flex Control</a></td>
					</tr>
				</table>
			</div>
		</td>
	</tr>
	<Flex Control-END-->
	
	<!-- Add new Menus here -->
</table>
</form>
</body>
</html>
