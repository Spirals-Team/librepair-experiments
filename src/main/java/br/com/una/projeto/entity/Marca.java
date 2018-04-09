package br.com.una.projeto.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Builder
public class Marca {

    @Id
    @GeneratedValue
    Integer id;

    @NotBlank
    @NotNull
    String descricao;
}
