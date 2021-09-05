package com.ezraonly.spring.service;

import com.ezraonly.spring.Autowired;
import com.ezraonly.spring.Component;
import com.ezraonly.spring.Scope;

@Component("testBean")
//@Scope("prototype")
public class TestBean {

    @Autowired
    private NodeTestBean nodeTestBean;

//    public TestBean(){
//        System.out.println("11111");
//    }
//    public TestBean(NodeTestBean nodeTestBean){
//        this.nodeTestBean = nodeTestBean;
//        System.out.println("2222222");
//    }

    public void test(){
        System.out.println(nodeTestBean);
    }
}
