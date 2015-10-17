package com.admintool.adv.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="company_data")
public class CompanyData {

	@Id
	@Column(name = "LIST_ID")
	private Integer listId;
	
	@Column(name = "CK_NID")
	private Integer ckNid;
	
	@Column(name = "POPULARITY")
	private Integer popularity;
	
	@Column(name = "LIST")
	private String list;
	
	@Column(name = "STANDARDIZED_COUNTRY")
	private String standardizedCountry;
	
	@Column(name = "COUNTRY")
	private String country;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "COMMON_NAME")
	private String commonName;
	
	@Column(name = "SIGNIFICANCE_NAME")
	private String significanceName;
	
	@Column(name = "URL")
	private String url;

	public Integer getListId() {
		return listId;
	}

	public void setListId(Integer listId) {
		this.listId = listId;
	}

	public Integer getCkNid() {
		return ckNid;
	}

	public void setCkNid(Integer ckNid) {
		this.ckNid = ckNid;
	}

	public Integer getPopularity() {
		return popularity;
	}

	public void setPopularity(Integer popularity) {
		this.popularity = popularity;
	}

	public String getList() {
		return list;
	}

	public void setList(String list) {
		this.list = list;
	}

	public String getStandardizedCountry() {
		return standardizedCountry;
	}

	public void setStandardizedCountry(String standardizedCountry) {
		this.standardizedCountry = standardizedCountry;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public String getSignificanceName() {
		return significanceName;
	}

	public void setSignificanceName(String significanceName) {
		this.significanceName = significanceName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
