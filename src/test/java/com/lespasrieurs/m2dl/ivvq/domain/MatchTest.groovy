package com.lespasrieurs.m2dl.ivvq.domain

import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory

class MatchTest extends Specification {

    Validator validator

    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Unroll
    void "test la validite d'un match avec deux equipe valide"(){
        given: "un match valide avec deux équipes"
        Match match = new Match(Mock(Equipe), Mock(Equipe))

        expect:"le match est valide"
        validator.validate(match).empty

        and:"le score domicile est de 0"
        match.getButDomicile().equals(0)

        and: "le score exterieur est de 0"
        match.getButExterieur().equals(0)

    }

    @Unroll
    void "test l'invalidité d'un match"() {

        given: "une equipe domicile et exterieur"
        Equipe equipeDomicile = Mock(Equipe)
        Equipe equipeExterieur = Mock(Equipe)

        when: "un match sans equipe domicile"
        Match match = new Match(null, equipeExterieur)

        then: "le match n'est pas valide"
        !validator.validate(match).empty

        when: "un match sans equipe domicile"
        match = new Match(equipeDomicile, null)

        then: "le match n'est pas valide"
        !validator.validate(match).empty
    }

    @Unroll
    void "test la validité des listes de l'equipe domicile" () {

        given: "une equipe domicile et exterieur"
        Equipe equipeDomicile = new Equipe("Paris-Saint-Germain")
        Equipe equipeExterieur = new Equipe("OM")

        when: "un match"
        Match match = new Match(equipeDomicile, equipeExterieur)

        then: "la liste domicile de l'equipe domicile n'est pas vide"
        !equipeDomicile.getMatchListDomicile().empty

        then: "la liste exterieur de l'equipe domicile est vide"
        equipeDomicile.getMatchListExterieur().empty

        and: "l'equipe domicile a le match dans sa liste domicile"
        Match matchDomicile = equipeDomicile.getMatchListDomicile().get(0)
        match.equals(matchDomicile)
    }

    @Unroll
    void "test la validité des listes de l'equipe exterieur" () {

        given: "une equipe domicile et exterieur"
        Equipe equipeDomicile = new Equipe("Paris-Saint-Germain")
        Equipe equipeExterieur = new Equipe("OM")

        when: "un match"
        Match match = new Match(equipeDomicile, equipeExterieur)

        then: "la liste domicile de l'equipe exterieur est vide"
        equipeExterieur.getMatchListDomicile().empty

        then: "la liste exterieur de l'equipe exterieur n'est pas vide"
        !equipeExterieur.getMatchListExterieur().empty

        and: "l'equipe domicile a le match dans sa liste domicile"
        Match matchExterieur = equipeExterieur.getMatchListExterieur().get(0)
        match.equals(matchExterieur)
    }

    @Unroll
    void "test la validité des modifications de score"() {

        given: "une equipe domicile et exterieur"
        Equipe equipeDomicile = new Equipe("Paris-Saint-Germain")
        Equipe equipeExterieur = new Equipe("OM")

        when: "un match"
        Match match = new Match(equipeDomicile, equipeExterieur)

        and: "modification de score"
        match.setButDomicile(3)
        match.setButExterieur(1)

        then: "les scores sont les bons"
        match.getButDomicile() == 3
        match.getButExterieur() == 1

        and:
        match.toString().equals("Paris-Saint-Germain 3 - 1 OM")
    }

    @Unroll
    void "test l'invalidité des modifications de score"(int unScoreDomicile, int unScoreExterieur) {

        given: "une equipe domicile et exterieur"
        Equipe equipeDomicile = new Equipe("Paris-Saint-Germain")
        Equipe equipeExterieur = new Equipe("OM")

        when: "un match"
        Match match = new Match(equipeDomicile, equipeExterieur)

        and: "modification de score"
        match.setButDomicile(unScoreDomicile)
        match.setButExterieur(unScoreExterieur)

        then: "les scores sont invalide"
        !validator.validate(match).empty

        where:

        unScoreDomicile     | unScoreExterieur
        -1                  | 5
        7                   | -8

    }

    @Unroll
    void "test modification equipe valide"() {

        given: "une equipe domicile et exterieur"
        Equipe equipeDomicile = new Equipe("Paris-Saint-Germain")
        Equipe equipeExterieur = new Equipe("OM")

        when: "un match"
        Match match = new Match(equipeDomicile, equipeExterieur)

        and: "modification equipe exterieur"
        Equipe equipeExterieur2 = new Equipe("OL")
        !equipeExterieur2.equals(equipeExterieur)
        match.setEquipeExterieur(equipeExterieur2)

        then: "la nouvelle equipe exterieur est la bonne"
        match.getEquipeExterieur().equals(equipeExterieur2)

        when: "un match"
        match = new Match(equipeDomicile, equipeExterieur)

        and: "modification equipe domicile"
        Equipe equipeDomicile2 = new Equipe("OL")
        !equipeDomicile2.equals(equipeDomicile)
        match.setEquipeDomicile(equipeDomicile2)

        then: "la nouvelle equipe domicile est la bonne"
        match.getEquipeDomicile().equals(equipeDomicile2)

    }

    @Unroll
    void "test modification equipe invalide"(Equipe uneEquipeDomicile, Equipe uneEquipeExterieur) {

        given: "une equipe domicile et exterieur"
        Equipe equipeDomicile = Mock(Equipe)
        Equipe equipeExterieur = Mock(Equipe)

        when: "un match"
        Match match = new Match(equipeDomicile, equipeExterieur)

        and: "modification equipe exterieur"
        Equipe equipeExterieur2 = uneEquipeExterieur
        !equipeExterieur2.equals(equipeExterieur)
        match.setEquipeExterieur(equipeExterieur2)

        then: "la nouvelle equipe exterieur invalide le match"
        validator.validate(match).empty

        when: "un match"
        match = new Match(equipeDomicile, equipeExterieur)

        and: "modification equipe domicile"
        Equipe equipeDomicile2 = uneEquipeDomicile
        !equipeDomicile2.equals(equipeDomicile)
        match.setEquipeDomicile(equipeDomicile2)

        then: "la nouvelle equipe domicile invalide le match"
        validator.validate(match).empty

        where:

        uneEquipeDomicile   | uneEquipeExterieur
        null                | Mock(Equipe)
        Mock(Equipe)        | null

    }
}
