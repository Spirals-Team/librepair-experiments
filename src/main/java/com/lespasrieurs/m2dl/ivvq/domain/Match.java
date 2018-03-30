package com.lespasrieurs.m2dl.ivvq.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;

/**
 * Created by Marti_000 on 30/03/2018.
 */
@Entity
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_match;

    @Min(0)
    private int but_domicile;

    @Min(0)
    private int but_exterieur;

    private Equipe equipeDomicile;
    private Equipe equipeExterieur;

    public Match() {}

    public Match(Equipe equipeDomicile, Equipe equipeExterieur) {
        this.equipeDomicile = equipeDomicile;
        this.equipeExterieur = equipeExterieur;
        this.but_domicile = 0;
        this.but_exterieur = 0;
    }

    public Equipe getEquipeDomicile() {
        return equipeDomicile;
    }

    public Equipe getEquipeExterieur() {
        return equipeExterieur;
    }

    public int getBut_domicile() {
        return but_domicile;
    }

    public int getBut_exterieur() {
        return but_exterieur;
    }

    public Long getId_match() {
        return id_match;
    }

    public void setBut_domicile(int but_domicile) {
        this.but_domicile = but_domicile;
    }

    public void setBut_exterieur(int but_exterieur) {
        this.but_exterieur = but_exterieur;
    }

    public void setEquipeDomicile(Equipe equipeDomicile) {
        this.equipeDomicile = equipeDomicile;
    }

    public void setEquipeExterieur(Equipe equipeExterieur) {
        this.equipeExterieur = equipeExterieur;
    }

}
