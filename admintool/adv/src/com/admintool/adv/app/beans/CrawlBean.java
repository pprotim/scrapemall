package com.admintool.adv.app.beans;

import org.springframework.stereotype.Component;

@Component
public class CrawlBean {

	private String crawlId;
	private String logo;
	private String companyName;
	private String brandName;
	private String categoryId;
	private String category;
	private String subcategoryId;
	private String subcategory;
	private String lastUpdatedDate;
	private String companyUrl;//homePage
	
	public String getCrawlId() {
		return crawlId;
	}
	public void setCrawlId(String crawlId) {
		this.crawlId = crawlId;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSubcategoryId() {
		return subcategoryId;
	}
	public void setSubcategoryId(String subcategoryId) {
		this.subcategoryId = subcategoryId;
	}
	public String getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}
	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
	public String getCompanyUrl() {
		return companyUrl;
	}
	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CrawlBean [crawlId=");
		builder.append(crawlId);
		builder.append(", logo=");
		builder.append(logo);
		builder.append(", companyName=");
		builder.append(companyName);
		builder.append(", brandName=");
		builder.append(brandName);
		builder.append(", categoryId=");
		builder.append(categoryId);
		builder.append(", category=");
		builder.append(category);
		builder.append(", subcategoryId=");
		builder.append(subcategoryId);
		builder.append(", subcategory=");
		builder.append(subcategory);
		builder.append(", lastUpdatedDate=");
		builder.append(lastUpdatedDate);
		builder.append(", companyUrl=");
		builder.append(companyUrl);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
