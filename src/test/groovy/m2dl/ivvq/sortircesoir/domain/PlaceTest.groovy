package m2dl.ivvq.sortircesoir.domain

import spock.lang.Specification

import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory

class PlaceTest extends Specification {

    Validator validator

    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory()
        validator = factory.getValidator()
    }

    void "test la validite d'une place valide"(String unName, String unAddress) {

        given: "une place initialisé correctement"
        Place place = new Place(name: unName, address: unAddress, owner: Mock(User))

        expect: "la place est valide"
        validator.validate(place).empty

        where:
        unName     | unAddress                                 |_
        "Capitole" | "11 Place de la Daurade, 31000 Toulouse"  |_

    }
    void "test l'invalidite d'une place non valide"(String aName, String aAddress) {

        given: "une place initialise de maniere non valide"
        Place place = new Place(name: aName, address: aAddress, owner:Mock(User))

        expect: "l'utilisateur est invalide"
        !validator.validate(place).empty

        where:
        aName     | aAddress
        ""         | "11 Place de la Daurade, 31000 Toulouse"
        null       | "11 Place de la Daurade, 31000 Toulouse"
        "Capitole" | ""
        "Capitole" | null
    }

}
