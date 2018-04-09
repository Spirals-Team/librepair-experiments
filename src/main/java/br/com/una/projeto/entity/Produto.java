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
public class Produto {

    @Id
    @GeneratedValue
    Integer id;

    @NotNull
    String nome;

    @NotNull
    Double precoVenda;

    @NotNull
    Double precoCusto;

    @NotNull
    Double descontoMax;

    @NotNull
    @OneToOne
    @JoinColumn(name = "idMarca", referencedColumnName = "id")
    Marca marca;

    @NotNull
    String descricao;

    @NotNull
    String unidadeMedida;

    String url;

    @NotNull
    @OneToOne
    @JoinColumn(name = "idCategoria", referencedColumnName = "id")
    Categoria categoria;
}
