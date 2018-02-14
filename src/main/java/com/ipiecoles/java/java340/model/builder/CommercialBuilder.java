package com.ipiecoles.java.java340.model.builder;

import com.ipiecoles.java.java340.exception.EmployeException;
import com.ipiecoles.java.java340.model.Commercial;
import com.ipiecoles.java.java340.model.Entreprise;
import org.joda.time.LocalDate;

public final class CommercialBuilder {
    private Double caAnnuel = 0d;
    private Integer performance;
    private Long id;
    private String nom;
    private String prenom;
    private String matricule;
    private LocalDate dateEmbauche;
    private Double salaire = Entreprise.SALAIRE_BASE;

    private CommercialBuilder() {
    }

    public static CommercialBuilder aCommercial() {
        return new CommercialBuilder();
    }

    public CommercialBuilder withCaAnnuel(Double caAnnuel) {
        this.caAnnuel = caAnnuel;
        return this;
    }

    public CommercialBuilder withPerformance(Integer performance) {
        this.performance = performance;
        return this;
    }

    public CommercialBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public CommercialBuilder withNom(String nom) {
        this.nom = nom;
        return this;
    }

    public CommercialBuilder withPrenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public CommercialBuilder withMatricule(String matricule) {
        this.matricule = matricule;
        return this;
    }

    public CommercialBuilder withDateEmbauche(LocalDate dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
        return this;
    }

    public CommercialBuilder withSalaire(Double salaire) {
        this.salaire = salaire;
        return this;
    }

    public Commercial build() throws EmployeException{
        Commercial commercial = new Commercial();
        commercial.setCaAnnuel(caAnnuel);
        commercial.setPerformance(performance);
        commercial.setId(id);
        commercial.setNom(nom);
        commercial.setPrenom(prenom);
        commercial.setMatricule(matricule);
        commercial.setDateEmbauche(dateEmbauche);
        commercial.setSalaire(salaire);
        return commercial;
    }
}
