package ru.job4j.loop;
/**
*Класс для подсчета факториала.
*@author ifedorenko
*@since 14.08.2017
*@version 1
*/
public class Factorial {
	/**
	*Основной метод в программе.
	*@param n число, для которого необходимо рассчитать факториал
	*@return fact факториал для числа n
	*/
	public int calc(int n) {
		int fact = 1;
		if (n == 0) {
			return fact;
		} else {
			for (; n > 0; n--) {
				fact = fact * n;
			}
		}
		return fact;
	}
}