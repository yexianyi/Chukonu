package com.yxy.chukonu.mybatis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.yxy.chukonu.mybatis.service.TransactionService;

@Controller("txController")
public class TransactionController {
	
	@Autowired
	public TransactionService txService ;
	
	@RequestMapping(value="submitTx", method=RequestMethod.POST)
	public ModelAndView submitTransaction(String fromAcct, String toAcct, float txAmount){
		txService.doTransaction(fromAcct, toAcct, txAmount);
		
		ModelAndView mv = new ModelAndView() ;
		mv.addObject("result", "success") ;
		mv.setView(new MappingJackson2JsonView());
		return mv;
		
	}
}
