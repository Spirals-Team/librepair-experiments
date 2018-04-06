package com.m2dl.dlmovie.movies.domain

import com.m2dl.dlmovie.movies.domain.Movie
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory

@SpringBootTest
class MovieTest extends Specification{

    private static Validator validator

    def setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory()
        validator = factory.getValidator()
    }

    def "Movie validation" (title, description, date, director, isValid){
        expect:
        (validator.validate(new Movie(title, description, date, director)).size() == 0) == isValid

        where:
        title     | description | date       | director     | isValid
        "Title"   | ""          | new Date() | "real"       | true
        "Title"   | "frzf"      | new Date() | "real"       | true
        ""        | ""          | new Date() | "real"       | false
        "Title"   | ""          | null       | "real"       | false
        "Title"   | ""          | new Date() | ""           | false
        "Title"   | ""          | new Date() | "r"          | false
    }
}

