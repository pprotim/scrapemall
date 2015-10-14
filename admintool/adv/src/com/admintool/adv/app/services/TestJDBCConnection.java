package com.admintool.adv.app.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestJDBCConnection {
	
	public static void main(String[] args) throws ParseException {
		TestJDBCConnection instance = new TestJDBCConnection();
		instance.testConnection();
		//instance.formatDate();
		//instance.formatSearchDate();
	}
	
	private void formatDate() throws ParseException {
		
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
	    String nowDateTime = sdf.format(cal.getTime());
	    System.out.println(nowDateTime);
	    System.out.println(sdf.parse(nowDateTime));
	}
	
	private void formatSearchDate() throws ParseException {
		
		String searchDate = "09/27/2015";
		SimpleDateFormat searchDatef = new SimpleDateFormat("MM/dd/yyyy");
		Date searchDateTime = searchDatef.parse(searchDate);
		System.out.println(searchDateTime);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
	    String serverDateTimeString = sdf.format(searchDateTime);
	    System.out.println(serverDateTimeString);
	    System.out.println(sdf.parse(serverDateTimeString));
	}
	private void testConnection() {
		
		 Connection conn = null;
		try {
				//String URL = "jdbc:mysql://localhost:3306/exodus";
				String URL = "jdbc:mysql://exodus.cyn9qtdt4hb5.us-west-2.rds.amazonaws.com:3306/exodus";
				String USER = "exodusapp";
				String PASS = "exodus123";
					
			   Class.forName("com.mysql.jdbc.Driver");
			   conn = DriverManager.getConnection(URL, USER, PASS);
			   Statement stmt = conn.createStatement();
			   ResultSet resultSet = stmt.executeQuery("select adid from Crawl");
			   while(resultSet.next()) {/*
				   String id = resultSet.getString(1);
				   String adid = resultSet.getString(2);
				   String dateid = resultSet.getString(3);
				   String websiteid = resultSet.getString(4);
				   String channelId = resultSet.getString(5);
				   String companyid = resultSet.getString(6);
				   String position = resultSet.getString(7);
				   String count = resultSet.getString(8);
				   StringBuffer sb = new StringBuffer();
				   sb.append(id).append("...").append(adid).append("...").append(dateid).append("...")
				   .append(websiteid).append("...").append(channelId).append("...")
				   .append(companyid).append("...").append(position).append("...").append(count);
				   System.out.println(sb.toString());
			   */
				   String adid = resultSet.getString(1);   
				   System.out.println(adid);
			   
			   }
			   
			   conn.close();
		}
		catch(ClassNotFoundException ex) {
		   System.out.println("Error: unable to load driver class!");
		   System.exit(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
