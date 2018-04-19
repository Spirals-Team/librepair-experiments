package br.net.itech.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.itech.entities.Address;
import br.net.itech.repositories.AddressRepository;
import br.net.itech.services.AddressService;

@Service
public class AddressServiceImpl implements AddressService {
	@Autowired
	AddressRepository addressRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(AddressServiceImpl.class);

	@Override
	public Optional<Address> findById(Integer id) {
		LOGGER.info("Buscando Address por Id: " + id);
		return addressRepository.findById(id);
	}

	@Override
	public Address create(Address address) {
		LOGGER.info("Persistindo um endereço: {}", address);
		return addressRepository.save(address);
	}
}