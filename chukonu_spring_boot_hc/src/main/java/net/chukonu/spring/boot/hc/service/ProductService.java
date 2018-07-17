package net.chukonu.spring.boot.hc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

	private final Logger logger = LoggerFactory.getLogger(ProductService.class);
	
	public String getProductInfo(String pId) {
		//do something
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return "Product Info:AAA";
	}
	
	
}
