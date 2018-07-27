package net.chukonu.spring.boot.controller;


import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import net.chukonu.spring.boot.param.AddressResult;

@RestController
@RequestMapping("/function")
public class FunctionController {

    @GetMapping("/test1")
    public ModelAndView test1() {
        return new ModelAndView("index");
    }
    
    @GetMapping("/test2")
    public ResponseEntity<String>  test2() {
    	return ResponseEntity.ok("OK");
    }
    
    @GetMapping("/local_ip")
    @ResponseBody
    public AddressResult getLocalIPAddr() {
    	InetAddress address;
		try {
			address = InetAddress.getLocalHost();
			return new AddressResult(address.getHostAddress()) ;     
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} 
		
		return null;
    }
}

