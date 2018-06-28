package ru.job4j.search;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class ConvertList {
	 /**
     * Метод конвертирует матрицу в список.
     * @param двумерный массив
     * @return Лист, состоящий из элементов массива.
     */
	 
	public List<Integer> toList(int[][] array) {
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				list.add(array[i][j]);
			}
		}
		return list;
		
	}
	
	/**
     * Метод разбивает лист на многомерный массив.
     * @param список, который разбиваем и количество разбиений списка
     * @return многомерный массив, состоящий из элементов списка и нулей, если элементы в списке закончились раньше.
     */
	public int[][] toArray(List<Integer> list, int rows) {
		int size = list.size() / rows + 1;
		int[][] array = new int[size][rows];
		Iterator<Integer> itr = list.iterator();
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < rows; j++) {
					if (itr.hasNext()) {
						array[i][j] = itr.next();
					} else {
						array[i][j] = 0;
					}	
				}
			}
		return array;
	}
	
	/**
     * Метод конвертирует все массивы, из которых состоит список в 1 общий лист интов.
     * @param список массивов
     * @return интов, из которых состояли переданные массивы.
     */
	public List<Integer> convert(List<int[]> list) {
		List<Integer> listConvert = new ArrayList<>();
		for (int[] lists : list) {
			for (int i = 0; i < lists.length; i++) {
				listConvert.add(lists[i]);
			}
		}
		return listConvert;
	}
}