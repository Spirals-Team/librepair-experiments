package ru.aserdyuchenko.convertList;
/**
 * @author Anton Serdyuchenko (anton415@gmail.com   +7 (916) 154-92-00)
 */
import java.util.*;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ConvertListTest {
/**
 * Тестирование конвертации массива в список.
 */
  @Test
  public void whenConvertArrayToList() {
    ConvertList convert = new ConvertList();
    int[][] array = {{1, 2, 3}, {4, 5, 6}};
    List<Integer> result = convert.toList(array);
    assertThat(result, is(Arrays.asList(1, 2, 3, 4, 5, 6)));
  }
/**
 * Тестирование конвертации списка в массив.
 */
  @Test
  public void whenConvertListToArray() {
    ConvertList convert = new ConvertList();
    List<Integer> list = Arrays.asList(1, 2, 3, 4);
    int[][] result = convert.toArray(list, 3);
    int[][] trueArray = {{1, 2, 3}, {4, 0, 0}};
    assertThat(result, is(trueArray));
  }
}
