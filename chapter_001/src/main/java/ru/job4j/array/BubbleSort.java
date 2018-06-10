package ru.job4j.array;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
 
 public class BubbleSort {
	/**
	* Метод сортировка пузырьком.
	* @param  array массив.
	* @return массив отсортированный.
	*/  
	public int[] sort(int[] arr) {
		for (int i = arr.length - 1; i > 0; i--) {
			for (int j = 0; j < i; j++) {
				if (arr[j] > arr[j + 1]) {
					int tmp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = tmp;
				}
			}	
        }
		return arr;
    }
}	