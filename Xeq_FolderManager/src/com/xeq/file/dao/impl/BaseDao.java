package com.xeq.file.dao.impl;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseDao {

	private SessionFactory sessionFactory;
	Properties properties = new Properties();
	InputStream inputStream = this.getClass().getResourceAsStream("/filesource.properties");

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	public  String rootPath() {
		//InputStream in;
		String path = "";
		try {
			/*in = new BufferedInputStream(new FileInputStream("/conf/filesource.properties"));
			Properties pro = new Properties();
			pro.load(in);
			path = pro.getProperty("file.root.parent");*/
			properties.load(inputStream);
			path=properties.getProperty("file.root.parent");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}
}
