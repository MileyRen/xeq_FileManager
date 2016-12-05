<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.ssh.xep.entity.JobInfo"%>
<%@page import="com.xeq.file.domain.JobStep"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet"
	href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<title>JobStep</title>
</head>
<body>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">

				<nav class="navbar navbar-default" role="navigation">
				<div class="container-fluid">
					<div class="navbar-header">
						<a class="navbar-brand" href="#">JOB:${jobStep.id}</a>
					</div>
					<div>
						<ul class="nav navbar-nav">
							<li><a>bgTime:${jobStep.bgTime}</a></li>
							<li><a>enTime:${jobStep.edTime}</a></li>
							<li><a>flowBasicInfoId:${flowBasicInfoId}</a></li>
							<li><a>status:${jobStep.status}</a></li>
							<li><a>user:${jobStep.userId}</a></li>
						</ul>
						<ul class="nav navbar-nav navbar-right">
							<li><a href="jobsList.action">BACK</a></li>
						</ul>
					</div>
				</div>
				</nav>
				<div class="row clearfix">
					<div class="col-md-12 column">
						<table class="table table-condensed">
							<tr>
								<th>id</th>
								<th>name</th>
								<th>bgTime</th>
								<th>edTime</th>
								<th>state</th>
							</tr>
							<s:iterator value="#session.processInfo" status="JobStep">
								<tr class="${css}">
									<td>${id}</td>
									<td>${name}</td>
									<td>${bgTime}</td>
									<td>${edTime}</td>
									<td><span class="label label-${label}">${state}</span></td>
								</tr>
							</s:iterator>
						</table>
					</div>
				</div>

			</div>
		</div>
	</div>
</body>
</html>