package com.admintool.adv.app.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.admintool.adv.app.beans.CategoryBean;
import com.admintool.adv.app.beans.CategorySubCategoryBean;
import com.admintool.adv.app.beans.CrawlBean;
import com.admintool.adv.app.beans.ResultBean;
import com.admintool.adv.app.beans.SubCategoryBean;
import com.admintool.adv.app.entity.CategorySubCategory;
import com.admintool.adv.app.services.AdService;
import com.google.gson.Gson;

@Controller
public class CrawlController {

	private static final String CATEGORY_BEAN_LIST = "CATEGORY_BEAN_LIST";
	private static final String SUBCATEGORY_BEAN_LIST = "SUBCATEGORY_BEAN_LIST";
	private static final String SUB_AND_CATEGORY_BEAN_LIST = "SUB_AND_CATEGORY_BEAN_LIST";
	
	@Value("${message_missing_information}")
	private String MISSING_INFORMATION;
	
	@Value("${message_invalid_format}")
	private String INVALID_FORMAT;
	
	@Value("${message_failure}")
	private String FAILURE_MESSAGE;
	
	@Autowired
	private AdService adService; 
	
	@RequestMapping(value = {"/searchByDate"}, method = RequestMethod.GET)
	public @ResponseBody String searchByDate(@RequestParam(value = "searchDate", required = false) String searchDate,
			HttpServletRequest request, Model model) {
		
	    SimpleDateFormat sdf =
	          new SimpleDateFormat(AdService.YYYY_MM_DD_HH_MM_SS_SS);
	    Calendar c1 = Calendar.getInstance(); // start time
	    String startTime = sdf.format(c1.getTime());
	    System.out.println("Start time="+startTime);
	    
	    
		System.out.println("searchDate="+searchDate);
		if(searchDate==null){
			model.addAttribute("errorDate", "Search Date is null");
			return "home";
		}
		
		String searchFromDate = StringUtils.EMPTY;
		String searchToDate = StringUtils.EMPTY;
		try{
			searchFromDate = formatFromSearchDate(searchDate);
			searchToDate = formatToSearchDate(searchDate);
		}catch(Exception e) {
			searchFromDate = "Invalid date format:"+e.getMessage();
			e.printStackTrace();
			model.addAttribute("errorDate", searchFromDate);
			return "home";
		}
		
		Map<String, Object> searchCriteria = new HashMap<String, Object>();
		searchCriteria.put(AdService.FROM_SEARCH_DATE, searchFromDate);
		searchCriteria.put(AdService.TO_SEARCH_DATE, searchToDate );
		
		//Getting from database
		HttpSession session = request.getSession();
		List<CrawlBean> listCrawlBean = adService.searchCrawlDetails(searchCriteria,session);
		model.addAttribute("crawlList", listCrawlBean);
		
		Set<CategoryBean> setCategory = getListCategory(CATEGORY_BEAN_LIST);
		model.addAttribute("listCategory", setCategory);
		System.out.println("setCategory in searchCrawlDetails="+setCategory);
		
		List<SubCategoryBean> listSubCategory = getListSubCategory(SUBCATEGORY_BEAN_LIST);
		model.addAttribute("listSubCategory", listSubCategory);
		System.out.println("listSubCategory in searchCrawlDetails="+listSubCategory);
		
		Gson gson = new Gson();
		String json = gson.toJson(listCrawlBean);
		model.addAttribute("crawlListJSON", json);
		
		Calendar c2 = Calendar.getInstance(); // end time
		String endTime = sdf.format(c2.getTime());
	    System.out.println("End time="+endTime);
	    
	    System.out.println("Difference time="+((c2.getTimeInMillis()-c1.getTimeInMillis())/1000)+" seconds");
		
		return json;
	}
	
	@RequestMapping(value = { "/crawl" }, method = RequestMethod.GET)
	public String crawl() {

		System.out.println("GOING TO crawl");
		return "home";
	}
	
