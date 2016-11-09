package com.xeq.file.dao;

import java.io.File;
import java.util.List;

import org.omg.PortableInterceptor.INACTIVE;

import com.xeq.file.domain.FileAndFolder;

public interface FolderDao {

	/**
	 * 在parentFolderId下创建文件夹，同级文件夹不能重名
	 * 
	 * @param：userId:用户id
	 * @param:parentForderId:父文件夹
	 * @return 创建后的文件夹Id
	 */
	int createFolder(Integer userId, String name, Integer parentFolderId, String folderPath,
			FileAndFolder parentObject);

	/** 保存新的对象到数据库中 */
	int saveFileAndFolder(FileAndFolder fileAndFolder);

	/**
	 * 查找parentFolderId当前级别下的文件及及文件
	 * 
	 * @param userId用户Id
	 * @param parentFolderid
	 *            父文件夹Id
	 * @return 返回一个FileAndFolder类型的列表
	 */
	List<FileAndFolder> getByFolderOrFiles(Integer userId, Integer parentFolderId);

	/** 按照Id查找文件 */
	FileAndFolder getById(Integer Id);

	/** 删除单个文件 */
	int delete(Integer id, String path);

	/**
	 * 将上传文件信息传入数据库 *
	 * 
	 * @parentFolder 父文件夹
	 * @filename 文件名
	 */
	int uploadFile(Integer parentFolderId, String filename, String size, String type, String folderPath, Integer userId,
			String mappingPath, FileAndFolder fileObject);

	/** 查找父文件夹 */
	String parentPath(Integer parentFolderId);

	/** 级联删除，文件夹删除 */
	void deleteFolder(FileAndFolder folder);

}
