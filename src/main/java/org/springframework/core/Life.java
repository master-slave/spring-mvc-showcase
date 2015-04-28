package org.springframework.core;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Date;

public class Life implements BeanNameAware, BeanFactoryAware,
        ApplicationContextAware, BeanPostProcessor, InitializingBean,
        DisposableBean {
    private int counter; // counter
 
    public int getCounter() {
        return counter;
    }
 
    public void setCounter(int counter) {
        this.counter = counter;
        System.out.println("1. Spring setter DI：" + this.counter);
    }
 
    public Life() {
        System.out.println("0. Spring calls constructor");
    }
 
    @Override
    public void destroy() throws Exception {
        System.out.println("8. DisposableBean#destroy：" + ++counter);
    }
 
    public void _destroy() throws Exception {
        System.out.println("8'. bean#_destroy：" + ++counter);
    }
 
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(new Date().getTime());
        System.out.println("6. InitializingBean#afterPropertiesSet："
                + ++counter);
    }
 
    public void init() throws Exception {
        System.out.println("6'. bean#init：" + ++counter);
    }
 
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        System.out.println(new Date().getTime());
        System.out
                .println("5. BeanPostProcessor#postProcessBeforeInitialization："
                        + ++counter);
        return bean;
    }
 
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        System.out
                .println("7. BeanPostProcessor#postProcessAfterInitialization："
                        + ++counter);
        return bean;
    }
 
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        System.out.println("4. ApplicationContextAware#setApplicationContext："
                + ++counter);
    }
 
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("3. BeanFactoryAware#setBeanFactory：" + ++counter);
    }
 
    @Override
    public void setBeanName(String name) {
        System.out.println("2. BeanNameAware#setBeanName：" + ++counter);
    }


}