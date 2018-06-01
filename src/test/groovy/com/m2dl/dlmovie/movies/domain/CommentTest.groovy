package com.m2dl.dlmovie.movies.domain

import com.m2dl.dlmovie.users.domain.User
import spock.lang.Specification

import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory

class CommentTest extends Specification{

    private static Validator validator;

    def setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory()
        validator = factory.getValidator()
    }

    def "Comment validation" (String text, isValid){
        expect:
        (validator.validate(new Comment(text)).size() == 0) == isValid

        where:
        text   | isValid
        "comment" | true
        "ceci est une description plus longue" | true
        "c" | false
        ""     | false
        null | false
    }

    def "Relation ManyToOne User" () {
        given: "a user and comment are created"
        def user = new User("u1","u1@email.fr","u1");
        def comment = new Comment("text");
        when:
        comment.setUser(user)

        then:
        comment.getUser().is(user)

    }

    def "Relation ManyToOne Movies" () {
        given: "a comment and movie are created"
        def movie = new Movie()
        def comment = new Comment("text")
        when:
        comment.setMovie(movie)

        then:
        comment.getMovie().is(movie)

    }

    def "Comment has an empty constructor"() {
        expect:
        new Comment()
    }

    def "Comment has an id" ( Long id, Long idToGet) {
        given:
        def comment = new Comment()
        comment.setId(id)

        expect:
        comment.getId() == idToGet

        where:
        id    | idToGet
        15L   | 15L
        5L    | 5L
        4L    | 4L
    }
}

