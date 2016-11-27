package com.xeq.file.dao.impl;

import java.io.IOException;
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

	public Integer pageSize() {
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Integer.parseInt(properties.getProperty("file.pageSize"));
	}

	public static void closeSession(Session session) {
		if (session != null) {
			session.close();
		}
	}

}
