package ru.job4j.condition;
/**
*Класс вычисляет площадь треугольника.
*@author ifedorenko
*@since 14.08.2017
*@version 1
*/
public class Triangle {
	/**
	*@param a точка а
	*/
	private Point a;
	/**
	*@param b точка b
	*/
	private Point b;
	/**
	*@param c точка c
	*/
	private Point c;
	/**
	*конструктор класса.
	*@param a точка a
	*@param b точка b
	*@param c точка с
	*/
	public Triangle(Point a, Point b, Point c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	/**
	* Метод должен вычислять расстояние между точками left и right.
	*
	* Для вычисления расстояния использовать формулу.
	* √(xb - xa)^2 + (yb - ya)^2
	*
	* где √ - корень квадратный, для извлечения корня использовать метод Math.sqrt().
	*
	* ^ - степень.
	*
	* @param left Точка слева
	* @param right Точка с права.
	* @return расстояние между точками left и right.
	*/
	public double distance(Point left, Point right) {
		double lenght = Math.sqrt(Math.pow(right.getX() - left.getX(), 2) + Math.pow(right.getY() - left.getY(), 2));
		return lenght;
	}
	/**
	* Метод вычисления периметра по длинам сторон.
	*
	* Формула.
	*
	* (ab + ac + bc) / 2
	*
	* @param ab расстояние между точками a b
	* @param ac расстояние между точками a c
	* @param bc расстояние между точками b c
	* @return Перимент.
	*/
	public double period(double ab, double ac, double bc) {
		double perim  = (ab + ac + bc) / 2;
		return perim;
	}
	/**
	* Метод должен вычислить прощадь треугольканива.
	*
	* Формула.
	*
	* √ p *(p - ab) * (p - ac) * (p - bc)
	*
	* где √ - корень квадратный, для извлечения корня использовать метод Math.sqrt().
	*
	* @return Вернуть прощадь, если треугольник существует или -1.
	*/
	public double area() {
		double rsl = -1;
		double ab = this.distance(this.a, this.b);
		double ac = this.distance(this.a, this.c);
		double bc = this.distance(this.b, this.c);
		double p = this.period(ab, ac, bc);
		if (this.exist(ab, ac, bc)) {
        rsl = Math.sqrt(p * (p - ab) * (p - ac) * (p - bc));
		}
    return rsl;
	}
	/**
	* Метод проверяет можно ли построить треугольник с такими длинами сторон.
	* Подумайте какое надо написать условие, чтобы определить можно ли построить треугольник.
	* @param ab Длина от точки a b.
	* @param ac Длина от точки a c.
	* @param bc Длина от точки b c.
	* @return результат
	*/
	private boolean exist(double ab, double ac, double bc) {
		if (ab + ac > bc && ab + bc > ac && ac + bc > ab) {
			return true;
		}
		return false;
	}
}
