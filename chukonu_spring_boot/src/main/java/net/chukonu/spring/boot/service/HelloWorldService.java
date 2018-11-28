package net.chukonu.spring.boot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldService {

	private final Logger logger = LoggerFactory.getLogger(HelloWorldService.class);
	
	@Async
	public String asyncSayHello(String words) throws InterruptedException {
		logger.info("Request#"+ words + " enters service()");
		System.out.println("Do something in asyn for 200 ms");
		Thread.sleep(200);
		logger.info("Request#"+ words + " quits service()");
		return "success";
	}
	
	
	public String syncSayHello(String words) throws InterruptedException {
		logger.info("Request#"+ words + " enters service()");
		logger.info("Do something for 3 sec");
		Thread.sleep(3000);
		logger.info("Request#"+ words + " quits service()");
		return "success";
	}
	
	
}
