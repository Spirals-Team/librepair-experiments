package br.com.rodolfo.pontointeligente.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rodolfo.pontointeligente.api.entities.Empresa;
import br.com.rodolfo.pontointeligente.api.repositories.EmpresaRepository;
import br.com.rodolfo.pontointeligente.api.services.EmpresaService;

/**
 * EmpresaServiceImpl
 */
@Service
public class EmpresaServiceImpl implements EmpresaService{

    private static final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);

    @Autowired
    private EmpresaRepository empresaRepository;

	@Override
	public Optional<Empresa> BuscarPorCnpj(String cnpj) {
        
        log.info("Buscando empresa para o CNPJ: {}", cnpj);

        return Optional.ofNullable(this.empresaRepository.findByCnpj(cnpj));
	}

	@Override
	public Empresa persistir(Empresa empresa) {

        log.info("Persistindo empresa: {}", empresa);
        
        return this.empresaRepository.save(empresa);
	}

    
}