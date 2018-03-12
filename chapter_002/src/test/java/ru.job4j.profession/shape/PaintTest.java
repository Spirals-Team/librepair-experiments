package ru.job4j.profession.shape;

import org.junit.Test;
import ru.job4j.shape.Shape;
import ru.job4j.shape.Square;
import ru.job4j.shape.Triangle;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test class Paint.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 09.10.2017
 */
public class PaintTest {
    /**
     * Проверяем реализацию фигуры квадрата.
     */
    @Test
    public void verifySquare() {
        StringBuilder expect = new StringBuilder();
        expect.append("+++++\n");
        expect.append("+   +\n");
        expect.append("+++++");
        Shape shape = new Square();
        String resault = shape.pic().toString();
        assertThat(resault, is(expect.toString()));
    }

    /**
     * Проверяем реализацию фигуры треугольника.
     */
    @Test
    public void verifyTriangle() {
        StringBuilder expect = new StringBuilder();
        expect.append("  +  \n");
        expect.append(" +++ \n");
        expect.append("+++++");
        Shape shape = new Triangle();
        String resault = shape.pic().toString();
        assertThat(resault, is(expect.toString()));
    }

}
