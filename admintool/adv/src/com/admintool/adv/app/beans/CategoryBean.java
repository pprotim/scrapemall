package com.admintool.adv.app.beans;

public class CategoryBean {

	private String categoryID;
	private String category;
	
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
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CategoryBean [categoryID=");
		builder.append(categoryID);
		builder.append(", category=");
		builder.append(category);
		builder.append("]");
		return builder.toString();
	}
}
