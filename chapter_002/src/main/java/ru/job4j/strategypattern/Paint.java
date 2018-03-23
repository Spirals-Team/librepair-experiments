package ru.job4j.strategypattern;
/**
 * Paint.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class Paint {
    /**
     * Draw shape.
     * @param shape shape
     */
    public void draw(Shape shape) {
        System.out.print(shape.pic());
    }
}
