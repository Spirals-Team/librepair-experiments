package ru.aserdyuchenko.convertListToMap;
/**
 * @author Anton Serdyuchenko (anton415@gmail.com   +7 (916) 154-92-00)
 */
import java.util.*;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UserConvertTest {
/**
 * Тестирование конвертации List в Map.
 */
  @Test
  public void whenConvertListToMap() {
    UserConvert userConvert = new UserConvert();
    List<User> list = new ArrayList<User>();
    list.add(new User(0, "Люк Скайуокер", "Татуин"));
    list.add(new User(1, "Энакин Скайуокер", "Татуин"));
    HashMap<Integer, User> result = userConvert.process(list);
    assertThat(result.get(1).getName(), is("Энакин Скайуокер"));
  }
}
