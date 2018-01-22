package py.org.fundacionparaguaya.pspserver.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import py.org.fundacionparaguaya.pspserver.families.dtos.PersonDTO;
import py.org.fundacionparaguaya.pspserver.families.services.PersonService;

@RestController
@RequestMapping(value = "/api/v1/people")
public class PersonController {

	private static final Logger LOG = LoggerFactory.getLogger(PersonController.class);
	
	private PersonService personService;
	
	public PersonController(PersonService personService) {
		this.personService = personService;
	}
	
	@PostMapping()
	public ResponseEntity<PersonDTO> addPerson(@Valid @RequestBody PersonDTO personDTO) throws URISyntaxException {
		PersonDTO result = personService.addPerson(personDTO);
		return ResponseEntity.created(new URI("/api/v1/people/" + result.getPersonId()))
				.body(result);
	}
	
	
	@PutMapping("/{personId}")
	public ResponseEntity<PersonDTO> updatePerson(@PathVariable("personId") Long personId, @RequestBody PersonDTO personDTO) {
		PersonDTO result = personService.updatePerson(personId, personDTO);
		return ResponseEntity.ok(result);
	}

	
	@GetMapping("/{personId}")
	public ResponseEntity<PersonDTO> getPersonById(@PathVariable("personId") Long personId) {
		PersonDTO dto = personService.getPersonById(personId);
		return ResponseEntity.ok(dto);
	}
	

	@GetMapping()
	public ResponseEntity<List<PersonDTO>> getAllPeople() {
		List<PersonDTO> people = personService.getAllPeople();
		return ResponseEntity.ok(people);
	}
	
	
	@DeleteMapping("/{personId}")
	public ResponseEntity<Void> deletePerson(@PathVariable("personId") Long personId) {
		LOG.debug("REST request to delete Person: {}", personId);
		personService.deletePerson(personId);
		return ResponseEntity.ok().build();
	}
	

}