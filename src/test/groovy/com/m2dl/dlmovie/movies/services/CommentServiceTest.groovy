package com.m2dl.dlmovie.movies.services

import com.m2dl.dlmovie.DlmovieApplication
import com.m2dl.dlmovie.movies.domain.Comment
import com.m2dl.dlmovie.movies.domain.Movie
import com.m2dl.dlmovie.movies.repositories.CommentRepository
import com.m2dl.dlmovie.users.domain.User
import com.m2dl.dlmovie.users.services.UserService
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class CommentServiceTest extends Specification {

    private CommentRepository commentRepository

    private CommentService commentService

    void setup() {
        commentRepository = Stub(CommentRepository);
        MovieService movieService = Mock()
        UserService userService = Mock()
        commentService = new CommentService(commentRepository,movieService,userService);
    }

    def "add Comment"() {
        given:

            def movie = new Movie()
            def user = new User()
            def comment = new Comment(text:"new comment",movie:movie,user: user)
            movie.setComments([])
            commentRepository.save(comment) >> {
                comment.setId(1L)
                return comment
            }

        when:
            Comment c = commentService.addComment(comment)
        then:
            c.getId() == 1L
    }

    def "add Comment without film or movie throw an error"(String text, Movie movie, User user) {
        when:
            commentService.addComment(new Comment(text: text, movie: movie, user: user))
        then:
            thrown(IllegalArgumentException)
        where:
            text      | movie       | user
            "comment" | new Movie() | null
            "comment" | null        | new User()
            "comment" | null        | null
    }

    def "add a null comment"() {
        when: "we save a null comment"
            commentService.addComment(null)
        then:
            thrown(IllegalArgumentException)

    }

    def "get all comments"() {
        given:
            commentRepository.findAll() >> {
                List<Comment> list = new ArrayList<>()
                list.add(new Comment("comment1"))
                list.add(new Comment("comment2"))
                list.add(new Comment("comment3"))
                list.add(new Comment("comment4"))
                return list
            }
        when:
            List<Comment> comments = commentService.findAll()
        then:
            comments.size() == 4
    }
}
