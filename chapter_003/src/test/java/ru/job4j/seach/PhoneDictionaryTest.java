package ru.job4j.seach;

import org.junit.Test;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PhoneDictionaryTest {
    @Test
    public void whenFindByName() {
        PhoneDictionary phones = new PhoneDictionary();
        phones.add(new Person("Daniil", "Emelianov", "79228820208", "Orenburg"));
        List<Person> persons = phones.find("Daniil");
        assertThat(persons.iterator().next().getName(), is("Daniil"));
    }
}