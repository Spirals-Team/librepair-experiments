package m2dl.ivvq.sortircesoir.domain

import spock.lang.Specification

import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory

class CommentTest extends Specification {

    Validator validator

    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory()
        validator = factory.getValidator()
    }

    void "test la validite d'un commentaire valide"(String unDescription) {

        given: "un commentaire initialisé correctement"
        Comment comment = new Comment(description: unDescription, user: Mock(User), place: Mock(Place))

        expect: "le commentaire est valide"
        validator.validate(comment).empty

        where:
        unDescription << ["place de la daurade restaurant est souhoute", "Le restaurant les bahs est super"]

    }
    void "test l'invalidite d'un commentaire non valide"(String unDescription) {

        given: "un commentaire initialisé de maniere non valide"
        Comment comment = new Comment(description: unDescription, user: Mock(User), place: Mock(Place))

        expect: "le commentaire est invalide"
        !validator.validate(comment).empty

        where:
        unDescription  << ["", null]
    }

}
