package br.net.itech.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.itech.entities.Diagnosis;
import br.net.itech.repositories.DiagnosisRepository;
import br.net.itech.services.DiagnosisService;

@Service
public class DiagnosisServiceImpl implements DiagnosisService {
	@Autowired
	DiagnosisRepository diagnosisRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(DiagnosisServiceImpl.class);

	@Override
	public Optional<Diagnosis> findById(Integer id) {
		LOGGER.info("Buscando Diagnosis por Id: " + id);
		return diagnosisRepository.findById(id);
	}
	
	@Override
	public Diagnosis create(Diagnosis diagnosis) {
		LOGGER.info("Persistindo um diagnostico: {}", diagnosis);
		return diagnosisRepository.save(diagnosis);
	}
}