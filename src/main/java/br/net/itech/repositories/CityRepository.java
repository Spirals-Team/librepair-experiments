package br.net.itech.repositories;

import java.util.Optional;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.net.itech.entities.City;

@Repository
@NamedQueries({
		@NamedQuery(name = "CityRepository.findByNameProvinceId", 
				query = "select city from CityRepository city where city.name = :name and city.province.id = :provinceId") })
public interface CityRepository extends JpaRepository<City, Integer> {
	/**
	 * Find Diagnosis by Id
	 * 
	 * @param Id
	 * @return Optional<Address>
	 */
	Optional<City> findById(Integer id);

	/**
	 * Find Diagnosis by Name
	 * 
	 * @param Name
	 * @return Optional<City>
	 */
	Optional<City> findByName(String name);

	Optional<City> findByNameAndProvinceId(@Param("name") String name, @Param("provinceId") Integer provinceId);
}