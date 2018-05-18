package com.lespasrieurs.m2dl.ivvq.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Marti_000 on 30/03/2018.
 */
@Entity
public class Groupe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_groupe;

    @NotNull
    @NotEmpty
    String nom;

    @NotNull
    @Min(2)
    private int nb_places;

    @OneToMany(mappedBy = "groupe", cascade=CascadeType.REMOVE)
    private Collection<Pronostic> pronostics = new ArrayList<>();

    @OneToMany(mappedBy = "groupe", cascade=CascadeType.REMOVE)
    private Collection<Score> scores = new ArrayList<>();



    public Groupe(String nom){
        this.nom = nom;
        this.nb_places = 10;
    }

    public Groupe(String nom, int nb_places){
        this.nom = nom;
        this.nb_places = nb_places;
    }

    /*public Long getId_groupe() {
        return id_groupe;
    }

    public void setId_groupe(Long id_groupe) {
        this.id_groupe = id_groupe;
    }*/

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNb_places() {
        return nb_places;
    }

    public void setNb_places(int nb_places) {
        this.nb_places = nb_places;
    }

    public Collection<Pronostic> getPronostics() {
        return pronostics;
    }

    public void setPronostics(Collection<Pronostic> pronostics) {
        this.pronostics = pronostics;
    }

    public Collection<Score> getScores() {
        return scores;
    }

    public void setScores(Collection<Score> scores) {
        this.scores = scores;
    }
}
