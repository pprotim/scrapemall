package com.admintool.adv.app.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.admintool.adv.app.beans.SubCategoryBean;
import com.admintool.adv.app.entity.CategorySubCategory;
import com.admintool.adv.app.services.AdService;
import com.google.gson.Gson;

@Controller
public class CrawlController {

	private static final String CATEGORY_BEAN_LIST = "CATEGORY_BEAN_LIST";
	private static final String SUBCATEGORY_BEAN_LIST = "SUBCATEGORY_BEAN_LIST";
	private static final String SUB_AND_CATEGORY_BEAN_LIST = "SUB_AND_CATEGORY_BEAN_LIST";
	@Autowired
	private AdService adService; 
	
	@RequestMapping(value = {"/searchByDate"}, method = RequestMethod.GET)
	public @ResponseBody String searchByDate(@RequestParam(value = "searchDate", required = false) String searchDate,
			HttpServletRequest request, Model model) {

		System.out.println("searchDate="+searchDate);
		if(searchDate==null){
			model.addAttribute("errorDate", "Search Date is null");
			return "home";
		}
		
		String resultSearchDate = StringUtils.EMPTY;
		
		try{
			resultSearchDate = formatSearchDate(searchDate);
		}catch(Exception e) {
			resultSearchDate = "Invalid date format:"+e.getMessage();
			e.printStackTrace();
			model.addAttribute("errorDate", resultSearchDate);
			return "home";
		}
		
		Map<String, Object> searchCriteria = new HashMap<String, Object>();
		searchCriteria.put("datetime", resultSearchDate);
		
		//Getting from database
		List<CrawlBean> listCrawlBean = adService.searchCrawlDetails(searchCriteria);
		model.addAttribute("crawlList", listCrawlBean);
		
		List<CategoryBean> listCategory = getListCategory(CATEGORY_BEAN_LIST);
		model.addAttribute("listCategory", listCategory);
		
		List<SubCategoryBean> listSubCategory = getListSubCategory(SUBCATEGORY_BEAN_LIST);
		model.addAttribute("listSubCategory", listSubCategory);
		
		Gson gson = new Gson();
		String json = gson.toJson(listCrawlBean);
		model.addAttribute("crawlListJSON", json);
		
		return json;
	}
	
	@RequestMapping(value = { "/crawl" }, method = RequestMethod.GET)
	public String crawl() {

		System.out.println("GOING TO crawl");
		return "home";
	}
	
	@RequestMapping(value = { "/save" }, method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String save(HttpServletRequest request, Model model, 
			@RequestBody CrawlBean crawlBean) {

		
		String message = "Row updated successfully!!";
		try {
			if(crawlBean!=null) {
				if(StringUtils.isNotBlank(crawlBean.getCompanyName())
						&& StringUtils.isNotBlank(crawlBean.getBrandName())) {
					
					boolean isSuccess = adService.saveAndUpdate(crawlBean);
					if(!isSuccess) {
						message = "ERROR in updating database!!";
					}
				}else {
					message = "Missing information, add company and brand";
				}
			}else {
				message = "data is not valid format";
			}
		
		}catch(Exception e) {
			message = "EXCEPTION OCCURED : "+e.getMessage();
			System.out.println(ExceptionUtils.getStackTrace(e));
			
		}
		return message;
	}
	
	@RequestMapping(value = { "/historical" }, method = RequestMethod.GET)
	public String historical() {

		System.out.println("GOING TO historical");
		return "historical";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<CategoryBean> getListCategory(String CATEGORY_BEAN_LIST) {
		
		Map<String, List> mapOfCategorySubcategory = this.fetchAllCategoriesSubCategories();
		List<CategoryBean> listCategory = mapOfCategorySubcategory.get(CATEGORY_BEAN_LIST);
		
		return listCategory;
	}
	
	@RequestMapping(value = {"/getListCategory"}, method = RequestMethod.GET)
	public @ResponseBody List<CategoryBean> getListCategory(HttpServletRequest request, Model model) {
		
		List<CategoryBean> listCategory = (List<CategoryBean>)request.getSession().getAttribute(CATEGORY_BEAN_LIST);
		if(listCategory==null || listCategory.size()==0) {
			
			listCategory = this.getListCategory(CATEGORY_BEAN_LIST);
			request.getSession().setAttribute(CATEGORY_BEAN_LIST, listCategory);
			
		}
		
		return listCategory;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<SubCategoryBean> getListSubCategory(String SUBCATEGORY_BEAN_LIST) {
		
		Map<String, List> mapOfCategorySubcategory = this.fetchAllCategoriesSubCategories();
		List<SubCategoryBean> listSubCategory = mapOfCategorySubcategory.get(SUBCATEGORY_BEAN_LIST);
		
		return listSubCategory;
	}
	
	@RequestMapping(value = {"/getListSubCategory"}, method = RequestMethod.GET)
	public @ResponseBody List<SubCategoryBean> getListSubCategory(HttpServletRequest request, Model model) {
		
		List<SubCategoryBean> listSubCategory = (List<SubCategoryBean>)request.getSession().getAttribute(SUBCATEGORY_BEAN_LIST);
		if(listSubCategory==null || listSubCategory.size()==0) {
			
			listSubCategory = this.getListSubCategory(SUBCATEGORY_BEAN_LIST);
			request.getSession().setAttribute(SUBCATEGORY_BEAN_LIST, listSubCategory);
		
		}
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
	
	
	private String formatSearchDate(String searchDate) throws ParseException {
		
		String resultSearchDate = StringUtils.EMPTY;
		SimpleDateFormat searchDatef = new SimpleDateFormat("MM/dd/yyyy");//from UI page
		Date searchDateTime = searchDatef.parse(searchDate);
		System.out.println("Search Date from UI:"+searchDateTime);
	    SimpleDateFormat sdf = AdService.getDateFormat();
	    resultSearchDate = sdf.format(searchDateTime);
	    System.out.println("Search date after setting proper format:"+resultSearchDate);
	    return resultSearchDate;
	}
}
