package com.tiagogouvea.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tiagogouvea.api.entities.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long>{

	Empresa findByCnpj(String cnpj);
	
}
