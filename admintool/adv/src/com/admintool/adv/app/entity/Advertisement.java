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
@Table(name ="Ad")
public class Advertisement {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "URL")
	private String url;
	
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "SIZE")
	private String size;
	
	@Column(name = "LANDINGPAGEURL")
	private String landingPageURL;
	
	@Column(name = "CREATEDDATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDateTime;
	
	@Column(name = "MODIFIEDDATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDateTime;
	
	@Column(name = "CompanyID")
	private Integer companyId;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getLandingPageURL() {
		return landingPageURL;
	}
	public void setLandingPageURL(String landingPageURL) {
		this.landingPageURL = landingPageURL;
	}
	public Date getCreatedDateTime() {
		return createdDateTime;
	}
	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	public Date getModifiedDateTime() {
		return modifiedDateTime;
	}
	public void setModifiedDateTime(Date modifiedDateTime) {
		this.modifiedDateTime = modifiedDateTime;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
}