	@RequestMapping(value = { "/save" }, method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultBean save(HttpServletRequest request, Model model, 
			@RequestBody CrawlBean crawlBean) {

		
		ResultBean resultBean = new ResultBean();
		try {
			if(crawlBean!=null) {
				if(StringUtils.isNotBlank(crawlBean.getCompanyName())
						&& StringUtils.isNotBlank(crawlBean.getBrandName())
							&& StringUtils.isNotBlank(crawlBean.getCategory())
								&& StringUtils.isNotBlank(crawlBean.getSubcategory())) {
					
					resultBean = adService.saveAndUpdate(crawlBean);
					if(resultBean!=null) {
						String message = resultBean.getMessage();
						System.err.println("message="+message);
						resultBean.setCrawlBean(crawlBean);
					} else {
						resultBean = new ResultBean();
						resultBean.setSucess(Boolean.FALSE);
						resultBean.setMessage(FAILURE_MESSAGE);
					}
				} else {
					resultBean.setMessage(MISSING_INFORMATION);
				}
			}else {
				resultBean.setMessage(INVALID_FORMAT);
			}
		
		}catch(Exception e) {
			resultBean.setMessage("EXCEPTION OCCURED : "+e.getMessage());
			System.out.println(ExceptionUtils.getStackTrace(e));
			
		}
		return resultBean;
	}
	
	@RequestMapping(value = { "/historical" }, method = RequestMethod.GET)
	public String historical() {

		System.out.println("GOING TO historical");
		return "historical";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Set<CategoryBean> getListCategory(String CATEGORY_BEAN_LIST) {
		
		Map<String, List> mapOfCategorySubcategory = this.fetchAllCategoriesSubCategories();
		List<CategoryBean> listCategory = mapOfCategorySubcategory.get(CATEGORY_BEAN_LIST);
		
		Set<CategoryBean> setCategory = new TreeSet<CategoryBean>();
		setCategory.addAll(listCategory);
		
		System.out.println("setCategory in getListCategory method="+setCategory);
		
		return setCategory;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"/getListCategory"}, method = RequestMethod.GET)
	public @ResponseBody Set<CategoryBean> getListCategory(HttpServletRequest request, Model model) {
		
		Set<CategoryBean> setCategory = (Set<CategoryBean>)request.getSession().getAttribute(CATEGORY_BEAN_LIST);
		if(setCategory==null || setCategory.size()==0) {
			
			setCategory = this.getListCategory(CATEGORY_BEAN_LIST);
			request.getSession().setAttribute(CATEGORY_BEAN_LIST, setCategory);
		}
		
		return setCategory;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<SubCategoryBean> getListSubCategory(String SUBCATEGORY_BEAN_LIST) {
		
		Map<String, List> mapOfCategorySubcategory = this.fetchAllCategoriesSubCategories();
		List<SubCategoryBean> listSubCategory = mapOfCategorySubcategory.get(SUBCATEGORY_BEAN_LIST);
		Collections.sort(listSubCategory);
		
		return listSubCategory;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"/getListSubCategory"}, method = RequestMethod.GET)
	public @ResponseBody List<SubCategoryBean> getListSubCategory(HttpServletRequest request, Model model) {
		
		List<SubCategoryBean> listSubCategory = (List<SubCategoryBean>)request.getSession().getAttribute(SUBCATEGORY_BEAN_LIST);
		if(listSubCategory==null || listSubCategory.size()==0) {
			
			listSubCategory = this.getListSubCategory(SUBCATEGORY_BEAN_LIST);
			request.getSession().setAttribute(SUBCATEGORY_BEAN_LIST, listSubCategory);
		
		}
		return listSubCategory;
	}
	
	@RequestMapping(value = {"/getListSubCategoryByCategoryId"}, method = RequestMethod.GET)
	public @ResponseBody List<SubCategoryBean> getListSubCategoryByCategoryId(HttpServletRequest request, Model model,
			@RequestParam(value = "categoryId", required = false) String categoryIdString) {
		
		Integer categoryId = 0;
		if(categoryIdString!=null){
			try{
			categoryId = Integer.parseInt(categoryIdString); 
			}catch(NumberFormatException nfe) {
				nfe.printStackTrace();
			}
		}
		List<SubCategoryBean> listSubCategory = adService.getSubCategoryListByCategoryId(categoryId);
		
		model.addAttribute("listSubCategoryDropdown", listSubCategory);
		
		return listSubCategory;
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String, List> fetchAllCategoriesSubCategories(){
		List<CategorySubCategory> list =  adService.fetchAllCategoriesSubCategories();
		Map<String, List> mapOfCategorySubcategory = new HashMap<String, List>();
		
		if(!list.isEmpty()) {
			
			List<CategoryBean> listCategory = new ArrayList<CategoryBean>();
			List<SubCategoryBean> listSubCategory = new ArrayList<SubCategoryBean>();
			List<CategorySubCategoryBean> listBean = new ArrayList<CategorySubCategoryBean>();
			
			for(CategorySubCategory catSubCat : list) {
				
				CategoryBean categoryBean = new CategoryBean();
				SubCategoryBean subCategoryBean = new SubCategoryBean();
				
				categoryBean.setCategoryID(catSubCat.getCategoryID()+"");
				categoryBean.setCategory(catSubCat.getCategory());
				
				subCategoryBean.setSubCategoryID(catSubCat.getSubCategoryID()+"");
				subCategoryBean.setSubCategory(catSubCat.getSubCategory());
				
				CategorySubCategoryBean bean = new CategorySubCategoryBean();
				
				bean.setId(catSubCat.getSubCategoryID()+"");
				bean.setCategoryID(catSubCat.getCategoryID()+"");
				bean.setCategory(catSubCat.getCategory());
				bean.setSubCategoryID(catSubCat.getSubCategoryID()+"");
				bean.setSubCategory(catSubCat.getSubCategory());
				
				listCategory.add(categoryBean);
				listSubCategory.add(subCategoryBean);
				listBean.add(bean);
			}
			
			mapOfCategorySubcategory.put(CATEGORY_BEAN_LIST, listCategory);
			mapOfCategorySubcategory.put(SUBCATEGORY_BEAN_LIST, listSubCategory);
			mapOfCategorySubcategory.put(SUB_AND_CATEGORY_BEAN_LIST, listBean);
				
		}
		
		return mapOfCategorySubcategory;
	}
	
	
	private String formatFromSearchDate(String searchDate) throws ParseException {
		
		String resultSearchDate = StringUtils.EMPTY;
		SimpleDateFormat searchDatef = new SimpleDateFormat("MM/dd/yyyy");//from UI page
		Date searchDateTime = searchDatef.parse(searchDate);
		System.out.println("Search From Date from UI:"+searchDateTime);
	    SimpleDateFormat sdf = AdService.getDateFormat();
	    resultSearchDate = sdf.format(searchDateTime);
	    System.out.println("Search date after setting proper format:"+resultSearchDate);
	    return resultSearchDate;
	}
	
	private String formatToSearchDate(String searchDate) throws ParseException {
		
		String resultSearchToDate = StringUtils.EMPTY;
		SimpleDateFormat searchDatef = new SimpleDateFormat("MM/dd/yyyy");//from UI page
		Date searchDateTime = searchDatef.parse(searchDate);
		Date searchToDateTime = new Date(searchDateTime.getTime()+86400000L);
		System.out.println("Search To Date:"+searchToDateTime);
	    SimpleDateFormat sdf = AdService.getDateFormat();
	    resultSearchToDate = sdf.format(searchToDateTime);
	    System.out.println("Search TO date after setting proper format:"+resultSearchToDate);
	    return resultSearchToDate;
	}
	
	public String formatSearchDateFForS3(String searchDate) throws ParseException {
		
		String resultSearchDateForS3 = StringUtils.EMPTY;
		SimpleDateFormat searchDatef = new SimpleDateFormat("MM/dd/yyyy");//from UI page
		Date searchDateTime = searchDatef.parse(searchDate);
		System.out.println("Search Date from UI:"+searchDateTime);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
	    resultSearchDateForS3 = sdf.format(searchDateTime);
	    System.out.println("Search date after setting proper format resultSearchDateForS3:"+resultSearchDateForS3);
	    return resultSearchDateForS3;
	}
}
