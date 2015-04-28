package org.springframework.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
// http://stackoverflow.com/questions/29743320/how-exactly-works-the-spring-bean-post-processor/29745132#29745132
public class Test {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "beanPostProcessorExample.xml");
        context.getBean("holder");
    }
}