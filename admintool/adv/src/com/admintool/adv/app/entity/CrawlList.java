package com.admintool.adv.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="LIST")
public class CrawlList {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "LIST_ID")
	private Integer id;
	
	@Column(name = "LIST_NAME")
	private Integer name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getName() {
		return name;
	}

	public void setName(Integer name) {
		this.name = name;
	}
}
