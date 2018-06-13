package ru.job4j.phone;

import java.util.ArrayList;
import java.util.List;

public class Search {
    private List<Person> persons = new ArrayList<Person>();

    public void add(Person person) {
        this.persons.add(person);
    }

    /**
     * Вернуть список всех пользователей, который содержат key в любых полях.
     *
     * @param key Ключ поиска.
     * @return Список подощедщих пользователей.
     */

    public List<Person> find(String key) {
        List<Person> result = new ArrayList<>();
        for (Person index : persons) {
            if (index.getName().contains(key) || index.getSurname().contains(key) || index.getAddress().contains(key) || index.getPhone().contains(key)) {
                result.add(index);
            }
        }
        return result;
    }
}
