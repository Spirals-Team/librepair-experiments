package com.abgi.pontointeligente.api.services;

import java.util.Optional;

import com.abgi.pontointeligente.api.entities.Empresa;

public interface EmpresaService {

	/**
	 * Retorna uma empresa dado um cnpj
	 * 
	 * @param cnpj
	 * @return Optional<mpresa>
	 */
	Optional<Empresa> buscarPorCnpj(String cnpj);

	/**
	 * Cadastra uma nova empresa na base de dados
	 * 
	 * @param empresa
	 * @return Empresa
	 */
	Empresa persistir(Empresa empresa);
}
