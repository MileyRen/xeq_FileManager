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
<!--用于初始化页面列表-->
	/*  $(function($) {
	if ($("input#init").val() == "")
	$("form#finit").submit();
	}); */
</script>
</head>
<%
	//初始化
	Integer id = (Integer) request.getSession().getAttribute("parentId");
	if (id == null || id.equals("")) {
		session.setAttribute("userId", 1);
		response.sendRedirect("folderlist");
		return;
	}
%>
<body>
	<%-- 	<!--用于初始化页面列表-->
	<form id="finit" action="folderlist" method="post"
		style="display: none">
		 <input id="init" type="text" value="${initFlag}"> 
		 <input id="init" type="hidden" name="parentFolderId" value="${parentId}"/> 
	</form> --%>
	<!-- 初始化页面列表结束 -->
	<div class="easyui-panel" style="padding: 10px;">
	<a href="backStack.action" id="btnback" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-back'">
		Back
	</a>
	<a href="#" id="btncreate" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">
		Create Folder
	</a>
	<a href="#" id="btndelete" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-no'">
		Delete
	</a>
	<a href="#" id="btndown" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-download'">
		DownLoad
	</a>
	<a href="#" id="btnupload" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-upload'">
		Upload
	</a>
</div>
	<table>
		<tr>
			<th>id</th>
			<th>name</th>
			<th>updateTime</th>
			<th>type</th>
			<th>size</th>
		</tr>
		<s:iterator value="#session.faflists" status="FileAndFolder">
			<tr>
				<s:if test="type=='folder'">
					<td colspan="4"><s:a action="folderlist">
							<s:param name="parentFolderId" value="id" />
							${id }
					${name}
					<s:date name="time" format="yyyy-MM-dd" />
					${type}
					${size}
				</s:a></td>
				</s:if>
				<td colspan="4"><s:else>
				${id }
					${name}
					<s:date name="time" format="yyyy-MM-dd" />
					${type}
					${size}
					</s:else></td>
			</tr>
		</s:iterator>
	</table>
	
	<!-- 上传文件窗口 -->
    <div id="win_upload" class="easyui-window" title="Upload New File"
         data-options="modal:true,closed:true,iconCls:'icon-upload'"
         style="width: 600px; height: 350px;" closable="true" closed="true">
    
    <div id="tools-bar">
    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:alert('Add')">Add</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="javascript:alert('Save')">Save</a>
    </div>    
     <s:form action="fileUpload" id="addFiles"  method ="post"
           style="padding:10px 20px 10px 80px;" enctype="multipart/form-data">
        <s:token />
		<s:fielderror/>
		<input type="hidden" name="parentFolderId" value=${session.parentId }>
		<input type="hidden" name="folderPath" value=${session.parentPath }>
		
		<s:file name="uploadFiles" label="select file"/>
		<s:file name="uploadFiles" label="select file"/>
		
		<a class="easyui-linkbutton" icon="icon-add" onclick="javascript:$('form#addFiles').submit()" >
			Submit
		</a>
		&nbsp;&nbsp;
		<a href="#" class="easyui-linkbutton" icon="icon-cancel" onclick="javascript:$('#win_upload').window('close')">
			Cancel
		</a>
	</s:form>
	
	
		
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
		<%-- <s:submit/> --%>
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