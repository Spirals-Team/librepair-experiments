package ru.job4j.condition;

import static org.hamcrest.number.IsCloseTo.closeTo;

import org.junit.Test;

//import static org.hamcrest.core.Is.is;

import static org.junit.Assert.assertThat;

/**
 *Test is the point (x,y) find's on line y(x) = a * x + b.
 *
 *@author Afonin Sergey (mailto:afonin1c@mail.ru)
 *
 *@version 1
 *
 *@since 06.10.2017
 */

public class TriangleTest {

    /**
     * is an area of triangle equals assumed value.
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

}
