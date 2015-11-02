package com.admintool.adv.app.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.admintool.adv.app.services.AdService;

@Controller
public class HomeController {

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String homePage() {
		System.out.println("hello");
		return "login";
	}
	
	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(value = { "/login", "login" }, method = RequestMethod.POST)
	public String login(@RequestParam(value = "username", required = false) String userid,
			@RequestParam(value = "password", required = false) String password,
			HttpServletResponse response, HttpSession session,Model model,
			Map<String, Object> map) {

		System.out.println("userid="+userid);
		System.out.println("password="+password);
		
		if(!userid.equals("test") || !password.equalsIgnoreCase("test")) {
			map.put("errorMsg", "Not a valid user");
			System.out.println("Not a valid user");
			model.addAttribute("errorMsg","Not a valid user");
			return "login";
		}
		model.addAttribute("USER",userid);
		session.setAttribute("USER", userid);
		
		return "home";
	}
	
	@RequestMapping(value = { "/login", "login" }, method = RequestMethod.GET, params = { "error" })
	public String loginError(@RequestParam("error") String errorMsg) {
		return "login";
	}

	@RequestMapping(value = { "logout", "/logout" })
	public String logout(HttpSession session) throws IOException {
		String tempDirectory = (String)session.getAttribute(AdService.USER_TEMP_DIRECTORY);
		System.out.println("tempDirectory inside logout==="+tempDirectory);
		if(tempDirectory!=null) {
			File dir = new File(tempDirectory);
			if(dir.exists()){
				delete(dir);
			}
		}
		session.invalidate();
		return "redirect:/login";

	}
	
	 public void delete(File file)
		    	throws IOException{
		 
		    	if(file.isDirectory()){
		 
		    		//directory is empty, then delete it
		    		if(file.list().length==0){
		    			
		    		   file.delete();
		    		   System.out.println("Directory is deleted : " 
		                                                 + file.getAbsolutePath());
		    			
		    		}else{
		    			
		    		   //list all the directory contents
		        	   String files[] = file.list();
		     
		        	   for (String temp : files) {
		        	      //construct the file structure
		        	      File fileDelete = new File(file, temp);
		        		 
		        	      //recursive delete
		        	     delete(fileDelete);
		        	   }
		        		
		        	   //check the directory again, if empty then delete it
		        	   if(file.list().length==0){
		           	     file.delete();
		        	     System.out.println("Directory is deleted : " 
		                                                  + file.getAbsolutePath());
		        	   }
		    		}
		    		
		    	}else{
		    		//if file, then delete it
		    		file.delete();
		    		System.out.println("File is deleted : " + file.getAbsolutePath());
		    	}
		    }
}
