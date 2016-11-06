package com.xeq.file.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xeq.file.dao.FolderDao;
import com.xeq.file.dao.impl.BaseDao;
import com.xeq.file.domain.FileAndFolder;
import com.xeq.file.service.FolderService;

@Service("FolderService")
public class FolderServiceImpl extends BaseDao implements FolderService {

	@Autowired
	private FolderDao folderDao;

	public void setFolderDao(FolderDao folderDao) {
		this.folderDao = folderDao;
	}

	@Override
	public int create(Integer userId, String name, Integer parentFolderId, String folderPath,
			FileAndFolder parentObject) {
		return folderDao.createFolder(userId, name, parentFolderId, folderPath, parentObject);
	}

	@Override
	public List<FileAndFolder> getByFolderOrFiles(Integer userId, Integer parentFolderId) {
		return folderDao.getByFolderOrFiles(userId, parentFolderId);
	}

	@Override
	public FileAndFolder getById(Integer Id) {
		return folderDao.getById(Id);
	}

	@Override
	public int uploadFile(Integer parentFolderId, String filename, String size, String type, String folderPath,
			Integer userId, String mappingPath,FileAndFolder fileObject) {
		return folderDao.uploadFile(parentFolderId, filename, size, type, folderPath, userId, mappingPath,fileObject);
	}

	@Override
	public String parentPath(Integer parentFolderId) {
		return folderDao.parentPath(parentFolderId);
	}

	@Override
	public int delete(Integer id, String path) {
		return folderDao.delete(id, path);
	}

	@Override
	public void deleteFolder(FileAndFolder folder) {
		folderDao.deleteFolder(folder);
	}

}
