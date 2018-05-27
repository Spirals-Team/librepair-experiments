package ru.job4j.ioc.demo;

import org.springframework.stereotype.Component;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
@Component
public class MemoryAnnotationStorage implements Storage {
    @Override
    public void add(User user) {
        System.out.println("Add user in Memory Annotation Storage");
    }
}
