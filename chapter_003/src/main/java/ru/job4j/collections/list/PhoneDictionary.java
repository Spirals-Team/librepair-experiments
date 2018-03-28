package ru.job4j.collections.list;

import java.util.ArrayList;
import java.util.List;

public class PhoneDictionary {
    private List<Person> personList = new ArrayList<>();

    private boolean checkIfContains(String value, String key) {
        boolean result = false;
        result = value.contains(key);
        return result;
    }

    public void add(Person person) {
        personList.add(person);
    }

    public List<Person> find(String key) {
        List<Person> result = new ArrayList<>();
        for (int i = 0; i < personList.size(); i++) {
            if (checkIfContains(personList.get(i).getAddress(), key) ||
                checkIfContains(personList.get(i).getName(), key) ||
                checkIfContains(personList.get(i).getPhone(), key) ||
                checkIfContains(personList.get(i).getSurname(), key)) {
                result.add(personList.get(i));
            }
        }
        return result;
    }
}
