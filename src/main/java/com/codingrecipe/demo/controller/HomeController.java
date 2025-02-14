package com.codingrecipe.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/") //기본페이지 요청메소드
    public String index(){
        return "index";
    }
}
