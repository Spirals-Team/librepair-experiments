package com.m2dl.dlmovie.movies.controllers

import com.m2dl.dlmovie.config.security.JwtAuthenticationResponse
import com.m2dl.dlmovie.config.security.requests.LoginRequest
import com.m2dl.dlmovie.movies.domain.Mark
import com.m2dl.dlmovie.movies.domain.Movie
import com.m2dl.dlmovie.movies.repositories.MarkRepository
import com.m2dl.dlmovie.movies.repositories.MovieRepository
import com.m2dl.dlmovie.movies.requests.MarkRequest
import com.m2dl.dlmovie.movies.services.MarkService
import com.m2dl.dlmovie.users.domain.User
import com.m2dl.dlmovie.users.repositories.UserRepository
import com.m2dl.dlmovie.users.services.UserService
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
class MarkControllerIntegrationTest extends Specification {

    @Autowired
    private MarkService markService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Shared token

    // workaround, autowired variables not accessible in setupSpec
    // http://spock-framework.3207229.n2.nabble.com/Autowiring-in-cleanupSpec-and-setupSpec-methods-td7573234.html
    def setup() {
        initToken()
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

    def "get all marks"(){
        given: "Some marks for a user"
            User user = new User("valentin", "v@v.com", "ynwa")
            Movie movie = new Movie(title: "SG8 LVP", description: "Super film", date: new Date(), director: "Klopp")
            markService.createMark(new Mark(user: user, value: 2, movie: movie, date: new Date()))
            markService.createMark(new Mark(user: user, value: 2, movie: movie, date: new Date()))
            markService.createMark(new Mark(user: user, value: 2, movie: movie, date: new Date()))
            markService.createMark(new Mark(user: user, value: 2, movie: movie, date: new Date()))
            def count = markService.getAll().size()
        when: "we call to the API"
            HttpHeaders headers = new HttpHeaders()
            headers.setContentType(MediaType.APPLICATION_JSON)
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            HttpEntity httpEntity = new HttpEntity<>(null, headers)
            ResponseEntity<List<Mark>> result = restTemplate.exchange("/api/v1/marks", HttpMethod.GET, httpEntity,
                    new ParameterizedTypeReference<List<Mark>>() {})
            println(result.getBody())
            List<Mark> marks = result.getBody()
        then: "we have 4 marks"
            marks.size() == count
    }

    def "create a mark"(){
        given: "a user, a movie and a value for the mark"
            User user = userRepository.save(new User("valentin", "v@v.com", "ynwa"));
            Movie movie = movieRepository.save(new Movie(title: "SG8 LVP", description: "Super film", date: new Date(), director: "Klopp"))
            int value = 4;
        when: "we call to the API"
            MarkRequest body = new MarkRequest(value, movie.getId(), user.getId());
            HttpHeaders headers = new HttpHeaders()
            headers.setContentType(MediaType.APPLICATION_JSON)
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            HttpEntity httpEntity = new HttpEntity<>(body, headers)
            ResponseEntity<Mark> result = restTemplate.exchange("/api/v1/marks", HttpMethod.POST, httpEntity,
                new ParameterizedTypeReference<Mark>() {})
            Mark mark = result.getBody();
        then: "the mark has an id, a movie and a film"
            mark.getId() != null
            mark.getValue() == value
            mark.getMovie().getId() == movie.getId()
            mark.getUser().getId() == user.getId()
    }

    def "create mark fail"(){
        given: "no user and no movie, just value"
            def body = """
                {
                    "value": 4,
                }
            """.toString()
        when: "we call the API"
            HttpHeaders headers = new HttpHeaders()
            headers.setContentType(MediaType.APPLICATION_JSON)
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            HttpEntity httpEntity = new HttpEntity<>(body, headers)
            ResponseEntity<Object> result = restTemplate.exchange("/api/v1/marks", HttpMethod.POST, httpEntity,
                    new ParameterizedTypeReference<Object>() {})
        then: "there is an error"
            result.statusCodeValue == 400
    }

}
