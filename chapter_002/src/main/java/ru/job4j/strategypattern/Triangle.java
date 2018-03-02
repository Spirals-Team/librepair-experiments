package ru.job4j.strategypattern;
/**
 * Triangle.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class Triangle implements Shape {
    /**
     * Create triangle.
     * @return string
     */
    @Override
    public String pic() {
        return "   *   \n  ***  \n ***** \n*******";
    }
}
