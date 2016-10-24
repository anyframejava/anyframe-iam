<%@ page contentType="text/html; charset=euc-kr" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<html>
<head>
<title>untitled document</title>
<meta http-equiv="content-type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="<c:url value='/sample/css/admin.css'/>" type="text/css">

<script language="JavaScript">
<!--

//-->
</script>


</head>

<body bgcolor="#ffffff" text="#000000">

<form name="listForm"  method="post">

<table width="100%" height="37" border="0" cellpadding="0"
	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="<c:url value='/sample/images/ct_ttl_img01.gif'/>" width="15" height="37"></td>
		<td background="<c:url value='/sample/images/ct_ttl_img02.gif'/>" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">Sales Management</td>
					<td width="20%" align="right"><b>1</b>/5Pages</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="<c:url value='/sample/images/ct_ttl_img03.gif'/>" width="12" height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="32" style="padding-left:12px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:3px;">
				<tr>
					<td width="8" style="padding-bottom:3px;">
						<img	src="<c:url value='/sample/images/ct_bot_ttl01.gif'/>" width="4" height="7">
					</td>
					<td class="ct_ttl02">Total 1 / Total Price 500,000</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td background="<c:url value='/sample/images/ct_line_ttl.gif'/>" height="1"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="right">
			Registered Date of Product
			<input type="text" name="Input2222" class="ct_input_g" style="width:75px; height:19px">
			<a href="#"><img src="<c:url value='/sample/images/ct_icon_date.gif'/>" width="16" height="18" border="0" align="absmiddle"></a>
			~
			<input type="text" name="Input22222" class="ct_input_g" style="width:75px; height:19px">
			<a href="#"><img src="<c:url value='/sample/images/ct_icon_date.gif'/>" width="16" height="18" border="0" align="absmiddle"></a>
			<select name="select3" class="ct_input_g" style="width:150px">
				<option selected>Status of Sales</option>
			</select>
			<select name="select4" class="ct_input_g" style="width:150px">
				<option selected>Status of Approval</option>
			</select>
		</td>
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23"><img src="<c:url value='/sample/images/ct_btnbg01.gif'/>" width="17" height="23"></td>
					<td background="<c:url value='/sample/images/ct_btnbg02.gif'/>" class="ct_btn01" style="padding-top:3px;">
						<a href="#">Search</a>
					</td>
					<td width="14" height="23"><img src="<c:url value='/sample/images/ct_btnbg03.gif'/>" width="14" height="23"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td class="ct_list_b">Category</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">Product Name</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">Seller</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">Price</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">Registered Date</td>
	</tr>
	<tr>
		<td colspan="17" bgcolor="808285" height="1"></td>
	</tr>
	<tr class="ct_list_pop">
		<td align="center">Computer/Network</td>
		<td></td>
		<td><a href="#">Samsung Notebook SENS X15-JUMP2</a></td>
		<td></td>
		<td align="center"><a href="#">GilDong Hong</a></td>
		<td></td>
		<td align="right" style="padding-right:3px;">500,000</td>
		<td></td>
		<td align="center">2006.12.22</td>
	</tr>
	<tr>
		<td colspan="17" bgcolor="D6D7D6" height="1"></td>
	</tr>
	<tr class="ct_list_pop">
		<td align="center">Computer/Network</td>
		<td></td>
		<td><a href="#">Samsung Notebook SENS X15-JUMP2</a></td>
		<td></td>
		<td align="center"><a href="#">GilDOng Hong</a></td>
		<td></td>
		<td align="right" style="padding-right:3px;">500,000</td>
		<td></td>
		<td align="center">2006.12.22</td>
	</tr>
	<tr>
		<td colspan="17" bgcolor="D6D7D6" height="1"></td>
	</tr>
	<tr class="ct_list_pop">
		<td align="center">Computer/Network</td>
		<td></td>
		<td><a href="#">Samsung Notebook SENS X15-JUMP2</a></td>
		<td></td>
		<td align="center"><a href="#">GilDong Hong</a></td>
		<td></td>
		<td align="right" style="padding-right:3px;">500,000</td>
		<td></td>
		<td align="center">2006.12.22</td>
	</tr>
	<tr>
		<td colspan="17" bgcolor="D6D7D6" height="1"></td>
	</tr>	
</table>
<!-- End of List -->

<!-- Paging-->
<table border="0" cellpadding="0" cellspacing="0" align="center" style="margin-top:12px;">
	<tr>
		<td>
			<img src="<c:url value='/sample/images/ct_btn_pre.gif'/>" height="16" alt="move before 10 pages" hspace="5" border="0">
			<span><img src="<c:url value='/sample/images/ct_btn_pre01.gif'/>" height="16" border="0" hspace="1"></span>
		</td>
		<td style="padding:0 10 1 10">
			<span>1</span>  &#149; 
			<a href="javascript:goPage('2')">2</a>  &#149; 
			<a href="javascript:goPage('3')">3</a>  &#149; 
			<a href="javascript:goPage('4')">4</a>
		</td>
		<td>
			<a href="javascript:goPage('2');"><img src="<c:url value='/sample/images/ct_btn_next01.gif'/>" height="16" border="0" hspace="1"></a>
			<img src="<c:url value='/sample/images/ct_btn_next.gif'/>" height="16" alt="move after 10 pages" border="0" hspace="5">
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"
	style="margin-top:10px;">
	<tr>
		<td align="right">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23"><img src="<c:url value='/sample/images/ct_btnbg01.gif'/>" width="17" height="23"></td>
					<td background="<c:url value='/sample/images/ct_btnbg02.gif'/>" class="ct_btn01" style="padding-top:3px;"><a href="#">Regist</a></td>
					<td width="14" height="23"><img src="<c:url value='/sample/images/ct_btnbg03.gif'/>" width="14" height="23"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>

</form>

</body>
</html>
