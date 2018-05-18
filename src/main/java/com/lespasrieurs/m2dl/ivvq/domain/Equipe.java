package com.lespasrieurs.m2dl.ivvq.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Equipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_equipe;

    @NotNull
    @NotEmpty
    private String nom;

    @OneToMany(mappedBy = "equipeExterieur")
    private Collection<Match> matchListExterieur = new ArrayList<>();

    @OneToMany(mappedBy = "equipeDomicile")
    private Collection<Match> matchListDomicile = new ArrayList<>();

    //public Equipe() {}

    public Equipe(String nom) {
        this.nom = nom;
    }

    /*public Long getId_equipe() {
        return id_equipe;
    }*/

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    /*public void setId_equipe(Long id_equipe) {
        this.id_equipe = id_equipe;
    }*/

    public Collection<Match> getMatchListExterieur() {
        return matchListExterieur;
    }

    /*public void setMatchListExterieur(Collection<Match> matchListExterieur) {
        this.matchListExterieur = matchListExterieur;
    }*/

    public Collection<Match> getMatchListDomicile() {
        return matchListDomicile;
    }

    /*public void setMatchListDomicile(Collection<Match> matchListDomicile) {
        this.matchListDomicile = matchListDomicile;
    }*/

    /*@Override
    public boolean equals(Object obj) {

        boolean res = false;

        if(obj instanceof Equipe) {
            Equipe equipe = (Equipe) obj;

            if(this.getNom() != null) {
                if(this.getNom().equals(equipe.getNom())) {
                    res = true;
                }
            }


        }

        return res;
    }

    @Override
    public int hashCode() {
        return 7 * this.matchListExterieur.hashCode() + 11 * this.matchListDomicile.hashCode() +
                7 * this.nom.hashCode();
    }*/

    public void ajoutMatchDomicile(Match match) {
        this.matchListDomicile.add(match);
    }

    public void ajoutMatchExterieur(Match match) {
        this.matchListExterieur.add(match);
    }

}
