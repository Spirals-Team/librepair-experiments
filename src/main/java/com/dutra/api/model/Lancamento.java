//package com.dutra.api.model;
//
//import com.dutra.api.enums.TipoEnum;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import java.io.Serializable;
//import java.util.Date;
//
//@Getter
//@Setter
//@ToString
//@Entity
//@Table(name = "lancamento")
//public class Lancamento implements Serializable {
//    private static final long serialVersionUID = 2778770387843221739L;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @NotNull
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date data;
//
//    private String descricao;
//
//    private String localizacao;
//
//    @NotNull
//    @Column(name = "data_criacao")
//    private Date dataCriacao;
//
//    @NotNull
//    @Column(name = "data_atualizacao")
//    private Date dataAtualizacao;
//
//    @NotNull
//    @Enumerated(value = EnumType.STRING)
//    private TipoEnum tipo;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    private Funcionario funcionario;
//
//    public Lancamento() {}
//}
