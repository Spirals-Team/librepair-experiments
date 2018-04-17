package com.ipiecoles.java.java340.model.builder;

import com.ipiecoles.java.java340.exception.EmployeException;
import com.ipiecoles.java.java340.model.Entreprise;
import com.ipiecoles.java.java340.model.Manager;
import com.ipiecoles.java.java340.model.Technicien;
import org.joda.time.LocalDate;

import java.util.HashSet;
import java.util.Set;

public final class ManagerBuilder {
    private Long id;
    private String nom;
    private String prenom;
    private String matricule;
    private Set<Technicien> equipe = new HashSet<>();
    private LocalDate dateEmbauche;
    private Double salaire = Entreprise.SALAIRE_BASE;

    private ManagerBuilder() {
    }

    public static ManagerBuilder aManager() {
        return new ManagerBuilder();
    }

    public ManagerBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public ManagerBuilder withNom(String nom) {
        this.nom = nom;
        return this;
    }

    public ManagerBuilder withPrenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public ManagerBuilder withMatricule(String matricule) {
        this.matricule = matricule;
        return this;
    }

    public ManagerBuilder withEquipe(Set<Technicien> equipe) {
        this.equipe = equipe;
        return this;
    }

    public ManagerBuilder withDateEmbauche(LocalDate dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
        return this;
    }

    public ManagerBuilder withSalaire(Double salaire) {
        this.salaire = salaire;
        return this;
    }

    public Manager build() throws EmployeException {
        Manager manager = new Manager();
        manager.setId(id);
        manager.setNom(nom);
        manager.setPrenom(prenom);
        manager.setMatricule(matricule);
        manager.setEquipe(equipe);
        manager.setDateEmbauche(dateEmbauche);
        manager.setSalaire(salaire);
        return manager;
    }
}