package br.net.itech.services;

import java.util.Optional;

import br.net.itech.entities.City;

public interface CityService {
	/**
	 * Find City by Id
	 * 
	 * @param Id
	 * @return Optional<City>
	 */
	Optional<City> findById(Integer id);

	/**
	 * Find City by Name
	 * 
	 * @param Id
	 * @return Optional<City>
	 */
	Optional<City> findByName(String name);
	
	/**
	 * Find City by Name and ProvinceId
	 * 
	 * @param name
	 * @param provinceId
	 * @return
	 */
	Optional<City> findByNameAndProvinceId(String name, Integer provinceId); 

	/**
	 * Create City
	 * 
	 * @param City
	 * @return City
	 */
	City create(City city);
}