package ru.job4j.condition;

/**
 * @autor Alexander Kaleganov
 * @version Triangle 1.000
 * @since 28.01.2018 23:48
 */
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.*;
public class TriangleTest {
    // создаем три объекта класса Point.
    Point a = new Point(0, 0);
    Point b = new Point(0, 2);
    Point c = new Point(2, 0);
    // Создаем объект треугольник и передаем в него объекты точек.
    Triangle triangle = new Triangle(a, b, c);

    @Test
    public void whenAreaSetThreePointsThenTriangleArea() {
        // Вычисляем площадь.
        double result = triangle.area();
        // Задаем ожидаемый результат.
        double expected = 2D;
        //Проверяем результат и ожидаемое значение.
        assertThat(result, closeTo(expected, 0.1));
    }

    @Test
    public void whenPeriodSetThree() {
        double result = triangle.period(2, 2, 2.83);
        double expected = 3.415;
        assertThat(result, closeTo(expected, 0.001));
    }


    @Test
    public void whenExistSetThree() {
        boolean result = triangle.exist(2, 2, 2.83) ? true : false;
        boolean expected = true;
        assertThat(result, is(expected));
    }
}