package com.example.test_web.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class jiutianke_Controller {

    @RequestMapping("/jiutianke")
        public String hello(){
        return "[九天客]-[蓬莱]";
    }

    @GetMapping("/user")
    //@RequestMapping(value = "/user",method = RequestMethod.GET)
        public String getUser(){
            return "GET-张三";
         }
    @PostMapping("/user")
    //  @RequestMapping(value = "/user",method = RequestMethod.POST)
        public String saveUser(){
         return "POST-张三";
         }

    @PutMapping("/user")
    //@RequestMapping(value = "/user",method = RequestMethod.PUT)
        public String putUser(){
             return "PUT-张三";
         }
    @DeleteMapping("/user")
     //@RequestMapping(value = "/user",method = RequestMethod.DELETE)
        public String deleteUser(){
            return "DELETE-张三";
         }
    //扩展点： 如何把_method 这个名字换成我们自己喜欢的


}
