package ru.aserdyuchenko.performanceOfCollections;
/**
 * @author Anton Serdyuchenko (anton415@gmail.com   +7 (916) 154-92-00)
 */
import java.util.*;
/**
 * Программа для замера времени на добавление и удаление элементов в коллекции.
 */
public class Performance {
  public String generateRandomeString(String symbols) {
    Random random = new Random();
    StringBuilder randString = new StringBuilder();
    int count = (int)(Math.random()*10);
    for (int i = 0; i <= count; i++) {
      randString.append(symbols.charAt((int)(Math.random()*symbols.length())));
    }
    return randString.toString();
  }
/**
 * Метод замера времени добавления элементов.
 * @param collection - коллекция в которую добавляются элементы.
 * @param line - строка которая добавляется в качестве элемента.
 * @param amount - количество элементов, которые будут добавлены.
 * @return result - время за которое были добавлены элементы.
 */
  public long add(Collection<String> collection, String line, int amount) {
    long start = System.currentTimeMillis();
    for (int index = 0; index <= amount; index++) {
      collection.add(generateRandomeString(line));
    }
    long finish = System.currentTimeMillis();
    long result = finish - start;
    return result;
  }

  /**
   * Метод замера времени удаления элементов.
   * @param collection - коллекция из которой удаляются элементы.
   * @param amount - количество элементов, которые будут удалены.
   * @return result - время за которое были удалены элементы.
   */
  public long delete(Collection<String> collection, int amount) {
    long start = System.currentTimeMillis();
    Iterator iterator = collection.iterator();
    for (int i = 0; i <= amount; i++) {
      if (iterator.hasNext()) {
        iterator.next();
        iterator.remove();
      } else break;
    }
    long finish = System.currentTimeMillis();
    long result = finish - start;
    return result;
  }
}
