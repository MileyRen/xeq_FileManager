package com.xeq.file.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.xeq.file.domain.FileAndFolder;
import com.xeq.file.domain.PageSource;
import com.xeq.file.service.FolderService;

@Namespace("/")
@ParentPackage("struts-default")
@Controller("f2MgrAction")
@Scope("prototype")
public class f2MgrAction extends ActionSupport implements SessionAware, ModelDriven<FileAndFolder>, Preparable {
	private static final long serialVersionUID = -5640743370611684124L;
	private static Logger logger = Logger.getLogger(f2MgrAction.class);

	@Autowired
	private FolderService folderService;
	@Autowired
	private PageSource pagesource;
	private Integer id;
	private Map<String, Object> session;
	private Integer parentFolderId;
	private String folderPath;// 父文件夹路径，用于新建文件夹时
	private String name;
	private String type;

	// 定义多文件上传的数组
	private List<File> uploadFiles;
	private List<String> uploadFilesFileName;
	private List<String> uploadFilesContentType;
	private String[] targetFileNames;
	private List<String> fileSize;
	private String targetFileDir;
	private int filesCount;
	
	// 下载
	private String downfileName;
	// 分页
	private String pageTag;

	@Action(value = "delete", results = {
			@Result(name = "success", type = "redirect", location = "pageList.action", params = { "parentFolderId",
					"%{parentFolderId}","pagesource.currentPage","%{pagesource.currentPage}"}) })
	public String deleteFile() {
		logger.debug("------删除单个文件-----------\n");
		int userId = (int) session.get("userId");
		int flag = folderService.delete(id, folderPath + name + type);
		logger.info("删除：" + flag + ";删除文件：" + folderPath + name + type);

		return "success";
	}

	@Action(value = "deleteDir", results = {
			@Result(name = "success", type = "redirect", location = "pageList.action", params = { "parentFolderId",
					"%{parentFolderId}" }) })
	public String deleteFolder() {
		logger.debug("------删除文件夹-----------\n");
		int userId = (int) session.get("userId");
		FileAndFolder folder = folderService.getById(id);
		logger.info("删除的文件夹ID="+getId());
		folderService.deleteFolder(folder);

		logger.info("删除文件夹：" + folderPath);

		return "success";
	}

	/***/
	// 直接在类名称的上端写入即可，value中指定要引入的拦截器的名称即可
	@Action(value = "addFolder", results = {
			@Result(name = "createFolder", type = "redirect", location = "pageList.action", params = {
					"parentFolderId", "%{parentFolderId}" }),
			@Result(name = "error", type = "redirect", location = "pageList.action", params = { "parentFolderId",
					"%{parentFolderId}" }) },
			interceptorRefs = { @InterceptorRef(value = "defaultStack"),
							@InterceptorRef(value = "token"), })
	public String createFolder() {
		// 创建文件夹,要判断同级文件夹是否重名，若重名则重新插入
		logger.debug("--------创建文件夹------------");

		int userId = (int) session.get("userId");

		if (name == null || name.equals("")) {
			addFieldError("name", "The name " + name + " is not null.");
			System.out.println("name is not null");
			return "error";
		}
		String str = StringFilter(name);
		if (str.length() > 0) {
			addFieldError("name", "FolderName cant't contions '/','\',';','*','\"','<','>'");
			return "error";
		}

		// 判断name是否已被占用
		List<FileAndFolder> fileAndFolders = folderService.getByFolderOrFiles(userId, parentFolderId);
		for (FileAndFolder fileAndFolder : fileAndFolders) {
			if (name == fileAndFolder.getName() || name.equals(fileAndFolder.getName())) {
				addFieldError("name", "The " + name + " is already exist!");
				System.out.println("The name " + name + " is already exist!");
				return "error";
			}
		}

		FileAndFolder parentObject = folderService.getById(parentFolderId);
		int id = folderService.create(userId, name, parentFolderId, folderPath, parentObject);
		if (id > 0) {
			return "createFolder";
		} else {
			return "error";
		}
	}

	// 过滤特殊字符
	private String StringFilter(String str) throws PatternSyntaxException {
		// 只允许字母和数字 // String regEx ="[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[^?|\\/;*\"<>]+";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();

	}

