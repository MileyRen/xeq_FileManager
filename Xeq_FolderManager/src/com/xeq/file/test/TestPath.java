package com.xeq.file.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xeq.file.dao.FolderOperate;
import com.xeq.file.domain.FileAndFolder;
import com.xeq.file.service.FolderService;

public class TestPath {
	private FolderService folderService;
	private FolderOperate folderOperate;
	ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
	
	@Test
	public void testP(){
		folderService = (FolderService) context.getBean("FolderService");
		List<FileAndFolder> lists = new ArrayList<FileAndFolder>();
		lists = folderService.getAll("From FileAndFolder where userId=1");
		for (FileAndFolder fileAndFolder : lists) {
			System.out.println(fileAndFolder.getId()+";"+fileAndFolder.getName()+";"+fileAndFolder.getParentFolderId());
		}
	}
}
