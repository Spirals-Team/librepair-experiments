package br.net.itech.services;

import java.util.Optional;

import br.net.itech.entities.Province;

public interface ProvinceService {
	/**
	 * Find Province by Id
	 * 
	 * @param Id
	 * @return Optional<Province>
	 */
	Optional<Province> findById(Integer id);
	
	/**
	 * Find Province by Name
	 * 
	 * @param Name
	 * @return Optional<Province>
	 */
	Optional<Province> findByName(String name);

	/**
	 * Create Province
	 * 
	 * @param Province
	 * @return Province
	 */
	Province create(Province province);
}