package ru.job4j.max;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Max {
	/**
     * Выводит максимум из двух чисел.
     * @param first, second два числа от клиента.
     * @return максимум.
     */
	public int max(int first, int second) {
		return (first > second) ? first : second;
	}
	
	/**
     * Выводит максимум из трех чисел, используя метод для двух чисел.
     * @param first, second, third три числа от клиента.
     * @return максимум.
     */
	public int max(int first, int second, int third) {
		return max(max(first, second), third);
	}
}	