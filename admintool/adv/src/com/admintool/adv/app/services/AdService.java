package com.admintool.adv.app.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.admintool.adv.app.beans.CrawlBean;
import com.admintool.adv.app.dao.AdDao;
import com.admintool.adv.app.entity.AdDate;
import com.admintool.adv.app.entity.Advertisement;
import com.admintool.adv.app.entity.CategorySubCategory;
import com.admintool.adv.app.entity.Company;
import com.admintool.adv.app.entity.Crawl;

@Service
public class AdService {
	
	@Autowired
	private AdDao adDao;
	
	public String fetchAllCategories(){
		return null;
		
	}
	
	public String fetchAllSubCategories(){
		return null;
	}
	
	public List<CategorySubCategory> fetchAllCategoriesSubCategories(){
		
		List<CategorySubCategory> list =  adDao.fetchAllCategoriesSubCategories();
		/*List<CategorySubCategoryBean> listBean = new ArrayList<CategorySubCategoryBean>();
		for(CategorySubCategory catSubCat : list) {
			
			CategorySubCategoryBean bean = new CategorySubCategoryBean();
			
			bean.setId(catSubCat.getId()+"");
			bean.setCategoryID(catSubCat.getCategoryID()+"");
			bean.setCategory(catSubCat.getCategory());
			bean.setSubCategoryID(catSubCat.getSubCategoryID()+"");
			bean.setSubCategory(catSubCat.getSubCategory());
			System.out.println(bean);
			listBean.add(bean);
		}*/
		
		return list;
	}
	
	public List<CrawlBean> searchCrawlDetails(Map<String, Object> searchCriteria){
		
		List<Crawl> list = adDao.searchCrawlDetails(searchCriteria);
		if(!list.isEmpty()) {
			List<CrawlBean> listCrawlBean = new ArrayList<CrawlBean>(list.size());
			for(Crawl crawl : list) {
				CrawlBean crawlBean = new CrawlBean();
				
				Company company = adDao.getCompanyById(crawl.getCompanyId());
				Advertisement advertisement = adDao.getAdvertisementById(crawl.getAdId());
				crawlBean.setCrawlId(crawl.getCrawlId()+"");
				crawlBean.setLogo(advertisement.getUrl());
				crawlBean.setCompanyName(company.getName());
				crawlBean.setBrandName(company.getBrand());
				crawlBean.setCategory(company.getCategory());
				crawlBean.setSubcategory(company.getSubCategory());
				crawlBean.setLastUpdatedDate(AdService.getDateFormat().format(crawl.getModifiedDateTime()));
				listCrawlBean.add(crawlBean);
			}
			return listCrawlBean;
		}
		return null;
	}
	
	@Transactional
	public boolean saveAndUpdate(CrawlBean crawlBean) throws Exception{
		
		System.out.println("Inside saveAndUpdate");
		
		Crawl crawl = adDao.getCrawlById(Integer.valueOf(crawlBean.getCrawlId()));
		
		Company company = adDao.getCompanyById(crawl.getCompanyId());
		AdDate adDate = adDao.getAdDateById(crawl.getDateId());
		
		Date lastModified = AdService.getCurrentDateTime();
		
		company.setName(crawlBean.getCompanyName());
		company.setBrand(crawlBean.getBrandName());
		company.setCategory(crawlBean.getCategory());
		company.setSubCategory(crawlBean.getSubcategory());
		company.setModifiedDateTime(lastModified);
		adDao.saveOrUpdate(company);
		
		adDate.setDatetime(lastModified);
		adDao.saveOrUpdate(adDate);
		
		crawl.setModifiedDateTime(lastModified);
		adDao.saveOrUpdate(crawl);
		
		return true;
		
	}
	
	public static Date getCurrentDateTime(){
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = AdService.getDateFormat();
	    Date currentDateTime = cal.getTime();
	    sdf.format(currentDateTime);
	    return currentDateTime;
	}
	
	public static SimpleDateFormat getDateFormat(){
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
	    return sdf;
	}

}
