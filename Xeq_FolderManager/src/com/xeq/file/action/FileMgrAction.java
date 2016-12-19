package com.xeq.file.action;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.io.*;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gene.utils.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.xeq.file.dao.FolderOperate;
import com.xeq.file.dao.impl.BaseDao;
import com.xeq.file.domain.FileAndFolder;
import com.xeq.file.domain.PageSource;
import com.xeq.file.service.FolderService;

import net.sf.json.JSONArray;

@Namespace("/")
@ParentPackage("struts-default")
@Controller("fileMgrAction")
@Scope("prototype")
public class FileMgrAction extends ActionSupport implements SessionAware, ModelDriven<FileAndFolder>, Preparable {
	@Autowired
	private FolderService folderService;
	@Autowired
	private PageSource pagesource;
	@Autowired
	private FolderOperate folderOperate;

	/** 栈，用于存放访问次序，若一个文件夹被删除，则应删除栈中所有该文件夹及子文件夹 */
	private static final Stack<FileAndFolder> folderStack = new Stack<FileAndFolder>();
	private static final long serialVersionUID = 2755957955541896021L;
	private static Logger logger = Logger.getLogger(FileMgrAction.class);
	private Map<String, Object> session;

	private Integer id;
	private Integer parentFolderId;
	private String folderPath;// 父文件夹路径，用于新建文件夹时
	private String name;
	private String type;

	// 分页
	private String pageTag;
	// 移动文件
	private Integer fromId;
	private String fromPath;
	private Integer toId;

	private Integer toIdBulk;

	@Action(value = "BulkMove", results = { @Result(name = "pagerlist", location = "/fileManager/f2Mgr.jsp"),
			@Result(name = "error", location = "/fileManager/error.jsp"),
			@Result(name = "input", location = "/fileManager/f2Mgr.jsp") })
	public String BulkMove() throws Exception {
		logger.info("----------批量移动文件-----------");
		HttpServletRequest request = ServletActionContext.getRequest();
		// 获取当前路径
		String parentPath = (String) session.get("parentPath");
		User user = (User) session.get("user");
		if (user == null) {
			return "error";
		}
		int movesize = Integer.parseInt(request.getParameter("movesize"));
		int toIdBulk = 0;
		if (request.getParameter("toIdBulk") == null) {
			toIdBulk = parentFolderId;
		} else {
			toIdBulk = Integer.parseInt(request.getParameter("toIdBulk"));
		}
		int moveSize = 0;// 文件移动成功数量
		for (int i = 0; i < movesize; i++) {
			int fromId = Integer.parseInt(request.getParameter("fromId[" + i + "]"));
			boolean move = moveFolderOrFile(parentPath, user, fromId, toIdBulk);
			if (move) {
				moveSize++;
				logger.info("文件移动成功.....");
			} else {
				logger.info("文件移动失败，跳过.....");
			}
		}
		logger.info("移动文件成功 " + moveSize + " 个");
		return getPage();
	}

