package com.concrete.ecommerce.api.services;

import java.util.List;

import com.concrete.ecommerce.api.entities.UsuarioPedidoProdutos;

public interface UsuarioPedidoProdutosService {

	UsuarioPedidoProdutos persistir(UsuarioPedidoProdutos usuarioPedidoProdutos);

	List<UsuarioPedidoProdutos> buscarPorUsuarioPedidoId(Long usuarioId, Long pedidoId);
}
