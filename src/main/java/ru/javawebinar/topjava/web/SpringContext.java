package ru.javawebinar.topjava.web;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/** @author danis.tazeev@gmail.com */
public final class SpringContext {
    private static final ApplicationContext ctx = new ClassPathXmlApplicationContext(
            "/spring/spring-app.xml", "/spring/spring-db.xml");
    public static ApplicationContext get() { return ctx; }
    private SpringContext() {}
}
