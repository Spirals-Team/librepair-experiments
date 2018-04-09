package br.com.una.projeto.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class Bairro {

    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank
    @NotNull
    private String nome;

    @NotNull
    @NotBlank
    @OneToOne
    @JoinColumn(name = "idCidade", referencedColumnName = "id")
    private Cidade cidade;
}
