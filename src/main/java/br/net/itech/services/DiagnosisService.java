package br.net.itech.services;

import java.util.Optional;

import br.net.itech.entities.Diagnosis;

public interface DiagnosisService {
	/**
	 * Find Diagnosis by Id
	 * 
	 * @param Id
	 * @return Optional<Diagnosis>
	 */
	Optional<Diagnosis> findById(Integer id);
	
	/**
	 * Create Diagnosis
	 * 
	 * @param Diagnosis
	 * @return Diagnosis
	 */
	Diagnosis create(Diagnosis diagnosis);
}