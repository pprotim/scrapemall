package com.admintool.adv.app.beans;

import org.springframework.stereotype.Component;

@Component
public class CategorySubCategoryBean {
	
	private String id;
	private String categoryID;
	private String category;
	private String SubCategoryID;
	private String subCategory;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSubCategoryID() {
		return SubCategoryID;
	}
	public void setSubCategoryID(String subCategoryID) {
		SubCategoryID = subCategoryID;
	}
	public String getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CategorySubCategoryBean [id=");
		builder.append(id);
		builder.append(", categoryID=");
		builder.append(categoryID);
		builder.append(", category=");
		builder.append(category);
		builder.append(", SubCategoryID=");
		builder.append(SubCategoryID);
		builder.append(", subCategory=");
		builder.append(subCategory);
		builder.append("]");
		return builder.toString();
	}

}