package ru.job4j.condition;
/**
*Класс помогает узнать, принадлежит ли точка функции y = a * x + b.
*@author ifedorenko
*@since 14.08.2017
*@version 1
*/
public class Point {
	/**
	*@param x значение по координате x
	*/
	private int x;
	/**
	*@param y значение по координате y
	*/
	private int y;
	/**
	*конструктор класса.
	*@param x значение по координате x
	*@param y значение по координате y
	*/
	public  Point(int x, int y) {
	this.x = x;
	this.y = y;
	}
	/**
	*Возвращает значение по координате x.
	*@return x
	*/
	public int getX() {
	return this.x;
	}
	/**
	*Возвращает значение по координате y.
	*@return y
	*/
	public int getY() {
	return this.y;
	}
	/**
	*Метод определяет принадлежит ли точка заданной функции.
	*@param a значение для функции
	*@param b значение для функции
	*@return результат проверки
	*/
	public boolean is(int a, int b) {
		return this.y == this.x * a + b;
	}
}