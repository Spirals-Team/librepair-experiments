package br.com.una.projeto.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rua {

    @Id
    @Column(nullable = false, unique = true)
    String cep;

    @NotNull
    String logradouro;
}
