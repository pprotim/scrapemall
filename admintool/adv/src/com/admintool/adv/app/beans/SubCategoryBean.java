package com.admintool.adv.app.beans;

import java.io.Serializable;

public class SubCategoryBean implements Comparable<SubCategoryBean>, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4556605220699123043L;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((SubCategoryID == null) ? 0 : SubCategoryID.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubCategoryBean other = (SubCategoryBean) obj;
		if (SubCategoryID == null) {
			if (other.SubCategoryID != null)
				return false;
		} else if (!SubCategoryID.equals(other.SubCategoryID))
			return false;
		return true;
	}
	@Override
	public int compareTo(SubCategoryBean o) {
		if (this == o)
			return 0;
		if (o == null)
			return 0;
		
		SubCategoryBean other = (SubCategoryBean) o;
		if(subCategory == null) return 0;
		if(subCategory != null) {
			return subCategory.compareTo(other.subCategory);
		}
		return 0;
	}
	
}
