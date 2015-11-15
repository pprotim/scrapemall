package com.admintool.adv.app.beans;

public class ResultBean {

	private boolean isSucess;
	private String message;
	private CrawlBean crawlBean;
	
	public boolean isSucess() {
		return isSucess;
	}
	public void setSucess(boolean isSucess) {
		this.isSucess = isSucess;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public CrawlBean getCrawlBean() {
		return crawlBean;
	}
	public void setCrawlBean(CrawlBean crawlBean) {
		this.crawlBean = crawlBean;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResultBean [isSucess=");
		builder.append(isSucess);
		builder.append(", message=");
		builder.append(message);
		builder.append(", crawlBean=");
		builder.append(crawlBean);
		builder.append("]");
		return builder.toString();
	}
}
