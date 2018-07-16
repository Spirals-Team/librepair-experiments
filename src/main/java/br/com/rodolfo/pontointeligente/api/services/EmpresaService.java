package br.com.rodolfo.pontointeligente.api.services;

import java.util.Optional;

import br.com.rodolfo.pontointeligente.api.entities.Empresa;

/**
 * EmpresaService
 */
public interface EmpresaService {

    /**
     * Retorna uma empresa dado um CNPJ
     * 
     * @param cnpj
     * @return Optional<Empresa>
     */
    Optional<Empresa> BuscarPorCnpj(String cnpj);


    /**
     * Cadastra uma mepresa n abase de dados
     * 
     * @param empresa
     * @return Empresa
     */
    Empresa persistir(Empresa empresa);
    
}