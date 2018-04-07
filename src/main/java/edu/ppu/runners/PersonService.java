package edu.ppu.runners;

import java.util.List;

import edu.ppu.domain.Person;

/**
 * 
 * This is a service that returns a list of {@Person}s. Assume its coming from
 * DB or internet
 * 
 * @author wesam
 *
 */
public interface PersonService {
	List<Person> getPersons(int maxAge);
}
