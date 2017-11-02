package com.yxy.chukonu.mybatis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller("indexController")
public class IndexController {

	@RequestMapping("index")
	public ModelAndView index(){
		ModelAndView mv = new ModelAndView() ;
		mv.setViewName("index");
		System.out.println("index()-------------------->");
		return mv;
		
	}
	
}
