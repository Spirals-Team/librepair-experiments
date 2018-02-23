package br.com.jordilucas.pontointeligente.api.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.com.jordilucas.pontointeligente.api.entities.Lancamento;

public interface LancamentoService {
	
	/**
	 * Busca um funcionario por id
	 * 
	 * @param funcionarioId
	 * @param pageRequest
	 * @return Page<Lancamento>
	 * 
	 * **/
	Page<Lancamento> buscaPorFuncionarioId(Long funcionarioId, PageRequest pageRequest);
	
	/**
	 * Retorna um lancamento por id
	 * 
	 * @param id
	 * @return Optional<Lancamento>
	 * 
	 * **/
	Optional<Lancamento> buscaPorId(long id);
	
	/**
	 * Persiste um lancamento na base de dados
	 * 
	 * @param lancamento
	 * @return Lancamento
	 * 
	 * 
	 * **/
	Lancamento persistir(Lancamento lancamento);
	
	/**
	 * Remove um lancamento por id
	 * 
	 * @param id
	 *
	 * **/
	void remover(Long id); 
	
}
