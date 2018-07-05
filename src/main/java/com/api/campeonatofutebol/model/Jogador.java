package com.api.campeonatofutebol.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;


import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity (name = "jogador")
public class Jogador implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Size (min = 2, max = 45)
    private String nome;

    @NotNull
    @JsonIgnore
    @JsonFormat (pattern = "dd/MM/yyyy")
    @Column (name = "data_nascimento")
    private LocalDate dataDeNascimento;

    @NotNull
    @Size (min = 1, max = 2)
    private String genero;

    @DecimalMin( value = "1.55")
    private double altura;

    @Column (name = "numero_camisa")
    private int numeroCamisa;

    private String posicao;

    @NotNull
    private boolean ativo;

    @ManyToOne
    @JsonBackReference
    @JoinColumn (name = "id_time")
    private Time timeQueJoga;

    @Transient
    @OneToOne
    @JsonIgnore
    private Time timeQueEhCapitao;

    public Jogador() {
    }

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

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public int getNumeroCamisa() {
        return numeroCamisa;
    }

    public void setNumeroCamisa(int numeroCamisa) {
        this.numeroCamisa = numeroCamisa;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Time getTimeQueJoga() {
        return timeQueJoga;
    }

    public void setTimeQueJoga(Time timeQueJoga) {
        this.timeQueJoga = timeQueJoga;
    }

    public Time getTimeQueEhCapitao() {
        return timeQueEhCapitao;
    }

    public void setTimeQueEhCapitao(Time timeQueEhCapitao) {
        this.timeQueEhCapitao = timeQueEhCapitao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jogador jogador = (Jogador) o;
        return Objects.equals(id, jogador.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

 }
