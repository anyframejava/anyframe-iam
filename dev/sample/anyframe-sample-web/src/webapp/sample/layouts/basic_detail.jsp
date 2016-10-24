<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.lang.String" %>    
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<% String webContextPath = getServletConfig().getServletContext().getRealPath("/"); 
%>
<head>
<title>untitled document</title>
<meta http-equiv="content-type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/<%=webContextPath%>/sample/css/admin.css" type="text/css">

<script language="javascript" src="/<%=webContextPath%>/sample/javascript/calendar.js"></script>
<script language="javascript" src="/<%=webContextPath%>/sample/javascript/PortalCommonScript.js"></script>
<script language="javaScript">
<!--

//--> 
</script>


</head>
<body bgcolor="#ffffff" text="#000000">

<form name="detailForm"  method="post">

<!--************************** Begin of Contents *****************************-->

<!-- Begin of Title -->
<table width="100%" height="37" border="0" cellpadding="0"
	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/<%=webContextPath%>/sample/images/ct_ttl_img01.gif" width="15" height="37"></td>
		<td background="/<%=webContextPath%>/sample/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="93%" class="ct_ttl01">Add Production</td>
				<td width="20%" align="right">&nbsp;</td>
			</tr>
		</table>
		</td>
		<td width="12" height="37"><img src="/<%=webContextPath%>/sample/images/ct_ttl_img03.gif" width="12" height="37"></td>
	</tr>
</table>
<!-- End of Title -->

<!-- Begin of Table -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:13px;">
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">Product Name <img src="/<%=webContextPath%>/sample/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"></td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01"><input type="text" name="Input3" class="ct_input_g" style="width:370px; height:19px" value="Samaung Notebook SENS X15-JUMP2" ></td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">Category <img src="/<%=webContextPath%>/sample/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"></td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td height="55" class="ct_write01">
			<!-- Begin of Table -->
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="26">
						<select name="select6" class="ct_input_g" style="width:200px">
							<option selected>--Classification in Large</option>
						</select>
						<select name="select6" class="ct_input_g" style="width:200px">
							<option selected>--Classification in Small</option>
						</select>
					</td>
					<td style="padding-left:5px;">
						<input type="checkbox" name="checkbox2" value="checkbox">
					</td>
					<td tyle="padding-top:3px;">
						<font color="0E5880">need check item</font>
					</td>
				</tr>
				<tr>
					<td height="22" colspan="3">
						<font color="969696">(※ You can define a description of item in here.)</font>
					</td>
				</tr>
			</table>
			<!-- End of Table -->
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">Seller <img src="/<%=webContextPath%>/sample/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"></td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<!-- Start Table -->
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="105">
						<input type="text" name="Input22" class="ct_input_g" style="width:100px; height:19px">
					</td>
					<td>
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="4" height="21"><img src="/<%=webContextPath%>/sample/images/ct_btng01.gif" width="4" height="21"></td>
								<td align="center" background="/<%=webContextPath%>/sample/images/ct_btng02.gif" class="ct_btn" style="padding-top:3px;"><a href="#">Search Seller ID</a></td>
								<td width="4" height="21"><img src="/<%=webContextPath%>/sample/images/ct_btng03.gif" width="4" height="21"></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<!-- End of Table -->
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">Selling Date</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<!-- Start Table -->
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="100%">
						<input type="text" name="startdate" class="ct_input_g" style="width:75px; height:19px">
						<a href="javascript:show_calendar('document.detailForm.startdate', document.detailForm.startdate.value)"><img src="/images/ct_icon_date.gif" width="16" height="18" border="0" align="absmiddle"></a>
						~
						<input type="text" name="enddate" class="ct_input_g" style="width:75px; height:19px">
						<a href="javascript:show_calendar('document.detailForm.enddate', document.detailForm.enddate.value)"><img src="/images/ct_icon_date.gif" width="16" height="18" border="0" align="absmiddle"></a>
					</td>
				</tr>
			</table>
			<!-- End of Table -->
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">Receipt</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<select name="select5" class="ct_input_g" style="width:100px">
				<option selected>Possible</option>
				<option>Impossible</option>
			</select>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">A/S</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<select name="select5" class="ct_input_g" style="width:100px">
				<option selected>Possible</option>
				<option>Impossible</option>
			</select>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">Quantity <img src="/<%=webContextPath%>/sample/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"></td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<input type="text" name="Input2222" class="ct_input_g" style="width:120px; height:19px; text-align:right" value="1">
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">Price <img src="/<%=webContextPath%>/sample/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"></td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			Rs. <input type="text" name="Input2222" class="ct_input_g" style="width:120px; height:19px; text-align:right" value="500000">
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">Detail Information</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<textarea name="Text3333" class="ct_input_g" cols="80" rows="4" ></textarea>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
</table>
<!-- End of Table -->

<!-- Begin of Table -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	style="margin-top:10px;">
	<tr>
		<td width="53%">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23"><img src="/<%=webContextPath%>/sample/images/ct_btnbg01.gif" width="17" height="23"></td>
					<td background="/<%=webContextPath%>/sample/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;"><a href="#">List</a></td>
					<td width="14" height="23"><img src="/<%=webContextPath%>/sample/images/ct_btnbg03.gif" width="14" height="23"></td>
				</tr>
			</table>
		</td>
		<td align="right">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23"><img src="/<%=webContextPath%>/sample/images/ct_btnbg01.gif" width="17" height="23"></td>
					<td background="/<%=webContextPath%>/sample/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;"><a href="#">Save</a></td>
					<td width="14" height="23"><img src="/<%=webContextPath%>/sample/images/ct_btnbg03.gif" width="14" height="23"></td>
					<td width="17" height="23"><img src="/<%=webContextPath%>/sample/images/ct_btnbg01.gif" width="17" height="23"></td>
					<td background="/<%=webContextPath%>/sample/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;"><a href="#">Cancel</a></td>
					<td width="14" height="23"><img src="/<%=webContextPath%>/sample/images/ct_btnbg03.gif" width="14" height="23"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<!-- End of Button-->

</form>

</body>
</html>
