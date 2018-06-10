package ru.job4j.array;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
 
 public class Square {
	/**
	* Метод должен заполнять массив квадратами чисел.
	* @param  bound - верхняя граница массива.
	* @return массив квардратов чисел.
	*/  
	public int[] calculate(int bound) {
		int[] rsl = new int[bound];
		// заполнить массив через цикл элементами от 1 до bound возведенные в квадрат
		for (int i = 0; i < bound; i++) {
			rsl[i] = (int) Math.pow(i + 1, 2);
		}
		return rsl;
	}
}