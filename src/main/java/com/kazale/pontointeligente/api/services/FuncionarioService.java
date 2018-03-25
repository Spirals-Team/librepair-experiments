package com.kazale.pontointeligente.api.services;

import java.util.Optional;

import com.kazale.pontointeligente.api.entities.Funcionario;

public interface FuncionarioService {

	/**
	 * Persiste um funcionário na base de dados
	 * @param funcionario
	 * @return
	 */
	Funcionario persistir(Funcionario funcionario);
	
	/**
	 * Retorna funcionário buscando pelo CPF
	 * @param cpf
	 * @return
	 */
	Optional<Funcionario> buscarPorCpf(String cpf);
	
	/**
	 * Retorna funcionário buscando pelo Email
	 * @param email
	 * @return
	 */
	Optional<Funcionario> buscarPorEmail(String email);
	
	/**
	 * Retorna funcionário buscando pelo ID
	 * @param id
	 * @return
	 */
	Optional<Funcionario> buscarPorId(Long id);
	
}
