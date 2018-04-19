package br.net.itech.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.itech.entities.Province;
import br.net.itech.repositories.ProvinceRepository;
import br.net.itech.services.ProvinceService;

@Service
public class ProvinceServiceImpl implements ProvinceService {
	@Autowired
	ProvinceRepository provinceRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProvinceServiceImpl.class);

	@Override
	public Optional<Province> findById(Integer id) {
		LOGGER.info("Buscando Province por Id: " + id);
		return provinceRepository.findById(id);
	}

	@Override
	public Optional<Province> findByName(String name) {
		LOGGER.info("Buscando Province por Name: " + name);
		return provinceRepository.findByName(name);
	}

	@Override
	public Province create(Province province) {
		LOGGER.info("Persistindo um estado: {}", province);
		return provinceRepository.save(province);
	}
}