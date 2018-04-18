package br.com.una.projeto.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Builder
public class Carrinho {

	@Id
	@GeneratedValue
	private Integer id;

	@JoinColumn(name = "idItem", referencedColumnName = "id")
	@ManyToOne
	@NotNull
	Item item;

	@NotNull
	Float precoTotal;

}
