package com.lespasrieurs.m2dl.ivvq.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Equipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_equipe;

    @NotNull
    @NotEmpty
    private String nom;

    public Equipe() {}

    public Equipe(String nom) {
        this.nom = nom;
    }

    public Long getId_equipe() {
        return id_equipe;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
