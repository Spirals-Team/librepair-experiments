package ru.job4j.array;

import java.util.Arrays;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
 
 public class ArrayDuplicate {
	/**
	* Метод удаление дубликатов из массива.
	* @param  array исходный массив.
	* @return array массив без дубликатов.
	*/  
	public String[] remove(String[] array) {
		int barrier = array.length;
		for (int out = 0; out < barrier; out++) {
			for (int in = out + 1; in < barrier; in++) {
				if (array[out].equals(array[in])) {
					array[in] = array[barrier - 1];
					barrier--;
					in--;
				}
			}
		}
		return Arrays.copyOf(array, barrier);
	}
}	