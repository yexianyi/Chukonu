package net.chukonu.spring.boot.controller;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.WebAsyncTask;

import net.chukonu.spring.boot.service.HelloWorldService;


@RestController
public class HelloWorldController {
	private final Logger logger = LoggerFactory.getLogger(HelloWorldController.class);
	
	@Autowired
	private HelloWorldService service;
	
	
	@GetMapping("/webAsyncHello")
	public WebAsyncTask<String> sayHelloWebAsync(String words) {
		logger.info("Reqeust#"+words+" enters controller");
		WebAsyncTask<String> webAsyncTask = new WebAsyncTask<>(10000, new Callable<String>() {
			@Override
			public String call() throws Exception {
				return service.asyncSayHello(words);
			}
		});

		webAsyncTask.onCompletion(new Runnable() {
			@Override
			public void run() {
				logger.info("Reqeust#"+words+" is complete in controller.");
			}
		});

		webAsyncTask.onTimeout(new Callable<String>() {
			@Override
			public String call() throws Exception {
				logger.info("Reqeust#"+words+" is timeout in controller.");
				throw new TimeoutException();
			}
		});
		
		logger.info("Reqeust#"+words+" leaves controller.");
		return webAsyncTask;
	}
	
	
	@GetMapping("/syncHello")
	public String sayHelloSync(String words) throws InterruptedException {
		logger.info("Reqeust#"+words+" enters controller.");
		service.asyncSayHello(words);
		logger.info("Reqeust#"+words+" leaves controller.");
		return "";
	}
	
	
	@GetMapping("/callableHello")
	public Callable<String> sayHelloAsync(String words) {
		logger.info("Reqeust#"+words+" enters controller.");
		Callable<String> asyncTask = new Callable<String>() {
			@Override
			public String call() throws Exception {
				return service.syncSayHello(words);
			}
		};

		logger.info("Reqeust#"+words+" leaves controller.");
		return asyncTask;
	}
	
	
	@GetMapping("/asyncRestClient")
    @ResponseBody
    public String asyncRestClient(){
        AsyncRestTemplate template = new AsyncRestTemplate();
        String url = "";
        ListenableFuture<ResponseEntity<String>> forEntity = template.getForEntity(url, String.class);
        forEntity.addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {
            @Override
            public void onFailure(Throwable ex) {
//                logger.error("=====rest response faliure======");
            }

            @Override
            public void onSuccess(ResponseEntity<String> result) {
//                logger.info("--->async rest response success----, result = "+result.getBody());
            }
        });
        return "Done";
    }
	
	
	
}
