package ru.job4j.loop;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
 
 public class Counter {
	 
	/**
	* Метод должен вычислить сумму четных чисел из заданного диапазона.
	* @param start, finish границы диапазона.
	* @return Вернуть сумму четных чисел.
	*/ 
	public int add(int start, int finish) {
		int sum = 0;
			for (int i = start; i <= finish; i++) {
				if (i % 2 == 0) {
					sum += i;
				}
			}
		return sum;
	}
	 
 }