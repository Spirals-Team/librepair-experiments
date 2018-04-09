package br.com.una.projeto.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
public class Estoque {

	@Id
	@GeneratedValue
	private Integer id;

	@JoinColumn(name = "idProduto", referencedColumnName = "id")
	@OneToOne
	Produto produto;

	private Integer quantidade;

}