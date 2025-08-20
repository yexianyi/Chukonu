/**
 * Copyright (c) 2025, Xianyi Ye
 *
 * This project includes software developed by Xianyi Ye
 * yexianyi@hotmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.yxy.chukonu.mybatis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.yxy.chukonu.mybatis.annotation.SystemLog;
import com.yxy.chukonu.mybatis.exceptions.InsufficientBalanceException;
import com.yxy.chukonu.mybatis.service.TransactionService;

@Controller("txController")
@EnableAspectJAutoProxy
public class TransactionController {
	
	@Autowired
	public TransactionService txService ;
	
	@RequestMapping(value="submitTx", method=RequestMethod.POST)
	@SystemLog(description = "transaction request")  
	public ModelAndView submitTransaction(String fromAcct, String toAcct, float txAmount){
		txService.doTransaction(fromAcct, toAcct, txAmount);
		
		ModelAndView mv = new ModelAndView() ;
		mv.addObject("result", "success") ;
		mv.setView(new MappingJackson2JsonView());
		return mv;
		
	}
	
	@ExceptionHandler(InsufficientBalanceException.class)
	public ModelAndView handleUserExist() {
		ModelAndView mv = new ModelAndView() ;
		mv.addObject("result", "failure") ;
		mv.addObject("reason", "Insufficient Balance") ;
		mv.setView(new MappingJackson2JsonView());
		
		return mv;
	}
}
