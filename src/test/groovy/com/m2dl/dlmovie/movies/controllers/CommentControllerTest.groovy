package com.m2dl.dlmovie.movies.controllers

import com.m2dl.dlmovie.movies.domain.Comment
import com.m2dl.dlmovie.movies.domain.Movie
import com.m2dl.dlmovie.movies.repositories.CommentRepository
import com.m2dl.dlmovie.movies.requests.CommentRequest
import com.m2dl.dlmovie.movies.services.CommentService
import com.m2dl.dlmovie.movies.services.MovieService
import com.m2dl.dlmovie.users.domain.User
import com.m2dl.dlmovie.users.services.UserService
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration
@SpringBootTest
class CommentControllerTest extends Specification{

    private UserService userService
    private MovieService movieService
    private CommentService commentService

    def setup() {
        commentService = Mock()
        userService = Mock()
        movieService = Mock()
    }

    def "we retrieve all comments" () {
        given: "stub commentService for controller"
            commentService.findAll() >> {
                List<Comment> comments = new ArrayList<>()
                comments.add(new Comment("text1"))
                comments.add(new Comment("text2"))
                comments.add(new Comment("text3"))
                comments.add(new Comment("text4"))
                return comments
            }
            CommentController commentController = new CommentController(commentService,userService,movieService)
        when: "we retrieve all comments"
            List<Comment> commentsList = commentController.index()
        then: "we have 4 comments"
            commentsList.size() == 4

    }

    def "we save a comment"() {
        given: "we stub commentService, userService and movieService"
            User user = new User("pseudo","email@email.fr","password");
            Movie movie = new Movie("title","description",new Date(),"director");
            userService.getById(1L) >> {
                return user
            }
            movieService.get(1L) >> {
                return movie
            }
            commentService.addComment(_) >> {
                Comment c = new Comment("new comment")
                c.setId(1L)
                return c
            }
        CommentController commentController = new CommentController(commentService,userService,movieService)
        when: "a comment is saved"
            def comment = commentController.save(new CommentRequest("comment",1L,1L))
        then: "comment has an id"
            comment.getId() == 1L
    }
}
