<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.ssh.xep.entity.JobInfo"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet"
	href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<body>
	<div class="container">

		<div class="row clearfix">
			<div class="col-md-12 column">查询条件</div>
		</div>
		<table class="table table-hover">
			<tr>
				<th>id</th>
				<th>bgTime</th>
				<th>edTime</th>
				<th>userId</th>
				<th>status</th>
				<th></th>
			</tr>
			<s:iterator value="#session.jList" status="JobInfo">
				<tr>
					<td>${id}</td>
					<td>${bgTime}</td>
					<td>${edTime}</td>
					<td>${userId}</td>
					<td>${status}</td>
					<td>
					<a type="button" class="btn btn-xs btn-info" onclick="javascript:window.location.href='jobInfo.action?id=${id}'">
					<span class="glyphicon glyphicon-search"></span> Info</a>
					</td>
				</tr>
			</s:iterator>
		</table>
	</div>
</body>
</html>