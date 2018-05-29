package net.chukonu.spring.boot;

import java.io.IOException;
import java.util.concurrent.Executor;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@ComponentScan(basePackages = "net.chukonu.spring.boot.*")  
@EnableAsync
public class ChukonuSpringBootApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ChukonuSpringBootApplication.class, args);
    }
    
    /**
     * This is config of thread pool for async methods processing
     * @return
     */
    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(100);
        executor.setMaxPoolSize(200);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Chukonu-");
        executor.initialize();
        return executor;
    }
    
    
    @Bean
    public EmbeddedServletContainerFactory tomcatEmbeddedServletContainerFactory() throws IOException {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        tomcat.addAdditionalTomcatConnectors(httpConnector());
        return tomcat;
    }

    public Connector httpConnector() throws IOException {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        Http11NioProtocol http11NioProtocol = (Http11NioProtocol) connector.getProtocolHandler();
        connector.setPort(8081);
        //设置最大线程数
        http11NioProtocol.setMaxThreads(100);
        //设置初始线程数  最小空闲线程数
        http11NioProtocol.setMinSpareThreads(20);
        //设置超时
        http11NioProtocol.setConnectionTimeout(5000);
        return connector;
    }
}
