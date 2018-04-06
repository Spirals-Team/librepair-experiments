package com.m2dl.dlmovie.users.domain

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory;

@SpringBootTest
class UserTest extends Specification {

    private static Validator validator

    def setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory()
        validator = factory.getValidator()
    }

    def "User validation" (pseudo, email, password, isValid){
        expect:
        (validator.validate(new User(pseudo, email, password)).size() == 0) == isValid

        where:
        pseudo    | email   | password   | isValid
        "Pseudo1" | "e@e.e" | "password" | true
        "Pseu"    | "e@e.e" | "password" | false
        "Pseudo1" | "e@e.e" | "pass"     | false
        "Pseudo1" | "ee"    | "password" | false
        ""        | "e@e.e" | "password" | false
        "Pseudo1" | ""      | "password" | false
        "Pseudo1" | "e@e.e" | ""         | false
    }

    def "User has an empty constructor" () {
        new User()
    }

    def "User has an id" (id, idToGet) {
        given:
        def user = new User();
        user.setId(id)

        expect:
        user.getId() == idToGet

        where:
        id    | idToGet
        15L   | 15L
        5L    | 5L
        4L    | 4L
    }
}