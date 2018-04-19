package br.net.itech.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.net.itech.entities.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
	/**
	 * Find Diagnosis by Id
	 * 
	 * @param Id
	 * @return Optional<City>
	 */
	Optional<Address> findById(Integer id);
}