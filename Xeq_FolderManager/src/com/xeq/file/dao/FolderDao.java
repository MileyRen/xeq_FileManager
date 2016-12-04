package com.xeq.file.dao;

import java.util.List;
import java.util.Stack;

import com.xeq.file.domain.FileAndFolder;
import com.xeq.file.domain.PageSource;

public interface FolderDao {

	/** 更新数据库文件 */
	void update(FileAndFolder obj);

	/**
	 * 查找文件及文件夹
	 * 
	 */
	List<FileAndFolder> getAll(String hql);

	/**
	 * 分页查询
	 * 
	 * @param page分页pojo类
	 * @param hql语句
	 * @return list列表
	 */
	List<FileAndFolder> pageReviwe(PageSource page, String hql);

	/**
	 * 在parentFolderId下创建文件夹，同级文件夹不能重名,传入一个对象
	 */
	int createFolder(FileAndFolder fgr);

	/** 保存新的对象到数据库中 */
	int saveFileAndFolder(FileAndFolder fileAndFolder);

	/**
	 * 查找parentFolderId当前级别下的文件及及文件
	 * 
	 * @param userId用户Id
	 * @param parentFolderid父文件夹Id
	 * @return 返回一个FileAndFolder类型的列表
	 */
	List<FileAndFolder> getByFolderOrFiles(Integer userId, Integer parentFolderId);

	/**
	 * 查找parentFolderId当前级别下的文件及文件夹
	 */
	// List<FileAndFolder> getByPage(String hql, int offset, int pageSize);

	/** 查找当前级别的所有文件 */
	// List<FileAndFolder> getAllRowCount(String hql);

	/** 按照Id查找文件 */
	FileAndFolder getById(Integer Id);

	/** 删除单个文件 */
	int delete(Integer id);

	/** 查找父文件夹 */
	String parentPath(Integer parentFolderId, Stack<FileAndFolder> folderStack, String rootpath);

	/** 级联删除，文件夹删除 */
	void deleteFolder(FileAndFolder folder);
}
