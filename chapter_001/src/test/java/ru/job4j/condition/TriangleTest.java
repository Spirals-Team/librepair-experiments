package ru.job4j.condition;

import org.junit.Test;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.*;

public class TriangleTest {
    @Test
    public void whenAreaSetThreePointsThenTriangleArea() {
        // создаем три объекта класса Point.
        Point a = new Point(0, 0);
        Point b = new Point(0, 2);
        Point c = new Point(2, 0);
        Triangle triangle = new Triangle(a, b, c);

        double result = triangle.area();
        double expected = 2D;

        assertThat(result, closeTo(expected, 0.1));
    }

    @Test
    public void whenDistanceDiffPointsThen10() {
        Point left = new Point(0, 0);
        Point right = new Point(0, 10);

        Triangle triangle = new Triangle(null, null, null);
        double rsl = triangle.distance(left, right);

        assertThat(rsl, closeTo(10, 0.01));
    }
}