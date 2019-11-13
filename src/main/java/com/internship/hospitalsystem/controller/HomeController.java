package com.internship.hospitalsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HomeController {

    public String home(){
        return "index";
    }

    @GetMapping(value = "admin")
    public ModelAndView admin(){
        return new ModelAndView("index");
    }
}
