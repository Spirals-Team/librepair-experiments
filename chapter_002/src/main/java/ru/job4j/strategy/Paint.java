package ru.job4j.strategy;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

public class Paint {
    public void draw(Shape shape) {
        System.out.println(shape.draw());
    }
}