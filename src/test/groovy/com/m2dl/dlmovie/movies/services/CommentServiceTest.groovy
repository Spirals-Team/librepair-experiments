package com.m2dl.dlmovie.movies.services

import com.m2dl.dlmovie.DlmovieApplication
import com.m2dl.dlmovie.movies.domain.Comment
import com.m2dl.dlmovie.movies.domain.Movie
import com.m2dl.dlmovie.movies.repositories.CommentRepository
import com.m2dl.dlmovie.users.domain.User
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(classes = DlmovieApplication.class)
class CommentServiceTest extends Specification {

    private CommentRepository commentRepository

    private CommentService commentService

    void setup() {
        commentRepository = Stub(CommentRepository);
        commentService = new CommentService(commentRepository);
    }

    def "add Comment"() {
        given:
            def user = new User("u1","u1@email.fr","u1")
            def comment = new Comment("new comment")
            def movie = new Movie()

        when:
            commentService.addComment(user,comment,movie)
        then:
            comment.getUser().is(user)
            comment.getMovie().is(movie)
    }
}
