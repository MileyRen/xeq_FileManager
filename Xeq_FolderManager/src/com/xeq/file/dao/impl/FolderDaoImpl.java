package com.xeq.file.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xeq.file.dao.FolderDao;
import com.xeq.file.dao.FolderOperate;
import com.xeq.file.domain.FileAndFolder;
import com.xeq.file.domain.PageSource;

@Repository("FolderDao")
public class FolderDaoImpl extends BaseDao implements FolderDao {
	private Logger log = Logger.getLogger(FolderDaoImpl.class);

	@Autowired
	private FolderOperate folderOperate;

	public void setFolderOperate(FolderOperate folderOperate) {
		this.folderOperate = folderOperate;
	}

	@Override
	public int createFolder(Integer userId, String name, Integer parentFolderId, String folderPath,
			FileAndFolder parentObject) {
		System.out.println("根目录 =" + rootPath());
		log.debug("-----------创建文件夹---------------");
		FileAndFolder fileAndFolder = new FileAndFolder();
		fileAndFolder.setName(name);
		fileAndFolder.setParentFolderId(parentFolderId);
		fileAndFolder.setSize("");
		fileAndFolder.setTime(new Date());
		fileAndFolder.setType("folder");
		fileAndFolder.setUserId(userId);
		fileAndFolder.setMappingPath(null);
		fileAndFolder.setDeleteFlag(parentObject);
		System.out.println("-------------输出名称" + name + "-------------------");
		String path = folderPath + fileAndFolder.getName() + "\\";
		if (parentFolderId == -1) {
			path = rootPath() + fileAndFolder.getName() + "\\";
		}
		fileAndFolder.setFolderPath(path);
		// 是否有重复值
		boolean repet = false;
		int ret = -1;

		// if (repet == false) {
		ret = (int) getSession().save(fileAndFolder);// 插入数据库
		boolean flag = false;
		if (parentFolderId == -1) {
			flag = folderOperate.createRealFolder(fileAndFolder.getName(), rootPath());
		}

		flag = folderOperate.createRealFolder(fileAndFolder.getName(), folderPath);
		if (flag == false) {
			// 若创建文件夹失败，则删除已经插入数据库的信息
			//
			//
			//
			//
		}
		// }

		return ret;
	}

	@Override
	public String parentPath(Integer parentFolderId) {
		String path = rootPath();
		if (parentFolderId != -1) {
			FileAndFolder fileAndFolder = getById(parentFolderId);
			path = fileAndFolder.getFolderPath();
		}
		return path;
	}

	@Override
	public List<FileAndFolder> getByFolderOrFiles(Integer userId, Integer parentFolderId) {
		log.debug("-------------查询文件及文件夹-----------------");

		Criteria criteria = getSession().createCriteria(FileAndFolder.class);
		Criterion criterion = Restrictions.eq("userId", userId);// 查询条件
		Criterion criterion2 = Restrictions.eq("parentFolderId", parentFolderId);

		criteria.add(criterion).add(criterion2);

		List<FileAndFolder> list = criteria.list();
		// 若还没有根目录，则先创建根目录
		if (parentFolderId == -1) {
			if (list.isEmpty() || list.size() == 0) {
				String name = "user_" + userId;
				int ret = createFolder(userId, name, parentFolderId, rootPath(), null);
				list = getByFolderOrFiles(userId, parentFolderId);
			}
		}
		return list;
	}

	@Override
	public FileAndFolder getById(Integer Id) {

		String hql = "from FileAndFolder where id=:id";
		FileAndFolder fileAndFolder = (FileAndFolder) getSession().createQuery(hql).setInteger("id", Id).uniqueResult();
		return fileAndFolder;
	}

	@Override
	public int uploadFile(Integer parentFolderId, String filename, String size, String type, String folderPath,
			Integer userId, String mappingPath, FileAndFolder fileObject) {
		FileAndFolder fileAndFolder = new FileAndFolder();
		fileAndFolder.setFolderPath(folderPath);
		fileAndFolder.setName(filename);
		fileAndFolder.setParentFolderId(parentFolderId);
		fileAndFolder.setSize(size);
		fileAndFolder.setTime(new Date());
		fileAndFolder.setType(type);
		fileAndFolder.setUserId(userId);
		fileAndFolder.setMappingPath(mappingPath);
		fileAndFolder.setDeleteFlag(fileObject);
		int ret = (int) getSession().save(fileAndFolder);
		return ret;
	}

	@Override
	public int delete(Integer id, String path) {
		String hql = "delete FileAndFolder as f where f.id=?";
		int ret = getSession().createQuery(hql).setInteger(0, id).executeUpdate();
		boolean flag = folderOperate.delete(path);
		return ret;
	}

	@Override
	public void deleteFolder(FileAndFolder folder) {
		getSession().delete(folder);
		String dir = folder.getFolderPath();
		boolean flag = folderOperate.deleteDirectory(dir);
	}

	@Override
	public int saveFileAndFolder(FileAndFolder fileAndFolder) {
		return (int) getSession().save(fileAndFolder);
	}

	@Override
	public List<FileAndFolder> pageReviwe(PageSource page, String hql) {
		Query query = getSession().createQuery(hql);
		query.setFirstResult((page.getCurrentPage() - 1) * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		List<FileAndFolder> list = query.list();
		return list;

	}

	@Override
	public List<FileAndFolder> getAll(String hql) {
		Query query = getSession().createQuery(hql);
		List<FileAndFolder> list = query.list();
		return list;
	}

}
