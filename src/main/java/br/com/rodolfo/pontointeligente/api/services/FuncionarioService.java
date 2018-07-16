package br.com.rodolfo.pontointeligente.api.services;

import java.util.Optional;

import br.com.rodolfo.pontointeligente.api.entities.Funcionario;

/**
 * FuncionarioService
 */
public interface FuncionarioService {

    /**
     * Persistir um fucionario na base de dados.
     * 
     * @param funcionario
     * @return Funcionario
     */
    Funcionario persistir(Funcionario funcionario);

    /**
     * Busca e retorna um funcionario dado um cpf
     * 
     * @param cpf
     * @return Optional<Funcionario>
     */
    Optional<Funcionario> buscarPorCpf(String cpf);

    /**
     * Busca e retorna um funcionario dado um email
     * 
     * @param email
     * @return Optional<Funcionario>
     */
    Optional<Funcionario> buscarPorEmail(String email);


    /**
     * Busca e retorna um funcionario dado um id
     * 
     * @param id
     * @return Optional<Funcionario>
     */
    Optional<Funcionario> buscarPorId(Long id);
    
}