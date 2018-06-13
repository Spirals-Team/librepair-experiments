package ru.job4j.strategy;

/**
 * @author Aleksandr Vysotskiiy (Aleksandr.vysotskiiy@gmail.com)
 * @version 1.0
 * @since 0.1
 */

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StrategyTest {
    public static String newline = System.getProperty("line.separator");

    @Test
    public void whenDrawSquare() {
        Square square = new Square();
        assertThat(square.draw(), is(new StringBuilder()
                .append("+ + + +" + newline)
                .append("+     +" + newline)
                .append("+     +" + newline)
                .append("+ + + +" + newline)
                .toString()));
    }

    @Test
    public void whenDrawTriangle() {
        Triangle triangle = new Triangle();
        assertThat(triangle.draw(), is(new StringBuilder()
                .append("   +   " + newline)
                .append("  + +  " + newline)
                .append(" + + + " + newline)
                .append("+ + + +")
                .toString()));
    }
}

