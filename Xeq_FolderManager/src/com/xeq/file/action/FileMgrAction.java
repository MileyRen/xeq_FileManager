package com.xeq.file.action;

import java.util.List;
import java.util.Map;

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
	
	//private Integer pageSize;
	//private int currentPage; // 当前页数

	@Action(value = "pageList", results = { @Result(name = "pagerlist", location = "/fileManager/testPage.jsp") })
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

		if (pageTag == null) {
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
		session.put("fileList", list);
		session.put("pagesource", pagesource);
		return "pagerlist";
	}

	/*public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}*/

	/*public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
*/
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
