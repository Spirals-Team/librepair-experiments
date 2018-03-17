package ru.job4j.condition;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

/**
 * TriangleTest.
 * @author Hincu Andrei (andreih1981@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class TriangleTest {
    /**
     * Test area.
     */
    @Test
    public void whenAreaSetThreePointsThenTriangleArea() {
        // создаем три объекта класса Point.
        Point a = new Point(0, 0);
        Point b = new Point(0, 2);
        Point c = new Point(2, 0);
        // Создаем объект треугольник и передаем в него объекты точек.
        Triangle triangle = new Triangle(a, b, c);
        // Вычисляем площадь.
        double result = triangle.area();
        // Задаем ожидаемый результат.
        double expected = 2D;
        //Проверяем результат и ожидаемое значение.
        assertThat(result, closeTo(expected, 0.1));
    }

    @Test
    public void noExistTriangle() throws Exception {
        Triangle triangle = new Triangle();
        boolean ex = triangle.exist(2, 1, 1);
        assertThat(ex, is(false));
    }

    @Test
    public void existTriangle() throws Exception {
        Triangle triangle = new Triangle();
        boolean ex = triangle.exist(2, 1, 2);
        assertThat(ex, is(true));
    }
    @Test
    public void nexistTriangle() throws Exception {
        Triangle triangle = new Triangle();
        boolean ex = triangle.exist(2, 1, 3);
        assertThat(ex, is(false));
    }
}
