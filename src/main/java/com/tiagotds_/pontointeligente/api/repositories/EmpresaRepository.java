package com.tiagotds_.pontointeligente.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.tiagotds_.pontointeligente.api.entities.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

	@Transactional(readOnly = true)
	Empresa findByCnpj(String cnpj);
}
