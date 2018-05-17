package com.dutra.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@Table(name = "empresa")
public class Empresa implements Serializable {

    private static final long serialVersionUID = -5634837990072362754L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "razao_social")
    private String razaoSocial;

    @NotEmpty
    private String cnpj;

    @NotNull
    @Column(name = "data_criacao")
    private Date dataCriacao;

    @NotNull
    @Column(name = "data_atualizacao")
    private Date dataAtualizacao;

//    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
//    private List<Funcionario> funcionarios;

    public Empresa() {
    }

    @PrePersist
    public void prePersist() {
        final Date atual = new Date();
        this.dataCriacao = atual;
        this.dataAtualizacao = atual;
    }

    @PreUpdate
    private void preUpdate() {
        this.dataAtualizacao = new Date();
    }
}
