package chukonu_spring_cloud_consul;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@EnableAutoConfiguration
@EnableDiscoveryClient
@RestController
public class Application {

	@RequestMapping("/")
	public String home() {
		return "Hello World";
	}

	@Autowired
	private LoadBalancerClient loadBalancer;

	@Autowired
	private static DiscoveryClient discoveryClient;

//	@RequestMapping("/discover")
//	public Object discover() {
//		return loadBalancer.choose("tomcat").getUri().toString();
//	}
//
//	@RequestMapping("/services")
//	public Object services() {
//		return discoveryClient.getInstances("tomcat");
//	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		
	}

}
