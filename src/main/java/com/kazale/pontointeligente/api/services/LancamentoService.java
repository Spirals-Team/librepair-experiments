package com.kazale.pontointeligente.api.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.kazale.pontointeligente.api.entities.Lancamento;

public interface LancamentoService {
	
	/**
	 * Buscar funcionario paginado por id 
	 * @param funcionarioId
	 * @param pageRequest
	 * @return
	 */
	Page<Lancamento> buscarPorFuncionarioId(Long funcionarioId, PageRequest pageRequest);
	
	/**
	 * Busca um lancamento pelo Id
	 * @param id
	 * @return
	 */
	Optional<Lancamento> buscarPorId(Long id);
	
	/**
	 * Persiste um novo lancamento
	 * @param lancamento
	 * @return
	 */
	Lancamento persistir(Lancamento lancamento);
	
	/**
	 * Remove pelo id
	 * @param id
	 */
	void remover(Long id);

}
