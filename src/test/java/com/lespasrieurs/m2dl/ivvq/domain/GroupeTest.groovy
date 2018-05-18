package com.lespasrieurs.m2dl.ivvq.domain

import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.Validation
import javax.validation.ValidatorFactory
import javax.validation.Validator

/**
 * Created by Marti_000 on 06/04/2018.
 */
class GroupeTest extends Specification {

    Validator validator

    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Unroll
    def "test d'un nom de groupe non valide"(String nomGroupe) {

        given: "initialise un groupe avec un nom vide"
        Groupe grp = new Groupe(nomGroupe)

        expect: "le groupe est invalide"
        !validator.validate(grp).empty

        where:
        nomGroupe << [null, ""]
    }

    @Unroll
    def "test d'un nombre de place non valide"(int nb_place) {

        given: "initialise un groupe avec un nombre de place à -1,0 ou 1"
        Groupe grp = new Groupe("Les fans de l'Olympique de Marseille",nb_place)

        expect: "le groupe est invalide"
        !validator.validate(grp).empty

        where:
        nb_place << [-1, 0, 1]
    }

    def "test la validite d'un groupe avec un nombre de place par défaut"(){
        given: "un groupe avec un nom de groupe valide"
        Groupe grp = new Groupe("Les fans de l'Olympique de Marseille")

        expect:"le groupe est valide"
        validator.validate(grp).empty

        and:"le groupe n'a pas de pronostics"
        !grp.pronostics

        and: "le groupe n'a pas de scores"
        !grp.scores

        and: "le nom du groupe correspond"
        grp.nom.equals("Les fans de l'Olympique de Marseille")

        and: "le nombre de place par défaut est 10"
        grp.nb_places.equals(10)
    }

    def "test la validite d'un groupe avec un nombre de place définit"(){
        given: "un groupe avec un nom de groupe valide et un nombre de place"
        int nb_place = 15;
        Groupe grp = new Groupe("Les fans de l'Olympique de Marseille",nb_place)

        expect:"le groupe est valide"
        validator.validate(grp).empty

        and:"le groupe n'a pas de pronostics"
        !grp.pronostics

        and: "le groupe n'a pas de scores"
        !grp.scores

        and: "le nom du groupe correspond"
        grp.nom.equals("Les fans de l'Olympique de Marseille")

        and: "le nombre de place est égal au nombre de place donné"
        grp.nb_places.equals(nb_place)
    }

    def "test de modification du nom du groupe"(){
        given: "un groupe avec un nom de groupe valide"
        Groupe grp = new Groupe("Les fans de l'Olympique de Marseille")

        when: "Changement de nom du groupe"
        grp.setNom("Les fans de l'OM")

        then: "Le groupe est toujours valide"
        validator.validate(grp).empty

        and: "Le groupe a changé de nom"
        grp.getNom().equals("Les fans de l'OM")
    }

    def "test de modification du nombre de place du groupe"(){
        given: "un groupe avec un nom de groupe valide et un nombre de place"
        Groupe grp = new Groupe("Les fans de l'Olympique de Marseille",15)

        when: "Changement du nombre de place du groupe"
        grp.setNb_places(20)

        then: "Le groupe est toujours valide"
        validator.validate(grp).empty

        and: "Le groupe a changé son nombre de place"
        grp.nb_places.equals(20)
    }


    def "test d'ajout d'un pronostic dans le groupe"(){
        given: "un groupe avec un nom de groupe valide"
        Groupe grp = new Groupe("Les fans de l'Olympique de Marseille")

        and:"le groupe n'a pas de pronostics"
        grp.getPronostics().size()==0

        and: "Un pronostic à ajouter"
        Pronostic p = new Pronostic()

        when: "On ajoute le pronostic"
        grp.addPronostic(p)

        then: "Le groupe est toujours valide"
        validator.validate(grp).empty

        and: "le groupe possède un pronostic"
        grp.getPronostics().size()>0
    }

    def "test d'ajout d'un pronostic null dans le groupe"(){
        given: "un groupe avec un nom de groupe valide"
        Groupe grp = new Groupe("Les fans de l'Olympique de Marseille")

        and:"le groupe n'a pas de pronostics"
        grp.getPronostics().size()==0

        when: "On ajoute le pronostic null"
        grp.addPronostic(null)

        then: "le groupe ne possède toujours pas de pronostics"
        grp.getPronostics().size()==0
    }

    def "test d'ajout d'un pronostic déjà ajouté"(){
        given: "un groupe avec un nom de groupe valide"
        Groupe grp = new Groupe("Les fans de l'Olympique de Marseille")

        and: "Un pronostic à ajouter"
        Pronostic p = new Pronostic()

        and: "On ajoute le pronostic"
        grp.addPronostic(p)

        and:"le groupe possède un seul pronostic"
        grp.getPronostics().size()==1

        when: "On ajoute de nouveau le même pronostic"
        grp.addPronostic(p)

        then: "le groupe possède toujours un seul pronostic"
        grp.getPronostics().size()==1
    }

    def "test d'ajout d'un score dans le groupe"(){
        given: "un groupe avec un nom de groupe valide"
        Groupe grp = new Groupe("Les fans de l'Olympique de Marseille")

        and:"le groupe n'a pas de score"
        grp.getScores().size()==0

        and: "Un score à ajouter"
        Score s = new Score()

        when: "On ajoute le score"
        grp.addScore(s)

        then: "Le groupe est toujours valide"
        validator.validate(grp).empty

        and: "le groupe possède un score"
        grp.getScores().size()>0
    }

    def "test d'ajout d'un score null dans le groupe"(){
        given: "un groupe avec un nom de groupe valide"
        Groupe grp = new Groupe("Les fans de l'Olympique de Marseille")

        and:"le groupe n'a pas de scores"
        grp.getScores().size()==0

        when: "On ajoute le score null"
        grp.addScore(null)

        then: "le groupe ne possède toujours pas de scores"
        grp.getScores().size()==0
    }


    def "test d'ajout d'un score déjà ajouté"(){
        given: "un groupe avec un nom de groupe valide"
        Groupe grp = new Groupe("Les fans de l'Olympique de Marseille")

        and: "Un score à ajouter"
        Score s = new Score()

        and: "On ajoute le score"
        grp.addScore(s)

        and:"le groupe possède un seul score"
        grp.getScores().size()==1

        when: "On ajoute de nouveau le même score"
        grp.addScore(s)

        then: "le groupe possède toujours un seul score"
        grp.getScores().size()==1
    }

    /*void cleanup() {

    }*/
}
