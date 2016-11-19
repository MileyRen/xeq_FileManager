package com.xeq.file.action;

import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
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
import com.xeq.file.dao.impl.BaseDao;
import com.xeq.file.domain.FileAndFolder;
import com.xeq.file.domain.PageSource;
import com.xeq.file.service.FolderService;

@Namespace("/")
@ParentPackage("struts-default")
@Controller("fileMgrAction")
@Scope("prototype")
public class FileMgrAction extends ActionSupport implements SessionAware, ModelDriven<FileAndFolder>, Preparable {
	@Autowired
	private FolderService folderService;
	@Autowired
	private PageSource pagesource;
	/** 栈，用于存放访问次序，若一个文件夹被删除，则应删除栈中所有该文件夹及子文件夹 */
	private static final Stack<Integer> preAndnext = new Stack<Integer>();
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
	
	public String moveFile(){
		logger.info("----------移动文件-----------");
		int userId = (int) session.get("userId");
		
		return "success";
	}
	
	
	@Action(value = "pageList", results = { @Result(name = "pagerlist", location = "/fileManager/f2Mgr.jsp") })
	public String getPage() throws Exception {
		logger.debug("------查询结果-----------");
		int userId = (int) session.get("userId");

		List<FileAndFolder> list = null;

		// 查找当前用户是否有文件夹
		List<FileAndFolder> exist = folderService
				.getAll("From FileAndFolder where userId=" + userId + " and parentFolderId=-1");
		if (exist.size() == 0) {
			// 若当前用户没有文件夹，则进行创建，并将其Id作为parentId
			parentFolderId = folderService.create(userId, "user_" + userId, -1, (new BaseDao()).rootPath(), null);
		}

		else if (parentFolderId == null) {
			// 若当前没有parentFolderId值，则表示第一次进入页面，则获取第一级目录的parentFolderId值
			parentFolderId = folderService.getByFolderOrFiles(userId, -1).get(0).getId();
		}

		String hql = "From FileAndFolder where userId=" + userId + " and parentFolderId=" + parentFolderId;
		List<FileAndFolder> all_list = folderService.getAll(hql);

		pagesource.setTotalRows(all_list.size());// 获取总行数

		if (pageTag!=null) {
			//说明是首次加载
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
		folderPath = folderService.parentPath(parentFolderId);
		if (preAndnext.size() > 0) {
			Integer temp = preAndnext.get(preAndnext.size() - 1);
			if (temp != parentFolderId) {
				preAndnext.add(parentFolderId);// 如果该parentId与上一级不同将此时的父文件夹id入栈
			}
		} else {
			preAndnext.add(parentFolderId);
		}
		
		session.put("fileList", list);
		session.put("pagesource", pagesource);
		session.put("parentPath", folderPath);
		session.put("parentId", parentFolderId);
		
		return "pagerlist";
	}
	
	@Action(value = "backStack", results = {@Result(name = "pagerlist", location = "/fileManager/f2Mgr.jsp") })
	public String getBack() throws Exception {
		Integer userId = (Integer) session.get("userId");
		// 初始化pid
		Integer parentId = folderService.getByFolderOrFiles(userId, -1).get(0).getId();
		Integer pid = parentId;// 初始化为第一页
		try {
			preAndnext.pop();// 当前父文件夹出栈
			pid = preAndnext.get(preAndnext.size() - 1);// 获得上一级文件夹
		} catch (Exception e) {// 若数组越界，则返回初始化值
			pid = parentId;
		}
		folderPath = folderService.parentPath(pid);
		
		//List<FileAndFolder> faflists = folderService.getByFolderOrFiles(userId, pid);
		session.put("parentPath", folderPath);
		session.put("parentId", pid);
		setParentFolderId(pid);
		
		logger.info("上一页的parentFolderId="+pid);
		//调用getPage
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

	public PageSource getPagesource() {
		return pagesource;
	}

	public void setPagesource(PageSource pagesource) {
		this.pagesource = pagesource;
	}

}
