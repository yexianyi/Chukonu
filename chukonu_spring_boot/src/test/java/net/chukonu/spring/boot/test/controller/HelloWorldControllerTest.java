package net.chukonu.spring.boot.test.controller;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import net.chukonu.spring.boot.Application;
import net.chukonu.spring.boot.controller.HelloWorldController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloWorldControllerTest extends MockMvcResultMatchers{
    
	private final Logger logger = LoggerFactory.getLogger(HelloWorldControllerTest.class);
	
	@LocalServerPort 
	private int port  ;
	private MockMvc mvc;

    
    @Before
    public void setup(){
       mvc = MockMvcBuilders.standaloneSetup(new HelloWorldController()).build();
    }
    
    @Test
    public void testHandleRequest() throws Exception {
    	int numReq = 500 ;
    	
    	// Configure Apache Http Client
        PoolingNHttpClientConnectionManager connectionManager = new PoolingNHttpClientConnectionManager( //
                new DefaultConnectingIOReactor(IOReactorConfig.DEFAULT));
        connectionManager.setMaxTotal(100); //Total Connections
        connectionManager.setDefaultMaxPerRoute(50); //Max connections per host

    	
        CloseableHttpAsyncClient httpclient = HttpAsyncClientBuilder.create()
        		.setConnectionManager(connectionManager).build() ;
        		
        httpclient.start();
        final CountDownLatch latch = new CountDownLatch(numReq);
        
        for(int i=0; i<numReq; i++) {
        	final HttpGet request = new HttpGet("http://localhost:"+port+"/callableHello?words="+i);
//        	final HttpGet request = new HttpGet("http://localhost:8080/handle?datasource="+i);
        	logger.info("Sent request#"+i);
        	final int reqId= i ;
        	
        	httpclient.execute(request, new FutureCallback<HttpResponse>() {
        		public void completed(final HttpResponse response) {
        			latch.countDown();
        			logger.info("Request#"+reqId+" is COMPLETED.");
        		}
        		
        		public void failed(final Exception ex) {
        			latch.countDown();
        			logger.info("Request#"+reqId+" is FAILED.");
        			ex.printStackTrace();
        		
        		}
        		
        		public void cancelled() {
        			latch.countDown();
        			logger.info("Request#"+reqId+" is CANCELLED.");
        		}
        		
        	});
        	
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            httpclient.close();
        } catch (IOException ignore) {

        }
        
        Thread.sleep(100000);

    }
	
//	@Test
//	public void testHandleRequest() throws Exception {
//		RequestBuilder request = null;
//		request = MockMvcRequestBuilders.get("/handle");
//		for (int i = 0; i < 10; i++) {
//			mvc.perform(request).andExpect(status().isOk());
//		}
//
//		 Thread.sleep(1000000);
//
//	}
	
}
