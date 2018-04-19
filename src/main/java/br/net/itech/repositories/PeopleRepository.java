package br.net.itech.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.net.itech.entities.People;

@Repository
public interface PeopleRepository extends JpaRepository<People, Integer> {
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
}