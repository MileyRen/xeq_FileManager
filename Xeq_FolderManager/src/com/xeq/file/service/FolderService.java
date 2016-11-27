package com.xeq.file.service;

import java.util.List;
import java.util.Stack;

import com.xeq.file.domain.FileAndFolder;
import com.xeq.file.domain.PageSource;

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
	String parentPath(Integer parentFolderId,Stack<FileAndFolder> folderStack,String rootPath);

	/** 级联删除，文件夹删除 */
	void deleteFolder(FileAndFolder folder);
}
