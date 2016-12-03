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
<title>Folder And File List</title>
  <!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.6/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.6/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.6/themes/fgr.css">
	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.6/themes/bootstrap/pagination.css">
	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.6/demo/demo.css">
 	<script type="text/javascript" src="jquery-easyui-1.3.6/jquery.min.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.3.6/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="jsFileManager/folder.js"></script>
</head>
<div class="container">
	<s:actionerror/>
	<!-- 初始化页面列表结束 -->
	<!-- 导航按钮开始-->
	<nav class="navbar navbar-default" role="navigation">
	
	<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
	<ul class="nav navbar-nav">		
    <ol class="breadcrumb">
    <li><span class="glyphicon glyphicon-folder-open"></span></li>
    <s:iterator value="#session.folderStack" status="FileAndFolder">
    <li>${name}</li>
    </s:iterator>
    </ol>
    </ul>
	<ul class="nav navbar-nav navbar-right">
     <li><a href="#modal-container-create" class="btn" data-toggle="modal">
         <span class="glyphicon glyphicon-plus-sign"></span> Create
     </a>
     </li>
     <li>
	<a href="#modal-container-upload" class="btn" data-toggle="modal">
         <span class="glyphicon glyphicon-cloud-upload"></span> Upload
    </a>	
	</li>
	 <li>
	<a href="#"
	   onclick="javascript:window.location.href='backStack.action'" class="btn">
	 <span class="glyphicon glyphicon-circle-arrow-left"></span> Back
	</a>
     </li>
	</ul>
	</div>
    </nav>
    <!-- 导航按钮结束 -->
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
		</tr>
		<s:iterator value="#session.fileList" status="FileAndFolder">
			<s:if test="type=='folder'">
			      <tr>
					<td onclick="return into(${id})">
					 <span class="icon tree-folder"></span>
					</td> 
					<td onclick="return into(${id})">${id}</td>
					<td onclick="return into(${id})">${name}</td>
					<td onclick="return into(${id})"><s:date name="time" format="yyyy-MM-dd" /></td>
					<td onclick="return into(${id})">${type}</td>
					<td onclick="return into(${id})">${size}</td>
					<td onclick="return into(${id})">${folderPath}</td>
					<td >
			        <div class="btn-group">
	                 <a class="btn btn-info dropdown-toggle btn-xs" data-toggle="dropdown">
	                 <span class="glyphicon  glyphicon-pencil"></span>
	                 edit <span class="caret"></span></a>
	                  <ul class="dropdown-menu" role="menu"  style="min-width: 100%;">
		                 <li>
		                 <a href="deleteDir.action?id=${id}&folderPath=${folderPath}&parentFolderId=${parentFolderId}&pagesource.currentPage=${pagesource.currentPage}"
					     onclick="return del();">
					     <span class="glyphicon glyphicon-trash"></span>delete
					     </a>
		                 </li>
		                 <li>
		                 <a href="#modal-container-move" data-toggle="modal" onclick="prom(${id})" >
                            <span class="glyphicon glyphicon-move"></span>move
                         </a>
		              </ul>
                    </div>
				    </td>
				</tr>
				</s:if>
			 </s:iterator>
			<s:iterator value="#session.fileList" status="FileAndFolder">
			   <s:if test="type!='folder'">
				<tr>
				    <td> <span class="icon tree-file"></span></td>
			        <td>${id}</td>
					<td>${name}</td>
					<td><s:date name="time" format="yyyy-MM-dd" /></td>
					<td>${type}</td>
					<td>${size}</td>
					<td>${folderPath}</td>
			        <td>
			        <!-- 按钮组开始 -->
			        <div class="btn-group">
	                 <a class="btn btn-info dropdown-toggle btn-xs" data-toggle="dropdown">
	                    <span class="glyphicon glyphicon-pencil"></span>
	                    edit <span class="caret"></span></a>
	                  <ul class="dropdown-menu" role="menu" style="min-width: 100%;" >
		                 <li>
		                    <a href="delete.action?&id=${id}&folderPath=${folderPath}&name=${name}&type=${type}&parentFolderId=${parentFolderId}&pagesource.currentPage=${pagesource.currentPage}" 
					            onclick="if(confirm(' ARE YOU SURE DELETE THE FILE?')==false)return false;" >
					        <span class="glyphicon glyphicon-trash"></span>delete
					        </a>
		                 </li>
		                 <li><a href="download.action?folderPath=${folderPath}&name=${name}$type=${type}&downfileName=${name}${type}">
		                     <span class="glyphicon  glyphicon-save"></span>download
                             </a></li>
		                 <li>
		                 <a href="#modal-container-move" data-toggle="modal" onclick="prom(${id})" >
                            <span class="glyphicon glyphicon-move"></span>move
                         </a>	
		              </ul>                                                                                                                                                                                                                                 
                    </div>
                    <!-- 按钮组结束 -->
			       </td>
		    	</tr>	
    	      </s:if>	
			</s:iterator>	
	</table>
	<!-- 文件列表结束 -->
   <!-- 分页效果开始 --> 
   <div style="margin:20px 0;"></div>
   <div class="easyui-panel"   style=" height: 50px;padding:10px 20px 10px 40px;">
	<a class="easyui-linkbutton" data-options="plain:true,iconCls:'pagination-first'" 
	   onclick="javascript:window.location.href='pageList.action?&parentFolderId=${parentFolderId}&pagesource.currentPage=1'"></a>
	<a class="easyui-linkbutton" data-options="plain:true,iconCls:'pagination-prev'" 
	   onclick="javascript:window.location.href='pageList.action?&parentFolderId=${parentFolderId}&pagesource.currentPage=${pagesource.currentPage-1 }'"></a>
       [ ${pagesource.currentPage} of ${pagesource.totalPages }] 
	<a class="easyui-linkbutton" data-options="plain:true,iconCls:'pagination-next'" 
	   onclick="javascript:window.location.href='pageList.action?&parentFolderId=${parentFolderId}&pagesource.currentPage=${pagesource.currentPage+1 }'"></a>
	<a class="easyui-linkbutton" data-options="plain:true,iconCls:'pagination-last'" 
	   onclick="javascript:window.location.href='pageList.action?&parentFolderId=${parentFolderId}&pagesource.currentPage=${pagesource.totalPages }'"></a>
		[total:${pagesource.totalRows}]
	</div>
   <!-- 分页效果结束 -->
   
