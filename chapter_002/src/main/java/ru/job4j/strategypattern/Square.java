package ru.job4j.strategypattern;
/**
 * Square.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class Square implements Shape {
    /**
     * Create square.
     * @return string
     */
    @Override
    public String pic() {
        return "*******\n*******\n*******\n*******";
    }
}
