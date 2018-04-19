package br.net.itech.services;

import java.util.Optional;

import br.net.itech.entities.People;

public interface PeopleService {
	/**
	 * Find People by Id
	 * 
	 * @param Id
	 * @return Optional<People>
	 */
	Optional<People> findById(Integer id);
	
	/**
	 * Find People by Email
	 * 
	 * @param Email
	 * @return Optional<People>
	 */
	Optional<People> findByEmail(String email);
	
	/**
	 * Find People by CPF
	 * 
	 * @param CPF
	 * @return Optional<People>
	 */
	Optional<People> findByCpf(String cpf);

	/**
	 * Create People
	 * 
	 * @param People
	 * @return People
	 */
	People create(People people);
}