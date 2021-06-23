package com.example.test_web.controller;


import com.example.test_web.bean.Person;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Controller
public class ResponseTestController {

    @ResponseBody
    @GetMapping("/he11")  //调用RequestResponseBodyMethodProcessor-->MessageConverter
    public FileSystemResource file(){

        //文件以这样的方式返回，看哪个MessageConverter能处理返回值

    return  null;
    }
    /**
     1.浏览器发送请求直接返回xml         浏览器的请求头说我能接收[application/xml]  ，   中间会有一些converter  （会有一个可支持xml的jacksonXmlConverter）能支持把数据转为xml  ，最终返回xml
     2.如果是ajax请求，返回json        ajax请求  规定请求能接受[application/json]        jacksonJsonConverter可支持
     3.如果（自定义程序）（教程中为硅谷app）本程序发请求，返回自定义协议数据  规定[application/x-guigu(自定义协议)]    应该能找到一个（自定义的）xxxxConverter进行处理
                                    属性值1；属性值2；


     步骤：
     1.添加自定义的MessageConverter进系统底层（用来处理自定义协议请求数据）
     2.系统底层就会统计出所有MessageConverter能操作哪些类型
     3.客户端内容协商  [自定请求数据----->自定义Converter]

     作业：如何以参数的方式进行（自定义）内容协商


     */


    @ResponseBody
    //（RequestResponseBodyMethodProcessor）SpringMVC支持 @ResponseBody的返回值
    // 标注了 @ResponseBody   即以Json数据写出去
    //利用返回值处理器里面的消息转换器进行处理
    @GetMapping("/test/person")
    public Person getPerson(){
        Person person = new Person();
        person.setAge(28);
        person.setBirth(new Date());
        person.setUsername("zhangsan");
        return person;
    }
}
