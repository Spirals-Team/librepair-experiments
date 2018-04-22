package br.com.jordilucas.pontointeligente.api.services;

import java.util.Optional;

import br.com.jordilucas.pontointeligente.api.entities.Funcionario;

public interface FuncionarioService {
	/**
	 * Persiste um funcionario na base de dados
	 * 
	 * @param funcionario
	 * @return funcionario
	 * 
	 * **/
	Funcionario persistir(Funcionario funcionario);
	
	
	/**
	 * Busca e retorna um funcionario por cpf
	 * 
	 * @param cpf
	 * @return <Funcionario>
	 * 
	 * **/
	Optional<Funcionario> buscaPorCpf(String cpf);
	
	/**
	 * Busca e retorna um funcionario por email
	 * 
	 * @param email
	 * @return <Funcionario>
	 * 
	 * **/
	Optional<Funcionario> buscaPorEmail(String email);
	
	/**
	 * Busca e retorna um funcionario por id
	 * 
	 * @param id
	 * @return <Funcionario>
	 * 
	 * **/
	Optional<Funcionario> buscaPorId(Long id);
	
	
}
