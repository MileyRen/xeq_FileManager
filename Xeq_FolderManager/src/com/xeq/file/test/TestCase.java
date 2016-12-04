package com.xeq.file.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gene.utils.User;
import com.ssh.xep.entity.JobInfo;
import com.xeq.file.dao.FolderOperate;
import com.xeq.file.dao.impl.BaseDao;
import com.xeq.file.domain.FileAndFolder;
import com.xeq.file.service.FolderService;
import com.xeq.file.service.JobsService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//@Controller("test")
public class TestCase extends BaseDao {
	private FolderService folderService;
	private JobsService jobsService;
	private FolderOperate folderOperate;
	ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

	public void ye() {
		jobsService = (JobsService) context.getBean("jobsService");
		String hql = "FROM JobInfo where userId=1";
		List<JobInfo> jList = jobsService.getJobList(hql);
		System.out.println(jList.size());
	}

	@Test
	public void testPath() {
		folderService = (FolderService) context.getBean("FolderService");
		List<String> list = new ArrayList<String>();
		list = getToPath(45, list, 1);
		StringBuffer sf = new StringBuffer();
		for (int i = list.size() - 1; i >= 0; i--) {
			sf.append(list.get(i) + File.separator);
		}
		String root_Path = sf.toString();
		System.out.println(root_Path);
	}

	public List<String> getToPath(Integer tId, List<String> list, Integer userId) {
		folderService = (FolderService) context.getBean("FolderService");
		FileAndFolder fgr = folderService.getById(tId);
		// 若有重名文件夹，则不移动
		// 判断要移动的文件夹名称与目的文件夹的子文件夹不重名
		list.add(fgr.getName());
		if (fgr.getParentFolderId() > -1) {
			return getToPath(fgr.getParentFolderId(), list, userId);
		} else {
			return list;
		}
	}

	@Test
	public void getTest() {
		folderService = (FolderService) context.getBean("FolderService");
		List<String> list = new ArrayList<String>();
		list = getToPath(11, 7, list);

		System.out.println("输出路径：");
		for (String string : list) {
			System.out.println(string);
		}
	}

	public List<String> getToPath(Integer fromId, Integer toId, List<String> list) {
		folderService = (FolderService) context.getBean("FolderService");
		FileAndFolder fgr = folderService.getById(toId);
		if (fgr.getParentFolderId() == fromId) {
			return null;
		} else {
			list.add(fgr.getName());
			if (fgr.getParentFolderId() > -1) {
				return getToPath(fromId, fgr.getParentFolderId(), list);
			} else {
				return list;
			}
		}
	}

	@Test
	public void testTree() {
		folderService = (FolderService) context.getBean("FolderService");
		String hqlFolder = "From FileAndFolder where userId=1  and type='folder'";
		List<FileAndFolder> lists = folderService.getAll(hqlFolder);

		JSONArray ja = new JSONArray();
		JSONObject jObject = new JSONObject();
		jObject.put("id", -1);
		jObject.put("text", "root");
		jObject.put("parentId", -2);
		jObject.put("iconCls", "icon-folder");

		JSONArray jsonArray = new JSONArray();
		for (FileAndFolder fileAndFolder : lists) {
			if (fileAndFolder.getParentFolderId() == -1) {
				jsonArray.add(getJson(fileAndFolder, 1));
			}
		}
		jObject.put("children", jsonArray);
		ja.add(jObject);
		System.out.println(ja.toString());
	}

	/** 获得当前级别的jsonObject */
	public JSONObject getJson(FileAndFolder fileAndFolder, Integer userId) {
		folderService = (FolderService) context.getBean("FolderService");
		Integer newPId = fileAndFolder.getId();
		String hqlFolder = "From FileAndFolder where userId=" + userId + " and type='folder' and parentFolderId="
				+ newPId;
		System.out.println("hal=" + hqlFolder);
		List<FileAndFolder> lists = folderService.getAll(hqlFolder);
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

	/** 将json 写入文件，返回一个文件地址 */
	public boolean writeJson(JSONArray jsonArray, String path) {
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file);
			fw.write(jsonArray.toString());
			fw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Test
	public void te() {
		folderService = (FolderService) context.getBean("FolderService");
		String hqlFolder = "From FileAndFolder where userId=1  and type='folder'";
		List<FileAndFolder> lists = folderService.getAll(hqlFolder);
		for (FileAndFolder fileAndFolder : lists) {
			System.out.println("doamin:=" + fileAndFolder.toString());
			System.out.println("delete=" + fileAndFolder.getDeleteFlag());

			try {
				Set<FileAndFolder> set = fileAndFolder.getDeleteFlagSets();

			} catch (Exception e) {

			}
			System.out.println("---------------------------------");
		}
	}

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
	public void ted() {
		String fileN = "User.hbm.xml";
		String tp = fileN.substring(fileN.indexOf("."));
		System.out.println(tp);
		String dd = fileN.substring(0, fileN.indexOf("."));
		System.out.println(dd);
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

}
