package br.net.itech.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.net.itech.entities.Diagnosis;

@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Integer> {
	/**
	 * Find Diagnosis by Id
	 * 
	 * @param Id
	 * @return Optional<Diagnosis>
	 */
	Optional<Diagnosis> findById(Integer id);
}