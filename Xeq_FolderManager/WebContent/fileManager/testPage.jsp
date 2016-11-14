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
<title>Insert title here</title>
</head>
<body>
	<table align="center" border="1" width="50%">
		<tr>
			<th>id</th>
			<th>name</th>
			<th>updateTime</th>
			<th>type</th>
			<th>size</th>
			<th>folderPath</th>
		</tr>
		<s:iterator value="#session.fileList" status="FileAndFolder">
		<tr>	
		    <td>${id}</td>
			<td>${name}</td>
			<td><s:date name="time"	format="yyyy-MM-dd" /></td>
			<td>${type}</td>
			<td>${size}</td>
			<td>${folderPath}</td>
			</tr>
		</s:iterator>
		  
		<tr>
		<td>每页显示：<input name="pagesource.pagesize"   value="${pagesource.pageSize}" >
        </td>
        <td>当前页：  <input name="pagesource.currentPage"   value="${pagesource.currentPage}" >  </td>
		<td colspan="4">
		<a	href="pageList.action?pageTag=1&parentFolderId=${parentFolderId}&pagesource.currentPage=${pagesource.currentPage-1 }">上一页</a>
				[每页显示：${pagesource.pageSize}条] [ 第${pagesource.currentPage}页 /共${pagesource.totalPages } 页] 
	    <a href="pageList.action?pageTag=1&parentFolderId=${parentFolderId}&pagesource.currentPage=${pagesource.currentPage+1 }">下一页</a>
	    </td>
		</tr>
	</table>
</body>
</html>