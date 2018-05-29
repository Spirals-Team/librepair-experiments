package ru.job4j.comparator;

import java.util.Comparator;

/**
 * Очень часто возникает ситуация, когда нужно сравнить два слова.

 У нас есть готовый метод String.compareTo.

 В это задании нужно создать подобный метод самому.

 Вам нужно реализовать компаратор для сравнения двух массивов символов.

 Необходимо реализовать поэлементное сравнение двух списков, т.е. сравниваем элементы двух списков, находящихся на одних и тех же позициях (по одним и тем же индексом). Сравнение в лексикографическом порядке.

 В этом задании нельзя использовать метод String.compateTo.

 Вы можете использовать

 String.charAt(int index)

 Integer.compare(int left, int right),

 Character.compare(char left, char right);
 */

public class ListCompare implements Comparator<String> {


    @Override
    public int compare(String left, String right) {
      int result = 0;
      int min = Math.min(left.length(), right.length());
      for (int index = 0; index < min; index++) {
          if (left.charAt(index) != right.charAt(index)) {
              result = Integer.compare(left.charAt(index), right.charAt(index));
              break;
          }
      }
      if (result == 0 && left.length() != right.length()) {
          result = left.length() - right.length();
      }
      return  result;
    }
}
