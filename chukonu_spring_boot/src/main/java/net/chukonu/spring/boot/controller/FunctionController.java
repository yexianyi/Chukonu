package net.chukonu.spring.boot.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
}

