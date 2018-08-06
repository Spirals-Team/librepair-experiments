package com.felipe.pontointeligente.api.services;

import java.util.Optional;

import com.felipe.pontointeligente.api.entities.Funcionario;

public interface FuncionarioService {

	/**
	 * Persiste um funcionário na base de dados.
	 * 
	 * @param funcionario
	 * @return Funcionario
	 */
	Funcionario persistir(Funcionario funcionario);
	
	/**
	 * Busca e retorna um funcionário dado um CPF
	 * 
	 * @param cpf
	 * @return Optional<Funcionario>
	 */
	Optional<Funcionario> buscarPorCpf(String cpf);
	
	/**
	 * Busca e retorna um funcionário dado um email.
	 * 
	 * @param email
	 * @return <OptionalFuncionario>
	 */
	Optional<Funcionario> buscarPorEmail(String email);
	
	/**
	 * Busca e retorna um funcionário dado um id.
	 * 
	 * @param id
	 * @return Optional<Funcionario>
	 */
	Optional<Funcionario> buscarPorId(Long id);
}
