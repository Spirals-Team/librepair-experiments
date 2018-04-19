package br.net.itech.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.itech.entities.People;
import br.net.itech.repositories.PeopleRepository;
import br.net.itech.services.PeopleService;

@Service
public class PeopleServiceImpl implements PeopleService {
	@Autowired
	PeopleRepository peopleRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(PeopleServiceImpl.class);

	@Override
	public Optional<People> findById(Integer id) {
		LOGGER.info("Buscando People por Id: " + id);
		return peopleRepository.findById(id);
	}

	@Override
	public Optional<People> findByEmail(String email) {
		LOGGER.info("Buscando People por Email: " + email);
		return peopleRepository.findByEmail(email);
	}

	@Override
	public Optional<People> findByCpf(String cpf) {
		LOGGER.info("Buscando People por CPF: " + cpf);
		return peopleRepository.findByCpf(cpf);
	}

	@Override
	public People create(People people) {
		LOGGER.info("Persistindo uma pessoa: {}", people);
		return peopleRepository.save(people);
	}
}