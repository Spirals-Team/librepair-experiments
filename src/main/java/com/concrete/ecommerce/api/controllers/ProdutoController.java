package com.concrete.ecommerce.api.controllers;

import java.text.ParseException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.concrete.ecommerce.api.dtos.ProdutoDto;
import com.concrete.ecommerce.api.entities.Produto;
import com.concrete.ecommerce.api.response.Response;
import com.concrete.ecommerce.api.services.ProdutoService;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
	private static final Logger log = LoggerFactory.getLogger(PedidoController.class);

	@Autowired
	private ProdutoService produtoService;

	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;

	public ProdutoController() {
	}


	/**
	 * Retorna a listagem de produtos .
	 * 
	 * @param usuarioId
	 * @return ResponseEntity<Response<ProdutoDto>>
	 */
	@GetMapping
	public ResponseEntity<Response<Page<ProdutoDto>>> listarProdutos(
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir) {
		log.info("Buscando produtos, página: {}", pag);
		Response<Page<ProdutoDto>> response = new Response<Page<ProdutoDto>>();

		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<Produto> produtos = this.produtoService.buscarTodos(pageRequest);
		Page<ProdutoDto> produtosDto = produtos.map(produto -> this.converterProdutoDto(produto));

		response.setData(produtosDto);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retorna um produto por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<ProdutoDto>>
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<ProdutoDto>> listarPorId(@PathVariable("id") Long id) {
		log.info("Buscando lançamento por ID: {}", id);
		Response<ProdutoDto> response = new Response<ProdutoDto>();
		Optional<Produto> produto = this.produtoService.buscarPorId(id);

		if (!produto.isPresent()) {
			log.info("Produto não encontrado para o ID: {}", id);
			response.getErrors().add("Produto não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.converterProdutoDto(produto.get()));
		return ResponseEntity.ok(response);
	}

	/**
	 * Adiciona um novo produto.
	 * 
	 * @param produto
	 * @param result
	 * @return ResponseEntity<Response<ProdutoDto>>
	 * @throws ParseException
	 */
	@PostMapping
	public ResponseEntity<Response<ProdutoDto>> adicionar(@Valid @RequestBody ProdutoDto produtoDto,
			BindingResult result) throws ParseException {
		log.info("Adicionando lançamento: {}", produtoDto.toString());
		Response<ProdutoDto> response = new Response<ProdutoDto>();
		Produto produto = this.converterDtoParaProduto(produtoDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando produto: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		produto = this.produtoService.persistir(produto);
		response.setData(this.converterProdutoDto(produto));
		return ResponseEntity.ok(response);
	}

	/**
	 * Atualiza os dados de um produto.
	 * 
	 * @param id
	 * @param produtoDto
	 * @return ResponseEntity<Response<Produto>>
	 * @throws ParseException
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<ProdutoDto>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody ProdutoDto produtoDto, BindingResult result) throws ParseException {
		log.info("Atualizando produto: {}", produtoDto.toString());
		Response<ProdutoDto> response = new Response<ProdutoDto>();
		produtoDto.setId(Optional.of(id));
		Produto produto = this.converterDtoParaProduto(produtoDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando produto: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		produto = this.produtoService.persistir(produto);
		response.setData(this.converterProdutoDto(produto));
		return ResponseEntity.ok(response);
	}

	/**
	 * Remove um produto por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<Produto>>
	 */
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Removendo produto: {}", id);
		Response<String> response = new Response<String>();
		Optional<Produto> produto = this.produtoService.buscarPorId(id);

		if (!produto.isPresent()) {
			log.info("Erro ao remover devido ao produto ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover produto. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.produtoService.remover(id);
		return ResponseEntity.ok(new Response<String>());
	}

	/**
	 * Converte uma entidade produto para seu respectivo DTO.
	 * 
	 * @param produto
	 * @return ProdutoDto
	 */
	private ProdutoDto converterProdutoDto(Produto produto) {
		ProdutoDto produtoDto = new ProdutoDto();
		produtoDto.setId(Optional.of(produto.getId()));
		produtoDto.setDescricao(produto.getDescricao());
		produtoDto.setValor(produto.getValor());

		return produtoDto;
	}

	/**
	 * Converte um ProdutoDto para uma entidade Produto.
	 * 
	 * @param produtoDto
	 * @param result
	 * @return Produto
	 * @throws ParseException
	 */
	private Produto converterDtoParaProduto(ProdutoDto produtoDto, BindingResult result) throws ParseException {
		Produto produto = new Produto();

		if (produtoDto.getId().isPresent()) {
			Optional<Produto> prod = this.produtoService.buscarPorId(produtoDto.getId().get());
			if (prod.isPresent()) {
				produto = prod.get();
			} else {
				result.addError(new ObjectError("produto", "Produto não encontrado."));
			}
		} 

		produto.setDescricao(produtoDto.getDescricao());
		produto.setValor(produtoDto.getValor());

		return produto;
	}
}
