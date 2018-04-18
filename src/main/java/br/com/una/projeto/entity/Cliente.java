package br.com.una.projeto.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Getter
@Setter
@Builder
public class Cliente {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String nome;

    private String sobrenome;

    @NotNull
    private String senha;

    @NotNull
    @Column(unique=true)
    private String cpf;

    @NotNull
    private String email;

    @NotNull
    private String telefone;

    @NotNull
    private String celular;

}
