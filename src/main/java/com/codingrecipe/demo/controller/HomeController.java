package com.codingrecipe.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/") //기본 주소로 요청
    public String index(){
        return "index";
    }
}
