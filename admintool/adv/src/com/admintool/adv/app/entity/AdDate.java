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
	private boolean isWeekDay;
	
	@Column(name = "ISWEEKEND")
	private boolean isWeekEnd;
	
	@Column(name = "ISHOLIDAY")
	private boolean isHoliday;
	
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
	public boolean isWeekDay() {
		return isWeekDay;
	}
	public void setWeekDay(boolean isWeekDay) {
		this.isWeekDay = isWeekDay;
	}
	public boolean isWeekEnd() {
		return isWeekEnd;
	}
	public void setWeekEnd(boolean isWeekEnd) {
		this.isWeekEnd = isWeekEnd;
	}
	public boolean isHoliday() {
		return isHoliday;
	}
	public void setHoliday(boolean isHoliday) {
		this.isHoliday = isHoliday;
	}
}
