package com.abgi.pontointeligente.api.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abgi.pontointeligente.api.entities.Empresa;
import com.abgi.pontointeligente.api.repositories.EmpresaRepository;
import com.abgi.pontointeligente.api.services.EmpresaService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmpresaServiceImpl implements EmpresaService {

	@Autowired
	private EmpresaRepository empresaRepository;

	@Override
	public Optional<Empresa> buscarPorCnpj(String cnpj) {
		log.info("Buscando uma empresa para o CnPJ {}", cnpj);
		return Optional.ofNullable(empresaRepository.findByCnpj(cnpj));
	}

	@Override
	public Empresa persistir(Empresa empresa) {
		log.info("Persistindo empresa {}", empresa);
		return this.empresaRepository.save(empresa);
	}

}
