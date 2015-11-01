package com.admintool.adv.app.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import com.admintool.adv.app.entity.AdDate;
import com.admintool.adv.app.entity.Advertisement;
import com.admintool.adv.app.entity.CategorySubCategory;
import com.admintool.adv.app.entity.Channel;
import com.admintool.adv.app.entity.Company;
import com.admintool.adv.app.entity.Website;

@SuppressWarnings("deprecation")
public class AdDaoImplTest {

	private SessionFactory sessionFactory;
	private AdDaoImpl adDaoImpl;
	
	
	private void setUpSessionFactory() {
		// setup the session factory
	      Configuration configuration = new AnnotationConfiguration();
	      configuration.addAnnotatedClass(AdDate.class)
	        .addAnnotatedClass(Advertisement.class)
	        .addAnnotatedClass(CategorySubCategory.class)
	        .addAnnotatedClass(Channel.class)
	        .addAnnotatedClass(Company.class)
	        .addAnnotatedClass(Website.class)
	        ;
	      configuration.setProperty("hibernate.dialect",
	        "org.hibernate.dialect.MySQLDialect");
	      configuration.setProperty("hibernate.connection.driver_class",
	        "com.mysql.jdbc.Driver");
	      configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/exodus");
	      configuration.setProperty("hibernate.hbm2ddl.auto", "update");
	      configuration.setProperty("hibernate.current_session_context_class", "thread");
	      configuration.setProperty("hibernate.connection.username", "ppm1");
	      configuration.setProperty("hibernate.connection.password", "password");
	      sessionFactory = configuration.buildSessionFactory();
	}
	
	//@Before
	public void setUp(){
		//setUpSessionFactory();
		adDaoImpl = new AdDaoImpl();
		adDaoImpl.setSessionFactory(sessionFactory);
	}
	
	
	//@Test
	public void testFetchCategorySubcategories() {
		List<CategorySubCategory> list = adDaoImpl.fetchAllCategoriesSubCategories();
		//Assert.assertNotNull(list);
	}
	
}
