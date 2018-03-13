package ru.job4j.strategytask;

/**
 * test.
 */
public class Test {
    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        ContextShape contextShape = new ContextShape(new ShapeSquare());
        System.out.println("" + contextShape.executeShape());

        contextShape = new ContextShape(new ShapeTriangle());
        System.out.println(contextShape.executeShape());
    }
}
