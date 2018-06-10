package ru.job4j.array;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
 
 public class Matrix {
	/**
	* Метод создания квардратной матрицы.
	* @param  size размерность квадратной матрицы.
	* @return квадртная матрица.
	*/  
	int[][] multiple(int size) {
		int[][] matr = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				matr[i][j] = i * j;
			}
		}
		return matr;
	}
 }	