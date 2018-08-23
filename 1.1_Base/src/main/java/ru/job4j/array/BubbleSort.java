package ru.job4j.array;
/**
*Класс выполняющий сортировку пузырьком.
*@author ifedorenko
*@since 16.08.2017
*@version 1
*/
public class BubbleSort {
	/**
	*Основной метод в программе.
	*@param array Входящий параметр. Неотсортированный массив чисел
	*@return array на вхыоде возвращает отсортированный массив массив.
	*/
	public int[] sort(int[] array) {
		int length = array.length - 1;
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length - i; j++) {
				if (array[j] > array[j + 1]) {
					int change = array[j + 1];
					array[j + 1] = array[j];
					array[j] = change;
				} else {
					continue;
				}
			}
		}
		return array;
	}
}