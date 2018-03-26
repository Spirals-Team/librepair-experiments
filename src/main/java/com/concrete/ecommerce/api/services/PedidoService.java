package com.concrete.ecommerce.api.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.concrete.ecommerce.api.dtos.PedidoDto;
import com.concrete.ecommerce.api.entities.Pedido;

public interface PedidoService {

	/**
	 * Retorna uma lista paginada de pedidos de um determinado usuário.
	 * 
	 * @param usuarioId
	 * @param pageRequest
	 * @return Page<Pedido>
	 */
	Page<Pedido> buscarPorUsuarioId(Long usuarioId, PageRequest pageRequest);
	
	/**
	 * Retorna um pedido por ID.
	 * 
	 * @param id
	 * @return Optional<Pedido>
	 */
	Optional<Pedido> buscarPorId(Long id);
	
	/**
	 * Persiste um pedido na base de dados.
	 * 
	 * @param pedido
	 * @return Pedido
	 */
	Pedido persistir(PedidoDto pedidoDto, Pedido pedido);
	
	/**
	 * Remove um pedido da base de dados.
	 * 
	 * @param id
	 */
	void remover(Long id);
	
}
