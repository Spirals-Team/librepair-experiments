package br.net.itech.factories.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.net.itech.dtos.PeopleRegistrationDto;
import br.net.itech.entities.People;

@Component
public class PeopleRegistrationFactory {
	@Autowired
	AddressFactory addressFactory;

	public People getPeople(PeopleRegistrationDto peopleRegistrationDto) {
		People people = new People();
		people.setEmail(peopleRegistrationDto.getEmail());
		people.setCpf(peopleRegistrationDto.getCpf());
		people.setName(peopleRegistrationDto.getName());
		people.setRg(peopleRegistrationDto.getRg());
		people.setAddress(addressFactory.getAddress(peopleRegistrationDto.getAddress()));
		return people;
	}
}