	// **上传文件,多文件上传*//*
	@Action(value = "fileUpload", results = {
			@Result(name = "success", type = "redirect", 
					location = "pageList.action", 
					params = { 
							"parentFolderId","%{parentFolderId}" 
							,"pagesource.currentPage","%{pagesource.currentPage}"
							}
			),
			@Result(name = "error", type = "redirect", location = "pageList.action", params = { "parentFolderId",
					"%{parentFolderId}" ,"pagesource.currentPage","%{pagesource.currentPage}"}) },
			interceptorRefs = { 
					@InterceptorRef(value = "defaultStack",params={
							"maxinumSize","2048"
					})
				})
	public String uploadFiles() {
		String retu = "success";
		int userId = (int) session.get("userId");
		try {
			// 文件上传到真正的路径，然后在数据库进行映射
			if (uploadFiles != null) {
				// 存到folderPath中
				String serverRealPath = folderPath;// ServletActionContext.getServletContext().getRealPath(folderPath);
				logger.info("---------------servletRealPath=" + serverRealPath + "------------------");
				File dir = new File(serverRealPath);
				if (!dir.exists()) {
					addActionError("Upload Error!");
					retu = "error";
				}
				filesCount = uploadFiles.size();
				if (filesCount == 0) {
					addFieldError("uploadFiles", "Please select a file!");
					return "error";
				}
				uploadFilesContentType = new ArrayList<String>();
				fileSize = new ArrayList<String>();
				targetFileNames = new String[filesCount];

				for (int i = 0; i < filesCount; i++) {
					String fileN = uploadFilesFileName.get(i);
					String tp = fileN.substring(fileN.lastIndexOf("."));
					System.out.println("后缀：" + tp + ";名称：" + fileN + ";大小：" + uploadFiles.get(i).length());

					uploadFilesContentType.add(tp);// 类型
					targetFileNames[i] = fileN;// 名称,不带后缀
					fileSize.add(FormetFileSize(uploadFiles.get(i).length()));// 大小
					System.out.println("文件名称：" + targetFileNames[i]);

					File targetFile = new File(serverRealPath, targetFileNames[i]);

					try {
						FileUtils.copyFile(uploadFiles.get(i), targetFile);
						logger.info("--------------------文件上传成功---------------");
					} catch (Exception e) {
						logger.info("upload failed");
						retu = "error";
					}
					// 传入数据库

					FileAndFolder fileObject = folderService.getById(parentFolderId);
					int ret = folderService.uploadFile(parentFolderId, fileN.substring(0, fileN.lastIndexOf(".")),
							fileSize.get(i), uploadFilesContentType.get(i), folderPath, userId, folderPath, fileObject);
				}

				addActionMessage("Upload success!");
				retu = "success";
			} else {
				addActionError("Please select a file.");
				retu = "error";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retu;
	}

	// 文件下载
	public InputStream getInputStream() throws FileNotFoundException, UnsupportedEncodingException {
		String realpath = folderPath + getDownfileName();
		File file = new File(realpath);
		InputStream inputStream = new FileInputStream(file);
		logger.info("下载路径：" + realpath);
		System.out.println("input==" + inputStream);

		if (inputStream == null) {
			session.put("downFlage", "Down Failed!");
			logger.info("请检查文件名");
		}
		return inputStream;
	}

	// 下载
	@Action(value = "download",
			results = { 
			@Result(name = "success", type = "stream",
					params = {  
					   "contentType","application/octet-stream,charset=utf-8",
					   "contentDisposition","attachment;filename=\"${downfileName}\"", 
					   "inputName", "inputStream", 
					   "bufferSize", "4096" }
			)})
	public String downloadFile() throws Exception {
		return "success";
	}

	/** 提供转换编码后的供下载用的文件名 */
	public String getDownloadFileName() {
		String downFileName = downfileName;
		try {
			downFileName = new String(downFileName.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return downFileName;
	}

	private String FormetFileSize(long fileS) {// 转换文件大小
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "b";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "k";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "m";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "g";
		}
		return fileSizeString;
	}

	public FolderService getFolderService() {
		return folderService;
	}

	public void setFolderService(FolderService folderService) {
		this.folderService = folderService;
	}

	public Integer getParentFolderId() {
		return parentFolderId;
	}

	public void setParentFolderId(Integer parentFolderId) {
		this.parentFolderId = parentFolderId;
	}

	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	@Override
	public void prepare() throws Exception {
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	private FileAndFolder model;

	@Override
	public FileAndFolder getModel() {
		return model;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<File> getUploadFiles() {
		return uploadFiles;
	}

	public void setUploadFiles(List<File> uploadFiles) {
		this.uploadFiles = uploadFiles;
	}

	public List<String> getUploadFilesFileName() {
		return uploadFilesFileName;
	}

	public void setUploadFilesFileName(List<String> uploadFilesFileName) {
		this.uploadFilesFileName = uploadFilesFileName;
	}

	public List<String> getUploadFilesContentType() {
		return uploadFilesContentType;
	}

	public void setUploadFilesContentType(List<String> uploadFilesContentType) {
		this.uploadFilesContentType = uploadFilesContentType;
	}

	public String[] getTargetFileNames() {
		return targetFileNames;
	}

	public void setTargetFilenames(String[] targetFileNames) {
		this.targetFileNames = targetFileNames;
	}

	public String getTargetFileDir() {
		return targetFileDir;
	}

	public void setTargetFileDir(String targetFileDir) {
		this.targetFileDir = targetFileDir;
	}

	public List<String> getFileSize() {
		return fileSize;
	}

	public void setFileSize(List<String> fileSize) {
		this.fileSize = fileSize;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDownfileName() throws UnsupportedEncodingException {
		return URLEncoder.encode(getDownloadFileName(), "utf-8");
	}

	public void setDownfileName(String downfileName) {
		this.downfileName = downfileName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PageSource getPagesource() {
		return pagesource;
	}

	public void setPagesource(PageSource pagesource) {
		this.pagesource = pagesource;
	}

	public String getPageTag() {
		return pageTag;
	}

	public void setPageTag(String pageTag) {
		this.pageTag = pageTag;
	}

}