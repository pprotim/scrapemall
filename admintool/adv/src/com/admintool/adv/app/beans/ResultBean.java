package com.admintool.adv.app.beans;

public class ResultBean {

	private boolean isSucess;
	private String message;
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
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResultBean [isSucess=");
		builder.append(isSucess);
		builder.append(", message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}
	
	
}
