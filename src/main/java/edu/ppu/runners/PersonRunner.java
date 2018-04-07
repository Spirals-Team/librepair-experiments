package edu.ppu.runners;

import java.util.List;

import edu.ppu.domain.Person;

public class PersonRunner {

	private PersonService personService;

	public PersonRunner(PersonService personService) {
		super();
		this.personService = personService;
	}

	public Person getOldestGuy() {
		Person maxAgePerson = null;
		for (Person currPerson : personService.getPersons(50)) {
			if (maxAgePerson == null || currPerson.getAge() > maxAgePerson.getAge())
				maxAgePerson = currPerson;
		}
		return maxAgePerson;
	}

	public double getAvg() {
		int sum = 0;
		List<Person> persons = personService.getPersons(50);
		for (Person person : persons) {
			sum += person.getAge();
		}
		return sum / persons.size();
	}

	public int getAgeCategory(int age) {

		if (age > 150)
			throw new IllegalArgumentException("invalid age");
		if (age < 20)
			return 1;
		else if (age < 60)
			return 2;
		else
			return 3;

	}
}
