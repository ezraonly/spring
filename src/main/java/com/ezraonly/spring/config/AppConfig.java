package com.ezraonly.spring.config;

import com.ezraonly.spring.ComponentScan;
import com.ezraonly.spring.service.TestBean;
import org.springframework.context.annotation.Bean;

@ComponentScan("com.ezraonly.spring.service")
public class AppConfig {

//    @Bean
//    public TestBean testBean(){
//        return new TestBean();
//    }
//    @Bean
//    public TestBean testBean2(){
//        return new TestBean();
//    }
}
