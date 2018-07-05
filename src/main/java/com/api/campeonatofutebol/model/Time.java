package com.api.campeonatofutebol.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity (name = "time")
public class Time {

    @Id
    @GeneratedValue (strategy =GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Size(min = 4, max = 45)
    private String nome;

    @OneToMany (mappedBy = "timeQueJoga", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Jogador> jogadores = new ArrayList<>();

    private Boolean ativo;

    @OneToOne
    @JoinColumn (name = "id_capitao")
    private Jogador capitao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }

    public Jogador getCapitao() {
        return capitao;
    }

    public void setCapitao(Jogador capitao) {
        this.capitao = capitao;
    }
}
