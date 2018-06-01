package com.m2dl.dlmovie.movies.services

import com.m2dl.dlmovie.DlmovieApplication
import com.m2dl.dlmovie.movies.domain.Comment
import com.m2dl.dlmovie.movies.domain.Mark
import com.m2dl.dlmovie.movies.domain.Movie
import com.m2dl.dlmovie.movies.repositories.CommentRepository
import com.m2dl.dlmovie.users.domain.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration
@SpringBootTest
class CommentServiceIntegrationTest extends Specification{

    @Autowired
    private CommentService commentService

    @Autowired
    private CommentRepository commentRepository

    void setup() {

    }

    def "Test get all comments"() {
        given: "3 saved comments"
            commentRepository.save(new Comment("comment1"))
            commentRepository.save(new Comment("comment2"))
            commentRepository.save(new Comment("comment3"))
        when: "we retrieve all comments"
           List<Comment> comments = commentService.findAll()
        then: "we have the three comments"
            comments.size() == 3
            commentRepository.deleteAll()
    }

    def "add a comment" () {
        given: "a comment is created"
            Comment comment = new Comment("comment 1")

        and: "a movie is created"
            Movie movie = new Movie("title","description",new Date(),"director")
            comment.setMovie(movie)

        and: "a user is created"
            User user = new User("pseudo","email@email.fr","password")
            comment.setUser(user)

        when: "a comment is saved"
            commentService.addComment(comment)

        then: "the comment is persisted and has an id"
            comment.id != null

        and: "the movie has an id after saving"
            movie.id != null

        and: "the user has and id after saving"
            user.id != null

        commentRepository.deleteAll()
    }

    def "remove a comment"() {
        given: "a comment was saved"
            Comment c = new Comment('comment1')
            commentRepository.save(c)
        when: "the same comment was deleted"
            commentService.remove(c)
        then: "we can't get the comment"
            List<Comment> comments = commentRepository.findAll()
            comments.size() == 0
            commentRepository.deleteAll()
    }
}
