package com.lespasrieurs.m2dl.ivvq.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by MONTASSER on 30/03/2018.
 */

@Entity
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_score;

    @NotNull
    private int points;

    @NotNull
    @ManyToOne (fetch= FetchType.LAZY)
    @JsonIgnore
    private Parieur parieur;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "id_groupe")
    private Groupe groupe;

    public Score() {
    }

    public Score(int points) {
        this.points = points;
    }

    public Long getId_score() {
        return id_score;
    }

    public void setId_score(Long id_score) {
        this.id_score = id_score;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Parieur getParieur() {
        return parieur;
    }

    public void setParieur(Parieur parieur) {
        this.parieur = parieur;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }
}
