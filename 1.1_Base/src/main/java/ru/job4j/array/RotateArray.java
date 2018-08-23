package ru.job4j.array;
/**
*Класс для поворота квадратного массива.
*@author ifedorenko
*@since 16.08.2017
*@version 1
*/
public class RotateArray {
	/**
	*Основной метод в программе.
	*@param array массив, который необходимо перевернуть.
	*@return array на выходе возвращает перевернутый массив.
	*/
	public int[][] rotate(int[][] array) {
		int j = 0;
		int n = array.length;
		for (int i = 0; i < n - 1; i++) {
			int change = array[j][i];
			array[j][i] = array[n - i - 1][j];
			array[n - i - 1][j] = array[n - 1][n - i - 1];
			array[n - 1][n - i - 1] = array[j + i][n - 1];
			array[j + i][n - 1] = change;
		}
		return array;
	}
}