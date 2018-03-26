package com.concrete.ecommerce.api.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.concrete.ecommerce.api.entities.Produto;

public interface ProdutoService {

	/**
	 * Retorna uma lista paginada de produtos.
	 * 
	 * @param pageRequest
	 * @return Page<Produto>
	 */
	Page<Produto> buscarTodos(PageRequest pageRequest);
	
	/**
	 * Retorna um produto por ID.
	 * 
	 * @param id
	 * @return Optional<Produto>
	 */
	Optional<Produto> buscarPorId(Long id);
	
	/**
	 * Persiste um produto na base de dados.
	 * 
	 * @param produto
	 * @return Produto
	 */
	Produto persistir(Produto produto);
	
	/**
	 * Remove um produto da base de dados.
	 * 
	 * @param id
	 */
	void remover(Long id);
	
}
