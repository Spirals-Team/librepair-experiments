package ru.job4j.strategy;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

public class Main {
    public static void main(String[] args) {
        Paint paint = new Paint();
        paint.draw(new Triangle());
        paint.draw(new Square());
    }
}