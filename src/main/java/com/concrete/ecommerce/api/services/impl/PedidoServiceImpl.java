package com.concrete.ecommerce.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.concrete.ecommerce.api.dtos.PedidoDto;
import com.concrete.ecommerce.api.entities.Pedido;
import com.concrete.ecommerce.api.entities.Produto;
import com.concrete.ecommerce.api.entities.UsuarioPedidoProdutos;
import com.concrete.ecommerce.api.repositories.PedidoRepository;
import com.concrete.ecommerce.api.services.PedidoService;
import com.concrete.ecommerce.api.services.UsuarioPedidoProdutosService;

@Service
public class PedidoServiceImpl implements PedidoService {

	private static final Logger log = LoggerFactory.getLogger(PedidoServiceImpl.class);

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private UsuarioPedidoProdutosService usuarioPedidoProdutosService;

	public Page<Pedido> buscarPorUsuarioId(Long usuarioId, PageRequest pageRequest) {
		log.info("Buscando pedidos para o usuário ID {}", usuarioId);
		return this.pedidoRepository.findByUsuarioId(usuarioId, pageRequest);
	}
	
	@Cacheable("pedidoPorId")
	public Optional<Pedido> buscarPorId(Long id) {
		log.info("Buscando um pedido pelo ID {}", id);
		return Optional.ofNullable(this.pedidoRepository.findOne(id));
	}
	
	@CachePut("pedidoPorId")
	public Pedido persistir(PedidoDto pedidoDto, Pedido pedido) {
		log.info("Persistindo o pedido: {}", pedido);
		Pedido pedidoBD = this.pedidoRepository.save(pedido);
		if(pedido != null) {
			this.insereTabelaRelacionamento(pedidoDto.getProdutos(), pedido);
		}
		return pedidoBD;
	}
	
	public void remover(Long id) {
		log.info("Removendo o pedido ID {}", id);
		this.pedidoRepository.delete(id);
	}
	
	private void insereTabelaRelacionamento(List<Produto> produtos, Pedido pedido) {
		UsuarioPedidoProdutos usuarioPedidoProdutos = null;
		if(produtos != null && !produtos.isEmpty()) {
			for (Produto produto : produtos) {
				usuarioPedidoProdutos = new UsuarioPedidoProdutos();
				usuarioPedidoProdutos.setUsuarioId(pedido.getUsuario().getId());
				usuarioPedidoProdutos.setPedidoId(pedido.getId());
				usuarioPedidoProdutos.setProdutoId(produto.getId());
				usuarioPedidoProdutosService.persistir(usuarioPedidoProdutos);
			}
		}
	}

}
