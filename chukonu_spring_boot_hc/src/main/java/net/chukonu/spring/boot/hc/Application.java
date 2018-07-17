package net.chukonu.spring.boot.hc;

import java.io.IOException;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@ComponentScan(basePackages = "net.chukonu.spring.boot.hc.*")
@EnableAsync
public class Application {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * This is config of thread pool for async methods processing
	 * 
	 * @return
	 * @throws IOException
	 */
	// @Bean
	// public Executor asyncExecutor() {
	// ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	// executor.setCorePoolSize(100);
	// executor.setMaxPoolSize(200);
	// executor.setQueueCapacity(500);
	// executor.setThreadNamePrefix("Chukonu-");
	// executor.initialize();
	// return executor;
	// }

//	@Bean
//	public TomcatServletWebServerFactory servletFactory() throws IOException {
//		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
////		tomcat.setPort(9001);
//		tomcat.addConnectorCustomizers(new TomcatConnectorCustomizer() {
//          @Override
//          public void customize(Connector connector) {
//        	  System.out.println("---------------------*************************_") ;
//              Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
//              protocol.setAcceptCount(100);
//              protocol.setMaxConnections(100); //NIO:10000 ; APR/native:8192; NIO:MaxThreads
//              protocol.setMaxThreads(20); //default is 200
////              protocol.setSelectorTimeout(3000);
////              protocol.setSessionTimeout(3000);
////              protocol.setConnectionTimeout(3000);
//          }
//      });
//		return tomcat;
//	}

}
