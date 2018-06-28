package ru.job4j.loop;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
 
 public class Factorial {
	 
	/**
	* Метод должен вычислить факториал числа.
	* @param n число, для которого находим фаториал.
	* @return Вернуть факториал числа.
	*/ 
	public int calc(int n) {
		int factor = 1;
		for (int i = 2; i <= n; i++) {
			factor *=  i;
		}
		return factor;
	}
}	