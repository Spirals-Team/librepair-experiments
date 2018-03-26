package com.concrete.ecommerce.api.dtos;

import java.util.Optional;

public class ProdutoDto {

	private Optional<Long> id = Optional.empty();
	private String descricao;
	private double valor;
	private Long pedidoId;

	public ProdutoDto() {
	}

	public Optional<Long> getId() {
		return id;
	}

	public void setId(Optional<Long> id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}

	@Override
	public String toString() {
		return "ProdutoDto [id=" + id + ", descricao=" + descricao + ", valor=" + valor + ", pedidoId=" + pedidoId
				+ "]";
	}

}
