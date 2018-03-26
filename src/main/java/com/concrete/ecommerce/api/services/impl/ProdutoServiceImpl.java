package com.concrete.ecommerce.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.concrete.ecommerce.api.entities.Produto;
import com.concrete.ecommerce.api.repositories.ProdutoRepository;
import com.concrete.ecommerce.api.services.ProdutoService;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	private static final Logger log = LoggerFactory.getLogger(ProdutoServiceImpl.class);

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Cacheable("produtoPorId")
	public Optional<Produto> buscarPorId(Long id) {
		log.info("Buscando um produto pelo ID {}", id);
		return Optional.ofNullable(this.produtoRepository.findOne(id));
	}
	
	@CachePut("produtoPorId")
	public Produto persistir(Produto produto) {
		log.info("Persistindo o produto: {}", produto);
		return this.produtoRepository.save(produto);
	}
	
	public void remover(Long id) {
		log.info("Removendo o produto ID {}", id);
		this.produtoRepository.delete(id);
	}

	@Override
	public Page<Produto> buscarTodos(PageRequest pageRequest) {
		log.info("Buscando todos os produtos");
		return this.produtoRepository.findAll(pageRequest);
	}


}
