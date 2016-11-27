package com.xeq.file.service.impl;

import java.io.File;
import java.util.List;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xeq.file.dao.FolderDao;
import com.xeq.file.dao.impl.BaseDao;
import com.xeq.file.domain.FileAndFolder;
import com.xeq.file.domain.PageSource;
import com.xeq.file.service.FolderService;

@Service("FolderService")
public class FolderServiceImpl extends BaseDao implements FolderService {

	@Autowired
	private FolderDao folderDao;

	public void setFolderDao(FolderDao folderDao) {
		this.folderDao = folderDao;
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
	public String parentPath(Integer parentFolderId,Stack<FileAndFolder> folderStack,String rootPath) {
		return folderDao.parentPath(parentFolderId,folderStack,rootPath);
	}

	@Override
	public int delete(Integer id) {
		return folderDao.delete(id);
	}

	@Override
	public void deleteFolder(FileAndFolder folder) {
		folderDao.deleteFolder(folder);
	}

	@Override
	public int saveFileAndFolder(FileAndFolder fileAndFolder) {
		return folderDao.saveFileAndFolder(fileAndFolder);
	}

	@Override
	public List<FileAndFolder> pageReviwe(PageSource page, String hql) {
		return folderDao.pageReviwe(page, hql);
	}

	@Override
	public List<FileAndFolder> getAll(String hql) {
		return folderDao.getAll(hql);
	}

	@Override
	public void update(FileAndFolder obj) {
		folderDao.update(obj);
	}

	@Override
	public int createFolder(FileAndFolder fgr) {
		return folderDao.createFolder(fgr);
	}
	

}
