package net.chukonu.spring.boot.controller;


import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import net.chukonu.spring.boot.controller.param.UserInfo;

@RestController
@RequestMapping("/form")
public class FormController {

    @PostMapping("/submit1")
    public ModelAndView submit1(String fname, String lname, String[] contactMethods, String car) {
    	 ModelAndView mv=new ModelAndView("form");
    	 mv.addObject("fname", fname);
    	 mv.addObject("lname", lname);
    	 mv.addObject("contactMethod", contactMethods);
    	 mv.addObject("car", car);
    	 mv.setViewName("form");  
    	 
    	 return mv;
    }
    
    @PostMapping("/submit2")
    public String submit2(String fname, String lname, String[] contactMethods, String car) {
    	 ModelAndView mv=new ModelAndView("form");
    	 mv.addObject("fname", fname);
    	 mv.addObject("lname", lname);
    	 mv.addObject("contactMethods", contactMethods);
    	 mv.addObject("car", car);
    	 mv.setViewName("form");  
    	 
    	 return "form";
    }
    
    @PostMapping("/submit3")
    public ResponseEntity<UserInfo> submit3(@RequestBody UserInfo userInfoJson) {
    	System.out.println(userInfoJson.getFname()) ;
    	System.out.println(userInfoJson.getLname()) ;
    	Arrays.asList(userInfoJson.getContactMethods()).forEach(System.out::println) ;
    	System.out.println(userInfoJson.getCar()) ;
    	
    	return ResponseEntity.ok(userInfoJson) ;
    	
    }
}

