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
public class Cidade {

    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank
    @NotNull
    private String nome;

    @NotNull
    @JoinColumn(name = "idEstado", referencedColumnName = "id")
    @ManyToOne
    private Estado estado;
}
