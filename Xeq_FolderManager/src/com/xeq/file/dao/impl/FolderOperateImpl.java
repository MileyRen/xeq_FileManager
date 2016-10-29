package com.xeq.file.dao.impl;

import java.io.File;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.xeq.file.dao.FolderOperate;

@Repository("FolderOperate")
public class FolderOperateImpl extends BaseDao implements FolderOperate {

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
				System.out.println("---------------Create Folder Success!---------------------");
			} else if (file.exists()) {
				System.out.println("folder is exist");
			}
		} catch (Exception e) {
			System.out.println("file.mkdirs error");
		}
		return ret;
	}

	@Override
	public boolean delete(String path) {
		return false;
	}
}
