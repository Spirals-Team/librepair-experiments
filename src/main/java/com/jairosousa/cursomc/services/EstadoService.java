package com.jairosousa.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jairosousa.cursomc.domain.Estado;
import com.jairosousa.cursomc.repository.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository estadoRepository;

	public List<Estado> findAll() {
		return estadoRepository.findAllByOrderByNome();
	}

}
