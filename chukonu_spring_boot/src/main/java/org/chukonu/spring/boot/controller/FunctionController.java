package org.chukonu.spring.boot.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/func")
public class FunctionController {

    @RequestMapping("/mvc1")
    public ModelAndView mvc1() {
        return new ModelAndView("index");
    }
}

