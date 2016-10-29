package com.xeq.file.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xeq.file.dao.FolderDao;
import com.xeq.file.dao.FolderOperate;
import com.xeq.file.domain.FileAndFolder;

@Repository("FolderDao")
public class FolderDaoImpl extends BaseDao implements FolderDao {
	private Logger log = Logger.getLogger(FolderDaoImpl.class);

	@Autowired
	private FolderOperate folderOperate;

	public void setFolderOperate(FolderOperate folderOperate) {
		this.folderOperate = folderOperate;
	}

	@Override
	public int createFolder(Integer userId, String name, Integer parentFolderId, String folderPath) {
		System.out.println("根目录 =" + rootPath());
		log.debug("-----------创建文件夹---------------");
		FileAndFolder fileAndFolder = new FileAndFolder();
		fileAndFolder.setName(name);
		fileAndFolder.setParentFolderId(parentFolderId);
		fileAndFolder.setSize("");
		fileAndFolder.setTime(new Date());
		fileAndFolder.setType("folder");
		fileAndFolder.setUserId(userId);
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
				int ret = createFolder(userId, name, parentFolderId, rootPath());
				list = getByFolderOrFiles(userId, parentFolderId);
			} /*
				 * else { list = getByFolderOrFiles(userId,
				 * list.get(0).getId()); }
				 */
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
			Integer userId) {
		FileAndFolder fileAndFolder = new FileAndFolder();
		fileAndFolder.setFolderPath(folderPath);
		fileAndFolder.setName(filename);
		fileAndFolder.setParentFolderId(parentFolderId);
		fileAndFolder.setSize(size);
		fileAndFolder.setTime(new Date());
		fileAndFolder.setType(type);
		fileAndFolder.setUserId(userId);
		int ret = (int) getSession().save(fileAndFolder);
		return ret;
	}

	@Override
	public int delete(Integer id ) {
		String hql="delete FileAndFolder as f where f.id=?";
		int ret =getSession().createQuery(hql).setInteger(0,id).executeUpdate();
		return ret;
	}

}
