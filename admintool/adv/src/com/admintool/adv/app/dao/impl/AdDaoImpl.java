package com.admintool.adv.app.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.admintool.adv.app.dao.AdDao;
import com.admintool.adv.app.entity.AdDate;
import com.admintool.adv.app.entity.Advertisement;
import com.admintool.adv.app.entity.CategorySubCategory;
import com.admintool.adv.app.entity.Channel;
import com.admintool.adv.app.entity.Company;
import com.admintool.adv.app.entity.Crawl;
import com.admintool.adv.app.entity.Website;
import com.admintool.adv.app.services.AdService;

@Repository(value = "adDao")
@Transactional
public class AdDaoImpl extends BaseDaoImpl implements AdDao{

	@Value("${hibernate.maxfetchrows}")
	private Integer maxRowsFetch;
	
	@Override
	public String fetchAllCategories() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String fetchAllSubCategories() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CategorySubCategory> fetchAllCategoriesSubCategories(){
		String baseQuery = "SELECT c FROM CategorySubCategory c";
		
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(baseQuery);
		List list = query.list();
		
		if(!list.isEmpty()) {
			System.out.println("Printing the fetched list="+list);
		}
		
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Crawl> searchCrawlDetails(Map<String, Object> searchCriteria) {
		String baseQuery = "SELECT c FROM Crawl c";
		
		Session session = getSessionFactory().getCurrentSession();
		
		baseQuery = getCriteriaValue(searchCriteria, baseQuery);

		Query query = session.createQuery(baseQuery);
		if(maxRowsFetch != null) {
			
			query.setMaxResults(maxRowsFetch);
		}
		
		List list = query.list();
		
		if(!list.isEmpty()) {
			System.out.println("Printing the fetched list="+list);
		}
		
		return list;
	}

	public AdDate getAdDateById(Integer adDateId) {
		String baseQuery = "SELECT a FROM AdDate a WHERE a.id=:adDateId";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(baseQuery);
		query.setParameter("adDateId", adDateId);
		AdDate adDate = (AdDate)query.uniqueResult();
		return adDate;
	}
	
	public Advertisement getAdvertisementById(Integer advertisementId) {
		String baseQuery = "SELECT a FROM Advertisement a WHERE a.id=:advertisementId";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(baseQuery);
		query.setParameter("advertisementId", advertisementId);
		Advertisement advertisement = (Advertisement)query.uniqueResult();
		return advertisement;
	}
	public CategorySubCategory getCategorySubCategoryById(Integer subCategoryId) {
		String baseQuery = "SELECT a FROM CategorySubCategory a WHERE a.subCategoryID=:subCategoryId";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(baseQuery);
		query.setParameter("subCategoryId", subCategoryId);
		CategorySubCategory categorySubCategory = (CategorySubCategory)query.uniqueResult();
		return categorySubCategory;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CategorySubCategory> getSubCategoryListByCategoryId(Integer categoryId) {
		
		String baseQuery = "SELECT c FROM CategorySubCategory c WHERE categoryID=:categoryIdParam";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(baseQuery);
		query.setParameter("categoryIdParam", categoryId);
		return query.list();
	}
	
	public Channel getChannelById(Integer channelId) {
		String baseQuery = "SELECT a FROM Channel a WHERE a.id=:channelId";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(baseQuery);
		query.setParameter("channelId", channelId);
		Channel channel = (Channel)query.uniqueResult();
		return channel;	
	}
	public Company getCompanyById(Integer companyId) {
		String baseQuery = "SELECT a FROM Company a WHERE a.id=:companyId";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(baseQuery);
		query.setParameter("companyId", companyId);
		Company company = (Company)query.uniqueResult();
		return company;	
	}
	public Crawl getCrawlById(Integer crawlId) {
		String baseQuery = "SELECT a FROM Crawl a WHERE a.id=:crawlId";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(baseQuery);
		query.setParameter("crawlId", crawlId);
		Crawl crawl = (Crawl)query.uniqueResult();
		return crawl;	
	}
	public Website getWebsiteById(Integer websiteId) {
		String baseQuery = "SELECT a FROM Website a WHERE a.id=:websiteId";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(baseQuery);
		query.setParameter("websiteId", websiteId);
		Website website = (Website)query.uniqueResult();
		return website;	
	}
	
	@SuppressWarnings("unchecked")
	public List<Company> isDuplicateCompanyData(String companyName, String brandName, String categoryName, String subCategoryName) {
		String baseQuery = "SELECT c FROM Company c WHERE c.name=:companyName and c.brand=:brandName and category=:categoryName "
				+ "and subCategory=:subCategoryName order by ModifiedDateTime desc";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(baseQuery);
		query.setParameter("companyName", companyName);
		query.setParameter("brandName", brandName);
		query.setParameter("categoryName", categoryName);
		query.setParameter("subCategoryName", subCategoryName);
		List<Company> companyList = (List<Company>)query.list();
		return companyList;	
	}
	
	
	@Override
	public void saveOrUpdate(Object object) {
		getSessionFactory().getCurrentSession().saveOrUpdate(object);
		
	}
	
	private String getCriteriaValue(Map<String, Object> searchCriteria, String baseQuery) {
		
		if(!searchCriteria.isEmpty()) {
			
			String fromDateTime = (String) searchCriteria.get(AdService.FROM_SEARCH_DATE);
			String toDateTime = (String) searchCriteria.get(AdService.TO_SEARCH_DATE);
			if(StringUtils.isNotBlank(fromDateTime) && StringUtils.isNotBlank(toDateTime)) {
				baseQuery = baseQuery + " WHERE createdDateTime >'"+fromDateTime+"' and createdDateTime <'"+toDateTime+"'" ;
			}
			
			System.out.println("Final query="+baseQuery);
			/*for(Map.Entry<String, Object> entry : searchCriteria.entrySet()) {
				System.out.println("Searching for entry.getKey()="+entry.getKey());
				if(entry.getKey().equalsIgnoreCase(AdService.FROM_SEARCH_DATE)) {
					baseQuery = baseQuery + " WHERE createdDateTime >'"+entry.getValue()+"' and createdDateTime <'"+ ;
				}
			}*/
		}
		
		return baseQuery;
	}

}
