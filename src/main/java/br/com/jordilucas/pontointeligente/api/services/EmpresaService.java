package br.com.jordilucas.pontointeligente.api.services;

import java.util.Optional;

import br.com.jordilucas.pontointeligente.api.entities.Empresa;

public interface EmpresaService {
	
	/**
	 * Retorna uma empresa por cnpj
	 * 
	 * @param cnpj
	 * @return Optional<Empresa>
	 * 
	 * **/
	Optional<Empresa> buscarPorCnpj(String cnpj);
	
	/**
	 * Cadastra uma nova empresa
	 * 
	 * @param empresa
	 * @result Empresa
	 * 
	 * **/
	Empresa persistir(Empresa empresa);
	
}
