package aserdyuchenko;


/**
 * 4.1. Создать программу вычисляющую формулу квадратного уравнения
 * Создать класс Square в нем один методы float calculate(int x) и void show(start, finish, step).
 * Метод должен вычислять значение функции y = a*x2 + b * x + c;
 * параметры a b c задаются через конструктор.
 * void show(start, finish, step) - вывести на экран значение функции в диапазоне от x1 до x2 c шагом step.
 *
 * @author Anton Serdyuchenko (anton415@gmail.com)
 * @version $Id$
 * @since 05.09.2016
 */

public class Square {
/**
 * @param a - a.
 */
    private float a;
/**
 * @param b - b.
 */
    private float b;
/**
 * @param c - c.
 */
    private float c;
/**
 * @param aPoint - aPoint.
 * @param bPoint - bPoint.
 * @param cPoint - cPoint.
 */
	public Square(float aPoint, float bPoint, float cPoint) {
        	this.a = aPoint;
        	this.b = bPoint;
        	this.c = cPoint;
	}
/**
 * calculate(int x).
 * @param x - x.
 * @return y - y.
 */
	float calculate(int x) {
       		float y = this.a * ((float) Math.pow(x, 2)) + this.b * x + this.c;
        	System.out.println(String.format("Значение квадратного уровнения: %s", y));
        	return y;
	}
/**
 * calculate(int x).
 * @param start - start.
 * @param finish - finish.
 * @param step - step.
 * @throws java.io.IOException java.io.IOException
 */
	void show(float start, float finish, float step) throws java.io.IOException {
		for (float index = start; index <= finish; index = index + step) {
			System.out.println(String.format("Значение функции, при выполнении цикла: %s", index));
		}
	}
}