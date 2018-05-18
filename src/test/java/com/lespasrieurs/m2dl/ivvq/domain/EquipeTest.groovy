package com.lespasrieurs.m2dl.ivvq.domain

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.Validation
import javax.validation.ValidatorFactory
import javax.validation.Validator

@SpringBootTest
class EquipeTest extends Specification {

    Validator validator

    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Unroll
    void "test la validite d'une equipe ayant pour nom un #nom"(){
        given: "une equipe avec un nom d'equipe valide"
        Equipe psg = new Equipe("Paris-Saint-Germain")

        expect:"l'equipe est valide"
        validator.validate(psg).empty

        and:"l'equipe n'a pas de match domicile"
        !psg.matchListDomicile

        and: "l'equipe n'a pas de match exterieur"
        !psg.matchListExterieur

        and:
        psg.nom.equals("Paris-Saint-Germain")
    }

    @Unroll
    void "test modification nom equipe"() {

        given: "une equipe avec un nom d'equipe valide"
        Equipe psg = new Equipe("Paris-Saint-Germain")

        when: "Changement de nom de l'equipe"
        psg.setNom("PSG")

        then: "L'equipe est toujours valide"
        validator.validate(psg).empty

        and: "L'equipe a changé de nom"
        psg.getNom().equals("PSG")

    }

    @Unroll
    void "test d'une equipe non valide"(String nomEquipe) {

        given: "initialise une equipe avec un nom vide"
        Equipe equipe = new Equipe(nomEquipe)

        expect: "l'equipe est invalide"
        !validator.validate(equipe).empty

        where:
        nomEquipe << [null, ""]
    }


    /*void cleanup() {
    }*/

    /*def "GetId_equipe"() {
    }





    def "SetId_equipe"() {
    }

    def "GetMatchListExterieur"() {
    }

    def "SetMatchListExterieur"() {
    }

    def "GetMatchListDomicile"() {
    }

    def "SetMatchListDomicile"() {
    }*/
}
