package ru.job4j.painter;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class ShapeTest {
    @Test
    public void whenDrawSquare() {
        Square square = new Square();
        assertThat(
                square.draw(),
                is(
                        new StringBuilder()
                                .append("+ + + +\n")
                                .append("+ + + +\n")
                                .append("+ + + +\n")
                                .append("+ + + +")
                                .toString()
                )
        );
    }

    @Test
    public void whenDrawTriangle() {
        Triangle square = new Triangle();
        assertThat(
                square.draw(),
                is(
                        new StringBuilder()
                                .append("+\n")
                                .append("+ +\n")
                                .append("+ + +\n")
                                .append("+ + + +")
                                .toString()
                )
        );
    }
}
