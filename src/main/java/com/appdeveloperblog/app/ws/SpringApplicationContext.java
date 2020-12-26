package com.appdeveloperblog.app.ws;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

// Note there is no annotation. This is added as a bean in the main APP class definition
public class SpringApplicationContext implements ApplicationContextAware {
    private static ApplicationContext CONTEXT;

    //implemented as part of ApplicationContextAware Interface. Automatically gets called
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CONTEXT = applicationContext;
    }

    //gets the Bean across different classes. The bean when created gets tagged to the application context.
    // Using bean name can type case it and then use the functions in the bean
    //example
    //AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
    //return appProperties.getTokenSecret();
    public static Object getBean(String beanName) {
        return CONTEXT.getBean(beanName);
    }
}
