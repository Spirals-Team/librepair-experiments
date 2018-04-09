package br.com.una.projeto.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
public class Carrinho {

	@Id
	@GeneratedValue
	private Integer id;

	@JoinColumn(name = "idProduto", referencedColumnName = "id")
	@ManyToOne
	Produto produto;

	@JoinColumn(name = "idItem", referencedColumnName = "id")
	@ManyToOne
	Item item;

	Float precoTotal;

}
