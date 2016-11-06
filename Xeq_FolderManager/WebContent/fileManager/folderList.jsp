<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
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
  
	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.6/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.6/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.6/demo/demo.css">
 	<script type="text/javascript" src="jquery-easyui-1.3.6/jquery.min.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.3.6/jquery.easyui.min.js"></script>
<script type="text/javascript" src="jsFileManager/folder.js"></script>
<script type="text/javascript">
</script>
</head>
<%
	//初始化页面
	Integer id = (Integer) request.getSession().getAttribute("parentId");
	if (id == null || id.equals("")) {
		session.setAttribute("userId", 1);
		response.sendRedirect("folderlist");
		return;
	}
%>
<body>
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

<table>
		<tr>
		<th></th>
			<th>name</th>
			<th>updateTime</th>
			<th>type</th>
			<th>size</th>
			<th>folderPath</th>
		</tr>
		<s:iterator value="#session.faflists" status="FileAndFolder">
			<s:if test="type=='folder'">
			    <tr onclick="javascript:window.location.href='folderlist.action?parentFolderId=${id}'">
					<td><a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-folder'"/></td>
					<td>${name}</td>
					<td><s:date name="time" format="yyyy-MM-dd" /></td>
					<td>${type}</td>
					<td>${size}</td>
					<td>${folderPath}</td>
					<td><a href="#" onclick="javascript:$('#win_deleteFolder').window('open')" 
					       class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-no'"></a>
				    </td>
				    <td><a href="#"  onclick="javascript:$('#win_downFolder').window('open')" 
				           id="btndown" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-download'">
				        </a>
				    </td>
			 <div style="display:none;width:0px;height:0px;position:absolute">
              <!-- 删除文件夹 -->
              <form action="deleteDir.action" id="deleteFolder" method="post">
                  <input type="text" name="id" value="${id }">
                  <input type="text" name="folderPath" value="${folderPath}">
                  <input type="text" name="parentFolderId" value="${parentFolderId }"></i>
              </form>
              </div>
				</tr>
				</s:if>
				<s:else>
				<tr>
				<td><a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-file'"/></td>
					<td>${name}</td>
					<td><s:date name="time" format="yyyy-MM-dd" /></td>
					<td>${type}</td>
					<td>${size}</td>
					<td>${folderPath}</td>
					<td><a href="#" onclick="javascript:$('#win_deleteFile').window('open')" 
					       class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-no'"></a>
					</td>
				    <td>
				        <a href="#" 
				           onclick="javascript:$('#downF').submit()" 
				           id="btndown" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-download'"></a>
				    </td>
		    	</tr>	
			  <div style="display:none;width:0px;height:0px;position:absolute">
			   <!-- 下载文件 -->
			   <form action="download.action" id="downF"  method ="post">
                   <input type="text" name="folderPath" value="${folderPath}">
		       <br><input type="text" name="name" value="${name}">
		       <br><input type="text" name="type" value="${type}">
		       <br><input type="text" name="downfileName" value="${name}${type}">
              </form>
              <!-- 删除文件 -->
              <form action="delete.action" id="deleteF" method="post">
                  <input type="text" name="id" value="${id }">
                  <input type="text" name="folderPath" value="${folderPath}">
                  <input type="text" name="name" value="${name}">
                  <input type="text" name="type" value="${type}">
                  <input type="text" name="parentFolderId" value="${parentFolderId }"></i>
              </form>
              </div>
    	   </s:else>
	   	</s:iterator>
	</table>
    <!-- 删除文件夹确认 -->
    <div id="win_deleteFolder" class="easyui-window" title="TIP" 
	     data-options="modal:true,closed:true,iconCls:'icon-tip'"
         style="width: 450px; height: 100px;" closable="true" closed="true">
         ARE YOU SURE DELETE THE FOLDER AND FILES IN THE FOLDER?
         <br><br>
		<center>
		<a href="#" class="easyui-linkbutton" icon="icon-ok" onclick="javascript:$('#deleteFolder').submit()">
			Yes
		</a>
		<a href="#" class="easyui-linkbutton" icon="icon-cancel" onclick="javascript:$('#win_deleteFolder').window('close')">
			No
		</a>
		</center>
   </div>
   
    <!-- 删除文件确认 -->
    <div id="win_deleteFile" class="easyui-window" title="TIP" 
	     data-options="modal:true,closed:true,iconCls:'icon-tip'"
         style="width: 250px; height: 100px;" closable="true" closed="true">
         ARE YOU SURE DELETE THE FILE?
         <br><br>
		<center><a href="#" class="easyui-linkbutton" icon="icon-ok" onclick="javascript:$('#deleteF').submit()">
			Yes
		</a>
		<a href="#" class="easyui-linkbutton" icon="icon-cancel" onclick="javascript:$('#win_deleteFile').window('close')">
			No
		</a>
		</center>
   </div>
    <!-- 删除文件或文件夹确认 -->
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
		
		<%-- 
		<s:file name="uploadFiles" label="select file"/>
		<s:file name="uploadFiles" label="select file"/> 
		--%>
		<div id="more"></div>
	</s:form>
		<!-- <div id="uploadBtn" style="display:none">
		<br>
		<a class="easyui-linkbutton" icon="icon-add" onclick="javascript:$('form#addFiles').submit()" >
			Submit
		</a>
		&nbsp;&nbsp;
		<a href="#" class="easyui-linkbutton" icon="icon-cancel" onclick="javascript:$('#win_upload').window('close')">
			Cancel
		</a>
		</div> -->
		
    </div>
    <!-- 上传文件窗口结束 -->

	<!-- 创建文件夹窗口 -->
	<div id="win_add" class="easyui-window" title="Create New Folder" 
	     data-options="modal:true,closed:true,iconCls:'icon-add'"
         style="width: 400px; height: 150px;" closable="true" closed="true">
	<s:form action="addFolder" id="addF" style="padding:10px 20px 10px 80px;" method ="post">
		<s:token />
		<s:fielderror/>
		<input type="hidden" name="parentFolderId" value=${session.parentId }>
		<input type="hidden" name="folderPath" value=${session.parentPath }>
		Folder Name:
		<input name="name" id="name" type="text" required>
		<br>
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