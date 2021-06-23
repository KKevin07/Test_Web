package com.example.test_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ViewTestController {

    @GetMapping("/jiutiankeView")
    public String jiutiankeView(Model model){
    //model中的数据会被放在请求域中，相当于了调用了request.Attribute("a",aa)
        model.addAttribute("msg","您好 jiutianke");
        model.addAttribute("link","https://jx3.xoyo.com");

        return "success";//不用加后缀“.html”，因为thymeleaf已经加了后缀处理
    }
}
