package com.lespasrieurs.m2dl.ivvq.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory

/**
 * Created by SoF on 06/04/2018.
 */
class ParieurTest extends Specification {

    Validator validator

    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Unroll
    void "test la validite d'un parieur valide"(String unPseudo, String unEmail, String unMotDePass) {

        given: "un parieur initialise correctement"
        Parieur parieur = new Parieur(pseudo: unPseudo, email: unEmail , motDePasse: unMotDePass)

        expect: "le parieur est valide"
        validator.validate(parieur).empty

        and: "il n'a aucun score"
        !parieur.scores

        where:
        unPseudo | unEmail | unMotDePass
        "damhj" | "jd@jd.com" | "m"
        "pse" | "jdemail@jd.fr" | "mdp"

    }

    @Unroll
    void "test l'invalidite d'un parieur non valide"(String unPseudo, String unEmail, String unMotDePass) {

        given: "un parieur initialise de maniere non valide"
        Parieur parieur = new Parieur(pseudo: unPseudo, email: unEmail , motDePasse: unMotDePass)

        expect: "le parieur est invalide"
        !validator.validate(parieur).empty

        where:
        unPseudo  | unEmail     | unMotDePass
        null      | "jd@jd.com" | "nv"
        ''        | "jd@jd.com" | "kjkhs88"
        "Dupond"  | "jd@com"    | ''
        "fgJJkf"  | null        | "kjkhs88"
        "Dupond"  | "jd@jd.com" | null
    }

}
