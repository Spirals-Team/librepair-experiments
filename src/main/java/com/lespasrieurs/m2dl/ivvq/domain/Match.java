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
    private int butsDomicile;

    @Min(0)
    private int butsExterieur;

    private Equipe equipeDomicile;
    private Equipe equipeExterieur;

    public Match() {}

    public Match(Equipe equipeDomicile, Equipe equipeExterieur) {
        this.equipeDomicile = equipeDomicile;
        this.equipeExterieur = equipeExterieur;
        this.butsDomicile = 0;
        this.butsExterieur = 0;
    }

    public Equipe getEquipeDomicile() {
        return equipeDomicile;
    }

    public Equipe getEquipeExterieur() {
        return equipeExterieur;
    }

    public int getButsDomicile() {
        return butsDomicile;
    }

    public int getButsExterieur() {
        return butsExterieur;
    }

    public Long getId_match() {
        return id_match;
    }

    public void setButsDomicile(int butsDomicile) {
        this.butsDomicile = butsDomicile;
    }

    public void setButsExterieur(int butsExterieur) {
        this.butsExterieur = butsExterieur;
    }

    public void setEquipeDomicile(Equipe equipeDomicile) {
        this.equipeDomicile = equipeDomicile;
    }

    public void setEquipeExterieur(Equipe equipeExterieur) {
        this.equipeExterieur = equipeExterieur;
    }

}
