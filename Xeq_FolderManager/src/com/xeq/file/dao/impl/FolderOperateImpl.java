package com.xeq.file.dao.impl;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.xeq.file.dao.FolderOperate;

@Repository("FolderOperate")
public class FolderOperateImpl extends BaseDao implements FolderOperate {
	private Logger log = Logger.getLogger(FolderDaoImpl.class);

	@Override
	public boolean createRealFolder(String name, String path) {
		boolean ret = false;
		try {
			String newPath = path + name + "\\";
			System.out.println("----------" + newPath + "----------");
			File file = new File(newPath);
			if (!file.exists()) {// 路径不存在，创建
				file.mkdirs();
				ret = true;
				log.info("---------------Create Folder Success!---------------------");
			} else if (file.exists()) {
				log.info("folder is exist");
				ret = true;
			}
		} catch (Exception e) {
			log.info("file.mkdirs error");
		}
		return ret;
	}

	@Override
	public boolean delete(String path) {
		File file = new File(path);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				log.info("删除单个文件" + path + "成功！");
				return true;
			} else {
				log.info("删除单个文件" + path + "失败！");
				return false;
			}
		} else {
			log.info("删除单个文件失败：" + path + "不存在！");
			return false;
		}
	}

	@Override
	public boolean deleteDirectory(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator))
			dir = dir + File.separator;
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
			log.info("删除目录失败：" + dir + "不存在！");
			return false;
		}
		boolean flag = true;
		// 删除文件夹中的所有文件包括子目录
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = delete(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
			// 删除子目录
			else if (files[i].isDirectory()) {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag) {
			log.info("删除目录失败！");
			return false;
		}
		// 删除当前目录
		if (dirFile.delete()) {
			log.info("删除目录" + dir + "成功！");
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean removeFileOrFolder(String fromPath, String toPath) {
		File fold = new File(fromPath);// 某路径下的文件
		File fnewpath = new File(toPath);
		if (!fnewpath.exists()) {
			log.info("移动目标文件夹不存在");
			return false;
		}
		File fnew = new File(toPath + fold.getName());
		fold.renameTo(fnew);
		return true;
	}
}
