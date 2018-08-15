package ru.job4j.array;
/**
*Класс позволяет перевернуть массив.
*@author ifedorenko
*@since 15.08.2017
*@version 1
*/
public class Turn {
	/**
	*Основной метод в программе.
	*@param array массив чисел
	*@return array возвращает перевернутый массив.
	*/
	public int[] back(int[] array) {
		int length = array.length - 1;
		for (int i = 0; i <= length; i++) {
			int change = array[i];
			array[i] = array[array.length - i - 1];
			array[array.length - i - 1] = change;
			length = length - 1;
		}
		return array;
	}
}
