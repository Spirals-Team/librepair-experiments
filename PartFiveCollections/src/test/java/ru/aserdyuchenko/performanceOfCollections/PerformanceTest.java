package ru.aserdyuchenko.performanceOfCollections;
/**
 * @author Anton Serdyuchenko (anton415@gmail.com   +7 (916) 154-92-00)
 */
import java.util.*;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import java.util.Random;

public class PerformanceTest {

/**
 * Тестирование времени на добавление новых эллементов.
 * Метод возвращает true, если скорость на добавление такая:
 * LinkedList < ArrayList < TreeSet
 */
  @Test
  public void whenFindPerformanceOfAdd() {
    Collection<String> arrayList = new ArrayList<String>();
    Collection<String> linkedList = new LinkedList<String>();
    Collection<String> treeSet = new TreeSet<String>();
    Performance performance = new Performance();
    long resultForArrayList = performance.add(arrayList, "qwertyuiopasdfghjklzxcvbnm", 5000);
    long resultForLinkedList = performance.add(linkedList, "qwertyuiopasdfghjklzxcvbnm", 5000);
    long resultForTreeSet = performance.add(treeSet, "qwertyuiopasdfghjklzxcvbnm", 5000);
    boolean result = (resultForLinkedList < resultForArrayList) && (resultForArrayList < resultForTreeSet) ? true : false;
  //  assertThat(result, is(true));
  }
/**
 * Тестирование времени на удаление первых элементов.
 * Тестируются только ArrayList и LinkedList.
 * Потому что, в TreeSet хранятся уникальные и отсортированные элементы.
 * Тоесть в нашем случае, он имеет только один элемент.
 * Метод возвращает true, если скорость на добавление такая:
 * LinkedList < ArrayList < TreeSet
 */
  @Test
  public void whenFindPerformanceOfRemove() {
    Collection<String> arrayList = new ArrayList<String>();
    Collection<String> linkedList = new LinkedList<String>();
    Collection<String> treeSet = new TreeSet<String>();
    Performance performance = new Performance();
    performance.add(arrayList, "qwertyuiopasdfghjklzxcvbnm", 5000);
    long resultForArrayList = performance.delete(arrayList, 1000);
    performance.add(linkedList, "qwertyuiopasdfghjklzxcvbnm", 5000);
    long resultForLinkedList = performance.delete(linkedList, 1000);
    performance.add(treeSet, "qwertyuiopasdfghjklzxcvbnm", 5000);
    long resultForTreeSet = performance.delete(treeSet, 1000);
    boolean result = (resultForLinkedList < resultForArrayList) && (resultForArrayList < resultForTreeSet) ? true : false;
  //  assertThat(result, is(true));
  }
}