</div>   
   <!-- 弹出框开始 -->
   <!-- 移动文件开始 ,文件可移动到任意文件夹-->
   	    <div class="modal fade" id="modal-container-move" role="dialog" aria-labelledby="myModalLabel"
        aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  ×
                </button>
                 <h4 class="modal-title" id="myModalLabel">
                <a href="#" class="btn" >
                <span class="glyphicon glyphicon-plus" >
                </span>Move To A Folder...
                </a>
                </h4> 
              </div>
            <div class="modal-body" style="height:250px">
             <div class="form-group">
             <s:form action="moveAction" id="movef"  method="post" role="form" style="padding:10px 20px 10px 80px;" >
             <select id="testTree" style="width:300px" class="form-control"></select>
             <input type="hidden" name="fromId" id="fromId" value=""/>
             <input type="hidden" name="pagesource.currentPage" value="${pagesource.currentPage}"/>
             <input type="hidden" name="parentFolderId" value="${session.parentId }"/>
             <input type="hidden" name="toId" id="toPathId" value="">
            </s:form>
            </div>  
           </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">
                  cancel
                </button>
                <button type="button" class="btn btn-primary" onclick="javascript:$('form#movef').submit()">
                  submit
                </button>
              </div>
            </div>
          </div>
        </div>
    <!-- 移动文件结束 -->
	
	<!-- 上传文件窗口 -->
	 <div class="modal fade" id="modal-container-upload" role="dialog" aria-labelledby="myModalLabel"
        aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  ×
                </button>
                <h5 class="modal-title" id="myModalLabel">
                 <a href="#" class="btn " onclick="addMore()">
                 <span class="glyphicon glyphicon-plus" >
                </span>Please Add A File...
                </a>
                </h5>
              </div>
              <div class="modal-body">
                <s:form action="fileUpload" id="addFiles"  method ="post"
                style="padding:10px 20px 10px 80px;" enctype="multipart/form-data">
                <s:token />
		        <s:fielderror/>
		        <input type="hidden" name="parentFolderId" value=${session.parentId }>
		        <input type="hidden" name="folderPath" value=${session.parentPath }>
		        <input type="hidden" name="pagesource.currentPage" value="${pagesource.currentPage}">
		        <div id="more"></div>
	            </s:form>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">
                  cancel
                </button>
                <button type="button" class="btn btn-primary" onclick="javascript:$('form#addFiles').submit()">
                  Upload
                </button>
              </div>
            </div>
          </div>
        </div>
    <!-- 上传文件窗口结束 -->

	<!-- 创建文件夹窗口 -->
	    <div class="modal fade" id="modal-container-create" role="dialog" aria-labelledby="myModalLabel"
        aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  ×
                </button>
                <h4 class="modal-title" id="myModalLabel">
                <a href="#" class="btn" >
                <span class="glyphicon glyphicon-plus" >
                </span>Create A Folder...
                </a>
                </h4>
              </div>
              <div class="modal-body">
                <s:form action="addFolder" id="addF" style="padding:10px 20px 10px 40px;"
                method="post">
                  <s:token />
                  <s:fielderror />
                  <input type="hidden" name="parentFolderId" value=${session.parentId }>
                  <input type="hidden" name="folderPath" value=${session.parentPath }>
                  <input type="hidden" name="pagesource.currentPage" value="${pagesource.currentPage}">
                  <s:fielderror name="name" />
                   <div class="input-group">
                   <span class="input-group-addon">
                   <span class="glyphicon glyphicon-folder-close"></span>
                   </span>
                  <input name="name" id="name" type="text" class="form-control" id="firstname"
                  placeholder="Please enter a folder name..." required>
                  </div>
                  <br>
                </s:form>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">
                  cancel
                </button>
                <button type="button" class="btn btn-primary" onclick="javascript:$('form#addF').submit()">
                  submit
                </button>
              </div>
            </div>
          </div>
        </div>
   
   <!-- 创建文件夹窗口结束 -->
   <script type="text/javascript">
   var data1=<%=session.getAttribute("folderJson")%>;
   $("#testTree").combotree({
	 valueField: 'id',
     textField: 'text',                                  
     data : data1,
     required:true,
     onSelect:function(node){
    	 document.getElementById("toPathId").value = node.id;
    	 //alert(node.id);
    }
 });
   </script>
   
</body>
</html>