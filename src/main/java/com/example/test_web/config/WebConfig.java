package com.example.test_web.config;


import com.example.test_web.bean.Pet;
import com.example.test_web.converter.JiutiankeMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.web.accept.ParameterContentNegotiationStrategy;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 改变默认的_method 为_m  ；要在对应html  的请求中写入_m的方式才生效

@Configuration(proxyBeanMethods = false)
public class WebConfig {

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter(){
        HiddenHttpMethodFilter methodFilter = new HiddenHttpMethodFilter();
        methodFilter.setMethodParam("_m");
        return methodFilter;
    }



    //@Bean   //@WebMvcConfiguration   自己写MVC 的配置
//    @Override
//    public void configurePathMatch(PathMatchConfigurer configurer){
//        UrlPathHelper urlPathHelper = new UrlPathHelper();
//        urlPathHelper.setRemoveSemicolonContent(false);  //设置为 不移除后面的内容。这样矩阵变量功能就可以生效。
//        configurer.setUrlPathHelper(urlPathHelper);
//    }

    //  第二种自定义的方法：给容器中放一个@WebMvcConfiguration
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {

            @Override
            public void configurePathMatch(PathMatchConfigurer configurer){
                UrlPathHelper urlPathHelper = new UrlPathHelper();
                urlPathHelper.setRemoveSemicolonContent(false);//设置为 不移除后面的内容。这样矩阵变量功能就可以生效。
                configurer.setUrlPathHelper(urlPathHelper);
            }

            @Override
            public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
                converters.add(new JiutiankeMessageConverter());
            }
            /**
             * 自定义基于参数内容协商策略，会覆盖spring MVC默认的请求处理配置，会丢失掉基于请求头的Converter处理，解析不了，所有写入的请求头只会写出json数据
             * 如要覆盖，除了制定自定义的内容协商，还要加上对请求头的处理，见本页第82行
             *
             * 有可能我们添加的自定义的功能会覆盖默认的很多功能，导致一些默认的功能失效，此时需要以debug方式找到原因，缺了哪个添哪个
             * */
            @Override
            public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
                //Map<String, MediaType> mediaTypes
                Map<String, MediaType>mediaTypes = new HashMap<>();
                mediaTypes.put("json",MediaType.APPLICATION_JSON);
                mediaTypes.put("xml",MediaType.APPLICATION_XML);
                mediaTypes.put("gg",MediaType.parseMediaType("applicaiton/x-jiutianke"));//验证成功，要先添加自定义的JiutiankeMessageConverter()

                //指定支持解析哪些参数对应的哪些媒体类型
                ParameterContentNegotiationStrategy parameterstrategy = new ParameterContentNegotiationStrategy(mediaTypes);

                HeaderContentNegotiationStrategy headerStrategy = new HeaderContentNegotiationStrategy();

                configurer.strategies(Arrays.asList(parameterstrategy,headerStrategy));//这里指定  基于参数  和  基于请求头
            }



            //自定义Converter
            @Override
            public void addFormatters(FormatterRegistry registry){
                registry.addConverter(new Converter<String,Pet>(){

                    @Override
                    public Pet convert(String source){
                        if (!StringUtils.isEmpty(source)){
                            Pet pet =new Pet();
                            String[] split = source.split(",");
                            pet.setName(split[0]);
                            pet.setAge(split[1]);
                            return pet;
                        }
                        return null;
                    }

                });
            }



        };

    }

}
