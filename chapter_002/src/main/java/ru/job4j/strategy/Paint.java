package ru.job4j.strategy;

/**
 * Paint.
 * @author Hincu Andrei (andreih1981@gmail.com) by 07.09.17;
 * @version $Id$
 * @since 0.1
 */
public class Paint {
    /**
     * конструктор.
     * @param shape фигура.
     */

    public void draw(Shape shape) {
        System.out.print(shape.pic());
    }
}
