package com.concrete.ecommerce.api.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usuario_pedido_produtos")
public class UsuarioPedidoProdutos implements Serializable {

	private static final long serialVersionUID = -4351368096042293052L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name = "usuario_id", nullable = false)
	private Long usuarioId;

	@Column(name = "pedido_id", nullable = false)
	private Long pedidoId;

	@Column(name = "produto_id", nullable = false)
	private Long produtoId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}

	public Long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}

	@Override
	public String toString() {
		return "UsuarioPedidoProdutos [id=" + id + ", usuarioId=" + usuarioId + ", pedidoId=" + pedidoId
				+ ", produtoId=" + produtoId + "]";
	}

}
