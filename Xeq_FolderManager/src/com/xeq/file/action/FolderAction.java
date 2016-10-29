package com.xeq.file.action;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
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
import com.sun.glass.ui.Size;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Intercepter;
import com.xeq.file.domain.FileAndFolder;
import com.xeq.file.service.FolderService;

@Namespace("/")
@ParentPackage("struts-default")
@Controller("folderAction")
@Scope("prototype")
public class FolderAction extends ActionSupport implements SessionAware, ModelDriven<FileAndFolder>, Preparable {
	private static final long serialVersionUID = -5640743370611684124L;
	private static Logger logger = Logger.getLogger(FolderAction.class);
	/** 栈，用于存放访问次序，若一个文件夹被删除，则应删除栈中所有该文件夹及子文件夹 */
	private static Stack<Integer> preAndnext = new Stack<Integer>();

	@Autowired
	private FolderService folderService;
	private Map<String, Object> session;
	private Integer parentFolderId;
	private String folderPath;// 父文件夹路径，用于新建文件夹时
	private String name;

	// 定义多文件上传的数组
	private List<File> uploadFiles;
	private List<String> uploadFilesFileName;
	private List<String> uploadFilesContentType;
	private String[] targetFileNames;
	private List<String> fileSize;
	private String targetFileDir;
	private int filesCount;

	/*** 显示当前级别的文件结果 */
	@Action(value = "folderlist", results = { @Result(name = "folderlist", location = "/fileManager/folderList.jsp") })
	public String FolderList() {

		logger.debug("------查询结果-----------");
		// session.put("userId", 1);// 用户Id,整合式需要修改
		int userId = (int) session.get("userId");
		List<FileAndFolder> faflists = new ArrayList<FileAndFolder>();
		if (parentFolderId == null) {
			faflists = folderService.getByFolderOrFiles(userId, -1);
			parentFolderId = faflists.get(0).getId();// 将根目录的id置为parentFolderId
		}
		// 从根目录下开始查找
		faflists = folderService.getByFolderOrFiles(userId, parentFolderId);

		FileAndFolder fileAndFolder = folderService.getById(parentFolderId);
		// System.out.println("parentId=" + fileAndFolder.getId() + ";path=" +
		// fileAndFolder.getFolderPath());
		folderPath = fileAndFolder.getFolderPath();

		if (preAndnext.size() > 0) {
			Integer temp = preAndnext.get(preAndnext.size() - 1);
			if (temp != parentFolderId) {
				preAndnext.add(parentFolderId);// 如果该parentId与上一级不同将此时的父文件夹id入栈
			}
		} else {
			preAndnext.add(parentFolderId);
		}

		session.put("parentPath", folderPath);
		session.put("parentId", parentFolderId);
		session.put("faflists", faflists);

		session.put("initFlag", 1);
		return "folderlist";
	}

	//
	//
	//返回上一级
	@Action(value = "backStack", results = { @Result(name = "success", location = "/fileManager/folderList.jsp") })
	public String getBack() {
		Integer pid = -1;//初始化为第一页
		Integer userId = (Integer) session.get("userId");
		if (preAndnext.size() > 0) {
			preAndnext.pop();// 当前父文件夹出栈
			if (preAndnext.size() > 0) {
				pid = preAndnext.get(preAndnext.size() - 1);// 获得上一级文件夹
			}
		}
		System.out.println("父文件夹----------------"+pid);
		List<FileAndFolder> faflists = folderService.getByFolderOrFiles(userId, pid);
		FileAndFolder fileAndFolder = folderService.getById(pid);
		folderPath = fileAndFolder.getFolderPath();

		session.put("parentPath", folderPath);
		session.put("parentId", pid);
		// session.put("Folderstack", preAndnext);
		session.put("faflists", faflists);

		return "success";
	}

	/***/
	// 直接在类名称的上端写入即可，value中指定要引入的拦截器的名称即可
	@Action(value = "addFolder", results = {
			@Result(name = "createFolder", type = "redirect", location = "folderlist", params = { "parentFolderId",
					"%{parentFolderId}" }),
			@Result(name = "error", type = "redirect", location = "folderlist", params = { "parentFolderId",
					"%{parentFolderId}" }) }, interceptorRefs = { @InterceptorRef(value = "defaultStack"),
							@InterceptorRef(value = "token"), }
	/*
	 * @Result(name = "input", location = "/fileManager/folderList.jsp"),
	 * 
	 * @Result(name = "error", location = "/fileManager/folderList.jsp") },
	 * interceptorRefs = {
	 * 
	 * @InterceptorRef(value = "defaultStack"), @InterceptorRef(value = "token")
	 * }
	 */)
	public String createFolder() {
		// 创建文件夹,要判断同级文件夹是否重名，若重名则重新插入
		logger.debug("--------创建文件夹------------");

		// session.put("userId", 1);
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

		int id = folderService.create(userId, name, parentFolderId, folderPath);
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
			@Result(name = "success", type = "redirect", location = "folderlist", params = { "parentFolderId",
					"%{parentFolderId}" }),
			@Result(name = "error", type = "redirect", location = "folderlist", params = { "parentFolderId",
					"%{parentFolderId}" }) })
	public String uploadFiles() {
		String retu = "success";
		int userId = (int) session.get("userId");
		try {
			if (uploadFiles != null) {
				// 存到folderPath中
				String serverRealPath = folderPath;// ServletActionContext.getServletContext().getRealPath(folderPath);
				System.err.println("---------------servletRealPath=" + serverRealPath + "------------------");
				File dir = new File(serverRealPath);
				if (!dir.exists()) {
					addActionError("Upload Error!");
					retu = "error";
				}
				filesCount = uploadFiles.size();
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
					FileUtils.copyFile(uploadFiles.get(i), targetFile);
					System.out.println("--------------------文件上传成功---------------");
					// 传入数据库

					int ret = folderService.uploadFile(parentFolderId, fileN.substring(0, fileN.lastIndexOf(".")),
							fileSize.get(i), uploadFilesContentType.get(i), folderPath, userId);
				}

				addActionMessage("Upload success!");
				retu = "success";
			} else {
				addActionError("Please select a file.");
				retu = "error";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return retu;
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

	// @Validations(requiredStrings = {
	// @RequiredStringValidator(fieldName = "name", message = "FolderName id
	// notnull.") }, regexFields = {
	// @RegexFieldValidator(fieldName = "name", expression = "[^?!\\/;*?“<>]+",
	// message = "FolderName cant't contions '/','\',';','*','”','<','>'") })
	/*
	 * @Validations(requiredStrings = {
	 * 
	 * @RequiredStringValidator(fieldName = "name", message =
	 * "FolderName id not null.") })
	 */
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

}
