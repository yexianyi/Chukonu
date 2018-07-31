package net.chukonu.spring.boot.kafka.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloworldController {
	private final Logger logger = LoggerFactory.getLogger(HelloworldController.class);
	
	@GetMapping("/hello")
	public String getProductInfo(String pId)  {
		logger.info("helloworld");
		
		return "OK";
	}

}
