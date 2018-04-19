package br.net.itech.services;

import java.util.Optional;

import br.net.itech.entities.Address;
import br.net.itech.entities.Diagnosis;

public interface AddressService {
	/**
	 * Find Address by Id
	 * 
	 * @param Id
	 * @return Optional<People>
	 */
	Optional<Address> findById(Integer id);

	/**
	 * Create Address
	 * 
	 * @param Address
	 * @return Address
	 */
	Address create(Address address);
}