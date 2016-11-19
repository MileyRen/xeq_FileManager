package com.xeq.file.test;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xeq.file.dao.FolderOperate;
import com.xeq.file.dao.impl.BaseDao;
import com.xeq.file.domain.FileAndFolder;
import com.xeq.file.service.FolderService;

//@Controller("test")
public class TestCase extends BaseDao {
	private FolderService folderService;
	private FolderOperate folderOperate;
	ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

	@Test
	public void testmove() {
		File fold = new File("e://java//java");// 某路径下的文件
		String strNewPath = "e://java//new file1//";// 新路径
		File fnewpath = new File(strNewPath);
		if (!fnewpath.exists())
			System.out.println("不存在该路径");
		File fnew = new File(strNewPath + fold.getName());
		fold.renameTo(fnew);
	}

	@Test
	public void testDel() {
		folderOperate = (FolderOperate) context.getBean("FolderOperate");
		boolean ret = folderOperate.deleteDirectory("I:\\xeqFileTest\\user1");
		Assert.assertEquals(true, ret);
	}

	@Test
	public void ted() {
		String name = "dddfasdad/*s)";
		if (name.contains("/"))
			System.out.println("name包含：/");

	}

	// 过滤特殊字符
	public String StringFilter(String str) throws PatternSyntaxException {
		// 只允许字母和数字 // String regEx ="[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[^?|\\/;*\"<>]+";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();

	}

	@Test
	public void testStringFilter() throws PatternSyntaxException {
		String str = "d!\\|/;*a\"”<>]da^ds?";
		System.out.println(str);
		System.out.println(StringFilter(str));
	}

	@Test
	public void te() {
		System.out.println(rootPath());
	}

	@Test
	public void createFolder() {
		folderOperate = (FolderOperate) context.getBean("FolderOperate");
		folderOperate.createRealFolder("2_root", "I:\\xeqFileTest\\");
	}

	@Test
	public void create() {
		folderService = (FolderService) context.getBean("FolderService");
		System.out.println(folderService.create(2, "user_1", -1, "I:\\xeqFileTest\\", null) + "创建成功");
	}

	@Test
	public void getAll() {
		folderService = (FolderService) context.getBean("FolderService");
		List<FileAndFolder> list = folderService.getByFolderOrFiles(1, 1);
		for (FileAndFolder fileAndFolder : list) {
			System.out.println("id=" + fileAndFolder.getId() + ",name=" + fileAndFolder.getName() + ",parentFolder="
					+ fileAndFolder.getParentFolderId() + ",time=" + fileAndFolder.getTime());
		}
	}

	@Test
	public void del() {
		folderService = (FolderService) context.getBean("FolderService");
		FileAndFolder folder = new FileAndFolder();
		folder.setId(15);
		folderService.deleteFolder(folder);
	}

	@Test
	public void testUpdate() {
		folderService = (FolderService) context.getBean("FolderService");
		FileAndFolder f = folderService.getById(2);
		System.out.println(f.toString());
		f.setName("name_Folder");
		System.out.println(f.toString());
		folderService.update(f);
		
	}

}
