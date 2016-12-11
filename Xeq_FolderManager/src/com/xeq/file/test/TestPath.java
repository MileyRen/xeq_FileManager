package com.xeq.file.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;
import org.junit.runners.model.TestTimedOutException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ssh.xep.entity.JobInfo;
import com.xeq.file.dao.FolderOperate;
import com.xeq.file.domain.FileAndFolder;
import com.xeq.file.service.FolderService;
import com.xeq.file.service.JobsService;

public class TestPath {
	private FolderService folderService;
	private FolderOperate folderOperate;
	private JobsService jobsService;
	ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

	@Test
	public void TestTime() {
		Date date = new Date();
		SimpleDateFormat ss = new SimpleDateFormat("YYYY-MM-dd");

		System.out.println(ss.format(date));
		String dateString = "";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
		try {
			Date date1 = sdf.parse(dateString);
			long time = date1.getTime();
			System.out.println(time);
		} catch (ParseException e) {
			System.out.println("0");
		}
	}

	@Test
	public void te() {
		String time = "1480853714302";
		long t = Long.parseLong(time);
		SimpleDateFormat ss = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		System.out.println(ss.format(t));

	}

	@Test
	public void testP() {
		folderService = (FolderService) context.getBean("FolderService");
		List<FileAndFolder> lists = new ArrayList<FileAndFolder>();
		lists = folderService.getAll("From FileAndFolder where userId=1");
		for (FileAndFolder fileAndFolder : lists) {
			System.out.println(
					fileAndFolder.getId() + ";" + fileAndFolder.getName() + ";" + fileAndFolder.getParentFolderId());
		}
	}

	// @Test
	// public void test() throws DocumentException {
	// String xmlstr = "<states>"
	// + "<script id=\"jbpm-unique-1\" name=\"name\" ret=\"name\" pid=\"1\"
	// bgTime=\"120303\" edTime=\"120304\" state=\"pending\" />"
	// + "<script id=\"jbpm-unique-2\" name=\"name\" ret=\"name\" pid=\"2\"
	// bgTime=\"120303\" edTime=\"120304\" state=\"pending\" />"
	// + "</states>";
	// Document document = DocumentHelper.parseText(xmlstr);
	// Element rootElement = document.getRootElement();
	// List<JobStep> lists = new ArrayList<JobStep>();
	// lists = getNodes(rootElement, lists);
	// for (JobStep jobStep : lists) {
	// System.out.println(jobStep.toString());
	// }
	// }
	//
	// public List<JobStep> getNodes(Element node, List<JobStep> lists) {
	// List<Attribute> listAttr = node.attributes();
	// JobStep jStep = new JobStep();
	// for (Attribute attr : listAttr) {// 遍历当前节点的所有属性
	// String name = attr.getName();
	// String value = attr.getValue();
	// System.out.println("属性名称：" + name + "属性值：" + value);
	// if (name == "id" || name.equals("id")) {
	// jStep.setId(value);
	// } else if (name == "name" || name.equals("name")) {
	// jStep.setName(value);
	// } else if (name == "ret" || name.equals("ret")) {
	// jStep.setRet(value);
	// } else if (name == "pid" || name.equals("pid")) {
	// jStep.setPid(value);
	// } else if (name == "bgTime" || name.equals("bgTime")) {
	// jStep.setBgTime(value);
	// } else if (name == "edTime" || name.equals("edTime")) {
	// jStep.setEdTime(value);
	// } else if (name == "state" || name.equals("state")) {
	// jStep.setState(value);
	// }
	// }
	// if (jStep.getId() != null) {
	// lists.add(jStep);
	// }
	// // 递归遍历当前节点所有的子节点
	// List<Element> listElement = node.elements();// 所有一级子节点的list
	// for (Element e : listElement) {// 遍历所有一级子节点
	// System.out.println("----------递归-----------");
	// getNodes(e, lists);// 递归
	// }
	// return lists;
	// }
}
