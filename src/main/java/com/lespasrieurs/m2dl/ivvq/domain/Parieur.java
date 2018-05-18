package com.lespasrieurs.m2dl.ivvq.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by MONTASSER on 30/03/2018.
 */

@Entity
public class Parieur {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_parieur;

    @NotNull
    @NotEmpty
    private String pseudo;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @NotEmpty
    private String motDePasse;

    @OneToMany(mappedBy = "parieur")
    private Collection<Score> scores = new ArrayList<>();

    public Parieur() {
    }

    public Parieur(String pseudo, String email, String motDePasse) {
        this.pseudo = pseudo;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    public Long getId_parieur() {
        return id_parieur;
    }

    public void setId_parieur(Long id_parieur) {
        this.id_parieur = id_parieur;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public Collection<Score> getScores() {
        return scores;
    }

    public void setScores(Collection<Score> scores) {
        this.scores = scores;
    }
}
