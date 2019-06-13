package net.yxy.chukonu.springcloud.eureka.provider;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class HelloController {
    
    @Value("${server.port}")
    private String port ;
    
    @Autowired
    private DiscoveryClient client ;
    
    @GetMapping("/hello")
    public String sayHello(@RequestParam String message) throws UnknownHostException {
        List<ServiceInstance> list = client.getInstances("hello-service");
        list.stream().forEach(instance -> {
            log.info("/hello, host:" + instance.getHost() + ":" + instance.getPort() + "/" + instance.getServiceId()) ;
        });
        
        return InetAddress.getLocalHost().getHostName() + ":" + port + " - recv: '" + message + "'" ;
    }
}
