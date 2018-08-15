package ru.job4j.loop;
/**
*Класс для подсчета суммы в заданном диапазоне числе.
*@author ifedorenko
*@since 14.08.2017
*@version 1
*/
public class Counter {
	/**
	*Основной метод в программе.
	*@param start начало диапазона
	*@param finish конец диапазона
	*@return summ возвращает сумму четных чисел
	*/
	public int add(int start, int finish) {
		int summ = 0;
		for ( ; start <= finish; start++) {
			if (start % 2 == 0) {
				summ = summ + start;
			}
		}
		return summ;
	}
}