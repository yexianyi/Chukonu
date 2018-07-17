package net.chukonu.spring.boot.hc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import net.chukonu.spring.boot.hc.service.ProductService;


@RestController
public class ProductController {
	private final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductService service;
	
	
	@GetMapping("/product")
	public String getProductInfo(String pId)  {
		service.getProductInfo(pId);
		return "";
	}
	
	
	
}
