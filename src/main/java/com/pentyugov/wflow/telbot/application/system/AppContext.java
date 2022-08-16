package com.pentyugov.wflow.telbot.application.system;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class AppContext {

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

    public static Object get(String name) {
        return applicationContext.getBean(name);
    }

    public static <T> T get(Class<T> beanType) {
        // Try to find static field NAME in bean defined;
        String name = null;
        try {
            Field nameField = beanType.getField("NAME");
            name = (String) nameField.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // ignore
        }
        if (name != null) {
            return applicationContext.getBean(name, beanType);
        } else {
            return applicationContext.getBean(beanType);
        }

    }
}
