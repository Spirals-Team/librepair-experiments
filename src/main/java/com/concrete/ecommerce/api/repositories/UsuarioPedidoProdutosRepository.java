package com.concrete.ecommerce.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.concrete.ecommerce.api.entities.UsuarioPedidoProdutos;

@Transactional(readOnly = true)
public interface UsuarioPedidoProdutosRepository extends JpaRepository<UsuarioPedidoProdutos, Long> {

	List<UsuarioPedidoProdutos> findByUsuarioIdAndPedidoId(Long usuarioId, Long pedidoId);
}
