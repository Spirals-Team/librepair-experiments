package m2dl.ivvq.sortircesoir.domain

import spock.lang.Specification

import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory

class UserTest extends Specification {

    Validator validator

    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory()
        validator = factory.getValidator()
    }

    void "test la validite d'un utilisateur valide"(String unEmail, String unLogin, String unPassword) {

        given: "un utilisateur initialise correctement"
        User user = new User(email: unEmail, login: unLogin, password: unPassword)

        expect: "l'utilisateur est valide"
        validator.validate(user).empty

        where:
        unEmail     | unLogin | unPassword
        "jd@jd.com" | "bahabdoulaye"    | "helloe"
        "jd@jd.com" | "abdiulaye"    | "helloe"
        "jd@jd.com" | "abdoulaye"    | "helloe"

    }
    void "test l'invalidite d'un utilisateur non valide"(String unEmail, String unLogin, String unPassword) {

        given: "un utilisateur initialise de maniere non valide"
        User user = new User(email: unEmail, login: unLogin, password: unPassword)

        expect: "l'utilisateur est invalide"
        !validator.validate(user).empty

        where:
        unEmail     | unLogin           | unPassword
        ""          | "bahabdoulaye"    | "helloe"
        "jd@jd.com" | ""                | "helloe"
        "jd@jd.com" | "abdoulaye"       | ""
        null        | "bahabdoulaye"    | "helloe"
        "jd@jd.com" | null              | "helloe"
        "jd@jd.com" | "abdoulaye"       | null
    }
}
