package com.eshop.tools;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

 
@Controller
public class WelcomeController{
	/**
	  * ����ӳ��
	  * @return
	  */
	    @RequestMapping(value="/test")
		public ModelAndView test6(){
			Map<String,Object> data=new HashMap<String,Object>();
			data.put("message", "test6-->xinssss");
			return new ModelAndView("/web/tools/index", data);
		}
	    
	    
	    @RequestMapping(value="/login")
		public ModelAndView login(){
			Map<String,Object> data=new HashMap<String,Object>();
			data.put("message", "登录");
			return new ModelAndView("/web/login", data);
		}
	    
	    @RequestMapping(value="/logining")
		public ModelAndView logining(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
	    	 request.setCharacterEncoding("UTF-8");
		     response.setContentType("text/html; charset=utf-8");
	    	
			Map<String,Object> data=new HashMap<String,Object>();
		    String userName=request.getParameter("userName");
			
			System.out.println("DD:"+userName);
			data.put("message", userName);
			return new ModelAndView("/web/login", data);
		}
 
}
