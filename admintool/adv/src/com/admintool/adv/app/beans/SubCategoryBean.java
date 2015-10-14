package com.admintool.adv.app.beans;

public class SubCategoryBean {
	
	private String SubCategoryID;
	private String subCategory;
	
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
		builder.append("SubCategoryBean [SubCategoryID=");
		builder.append(SubCategoryID);
		builder.append(", subCategory=");
		builder.append(subCategory);
		builder.append("]");
		return builder.toString();
	}
	
	
}
