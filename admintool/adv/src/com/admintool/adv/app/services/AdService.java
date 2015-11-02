package com.admintool.adv.app.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
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
import com.admintool.adv.app.plugin.S3Plugin;
import com.amazonaws.util.IOUtils;

@Service
public class AdService {
	
	@Autowired
	private AdDao adDao;
	
	@Autowired
	private S3Plugin s3Plugin; 
	
	@Autowired
	private ServletContext context; 
	
	public final static String USER_TEMP_DIRECTORY = "userTempDirectory";
	public final static String IMAGE_TEMP_FOLDER = "imagesTempFolder";
	
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
	
	public List<CrawlBean> searchCrawlDetails(Map<String, Object> searchCriteria,
			HttpSession session){
		
		String tempDirectory = this.getUserDirectory(session);
		String tempFolder = (String)session.getAttribute(AdService.IMAGE_TEMP_FOLDER);
		
		List<Crawl> list = adDao.searchCrawlDetails(searchCriteria);
		if(!list.isEmpty()) {
			List<CrawlBean> listCrawlBean = new ArrayList<CrawlBean>(list.size());
			int count = 0;
			for(Crawl crawl : list) {
				CrawlBean crawlBean = new CrawlBean();
				
				Advertisement advertisement = adDao.getAdvertisementById(crawl.getAdId());
				crawlBean.setCrawlId(crawl.getCrawlId()+"");
				
				if(advertisement!=null){
					String extension = advertisement.getType();
					String url = advertisement.getUrl();
					//String logoUrl = crawlBean.setLogo(getImageFromS3URL(".jpg","https://s3-ap-southeast-1.amazonaws.com/adsrepo/img20151028194003602",(++count), tempDirectory, tempFolder));
					String logoUrl = getImageFromS3URL(extension,url,(++count),tempDirectory, tempFolder);
					crawlBean.setLogo(logoUrl);
					
				}
				
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
	
	private String getImageFromS3URL(String extensionType, String url, 
			int count, String tempDirectory, String tempFolder) {
		
		String fileLocation = null;
		String keyName = null;
		if(url == null) return null;
		
		if(url.contains("/") && url.contains("amazonaws.com")) {
			keyName = url.substring(url.lastIndexOf("/")+1);
			if(StringUtils.isNotBlank(keyName)) {
				try {
					InputStream inputStream = s3Plugin.getImageFromS3(keyName);
					fileLocation = tempDirectory+File.separator+keyName+extensionType;
					keyName = tempFolder+File.separator+keyName+extensionType;
					IOUtils.copy(inputStream, new FileOutputStream(fileLocation));
					System.out.println("tmpdir====keyName="+keyName);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return keyName;
	}
	
	private String getUserDirectory(HttpSession session) {
		
		String imagesDirectory = context.getRealPath("/images");
		System.out.println("imagesDirectory path="+imagesDirectory);
		
		String userId = (String)session.getAttribute("USER");
		Date date = new Date();
		long sysTime = date.getTime();
		String imagesTempFolder = userId+"_"+sysTime;
		String folderLocation = "";
		System.out.println("Printing imagesTempFolder========================"+imagesTempFolder);
		
		File tmpdir = new File(imagesDirectory+File.separator+imagesTempFolder);
		if(!tmpdir.exists()) {
			if (tmpdir.mkdir()) {
				System.out.println("Directory created");
			}else{
				System.out.println("Directory PATH NOT FOUND");
			}	
		}
		
		folderLocation = tmpdir+"";
		session.setAttribute(AdService.IMAGE_TEMP_FOLDER, imagesTempFolder);
		session.setAttribute(AdService.USER_TEMP_DIRECTORY, folderLocation);
		System.out.println("Printing folderLocation========================"+folderLocation);
		return folderLocation;
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
