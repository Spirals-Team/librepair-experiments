package ru.job4j.array;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
 
 public class Turn {
	/**
	* Метод должен перевернуть массив.
	* @param  array массив.
	* @return массив перевернутый.
	*/  
	public int[] back(int[] array) {
		for (int i = 0; i < array.length / 2; i++) {
			int tmp = array[i];
			array[i] = array[array.length - 1 - i];
			array[array.length - 1 - i] = tmp;
		}
		return array;
	} 
}	