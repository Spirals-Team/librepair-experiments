package com.hoffmann.pontointeligente.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.hoffmann.pontointeligente.api.entities.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long>{

	/**
	 * Transactional pois é uma consulta ao banco de dados 
	 * e não precisa de uma transação
	 * deixando mais rapido
	 * */
	@Transactional(readOnly = true)
	Empresa findByCnpj(String cnpj);
	
	
	
	
}
