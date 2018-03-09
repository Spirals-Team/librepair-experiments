package ru.aserdyuchenko.convertListToInteger;

import java.util.*;

class ConvertList {
  /**
   * Конвертация листа массивов в один лист Integer.
   */
  public List<Integer> convert(List<int[]> list) {
    List<Integer> newList = new ArrayList<Integer>(10);
    for (int[] array : list) {
      for (int number: array) {
        newList.add(number);
      }
    }
    return newList;
  }
  /**
   * Конвертация двумерного массива в коллекцию.
   */
  public List<Integer> toList(int[][] array) {
    List<Integer> list = new ArrayList<Integer>(10);
    for (int[] innerArray : array) {
      for (int number: innerArray) {
        list.add(number);
      }
    }
    return list;
  }
  /**
   * Конвертация коллекции в двумерный массив.
   */
  public int[][] toArray(List<Integer> list, int rows) {
    // Создание нового массива.
    int columns;
    if ((list.size() % rows) == 0) {
      columns = list.size() / rows;
    } else {
      columns = (list.size() / rows) + 1;
    }
    int[][] array = new int[columns][rows];
    // Заполнение массива.
    Iterator<Integer> iterator = list.iterator();
    for (int i = 0; i < array.length; i++) {
      for (int j = 0; j < array[i].length; j++) {
        if (iterator.hasNext()) {
          array[i][j] = iterator.next();
        } else {
          break;
        }
      }
    }
    return array;
  }
}
