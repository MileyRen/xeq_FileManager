package com.xeq.file.service;

import java.util.List;

import com.xeq.file.domain.FileAndFolder;

public interface FolderService {
	int create(Integer userId, String name, Integer parentFolderId, String folderPath);

	List<FileAndFolder> getByFolderOrFiles(Integer userId, Integer parentFolderId);

	/** 按照Id查找文件 */
	FileAndFolder getById(Integer Id);

	/** 删除文件夹及数据，若该文件夹下还有文件，则不予以删除,放在action中判断 */
	int delete(Integer id);

	/** 上传文件信息到数据库 */
	int uploadFile(Integer parentFolderId, String filename, String string, String type, String folderPath,
			Integer userId, String mappingPath);
	/**查找父文件夹*/
	String parentPath(Integer parentFolderId);
}
