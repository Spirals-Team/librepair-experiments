package br.com.una.projeto.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Pedido {

	@Id
	@GeneratedValue
	private Integer id;

	@JoinColumn(name = "idCliente", referencedColumnName = "id")
	@OneToOne
	Cliente cliente;

	@JoinColumn(name = "idEndereco", referencedColumnName = "id")
	@OneToOne
	Endereco endereco;

	@JoinColumn(name = "idItem", referencedColumnName = "id")
	@ManyToOne
	Item item;

}