<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="java.net.URLDecoder" %>
<%@page import="com.xeq.file.domain.FileAndFolder"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Folder_List</title>
  
  <!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>

	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.6/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.6/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.6/themes/bootstrap/pagination.css">
	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.6/demo/demo.css">
 	<script type="text/javascript" src="jquery-easyui-1.3.6/jquery.min.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.3.6/jquery.easyui.min.js"></script>
<script type="text/javascript" src="jsFileManager/folder.js"></script>
<script type="text/javascript">
</script>
</head>
<%-- <%
	//初始化页面
	Integer id = (Integer) request.getSession().getAttribute("parentId");
	if (id == null || id.equals("")) {
		session.setAttribute("userId", 1);
		response.sendRedirect("folderlist");
		return;
	}
%> --%>
<body>
<!-- 文件列表开始 -->
<div >

    <s:actionerror/>
	<!-- 初始化页面列表结束 -->
	<div class="easyui-panel" style="padding: 10px;">
	<a href= "backStack.action" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-back'"
	   onclick="javascript:window.location.href='backStack.action'">
		Back
	</a>
	<a href="#" id="btncreate" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">
		Create Folder
	</a>
	
	<a href="#" id="btnupload" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-upload'">
		Upload
	</a>
</div>
<table class="table table-hover">
		<tr>
		<th></th>
		<th>id</th>
			<th>name</th>
			<th>updateTime</th>
			<th>type</th>
			<th>size</th>
			<th>folderPath</th>
			<th></th>
			<th></th>
		</tr>
		<s:iterator value="#session.faflists" status="FileAndFolder">
			<s:if test="type=='folder'">
			      <tr>
					<td onclick="return post(${id})">
					 <img class="icon tree-folder"/>
					</td>
					<td onclick="return post(${id})">${id}</td>
					<td onclick="return post(${id})">${name}</td>
					<td onclick="return post(${id})"><s:date name="time" format="yyyy-MM-dd" /></td>
					<td onclick="return post(${id})">${type}</td>
					<td onclick="return post(${id})">${size}</td>
					<td onclick="return post(${id})">${folderPath}</td>
					<td onclick="return post(${id})">
			        <a href="deleteDir.action?id=${id}&folderPath=${folderPath}&parentFolderId=${parentFolderId}"
					 onclick="return del();"
					 >
				    	<img src="jquery-easyui-1.3.6/themes/icons/no.png">
					 </a>
				    </td>
				    <td>
				    </td>
				</tr>
				</s:if>
				<s:else>
				<tr>
				<td> <img class="icon tree-file"/></td>
					<td>${id}</td>
					<td>${name}</td>
					<td><s:date name="time" format="yyyy-MM-dd" /></td>
					<td>${type}</td>
					<td>${size}</td>
					<td>${folderPath}</td>
					<td>
					<a href="delete.action?id=${id}&folderPath=${folderPath}&name=${name}&type=${type}&parentFolderId=${parentFolderId}" 
					onclick="if(confirm(' ARE YOU SURE DELETE THE FILE?')==false)return false;" >
					    <img src="jquery-easyui-1.3.6/themes/icons/no.png">
					</a>
					</td>
				    <td>
	                <a href="download.action?folderPath=${folderPath}&name=${name}$type=${type}&downfileName=${name}${type}">
                     <img src="jquery-easyui-1.3.6/themes/icons/download.png">
                    </a>
				     </td>
		    	</tr>	
    	   </s:else>
	   	</s:iterator>
	</table>
  </div>
  <!-- 文件列表结束 -->
  <!-- 分页效果开始 --> 
   <div style="margin:20px 0;"></div>
   <div class="easyui-panel"   style=" height: 50px;padding:10px 20px 10px 40px;""  >
	<select name="Pagesize" required>
	   <option value ="one" selected="selected">10</option>
       <option value ="two">20</option>
       <option value="th">50</option>
	</select>
	<a class="easyui-linkbutton" data-options="plain:true,iconCls:'pagination-first'"></a>
	<a class="easyui-linkbutton" data-options="plain:true,iconCls:'pagination-prev'" ></a>
	
	<input name="pageNum" value="1" style="width:50px" >
	
	<a class="easyui-linkbutton" data-options="plain:true,iconCls:'pagination-next'" ></a>
	<a class="easyui-linkbutton" data-options="plain:true,iconCls:'pagination-last'" ></a>
	</div>
<!-- 分页效果结束 -->
	
	
	
	
	
	<!-- 下载文件夹失败 -->
	<div id="win_downFolder" class="easyui-window" title="TIP" 
	     data-options="modal:true,closed:true,iconCls:'icon-tip'"
         style="width: 250px; height: 100px;" closable="true" closed="true">
         YOU CAN'T DOWNLOAD A FOLDER!
         <br><br>
		<center><a href="#" class="easyui-linkbutton" icon="icon-ok" onclick="javascript:$('#win_downFolder').window('close')">
			OK
		</a>
		</center>
   </div>
	
	<!-- 上传文件窗口 -->
    <div id="win_upload" class="easyui-window" title="Upload New File"
         data-options="modal:true,closed:true,iconCls:'icon-upload'"
         style="width: 600px; height: 350px;" closable="true" closed="true">
    <div id="tools-bar">
    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addMore()">Add</a>
    <a class="easyui-linkbutton" icon="icon-upload" plain="true"  
       onclick="javascript:$('form#addFiles').submit()" >
	   Upload
	</a>
    </div>    
     <s:form action="fileUpload" id="addFiles"  method ="post"
           style="padding:10px 20px 10px 80px;" enctype="multipart/form-data">
        <s:token />
		<s:fielderror/>
		<input type="hidden" name="parentFolderId" value=${session.parentId }>
		<input type="hidden" name="folderPath" value=${session.parentPath }>
		
		<div id="more"></div>
	</s:form>
		
    </div>
    <!-- 上传文件窗口结束 -->

	<!-- 创建文件夹窗口 -->
	<div id="win_add" class="easyui-window" title="Create New Folder" 
	     data-options="modal:true,closed:true,iconCls:'icon-add'"
         style="width: 350px; height: 150px;" closable="true" closed="true">
	<s:form action="addFolder" id="addF" style="padding:10px 20px 10px 40px;" method ="post">
		<s:token />
		<s:fielderror/>
		<input type="hidden" name="parentFolderId" value=${session.parentId }>
		<input type="hidden" name="folderPath" value=${session.parentPath }>
		<s:fielderror name="name"/>
		<input name="name" id="name" type="text" class="form-control" id="firstname" 
				   placeholder="Please enter a folder name..." required>
		<br>
		<a class="easyui-linkbutton" icon="icon-add" onclick="javascript:$('form#addF').submit()">
			Submit
		</a>
		&nbsp;&nbsp;
		<a href="#" class="easyui-linkbutton" icon="icon-cancel" onclick="javascript:$('#win_add').window('close')">
			Cancel
		</a>
	</s:form>
   </div>
   <!-- 创建文件夹窗口结束 -->
</body>
</html>