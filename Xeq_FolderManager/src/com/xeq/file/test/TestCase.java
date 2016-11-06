package com.xeq.file.test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

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
	public void testDel(){
		folderOperate = (FolderOperate) context.getBean("FolderOperate");
		boolean ret = folderOperate.deleteDirectory("I:\\xeqFileTest\\user1");
		Assert.assertEquals(true, ret);
	}
	
	@Test
	public void ted() {
		String name = "dddfasdad/*s)";
		if(name.contains("/"))System.out.println("name包含：/");

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
		System.out.println(folderService.create(1, "user1", -1, "I:\\xeqFileTest\\\\") + "创建成功");
	}

	@Test
	public void getAll() {
		folderService = (FolderService) context.getBean("FolderService");
		List<FileAndFolder> list = folderService.getByFolderOrFiles(1, -1);
		for (FileAndFolder fileAndFolder : list) {
			System.out.println("id=" + fileAndFolder.getId() + ",name=" + fileAndFolder.getName() + ",parentFolder="
					+ fileAndFolder.getParentFolderId() + ",time=" + fileAndFolder.getTime());
		}
	}

}