	/**
	 * 移动文件
	 * 
	 * @param parentPath:父路径
	 * @param User：用户对象
	 * @param fromId:被移动文件Id
	 * @param toId:移动到的文件夹Id
	 * @return 移动成功或者失败
	 */
	public boolean moveFolderOrFile(String parentPath, User user, int fromId, int toId) {
		int userId = user.getId();
		if (fromId == toId) {
			logger.info("fromId==toId");
			return false;
		}
		FileAndFolder frompojo = folderService.getById(fromId);// 移动文件夹
		FileAndFolder topojo = folderService.getById(toId);// 目标文件夹
		if (topojo == null) {
			topojo = new FileAndFolder();
			topojo.setId(-1);
			topojo.setParentFolderId(-2);
		}

		logger.debug("检查目标文件夹下是否有同名..........");
		List<FileAndFolder> listFilesAndFolders = folderService
				.getAll("From FileAndFolder where userId=" + userId + " and parentFolderId=" + toId);
		if (listFilesAndFolders.size() != 0) {
			for (FileAndFolder fl : listFilesAndFolders) {
				String fullToName = fl.getName() + fl.getType();
				String fullFromName = frompojo.getName() + frompojo.getType();
				if (fullFromName == fullToName || fullFromName.equals(fullToName)) {
					logger.info("有同名文件，不进行移动");
					return false;
				}
			}
		}
		logger.debug("检查是否为子文件夹....");
		if (frompojo.getType() == "folder" || frompojo.getType().equals("folder")) {
			int parentId = topojo.getParentFolderId();
			while (parentId > -1) {
				if (parentId == fromId) {
					logger.info("不能移动到子文件夹中");
					return false;
				} else {
					parentId = folderService.getById(parentId).getParentFolderId();
				}
			}
		}

		String root_Path = null;
		try {
			root_Path = folderService.getToPath(fromId, toId, user.getFolder(), userId);
			logger.info("目标目录==" + root_Path);
		} catch (NullPointerException e) {
			logger.info("移动失败");
			return false;
		}
		String from_path = parentPath;
		logger.info("fromPath===" + from_path);
		// 获取移动到的文件夹的路径结束-----------------------------
		if (frompojo.getType() == "folder" || frompojo.getType().equals("folder")) {
			from_path = from_path + frompojo.getName() + File.separator;
		} else {
			from_path = from_path + frompojo.getName() + frompojo.getType();
		}
		logger.info("from——path是===========" + from_path);
		String new_path = root_Path;

		Integer new_parentFolderId = -1;
		FileAndFolder new_delFlag = null;
		if (topojo.getId() > -1) {
			new_parentFolderId = topojo.getId();
			new_delFlag = topojo;
		}
		// }
		boolean moveFlag = folderOperate.removeFileOrFolder(from_path, new_path);// 真实目录移动
		if (moveFlag == true) {
			frompojo.setDeleteFlag(new_delFlag);
			frompojo.setParentFolderId(new_parentFolderId);
			folderService.update(frompojo);
			return true;
		} else {
			return false;
		}
	}

	@Action(value = "moveAction", results = { @Result(name = "pagerlist", location = "/fileManager/f2Mgr.jsp"),
			@Result(name = "error", location = "/fileManager/error.jsp"),
			@Result(name = "input", location = "/fileManager/f2Mgr.jsp") })
	public String moveFileOrFolder() throws Exception {
		logger.info("----------移动文件-----------");
		// 获取当前路径
		String parentPath = (String) session.get("parentPath");
		User user = (User) session.get("user");
		if (user == null) {
			return "error";
		}
		if (toId == null) {
			toId = fromId;
		}
		Boolean move = moveFolderOrFile(parentPath, user, fromId, toId);
		if (move) {
			logger.info("移动文件或文件夹成功");
			return getPage();
		} else {
			logger.info("移动失败");
			return "error";
		}
	}

	@Action(value = "pageList", results = { @Result(name = "pagerlist", location = "/fileManager/f2Mgr.jsp"),
			@Result(name = "error", location = "/fileManager/f2Mgr.jsp") })
	public String getPage() throws Exception {
		logger.debug("------查询结果-----------");
		User user = (User) session.get("user");
		if (user == null)
			return "error";
		int userId = user.getId();

		// 获取文件夹的json串, session中放置json的字符串
		JSONArray jsonArray = folderService.getJsonArray(userId);
		session.put("folderJson", jsonArray.toString());

		String rootPath = user.getFolder();
		List<FileAndFolder> list = null;

		// 若当前没有parentFolderId值，则表示第一次进入页面
		if (parentFolderId == null) {
			parentFolderId = -1;
		}
		logger.info("当前父Id=" + parentFolderId);
		FileAndFolder fgr = folderService.getById(parentFolderId);
		if (fgr != null) {
			try {
				FileAndFolder peek = folderStack.peek();
				if (peek.getParentFolderId() != fgr.getParentFolderId()) {
					folderStack.push(fgr);
				}
			} catch (EmptyStackException e) {
				folderStack.push(fgr);
			}
		}
		System.out.println("------------------------------输出Stack----------------------------------------");
		for (FileAndFolder fileAndFolder : folderStack) {
			System.out.println(fileAndFolder.toString());
		}
		System.out.println("------------------------------输出Stack----------------------------------------");
		folderPath = folderService.intoPath(parentFolderId, userId, rootPath);
		logger.info("当前路径==" + folderPath);

		///// 获取当前路径的列表，用于面包屑导航////////
		List<FileAndFolder> pathList = new ArrayList<FileAndFolder>();
		List<FileAndFolder> breadcrumb = new ArrayList<FileAndFolder>();
		pathList = folderService.intoPath(parentFolderId, pathList, userId);
		if (pathList.size() > 0) {
			for (int i = pathList.size() - 1; i >= 0; i--) {
				breadcrumb.add(pathList.get(i));
			}
		}
		session.put("breadcrumb", breadcrumb);
		String hql = "From FileAndFolder where userId=" + userId + " and parentFolderId=" + parentFolderId;
		List<FileAndFolder> all_list = folderService.getAll(hql);
		String hqlFolder = "From FileAndFolder where userId=" + userId + " and type='folder'";
		// 所有文件夹
		List<FileAndFolder> all_folder = folderService.getAll(hqlFolder);
		pagesource.setTotalRows(all_list.size());// 获取总行数

		if (pageTag != null) {
			// 说明是首次加载
			pagesource.init(all_list.size(), (new BaseDao()).pageSize());// 初始化，用以获取总页数
			pagesource.setCurrentPage(1);
			pagesource.setPageSize((new BaseDao()).pageSize());

			logger.info(pagesource.toString());
			list = folderService.pageReviwe(pagesource, hql);
			logger.info("hql=" + hql + ";size=" + list.size());
			System.out.println("没有执行分页的代码");
		} else {
			// /执行分页操纵
			System.out.println("执行了分页的代码");
			pagesource.init(all_list.size(), pagesource.getPageSize());// 初始化，用以获取总页数
			pagesource.setPageSize(pagesource.getPageSize());

			if (pagesource.getCurrentPage() >= pagesource.getTotalPages()) {
				pagesource.setCurrentPage(pagesource.getTotalPages());
			} else if (pagesource.getCurrentPage() < 1) {
				pagesource.setCurrentPage(1);
			}
			logger.info(pagesource.toString());
			list = folderService.pageReviwe(pagesource, hql);
		}

		session.put("folderlist", all_folder);
		session.put("fileList", list);// 所有文件和文件夹列表
		session.put("pagesource", pagesource);
		session.put("parentPath", folderPath);
		session.put("parentId", parentFolderId);
		session.put("folderStack", folderStack);
		return "pagerlist";
	}

