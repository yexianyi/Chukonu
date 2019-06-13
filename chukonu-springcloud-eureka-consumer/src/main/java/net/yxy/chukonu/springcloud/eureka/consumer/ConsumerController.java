package net.yxy.chukonu.springcloud.eureka.consumer;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/ribbon-consumer")
    public String sayHello(@RequestParam String message) throws UnknownHostException {
        return restTemplate.getForEntity("http://HELLO_SERVICE/hello?message=" + message, String.class).getBody();
    }
}
