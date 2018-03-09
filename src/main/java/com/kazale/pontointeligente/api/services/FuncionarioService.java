package com.kazale.pontointeligente.api.services;

import java.util.Optional;

import com.kazale.pontointeligente.api.entities.Funcionario;

public interface FuncionarioService {
	
	/**
	 * Persiste um funcionário na base de dados.
	 * 
	 * @param funcionario
	 * @return
	 */
	Funcionario persistir(Funcionario funcionario);
	
	/**
	 *  Busca e retorna un funcionario dado um CPF.
	 *  
	 * @param cpf
	 * @return
	 */
	Optional<Funcionario> buscarPorCpf(String cpf);
	
	/**
	 * Busca e retorna un funcionario dado um email
	 * 
	 * @param email
	 * @return
	 */
	Optional<Funcionario> buscarPorEmail(String email);
	
	/**
	 * Busca e retorna un funcionario dado um id
	 * 
	 * @param email
	 * @return
	 */
	Optional<Funcionario> buscarPorId(Long id);

}