	@Action(value = "backStack", results = { @Result(name = "pagerlist", location = "/fileManager/f2Mgr.jsp") })
	public String getBack() throws Exception {
		User user = (User) session.get("user");
		Integer userId = user.getId();
		String rootPath = user.getFolder();
		Integer pid = -1;// 初始化为第一页
		try {
			pid = folderStack.peek().getParentFolderId();// 获得上一级文件夹
			folderStack.pop();// 当前父文件夹出栈
		} catch (Exception e) {// 若数组越界，则返回初始化值
			pid = -1;
		}

		System.out.println("------------------------------输出Back Stack----------------------------------------");
		for (FileAndFolder fileAndFolder : folderStack) {
			System.out.println(fileAndFolder.toString());
		}
		System.out.println("------------------------------输出Back Stack----------------------------------------");
		folderPath = folderService.intoPath(pid, userId, rootPath);
		logger.info("backfolderPath========" + folderPath);

		session.put("parentPath", folderPath);
		session.put("parentId", pid);
		session.put("folderStack", folderStack);

		setParentFolderId(pid);

		logger.info("上一页的parentFolderId=" + pid);
		// 调用getPage
		return getPage();
	}

	public String getPageTag() {
		return pageTag;
	}

	public void setPageTag(String pageTag) {
		this.pageTag = pageTag;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public FolderService getFolderService() {
		return folderService;
	}

	public void setFolderService(FolderService folderService) {
		this.folderService = folderService;
	}

	public PageSource getPagesource() {
		return pagesource;
	}

	public void setPagesource(PageSource pagesource) {
		this.pagesource = pagesource;
	}

	public Integer getFromId() {
		return fromId;
	}

	public void setFromId(Integer fromId) {
		this.fromId = fromId;
	}

	public String getFromPath() {
		return fromPath;
	}

	public void setFromPath(String fromPath) {
		this.fromPath = fromPath;
	}

	public Integer getToId() {
		return toId;
	}

	public void setToId(Integer toId) {
		this.toId = toId;
	}

	public Integer getToIdBulk() {
		return toIdBulk;
	}

	public void setToIdBulk(Integer toIdBulk) {
		this.toIdBulk = toIdBulk;
	}

	public FolderOperate getFolderOperate() {
		return folderOperate;
	}

	public void setFolderOperate(FolderOperate folderOperate) {
		this.folderOperate = folderOperate;
	}

	public void setModel(FileAndFolder model) {
		this.model = model;
	}

	@Override
	public void prepare() throws Exception {

	}

	private FileAndFolder model;

	@Override
	public FileAndFolder getModel() {
		return model;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
