package com.lespasrieurs.m2dl.ivvq.domain;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by Marti_000 on 30/03/2018.
 */
@Entity
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_match;

    @Min(0)
    private int butDomicile;

    @Min(0)
    private int butExterieur;

    @NotNull
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "id_equipe_domicile")
    private Equipe equipeDomicile;

    @NotNull
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "id_equipe_exterieur")
    private Equipe equipeExterieur;

    public Match() {}

    public Match(Equipe equipeDomicile, Equipe equipeExterieur) {
        this.equipeDomicile = equipeDomicile;
        this.equipeExterieur = equipeExterieur;
        this.butDomicile = 0;
        this.butExterieur = 0;

        //on rajoute le match dans la liste des equipes
        if(this.equipeDomicile != null && this.equipeExterieur != null) {
            this.equipeDomicile.ajoutMatchDomicile(this);
            this.equipeExterieur.ajoutMatchExterieur(this);
        }
    }

    public Equipe getEquipeDomicile() {
        return equipeDomicile;
    }

    public Equipe getEquipeExterieur() {
        return equipeExterieur;
    }

    public int getButDomicile() {
        return butDomicile;
    }

    public int getButExterieur() {
        return butExterieur;
    }

    public void setButDomicile(int but_domicile) {
        this.butDomicile = but_domicile;
    }

    public void setButExterieur(int but_exterieur) {
        this.butExterieur = but_exterieur;
    }

    public void setEquipeDomicile(Equipe equipeDomicile) {

        if(equipeDomicile != null) {
            this.equipeDomicile = equipeDomicile;
        }

    }

    public void setEquipeExterieur(Equipe equipeExterieur) {
        if(equipeExterieur != null) {
            this.equipeExterieur = equipeExterieur;
        }

    }

    @Override
    public String toString() {
        return this.equipeDomicile.getNom() + " " + this.butDomicile
                + " - "
                + this.getButExterieur() + " " + this.equipeExterieur.getNom();
    }

    /*
    @Override
    public boolean equals(Object obj) {

        boolean res = false;

        if(obj instanceof Match) {
            Match match = (Match) obj;

            if(this.getId_match() == match.getId_match()) {
                res = true;
            }
        }

        return res;
    }

    @Override
    public int hashCode() {
        return 7 * this.equipeExterieur.hashCode() + 11 * this.equipeDomicile.hashCode() +
                7 * Long.toString(this.id_match).hashCode();
    }*/
}
