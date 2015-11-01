package com.admintool.adv.app.beans;

import java.io.Serializable;

public class CategoryBean implements Comparable<CategoryBean>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8589155195056485079L;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((categoryID == null) ? 0 : categoryID.hashCode());
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
		CategoryBean other = (CategoryBean) obj;
		if (categoryID == null) {
			if (other.categoryID != null)
				return false;
		} else if (!categoryID.equals(other.categoryID))
			return false;
		return true;
	}
	@Override
	public int compareTo(CategoryBean o) {
		if (this == o)
			return 0;
		if (o == null)
			return 0;
		
		CategoryBean other = (CategoryBean) o;
		if(category == null) return 0;
		if(category != null) {
			return category.compareTo(other.category);
		}
		
		return 0;
	}
}
