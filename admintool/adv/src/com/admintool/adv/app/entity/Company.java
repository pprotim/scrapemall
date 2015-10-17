package com.admintool.adv.app.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table (name = "Company")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "DOMAIN")
	private String domain;
	
	@Column(name = "HOMEPAGE")
	private String homePage;
	
	@Column(name = "BRAND")
	private String brand;
	
	@Column(name = "CATEGORY")
	private String category;
	
	@Column(name = "SUBCATEGORY")
	private String subCategory;
	
	@Column(name = "fyRevenue")
	private double fyRevenue;
	
	@Column(name = "competitorID")
	private Integer competitorID;
	
	@Column(name = "hqlat")
	private Long hqlat;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "CREATEDDATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date CreatedDateTime;
	
	@Column(name = "MODIFIEDDATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date ModifiedDateTime;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public double getFyRevenue() {
		return fyRevenue;
	}

	public void setFyRevenue(double fyRevenue) {
		this.fyRevenue = fyRevenue;
	}

	public Integer getCompetitorID() {
		return competitorID;
	}

	public void setCompetitorID(Integer competitorID) {
		this.competitorID = competitorID;
	}

	public Long getHqlat() {
		return hqlat;
	}

	public void setHqlat(Long hqlat) {
		this.hqlat = hqlat;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Date getCreatedDateTime() {
		return CreatedDateTime;
	}

	public void setCreatedDateTime(Date createdDateTime) {
		CreatedDateTime = createdDateTime;
	}

	public Date getModifiedDateTime() {
		return ModifiedDateTime;
	}

	public void setModifiedDateTime(Date modifiedDateTime) {
		ModifiedDateTime = modifiedDateTime;
	}

}
