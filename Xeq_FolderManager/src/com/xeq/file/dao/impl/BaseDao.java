package com.xeq.file.dao.impl;

import java.io.InputStream;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("baseDao")
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
		String path = "";
		try {
			properties.load(inputStream);
			path=properties.getProperty("file.root.parent");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}
	
	
	
}
