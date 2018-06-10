package ru.job4j.search;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */

import java.util.ArrayList;
import java.util.List;

public class PhoneDictionary {
    private List<Person> persons = new ArrayList<Person>();
    
    public void add(Person person) {
        this.persons.add(person);
    }

    /**
     * Вернуть список всех пользователей, который содержат key в любых полях.
     * @param key Ключ поиска.
     * @return Список подощедщих пользователей.
     */
    public List<Person> find(String key) {
        List<Person> result = new ArrayList<>();
		for (Person person : persons) {
			if (((person.getName()).contains(key)) || ((person.getSurname()).contains(key)) || ((person.getPhone()).contains(key)) || ((person.getAddress()).contains(key))) {
				result.add(person);
			}	
		}
        return result;
    }
}