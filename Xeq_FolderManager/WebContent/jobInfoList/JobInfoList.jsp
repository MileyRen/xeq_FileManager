<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.ssh.xep.entity.JobInfo"%>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="com.xeq.file.domain.JobCss" %>
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
<!-- bootstrap date插件 -->

<script type="text/javascript" src="styleRen/bootstrap/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript"  src="styleRen/bootstrap/locals/bootstrap-datetimepicker.fr.js" charset="UTF-8"></script>

<body>

	<div class="container">
		<div class="page-header">
			<div class="row clearfix">
				<div class="col-md-12 column">
					<form action="" id="searchJobs" class="form-horizontal"  role="form" method="post">
						<div class="form-group">
						Job Status:<select required name="status" style="width:50px">
							<option value="" selected>ALL</option>
							<option value="">SUCCESS</option>
							<option value="">RUN</option>
							<option value="">ERROR</option>
						</select> 
						Create Time:
						<input type="checkbox" name="createTime" value="true">
						
						<% Date date = new Date();
						   SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd"); 
						%>
                       From: <div class="input-group date form_date" data-date="<%=new Date() %>" data-date-format="yyyy-mm-dd" data-link-field="dtp_input1" 
                       data-link-format="yyyy-mm-dd" style="width: 150px ;display:inline-table">
                                 <input class="form-control" size="16" type="text" value="<%=df.format(date) %>" readonly style="height:80%">
					             <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                            </div>
				            <input type="hidden" id="dtp_input1" name="fromTime" value="">
						To:
                            <div class="input-group date form_date col-md-5" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" 
                            data-link-format="yyyy-mm-dd" style="width: 150px;display:inline-table">
                                 <input class="form-control" size="16" type="text" value="<%=df.format(date) %>" readonly style="height:80%">
					             <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                            </div>
				            <input type="hidden" id="dtp_input2" name="toTime"  value="">
						
						SortBy:
						<input type="checkbox" name="sort" value="true">
						<select name="sortByTime">
						   <option value="bgTime" selected>bgTime</option>
						   <option value="edTime">edTime</option>
						</select>
						<select name="sortDA">
							<option value="desc" selected>desc</option>
							<option value="asc">asc</option>
						</select> 
						<a class="btn btn-info btn-xs" onclick="javascript:$('form#searchJobs').submit()"> 
						<span class="glyphicon glyphicon-search"></span>SEARCH</a>
						</div>
					</form>
				</div>
			</div>
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
			<s:iterator value="#session.jcList" status="JobCss">
				<tr>
					<td>${id}</td>
					<td>${bgTime}</td>
					<td>${edTime}</td>
					<td>${userId}</td>
					<td><span class="label label-${label}">${status}</span></td>
					<td><a type="button" class="btn btn-xs btn-info"
						onclick="javascript:window.location.href='jobInfo.action?id=${id}'">
							<span class="glyphicon glyphicon-search"></span> Info
					</a></td>
				</tr>
			</s:iterator>
		</table>
		<!-- 分页效果开始 -->
		<div>
			<ul class="pagination">
				<li><a href="#">&laquo;</a></li>
				<li><a href="#">Prev</a></li>
				<li><a href="#">[page/page] [total:]</a></li>
				<li><a href="#">Next</a></li>
				<li><a href="#">&raquo;</a></li>
			</ul>
		</div>
		<!--分页效果结束 -->
	</div>

<script type="text/javascript">
	$('.form_date').datetimepicker({
        language:  'en',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0
    });
</script>
</body>
</html>