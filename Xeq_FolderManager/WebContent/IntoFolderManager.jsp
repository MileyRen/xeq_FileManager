<%@page import="com.gene.utils.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<head>
<link rel="stylesheet"
	href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<br>
	<%
		User user = new User();
		user.setId(1);
		user.setUserName("user_1");
		user.setFolder("D:\\xeptest\\user_1\\");
		session.setAttribute("user", user);
		session.setAttribute("userId", 1);
		session.setAttribute("basepath", basePath);
	%>
	<br> &nbsp;
	<a class="btn btn-info" href="pageList.action?pageTag=1">文件管理页面</a>
	<a class="btn btn-info" href="jobsList.action">作业状态查询</a>
	<br>
</body>
</html>