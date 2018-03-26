package com.concrete.ecommerce.api.controllers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
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

import com.concrete.ecommerce.api.dtos.PedidoDto;
import com.concrete.ecommerce.api.entities.Pedido;
import com.concrete.ecommerce.api.entities.Produto;
import com.concrete.ecommerce.api.entities.Usuario;
import com.concrete.ecommerce.api.entities.UsuarioPedidoProdutos;
import com.concrete.ecommerce.api.response.Response;
import com.concrete.ecommerce.api.services.PedidoService;
import com.concrete.ecommerce.api.services.ProdutoService;
import com.concrete.ecommerce.api.services.UsuarioPedidoProdutosService;
import com.concrete.ecommerce.api.services.UsuarioService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

	private static final Logger log = LoggerFactory.getLogger(PedidoController.class);

	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private UsuarioPedidoProdutosService usuarioPedidoProdutosService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private UsuarioService usuarioService;

	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;

	public PedidoController() {
	}

	/**
	 * Retorna a listagem de pedidos de um usuário.
	 * 
	 * @param usuarioId
	 * @return ResponseEntity<Response<PedidoDto>>
	 */
	@GetMapping(value = "/usuario/{usuarioId}")
	public ResponseEntity<Response<Page<PedidoDto>>> listarPorUsuarioId(@PathVariable("usuarioId") Long usuarioId,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir) {
		log.info("Buscando produtos por ID do usuário: {}, página: {}", usuarioId, pag);
		Response<Page<PedidoDto>> response = new Response<Page<PedidoDto>>();

		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<Pedido> pedidos = this.pedidoService.buscarPorUsuarioId(usuarioId, pageRequest);
		Page<PedidoDto> pedidosDto = pedidos.map(pedido -> this.converterPedidoDto(pedido));
		
		response.setData(pedidosDto);
		return ResponseEntity.ok(response);
	}

	/**
	 * Retorna um pedido por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<PedidoDto>>
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<PedidoDto>> listarPorId(@PathVariable("id") Long id) {
		log.info("Buscando pedido por ID: {}", id);
		Response<PedidoDto> response = new Response<PedidoDto>();
		Optional<Pedido> pedido = this.pedidoService.buscarPorId(id);

		if (!pedido.isPresent()) {
			log.info("Pedido não encontrado para o ID: {}", id);
			response.getErrors().add("Pedido não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.converterPedidoDto(pedido.get()));
		return ResponseEntity.ok(response);
	}

	/**
	 * Adiciona um novo pedido.
	 * 
	 * @param pedido
	 * @param result
	 * @return ResponseEntity<Response<PedidoDto>>
	 * @throws ParseException
	 */
	@PostMapping
	public ResponseEntity<Response<PedidoDto>> adicionar(@Valid @RequestBody PedidoDto pedidoDto, BindingResult result)
			throws ParseException {
		log.info("Adicionando pedido: {}", pedidoDto.toString());
		Response<PedidoDto> response = new Response<PedidoDto>();
		validarUsuario(pedidoDto, result);
		Pedido pedido = this.converterDtoParaPedido(pedidoDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando pedido: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		pedido = this.pedidoService.persistir(pedidoDto, pedido);
		response.setData(this.converterPedidoDto(pedido));
		return ResponseEntity.ok(response);
	}

	/**
	 * Atualiza os dados de um lançamento.
	 * 
	 * @param id
	 * @param pedidoDto
	 * @return ResponseEntity<Response<Pedido>>
	 * @throws ParseException
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<PedidoDto>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody PedidoDto pedidoDto, BindingResult result) throws ParseException {
		log.info("Atualizando lançamento: {}", pedidoDto.toString());
		Response<PedidoDto> response = new Response<PedidoDto>();
		validarUsuario(pedidoDto, result);
		pedidoDto.setId(Optional.of(id));
		Pedido pedido = this.converterDtoParaPedido(pedidoDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando pedido: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		pedido = this.pedidoService.persistir(pedidoDto, pedido);
		
		response.setData(this.converterPedidoDto(pedido));
		return ResponseEntity.ok(response);
	}


	/**
	 * Remove um pedido por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<Pedido>> TODO remover essa lógica daqui
	 *         passar para um component
	 */
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Removendo pedido: {}", id);
		Response<String> response = new Response<String>();
		Optional<Pedido> pedido = this.pedidoService.buscarPorId(id);

		if (!pedido.isPresent()) {
			log.info("Erro ao remover devido ao pedido ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover pedido. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.pedidoService.remover(id);
		return ResponseEntity.ok(new Response<String>());
	}

	/**
	 * Valida um usuário, verificando se ele é existente e válido no sistema.
	 * 
	 * @param pedidoDto
	 * @param result
	 */
	private void validarUsuario(PedidoDto pedidoDto, BindingResult result) {
		if (pedidoDto.getUsuarioId() == null) {
			result.addError(new ObjectError("usuario", "Usuário não informado."));
			return;
		}

		log.info("Validando usuário id {}: ", pedidoDto.getUsuarioId());
		Optional<Usuario> usuario = this.usuarioService.buscarPorId(pedidoDto.getUsuarioId());
		if (!usuario.isPresent()) {
			result.addError(new ObjectError("usuario", "Usuário não encontrado. ID inexistente."));
		}
	}

	/**
	 * Converte uma entidade pedido para seu respectivo DTO.
	 * 
	 * @param pedido
	 * @return PedidoDto TODO remover essa lógica daqui passar para um component
	 */
	private PedidoDto converterPedidoDto(Pedido pedido) {
		PedidoDto pedidoDto = new PedidoDto();
		pedidoDto.setId(Optional.of(pedido.getId()));
		pedidoDto.setDescricao(pedido.getDescricao());
		pedidoDto.setEnderecoEntrega(pedido.getEnderecoEntrega());
		pedidoDto.setUsuarioId(pedido.getUsuario().getId());
		List<UsuarioPedidoProdutos> listaIdProdutos = usuarioPedidoProdutosService.buscarPorUsuarioPedidoId(pedido.getUsuario().getId(), pedido.getId());
		List<Produto> produtos = new ArrayList<>();
		for (UsuarioPedidoProdutos usuarioPedidoProdutos : listaIdProdutos) {
			Optional<Produto> produto = produtoService.buscarPorId(usuarioPedidoProdutos.getProdutoId());
			if(produto.isPresent()) {
				produtos.add(produto.get());
			}
		}
		pedidoDto.setProdutos(produtos);
		
		return pedidoDto;
	}

	/**
	 * Converte um PedidoDto para uma entidade Pedido.
	 * 
	 * @param pedidoDto
	 * @param result
	 * @return Pedido
	 * @throws ParseException
	 *             TODO remover essa lógica daqui passar para um component
	 */
	private Pedido converterDtoParaPedido(PedidoDto pedidoDto, BindingResult result) throws ParseException {
		Pedido pedido = new Pedido();

		if (pedidoDto.getId().isPresent()) {
			Optional<Pedido> ped = this.pedidoService.buscarPorId(pedidoDto.getId().get());
			if (ped.isPresent()) {
				pedido = ped.get();
			} else {
				result.addError(new ObjectError("pedido", "Pedido não encontrado."));
			}
		} else {
			pedido.setUsuario(new Usuario());
			pedido.getUsuario().setId(pedidoDto.getUsuarioId());
		}
		
		if(pedidoDto.getProdutos() == null || pedidoDto.getProdutos().isEmpty()) {
			result.addError(new ObjectError("pedido", "Lista de Produtos não pode estar vazia."));
		}

		pedido.setDescricao(pedidoDto.getDescricao());
		pedido.setEnderecoEntrega(pedidoDto.getEnderecoEntrega());
		
		return pedido;
	}

}
