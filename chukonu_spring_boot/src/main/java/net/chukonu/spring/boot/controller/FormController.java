package net.chukonu.spring.boot.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/form")
public class FormController {

    @PostMapping("/submitForm")
    public ModelAndView test1(String fname, String lname, String contactMethod, String car) {
    	 ModelAndView mv=new ModelAndView("form");
    	 mv.addObject("fname", fname);
    	 mv.addObject("lname", lname);
    	 mv.addObject("contactMethod", contactMethod);
    	 mv.addObject("car", car);
    	      
    	 return mv;
    }
    
    @GetMapping("/test2")
    public ResponseEntity<String>  test2() {
    	return ResponseEntity.ok("OK");
    }
}

