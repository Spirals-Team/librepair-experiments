package com.m2dl.dlmovie.movies.domain

import spock.lang.Specification

import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory

class MarkTest extends Specification {

    private static Validator validator

    def setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory()
        validator = factory.getValidator()
    }

    def "Mark Validation" (value, date, isValid){
        expect:
        (validator.validate(new Mark(value, date)).size() == 0) == isValid

        where:
        value | date       | isValid
        1     | new Date() | true
        2     | null       | false
        0     | new Date() | false
        6     | new Date() | false
        0     | null       | false
    }

}
