package br.com.rodolfo.pontointeligente.api.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.com.rodolfo.pontointeligente.api.entities.Lancamento;

/**
 * LancamentoService
 */
public interface LancamentoService {

    /**
     * Retorna uma lista paginada de lançamentos de um determinado funcionário
     * 
     * @param funcionarioId
     * @param pageRequest
     * @return Page<Lancamento>
     */
    Page<Lancamento> buscarFuncionarioPorId(Long funcionarioId, PageRequest pageRequest);

    /**
     * Retorna um lançamento por id
     * 
     * @param id
     * @return Optional<Lancamento>
     */
    Optional<Lancamento> buscarPorId(Long id);
    
    /**
     * Persistir um lançamento na base de dados
     * 
     * @param lancamento
     * @return Lancamento
     */
    Lancamento persistir(Lancamento lancamento);

    /**
     * Remove um lançamento na base de dados
     * 
     * @param id
     */
    void remover(Long id);


}