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
@Table(name ="Date")
public class AdDate {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "FULL_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date datetime;
	
	@Column(name = "DAY")
	private String day;
	
	@Column(name = "WEEK")
	private String week;
	
	@Column(name = "MONTH")
	private String month;
	
	@Column(name = "YEAR")
	private String year;
	
	@Column(name = "ISWEEKDAY")
	private Boolean isWeekDay;
	
	@Column(name = "ISWEEKEND")
	private Boolean isWeekEnd;
	
	@Column(name = "ISHOLIDAY")
	private Boolean isHoliday;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public Boolean getIsWeekDay() {
		return isWeekDay;
	}
	public void setIsWeekDay(Boolean isWeekDay) {
		this.isWeekDay = isWeekDay;
	}
	public Boolean getIsWeekEnd() {
		return isWeekEnd;
	}
	public void setIsWeekEnd(Boolean isWeekEnd) {
		this.isWeekEnd = isWeekEnd;
	}
	public Boolean getIsHoliday() {
		return isHoliday;
	}
	public void setIsHoliday(Boolean isHoliday) {
		this.isHoliday = isHoliday;
	}


}
