package org.example.spring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.spring.facade.FacadeImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.apache.logging.log4j.Level.DEBUG;

public class Runner {


    public Runner() {

    }

    public static void main(String[] args) {

        ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
        FacadeImpl facade = (FacadeImpl) app.getBean("facadeImpl");
        System.out.println(facade.getEventById(1)+"123");
    }
}
