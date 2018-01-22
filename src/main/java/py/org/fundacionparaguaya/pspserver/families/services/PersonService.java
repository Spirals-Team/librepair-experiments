package py.org.fundacionparaguaya.pspserver.families.services;

import java.util.List;

import py.org.fundacionparaguaya.pspserver.families.dtos.PersonDTO;



public interface PersonService {
	
	PersonDTO updatePerson(Long personId, PersonDTO personDTO);

	PersonDTO addPerson(PersonDTO personDTO);
	
	PersonDTO getPersonById(Long personId);
	
	List<PersonDTO> getAllPeople();
	
	void deletePerson(Long personId);
}
