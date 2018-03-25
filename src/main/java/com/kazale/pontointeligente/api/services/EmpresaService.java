package com.kazale.pontointeligente.api.services;

import java.util.Optional;

import com.kazale.pontointeligente.api.entities.Empresa;

public interface EmpresaService {

	/**
	 * Busca uma empresa pelo CNPJ
	 * @param cnpj
	 * @return
	 */
	Optional<Empresa> buscarPorCnpj(String cnpj);
	
	/**
	 * Cadastra uma nova empresa
	 * @param empresa
	 * @return
	 */
	Empresa persistir(Empresa empresa);
}
