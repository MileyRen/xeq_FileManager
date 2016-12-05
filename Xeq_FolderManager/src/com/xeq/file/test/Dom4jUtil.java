package com.xeq.file.test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

public class Dom4jUtil {
	public static void main(String[] args) throws DocumentException {

		String xml = "<resp>" + "<name>jack</name>" + "<age>21</age>" + "<birthday>1990-02-01</birthday>"
				+ "<sex>man</sex>" + "</resp>";
		Person student = getPerson(xml);
		System.out.println(
				student.getName() + "|" + student.getAge() + "|" + student.getBirthday() + "|" + student.getSex());
		System.out.println("person对象");
		String modelXMl = "<name>jack</name>" + "<age>21</age>" + "<birthday>1990-02-01</birthday>" + "<sex>man</sex>";
		String xmlList = "<resp>" + "<desc>0</desc>" + "<depict>ok</depict>" + "<content>" + modelXMl + "</content>"
				+ "<content>" + modelXMl + "</content>" + "<content>" + modelXMl + "</content>" + "</resp>";
		System.out.println("person集合列表");
		List<Person> list = getPersonList(xmlList);
		for (Person person : list) {
			System.out.println(
					person.getName() + "|" + person.getAge() + "|" + person.getBirthday() + "|" + person.getSex());
		}
	}

	/**
	 * 把xml格式转化为person对象
	 * 
	 * @param xml
	 * @return
	 */
	public static Person getPerson(String xml) {
		Person person = null;
		InputSource in = new InputSource(new StringReader(xml));
		in.setEncoding("UTF-8");
		SAXReader reader = new SAXReader();
		Document document;
		try {
			document = reader.read(in);
			Element root = document.getRootElement();
			person = (Person) XmlUtil.fromXmlToBean(root, Person.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("数据解析错误");

		}
		return person;
	}

	/**
	 * 把xml转化为person集合
	 * 
	 * @param xml
	 * @return
	 */
	public static List<Person> getPersonList(String xml) {

		Document doc = null;
		List<Person> list = new ArrayList<Person>();
		try {

			// 读取并解析XML文档

			// SAXReader就是一个管道，用一个流的方式，把xml文件读出来

			// SAXReader reader = new SAXReader(); //User.hbm.xml表示你要解析的xml文档

			// Document document = reader.read(new File("User.hbm.xml"));

			// 下面的是通过解析xml字符串的

			doc = DocumentHelper.parseText(xml); // 将字符串转为XML

			Element rootElt = doc.getRootElement(); // 获取根节点

			System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称

			String returnCode = rootElt.elementTextTrim("desc");
			if (!"0".equals(returnCode)) {
				System.out.println("后台数据返回有问题");
				return null;
			}

			Iterator<Element> it = rootElt.elementIterator("content");// 获取根节点下所有content
			while (it.hasNext()) {
				Element elementGroupService = (Element) it.next();
				Person baseBean = (Person) XmlUtil.fromXmlToBean(elementGroupService, Person.class);
				list.add(baseBean);
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("数据解析错误");
		}

		return list;
	}

}
