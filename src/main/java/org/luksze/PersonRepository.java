package org.luksze;

import java.util.ArrayList;
import java.util.List;

class PersonRepository {

    private final List<Person> people = new ArrayList<>();

    public PersonRepository() {
        people.add(new Person("John", "Smith"));
        people.add(new Person("George", "Newman"));
    }

    public List<Person> getAll() {
        return people;
    }

    public void add(Person person) {
        people.add(person);
    }
}
