package ru.job4j.array;

import java.util.Arrays;
/**
*Класс для удаления повторяющихся элементов.
*@author ifedorenko
*@since 16.08.2017
*@version 1
*/
public class ArrayDuplicate {
	/**
	*Основной метод в программе.
	*@param array Входящий параметр. Массив  для проверки.
	*@return array на выходе возвращает массив, с уникальными именами
	*/
	public String[] remove(String[] array) {
		int count = 0; //счетчик повторений в массиве.
		int length = array.length; //размер массива.
		for (int i = 0; i < length; i++) {
			for (int j = 1 + i; j < length; j++) {
				if (array[i].equals(array[j])) {
					array[j] = array[length - 1];
					array[length - 1] = "0";
					length--; //закрываем для сравнения крайний элемент массива.
					j--; // для проверки вновь вставленного значения на старой позиции.
					count++;
				}
			}
		}
		return Arrays.copyOf(array, array.length - count);
	}
}



