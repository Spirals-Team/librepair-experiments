package br.com.una.projeto.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Builder
public class Endereco {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "idCliente", referencedColumnName = "id")
    private Cliente cliente;

    @NotNull
    @JoinColumn(name = "cepRua", referencedColumnName = "cep")
    @ManyToOne
    private Rua rua;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "idBairro", referencedColumnName = "id")
    private Bairro bairro;

    @NotNull
    private int numero;

    private String complemento;
}
