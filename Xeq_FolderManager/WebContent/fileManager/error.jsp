<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.xeq.file.domain.FileAndFolder"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Folder And File List</title>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.6/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.6/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.6/themes/fgr.css">
	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.6/themes/bootstrap/pagination.css">
	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.6/demo/demo.css">
 	<script type="text/javascript" src="jquery-easyui-1.3.6/jquery.min.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.3.6/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="jsFileManager/folder.js"></script>
</head>
<body>
	error!
	<div class="container">

		<input id="lefile" type="file" style="display: none">
		<div class="input-append">
			<a class="btn" onclick="$('input[id=lefile]').click();"><span
				class="glyphicon glyphicon-folder-open"></span> Browse</a><input
				id="photoCover" class="input-large" type="text"
				style="height: 30px;" readOnly="true">
		</div>
		<script type="text/javascript">
			$('input[id=lefile]').change(function() {
				$('#photoCover').val($(this).val());
			});
		</script>
	</div>
	   <select id="cc" class="easyui-combotree" style="width: 200px;"
		data-options="url:'tree_data1.json',required:true">
	</select>
</body>

</html>