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
import com.admintool.adv.app.beans.SubCategoryBean;
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
				
		return list;
	}
	
	public List<SubCategoryBean> getSubCategoryListByCategoryId(Integer categoryId){
		
		List<CategorySubCategory> listSubCategory =  adDao.getSubCategoryListByCategoryId(categoryId);
		List<SubCategoryBean> listSubCategoryBean = new ArrayList<SubCategoryBean>();
		for(CategorySubCategory catSubCat : listSubCategory) {
			
			SubCategoryBean subCategoryBean = new SubCategoryBean();
			subCategoryBean.setSubCategoryID(catSubCat.getSubCategoryID()+"");
			subCategoryBean.setSubCategory(catSubCat.getSubCategory());
			listSubCategoryBean.add(subCategoryBean);
		}
				
		return listSubCategoryBean;
	}
	
	public List<CrawlBean> searchCrawlDetails(Map<String, Object> searchCriteria){
		
		List<Crawl> list = adDao.searchCrawlDetails(searchCriteria);
		if(!list.isEmpty()) {
			List<CrawlBean> listCrawlBean = new ArrayList<CrawlBean>(list.size());
			for(Crawl crawl : list) {
				CrawlBean crawlBean = new CrawlBean();
				
				Advertisement advertisement = adDao.getAdvertisementById(crawl.getAdId());
				crawlBean.setCrawlId(crawl.getCrawlId()+"");
				if(advertisement!=null)
				crawlBean.setLogo(advertisement.getUrl());
				if(crawl.getCompanyId()!=null) {
					
					Company company = adDao.getCompanyById(crawl.getCompanyId());
					crawlBean.setCompanyName(company.getName());
					crawlBean.setBrandName(company.getBrand());
					crawlBean.setCategory(company.getCategory());
					crawlBean.setSubcategory(company.getSubCategory());
				}
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
		Date lastModified = AdService.getCurrentDateTime();
		Company company = null;
		if(crawl.getCompanyId()!=null) {
			company = adDao.getCompanyById(crawl.getCompanyId());
			if(company!=null) {
				company = getCompanyData(company, crawlBean, lastModified);
				adDao.saveOrUpdate(company);
				
			}else {
				company = getCompanyData(company, crawlBean, lastModified);
				adDao.saveOrUpdate(company);
			}
			
		} else {
			
			company = getCompanyData(null, crawlBean, lastModified);
			adDao.saveOrUpdate(company);
		}
		
		AdDate adDate = adDao.getAdDateById(crawl.getDateId());
		if(adDate!=null) {
			adDate.setDatetime(lastModified);
			adDao.saveOrUpdate(adDate);
		}
		
		crawl.setModifiedDateTime(lastModified);
		crawl.setCompanyId(company.getId());
		adDao.saveOrUpdate(crawl);
		return true;
		
	}
	
	private Company getCompanyData(Company companyParam, CrawlBean crawlBean, Date lastModified) {
		Company company = null;
		if(companyParam==null)
			company = new Company();
		else
			company = companyParam;
		
		company.setName(crawlBean.getCompanyName());
		company.setBrand(crawlBean.getBrandName());
		company.setCategory(crawlBean.getCategory());
		company.setSubCategory(crawlBean.getSubcategory());
		company.setModifiedDateTime(lastModified);
		return company;
				
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
