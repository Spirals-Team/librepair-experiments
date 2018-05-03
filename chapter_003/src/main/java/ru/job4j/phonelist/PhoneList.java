package ru.job4j.phonelist;

import java.util.ArrayList;

/**
 * этот класс будет осуществлть работу телефонного справочника
 */
public class PhoneList {
    private ArrayList<Person> spravochnik = new ArrayList<>();

    public void addAbonent(Person person) {
        this.spravochnik.add(person);
    }

    public ArrayList<Person> search(String key) {
        ArrayList<Person> result = new ArrayList<>();
        for (Person i : spravochnik) {
            if (key.contains(i.getAdres()) || key.contains(i.getName()) || key.contains(i.getPhone()) || key.contains(i.getSurname())) {
                result.add(i);
            }
        }
        return result;
    }
}
