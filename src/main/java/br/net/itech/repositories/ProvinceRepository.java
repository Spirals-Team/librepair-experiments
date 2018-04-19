package br.net.itech.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.net.itech.entities.Province;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer> {
	/**
	 * Find Diagnosis by Id
	 * 
	 * @param Id
	 * @return Optional<Province>
	 */
	Optional<Province> findById(Integer id);

	/**
	 * Find Diagnosis by Name
	 * 
	 * @param Name
	 * @return Optional<Province>
	 */
	Optional<Province> findByName(String name);
}