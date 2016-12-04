package com.xeq.file.service;

import java.util.List;
import java.util.Stack;

import com.xeq.file.domain.FileAndFolder;
import com.xeq.file.domain.PageSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface FolderService {

	/** 更新数据库文件 */
	void update(FileAndFolder obj);

	/** 查找当前级别所有文件及文件夹 */
	List<FileAndFolder> getAll(String hql);

	/**
	 * 分页查询
	 * 
	 * @param page分页pojo类
	 * @param hql语句
	 * @return list列表
	 */
	List<FileAndFolder> pageReviwe(PageSource page, String hql);

	int createFolder(FileAndFolder fgr);

	/** 保存新的对象到数据库中 */
	int saveFileAndFolder(FileAndFolder fileAndFolder);

	List<FileAndFolder> getByFolderOrFiles(Integer userId, Integer parentFolderId);

	/** 按照Id查找文件 */
	FileAndFolder getById(Integer Id);

	/** 删除文件夹及数据，若该文件夹下还有文件，则不予以删除,放在action中判断 */
	int delete(Integer id);

	/** 查找父文件夹 */
	String parentPath(Integer parentFolderId, Stack<FileAndFolder> folderStack, String rootPath);

	/** 级联删除，文件夹删除 */
	void deleteFolder(FileAndFolder folder);

	/** 获取移动文件夹路径 */
	List<String> getToPath(Integer fId, Integer tId, List<String> list, Integer userId);

	/** 获取移动文件夹路径 */
	String getToPath(Integer fId, Integer tId, String root_Path, Integer userId);
/*
	*//** 获取UserId的tId的路径 *//*
	List<String> intoPath(Integer tId, List<String> list, Integer userId);

	*//** 获取userId的Tid的路径 *//*
	String intoPath(Integer tId,  Integer userId, String rootPath);*/
	/** 获取UserId的tId的路径 */
	List<FileAndFolder> intoPath(Integer tId, List<FileAndFolder> list, Integer userId);

	/** 获取userId的Tid的路径 */
	String intoPath(Integer tId,  Integer userId, String rootPath);

	/** 获得当前级别的jsonObject */
	public JSONObject getJson(FileAndFolder fileAndFolder, Integer userId);

	/** 返回所有文件夹的JsonArray */
	public JSONArray getJsonArray(Integer userId);

}
