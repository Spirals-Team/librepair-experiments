package com.m2dl.dlmovie.movies.controllers

import com.m2dl.dlmovie.config.security.JwtAuthenticationResponse
import com.m2dl.dlmovie.config.security.requests.LoginRequest
import com.m2dl.dlmovie.movies.domain.Comment
import com.m2dl.dlmovie.movies.domain.Movie
import com.m2dl.dlmovie.movies.repositories.MovieRepository
import com.m2dl.dlmovie.movies.requests.CommentRequest
import com.m2dl.dlmovie.movies.services.CommentService
import com.m2dl.dlmovie.users.domain.User
import com.m2dl.dlmovie.users.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
class CommentControllerIntegrationTest extends Specification{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CommentService commentService;

    @Shared token

    def setup() {
        if(token == null) {
            initToken()
        }
    }

    def initToken() {
        def pseudo = "Testeur"
        def email = "testeur@test.com"
        def password = "TesteurPwd"
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)

        def body = """
            {
                "pseudo": "${pseudo}",
                "email": "${email}",
                "password": "${password}"
            }
        """.toString()

        HttpEntity<String> httpEntityRegister = new HttpEntity<>(body, headers)
        restTemplate.exchange("/api/v1/users", HttpMethod.POST, httpEntityRegister,
                new ParameterizedTypeReference<User>() {})

        HttpEntity<LoginRequest> httpEntityLogin = new HttpEntity<>(new LoginRequest(pseudo, password), headers)
        ResponseEntity<JwtAuthenticationResponse> response = restTemplate.exchange("/api/v1/users/login",
                HttpMethod.POST, httpEntityLogin,
                new ParameterizedTypeReference<JwtAuthenticationResponse>() {})

        token = response.getBody().getAccessToken()
    }

    def "get all comments"(){
        given: "Some comments"
            User user = new User("pseudo", "pseudo@mail.com", "password")
            Movie movie = new Movie(title: "movie", description: "descr", date: new Date(), director: "director")
            commentService.addComment(new Comment(text: "comment1",movie:movie,user:user))
            commentService.addComment(new Comment(text: "comment2",movie: movie,user: user));
            commentService.addComment(new Comment(text: "comment3",movie: movie,user: user));
            commentService.addComment(new Comment(text: "comment4",movie: movie,user: user));
        when: "we call to the API"
            HttpHeaders headers = new HttpHeaders()
            headers.setContentType(MediaType.APPLICATION_JSON)
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            HttpEntity httpEntity = new HttpEntity<>(null, headers)
            ResponseEntity<List<Comment>> result = restTemplate.exchange("/api/v1/comments", HttpMethod.GET, httpEntity,
                    new ParameterizedTypeReference<List<Comment>>() {})
            println(result.getBody())
            List<Comment> comments = result.getBody()
        then: "we have 4 comments"
        comments.size() == 4
    }

    def "create a comment"(){
        given: "a user, a movie and a description for the comment"
            User user = userRepository.save(new User("pseudo", "pseudo@mail.com", "password"));
            Movie movie = movieRepository.save(new Movie(title: "movie", description: "descr", date: new Date(), director: "director"))
            def description = "description"

        when: "we call to the API"
            CommentRequest body = new CommentRequest(description, movie.getId(), user.getId());
            HttpHeaders headers = new HttpHeaders()
            headers.setContentType(MediaType.APPLICATION_JSON)
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            HttpEntity httpEntity = new HttpEntity<>(body, headers)
            ResponseEntity<Comment> result = restTemplate.exchange("/api/v1/comments", HttpMethod.POST, httpEntity,
                    new ParameterizedTypeReference<Comment>() {})
        println(result.getBody());
            Comment comment = result.getBody();

        then: "the comment has an id, a movie and a film"
            comment.getId() != null
            comment.getMovie().getId() == movie.getId()
            comment.getUser().getId() == user.getId()
    }
}
