package com.admintool.adv.app.dao;

import java.util.List;
import java.util.Map;

import com.admintool.adv.app.entity.AdDate;
import com.admintool.adv.app.entity.Advertisement;
import com.admintool.adv.app.entity.CategorySubCategory;
import com.admintool.adv.app.entity.Channel;
import com.admintool.adv.app.entity.Company;
import com.admintool.adv.app.entity.Crawl;
import com.admintool.adv.app.entity.Website;

public interface AdDao {

	public String fetchAllCategories();
	
	public String fetchAllSubCategories();
	
	public List<CategorySubCategory> fetchAllCategoriesSubCategories();
	
	public List<Crawl> searchCrawlDetails(Map<String, Object> searchCriteria);
	
	public AdDate getAdDateById(Integer adDateId) ;
	
	public Advertisement getAdvertisementById(Integer advertisementId) ;
	
	public CategorySubCategory getCategorySubCategoryById(Integer categorySubCategoryId) ;
	
	public List<CategorySubCategory> getSubCategoryListByCategoryId(Integer categoryId) ;
	
	public Channel getChannelById(Integer channelId) ;
	
	public Company getCompanyById(Integer companyId) ;
	
	public Crawl getCrawlById(Integer crawlId) ;
	
	public Website getWebsiteById(Integer websiteId);
	
	public void saveOrUpdate(Object object);
}
