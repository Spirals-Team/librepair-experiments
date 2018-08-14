package com.Gabriel.Biblioteca.api.services.implement;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.Gabriel.Biblioteca.api.entities.Autor;
import com.Gabriel.Biblioteca.api.repositories.AutorRepository;
import com.Gabriel.Biblioteca.api.services.AutorService;

@Service
public class AutorServiceImpl implements AutorService {

	private static final Logger log = LoggerFactory.getLogger(AutorServiceImpl.class);

	@Autowired
	private AutorRepository autorRepository;

	@Override
	public Optional<Autor> buscaAutorPorCodigo(String codigo) {
		log.info("Buscando Autor pelo codigo {}", codigo);
//		return Optional.ofNullable(autorRepository.procuraPorCodigo(codigo));
		return null;
	}

	@Override
	public Autor persistir(Autor autor) {
		log.info("Cadastrando autor: {}", autor);
		return this.autorRepository.save(autor);
	}

}
