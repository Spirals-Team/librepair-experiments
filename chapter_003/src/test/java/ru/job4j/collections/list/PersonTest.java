package ru.job4j.collections.list;

import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;


public class PersonTest {
    @Test
    public void whenFindByName() {
        PhoneDictionary phones = new PhoneDictionary();
        phones.add(
                new Person("Abram", "Brown", "537221", "Boston")
        );
        List<Person> persons = phones.find("Abram");
        assertThat(persons.iterator().next().getSurname(), is("Brown"));
    }
}