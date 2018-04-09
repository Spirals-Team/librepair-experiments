package br.com.una.projeto.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class Endereco {

    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank
    @NotNull
    @ManyToOne
    @JoinColumn(name = "idCliente", referencedColumnName = "id")
    private Cliente cliente;

    @NotBlank
    @NotNull
    private String logradouro;

    @NotBlank
    @NotNull
    private String cep;

    @NotBlank
    @NotNull
    @ManyToOne
    @JoinColumn(name = "idEstado", referencedColumnName = "id")
    private Estado estado;

    @NotBlank
    @NotNull
    @ManyToOne
    @JoinColumn(name = "idCidade", referencedColumnName = "id")
    private Cidade cidade;

    @NotBlank
    @NotNull
    @ManyToOne
    @JoinColumn(name = "idBairro", referencedColumnName = "id")
    private Bairro bairro;

    @NotBlank
    @NotNull
    private int numero;

    @NotBlank
    @NotNull
    private String complemento;

}
