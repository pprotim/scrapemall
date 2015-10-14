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
@Table (name = "CRAWL")
public class Crawl {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer crawlId;
	
	@Column(name = "adId")
	private Integer adId;
	
	@Column(name = "dateId")
	private Integer dateId;
	
	@Column(name = "websiteId")
	private Integer websiteId;
	
	@Column(name = "channelId")
	private Integer channelId;
	
	@Column(name = "companyId")
	private Integer companyId;
	
	@Column(name = "count")
	private Integer count;
	
	@Column(name = "position")
	private String position;
	
	@Column(name = "CREATEDDATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDateTime;
	
	@Column(name = "MODIFIEDDATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDateTime;
	

	public Integer getCrawlId() {
		return crawlId;
	}

	public void setCrawlId(Integer crawlId) {
		this.crawlId = crawlId;
	}

	public Integer getAdId() {
		return adId;
	}

	public void setAdId(Integer adId) {
		this.adId = adId;
	}

	public Integer getDateId() {
		return dateId;
	}

	public void setDateId(Integer dateId) {
		this.dateId = dateId;
	}

	public Integer getWebsiteId() {
		return websiteId;
	}

	public void setWebsiteId(Integer websiteId) {
		this.websiteId = websiteId;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
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
}
