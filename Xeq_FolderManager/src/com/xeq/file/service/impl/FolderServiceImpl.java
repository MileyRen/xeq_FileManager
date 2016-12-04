package com.xeq.file.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xeq.file.dao.FolderDao;
import com.xeq.file.dao.impl.BaseDao;
import com.xeq.file.domain.FileAndFolder;
import com.xeq.file.domain.PageSource;
import com.xeq.file.service.FolderService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
	public String parentPath(Integer parentFolderId, Stack<FileAndFolder> folderStack, String rootPath) {
		return folderDao.parentPath(parentFolderId, folderStack, rootPath);
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

	@Override
	public List<String> getToPath(Integer fId, Integer tId, List<String> list, Integer userId) {
		FileAndFolder fgr = folderDao.getById(tId);
		// 若有重名文件夹，则不移动
		// 判断要移动的文件夹名称与目的文件夹的子文件夹不重名
		if (fgr.getParentFolderId() == fId) {
			return null;
		} else {
			list.add(fgr.getName());
			if (fgr.getParentFolderId() > -1) {
				return getToPath(fId, fgr.getParentFolderId(), list, userId);
			} else {
				return list;
			}
		}
	}

	@Override
	public String getToPath(Integer fId, Integer tId, String root_Path, Integer userId) {
		String result = root_Path;
		try {
			List<String> list = new ArrayList<String>();
			list = getToPath(fId, tId, list, userId);
			StringBuffer sf = new StringBuffer();
			sf.append(root_Path);
			for (int i = list.size() - 1; i >= 0; i--) {
				sf.append(list.get(i) + File.separator);
			}
			result = sf.toString();
		} catch (NullPointerException e) {
			return result;
		}
		return result;
	}

	@Override
	public List<FileAndFolder> intoPath(Integer tId, List<FileAndFolder> list, Integer userId) {
		FileAndFolder fgr = folderDao.getById(tId);
		list.add(fgr);
		try {
			if (fgr.getParentFolderId() > -1) {
				return intoPath(fgr.getParentFolderId(), list, userId);
			} else {
				return list;
			}
		} catch (NullPointerException e) {
			return list;
		}
	}

	@Override
	public String intoPath(Integer tId, Integer userId, String rootPath) {
		String result = rootPath;
		try {
			List<FileAndFolder> list = new ArrayList<FileAndFolder>();
			list = intoPath(tId, list, userId);
			StringBuffer sf = new StringBuffer();
			sf.append(rootPath);
			for (int i = list.size() - 1; i >= 0; i--) {
				sf.append(list.get(i).getName() + File.separator);
			}
			result = sf.toString();
		} catch (NullPointerException e) {
			return result;
		}
		return result;
	}

	/** 返回所有文件夹的JsonArray */
	@Override
	public JSONArray getJsonArray(Integer userId) {
		String hqlFolder = "From FileAndFolder where userId=" + userId + "  and type='folder'";
		
		JSONArray ja = new JSONArray();
		JSONObject jObject = new JSONObject();
		jObject.put("id", -1);
		jObject.put("text", "root");
		jObject.put("parentId", -2);
		jObject.put("iconCls", "icon-folder");

		List<FileAndFolder> lists = folderDao.getAll(hqlFolder);
		JSONArray jsonArray = new JSONArray();
		for (FileAndFolder fileAndFolder : lists) {
			if (fileAndFolder.getParentFolderId() == -1) {
				jsonArray.add(getJson(fileAndFolder, 1));
			}
		}
		
		jObject.put("children", jsonArray);
		ja.add(jObject);
		System.out.println(ja.toString());
		return ja;
	}

	/** 获得当前级别的jsonObject */
	@Override
	public JSONObject getJson(FileAndFolder fileAndFolder, Integer userId) {
		Integer newPId = fileAndFolder.getId();
		String hqlFolder = "From FileAndFolder where userId=" + userId + " and type='folder' and parentFolderId="
				+ newPId;
		System.out.println("hal=" + hqlFolder);
		List<FileAndFolder> lists = folderDao.getAll(hqlFolder);
		JSONObject jt = new JSONObject();
		jt.put("id", fileAndFolder.getId());
		jt.put("text", fileAndFolder.getName());
		jt.put("parentId", fileAndFolder.getParentFolderId());
		jt.put("iconCls", "icon-folder");
		JSONArray array = new JSONArray();
		if (lists.size() != 0) {// 若不为最后一层文件夹
			jt.put("state", "closed");
			for (FileAndFolder ff : lists) {
				array.add(getJson(ff, userId));
			}
			jt.put("children", array);
		}
		return jt;
	}

}
