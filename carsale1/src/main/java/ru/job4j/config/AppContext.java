package ru.job4j.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class AppContext {
    private final ApplicationContext context;

    public AppContext() {
        this.context = new ClassPathXmlApplicationContext("spring-context.xml");
    }

    public ApplicationContext getContext() {
        return this.context;
    }
}
