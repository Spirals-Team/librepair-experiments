package edu.ppu.runners;

import java.util.Arrays;
import java.util.List;

import edu.ppu.domain.Person;

public class PersonServiceImpl implements PersonService {

	public List<Person> getPersons(int maxAge) {
		return Arrays.asList(new Person("p2", 10), new Person("p3", 20), new Person("Gohst", 3000));
	}

}
