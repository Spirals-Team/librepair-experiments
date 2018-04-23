package ru.aserdyuchenko.convertListToInteger;
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
  public void whenConvertListToInteger() {
    ConvertList convertList = new ConvertList();
    List<int[]> list = new ArrayList<int[]>();
    list.add(new int[] {1, 2});
    list.add(new int[] {3, 4, 5, 6});
    List<Integer> result = convertList.convert(list);
    assertThat(result, is(Arrays.asList(1, 2, 3, 4, 5, 6)));
  }
}
