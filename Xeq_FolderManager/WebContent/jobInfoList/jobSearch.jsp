<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.ssh.xep.entity.JobInfo"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.xeq.file.domain.JobCss"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<!-- bootstrap date插件 -->
<script type="text/javascript" src="styleRen/bootstrap/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="styleRen/bootstrap/locals/bootstrap-datetimepicker.fr.js"
	charset="UTF-8"></script>
<body>
	<form action="jobsList.action" id="search" class="form-horizontal" role="form" method="post">
		<div class="form-group">
			Job Status:<select required name="JobInfo.status" id="status" style="width: 50px">
				<option value="ALL" selected>ALL</option>
				<option value=" and status = 'stop' ">STOP</option>
				<option value=" and status = 'running' ">RUNNING</option>
				<option value=" and status = 'pending'">PENDING</option>
			</select> Create Time: <input type="checkbox" name="createTime" id="creatTime" value="select">
			<%
				Date date = new Date();
				SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd");
			%>
			From:
			<div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd"
				style="width: 150px; display: inline-table">
				<input class="form-control" size="16" type="text" name="fTime" value="<%=df.format(date)%>"
					readonly style="height: 80%"> <span class="input-group-addon"><span
					class="glyphicon glyphicon-calendar"></span></span>
			</div>
			To:
			<div class="input-group date form_date col-md-5" data-date="" data-date-format="yyyy-mm-dd"
				style="width: 150px; display: inline-table">
				<input class="form-control" size="16" type="text" name="tTime" value="<%=df.format(date)%>"
					readonly style="height: 80%"> <span class="input-group-addon"><span
					class="glyphicon glyphicon-calendar"></span></span>
			</div>
			SortBy: <input type="checkbox" name="sort" value="select"> <select name="sortByTime">
				<option value=" order by bgTime " selected>bgTime</option>
				<option value=" order by edTime ">edTime</option>
			</select> <select name="sortDA">
				<option value=" desc " selected>desc</option>
				<option value=" asc ">asc</option>
			</select> <a class="btn btn-info btn-xs" onclick="javascript:$('form#search').submit()"> <span
				class="glyphicon glyphicon-search"></span>SEARCH
			</a>
		</div>
	</form>
</body>