//package com.dutra.api.model;
//
//import com.dutra.api.enums.PerfilEnum;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//import org.hibernate.validator.constraints.NotEmpty;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import java.io.Serializable;
//import java.math.BigDecimal;
//import java.util.Date;
//import java.util.List;
//
//@Getter
//@Setter
//@ToString
//@Entity
//@Table(name = "funcionario")
//public class Funcionario implements Serializable{
//
//    private static final long serialVersionUID = 905550469607856579L;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @NotEmpty
//    private String nome;
//
//    @NotEmpty
//    private String email;
//
//    @NotEmpty
//    private String senha;
//
//    @NotEmpty
//    private String cpf;
//
//    @Column(name = "valor_hora")
//    private BigDecimal valorHora;
//
//    @Column(name = "qtd_horas_trabalho_dia")
//    private Float qtdHorasTrabalhoDia;
//
//    @Column(name = "qtd_horas_almoco")
//    private Float qtdHorasAlmoco;
//
//    @NotNull
//    @Enumerated(value = EnumType.STRING)
//    private PerfilEnum perfil;
//
//    @NotNull
//    @Column(name = "data_criacao")
//    private Date dataCriacao;
//
//    @NotNull
//    @Column(name = "data_atualizacao")
//    private Date dataAtualizacao;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    private Empresa empresa;
//
//    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL)
//    private List<Lancamento> lancamentos;
//
//    public Funcionario(){}
//
//    @PrePersist
//    public void prePersist() {
//        final Date atual = new Date();
//        this.dataCriacao = atual;
//        this.dataAtualizacao = atual;
//    }
//
//    @PreUpdate
//    public void preUpdate() {
//        this.dataAtualizacao = new Date();
//    }
//}
