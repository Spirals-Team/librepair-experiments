package ru.job4j;

import java.util.Arrays;


/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */

public class Concat {
	/**
	* Метод реализует обединение двух отсортированных массив в один.
	* @param 2 отсортированных массива.
	* @return  массив, состоящий из двух массивов.
	*/
	public static int[] merge(int[] a, int[] b) {
		int[] c = new int[a.length + b.length];
		int aIndex = 0;
		int bIndex = 0;
		int i = 0;
		while (i < c.length) {
			c[i] = a[aIndex] < b[bIndex] ? a[aIndex++] : b[bIndex++];
			if (aIndex == a.length) {
				System.arraycopy(b, bIndex, c, ++i, b.length - bIndex);
				break;
			}
			if (bIndex == b.length) {
				System.arraycopy(a, aIndex, c, ++i, a.length - aIndex);
				break;
			}
			i++;
		}
		return c;
	}
}