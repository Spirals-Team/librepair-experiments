package ru.job4j.controlvopros;
/**
 * тестирование класса сортер
 */

import org.hamcrest.core.Is;
import org.junit.Test;
import ru.job4j.convertlistinmap.User;

import java.util.*;

import static org.junit.Assert.*;

public class SorterTest {

    /**
     * тестирование метода сорт
     */
    @Test
    public void sotrTestArrtoset() {
        ArrayList<User> listuesers = new ArrayList<>();
        User user1 = new User("Vasia", 15, "Sib");
        User user2 = new User("Vova", 11, "Moskva");
        User user3 = new User("Sesha", 30, "Novosibirsk");
        listuesers.addAll(Arrays.asList(user1, user2, user3));
        TreeSet<User> expected = new TreeSet<User>(Arrays.asList(user1, user2, user3));
        Set<User> result = new Sorter().sort(listuesers);
        assertThat(expected, Is.is(result));
    }


    @Test
    public void sortTestnameLenght() {
        ArrayList<User> listuesers = new ArrayList<>();
        User user1 = new User("Vasia", 15, "Sib");
        User user2 = new User("Vova", 11, "Moskva");
        User user3 = new User("Sesha", 30, "Novosibirsk");
        listuesers.addAll(Arrays.asList(user1, user2, user3));
        List<User> result = new Sorter().sortnameLength(listuesers);
        User expected = user2;
        assertThat(expected, Is.is(result.get(0)));
    }

    @Test
    public void sortTestnameLexicandAge() {
        ArrayList<User> listuesers = new ArrayList<>();
        User user1 = new User("Vasia", 15, "Sib");
        User user2 = new User("Vasia", 11, "Moskva");
        User user3 = new User("Sesha", 30, "Novosibirsk");
        listuesers.addAll(Arrays.asList(user1, user2, user3));
        List<User> result = new Sorter().sortnameandLexicandAge(listuesers);
        User expected = user2;
        assertThat(expected, Is.is(result.get(1)));
    }
}