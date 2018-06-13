package ru.job4j.phone;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import java.util.List;

public class SearchTest {
    @Test
    public void whenFindByName() {
        Search search = new Search();
        search.add(new Person("Elon", "Mask", "20091996", "Silicon Valley"));
        List<Person> persons = search.find("Elon");
        assertThat(persons.iterator().next().getSurname(), is("Mask"));
    }
}