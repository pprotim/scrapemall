package com.admintool.adv.app.dao.impl;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class BaseDaoImpl extends CustomHibernateDaoSupport {

	@SuppressWarnings("rawtypes")
	public List find(String query) {
		Query queryObject = getSessionFactory().getCurrentSession()
				.createQuery(query);
		return queryObject.list();
	}

	public Session getCurrentSession() {
		return getSessionFactory().getCurrentSession();
	}

	
}
