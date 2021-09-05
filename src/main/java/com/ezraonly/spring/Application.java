package com.ezraonly.spring;

import com.ezraonly.spring.config.AppConfig;
import com.ezraonly.spring.service.TestBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//@SpringBootApplication
public class
Application {

    public static void main(String[] args) {
//        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
//        TestBean testBean = (TestBean) annotationConfigApplicationContext.getBean("testBean");
        EzraonlyApplicationContext ezraonlyApplicationContext = new EzraonlyApplicationContext(AppConfig.class);
        TestBean testBean = (TestBean) ezraonlyApplicationContext.getBean("testBean");
        System.out.println(testBean);
        testBean.test();
    }

}
