package ru.job4j.strategy;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

public class Triangle implements Shape {
    @Override
    public String draw() {
        StringBuilder shape = new StringBuilder();
        shape.append("   +\n");
        shape.append("  + +\n");
        shape.append(" +   +\n");
        shape.append("+++++++\n");
        return shape.toString();
    }
}