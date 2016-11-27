<%@page import="com.gene.utils.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	这是文件管理的入口，点击之后进入文件管理
	<br>


	<%
		User user = new User();
		user.setId(1);
		user.setUserName("user_1");
		user.setFolder("F:\\xeqFileTest\\user_1\\");
		session.setAttribute("user", user);
		session.setAttribute("userId", 1);
	%>
	<a href="folderlist.action">点击进入文件管理</a>
	<br>
	<a href="pageList.action?pageTag=1">分页测试</a>
	<br>

</body>
</html>