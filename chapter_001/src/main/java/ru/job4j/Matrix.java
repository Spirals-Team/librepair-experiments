package ru.job4j;

import java.util.Arrays;


/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */

public class Matrix {
	/**
	* Метод реализует поворот матрицы на 90 градусов.
	* @param исходная матрица.
	* @return перевернутая матрица.
	*/
	public int[][] convertWithMatrix(int[][] srcMtrx) {
		int[][] resMtrx = new int[srcMtrx.length][srcMtrx.length];
		for (int i = 0; i < srcMtrx.length; i++) {
			for (int j = 0; j < srcMtrx.length; j++) {
				resMtrx[i][j] = srcMtrx[j][i];
			}
		}
		return resMtrx;
	}
	
	/**
	* Метод реализует поворот матрицы на 90 градусов без использования промежуточной матрицы.
	* @param исходная матрица.
	* @return перевернутая матрица.
	*/
	public int[][] convertWithOutMatrix(int[][] mtrx) {
		for (int i = 0; i < mtrx.length; i++) {
			for (int j = 0; j < i; j++) {
				int tmp = mtrx[i][j];
				mtrx[i][j] = mtrx[j][i];
				mtrx[j][i] = tmp;
			}
		}
		return mtrx;
	}
}	