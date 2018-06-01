package com.m2dl.dlmovie.movies.controllers

import com.m2dl.dlmovie.config.security.JwtAuthenticationResponse
import com.m2dl.dlmovie.config.security.requests.LoginRequest
import com.m2dl.dlmovie.movies.domain.Movie
import com.m2dl.dlmovie.users.domain.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.*
import org.springframework.test.context.ContextConfiguration
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
class MovieControllerIntegrationTest extends Specification {

    @Autowired
    private TestRestTemplate restTemplate

    @Shared token

    // workaround, autowired variables not accessible in setupSpec
    // http://spock-framework.3207229.n2.nabble.com/Autowiring-in-cleanupSpec-and-setupSpec-methods-td7573234.html
    def setup() {
        if(token == null) {
            initToken()
        }
    }

    def initToken() {
        def pseudo = "Testeur3"
        def email = "testeur@test.com3"
        def password = "TesteurPwd3"
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

    def "MovieController create integration" (title, description, date, director){

        given:
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        Movie body = new Movie()
        body.setTitle(title)
        body.setDescription(description)
        body.setDate(date)
        body.setDirector(director)
        HttpEntity httpEntity = new HttpEntity<>(body, headers)
        ResponseEntity<Movie> result = restTemplate.exchange("/api/v1/movies", HttpMethod.POST, httpEntity,
                new ParameterizedTypeReference<Movie>() {})

        Movie movie = result.getBody()

        expect:
        movie.getId() != null
        movie.getTitle() != null
        movie.getDate() != null
        movie.getDirector() != null

        where:
        title     | description | date       | director
        "Title"   | ""          | new Date() | "real"

    }

    def "MovieController getAll integration" (){

        given:
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token)

        ResponseEntity<List<Movie>> resultGetAll1 = restTemplate.exchange(
                "/api/v1/movies",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<Movie>>() {})
        List<Movie> movies1 = resultGetAll1.getBody()

        Movie body = new Movie()
        body.setTitle(title)
        body.setDescription(description)
        body.setDate(date)
        body.setDirector(director)
        HttpEntity httpEntity = new HttpEntity<>(body, headers)
        ResponseEntity<Movie> resultCreate = restTemplate.exchange("/api/v1/movies", HttpMethod.POST, httpEntity,
                new ParameterizedTypeReference<Movie>() {})
        Movie movieAdded = resultCreate.getBody()
        ResponseEntity<List<Movie>> resultGetAll2 = restTemplate.exchange(
                "/api/v1/movies", HttpMethod.GET,
                new HttpEntity<>(headers), new ParameterizedTypeReference<List<Movie>>() {})
        List<Movie> movies2 = resultGetAll2.getBody()

        expect:
        movies2.size() == movies1.size() + 1
        movies2.get(movies2.size()-1).getTitle() == movieAdded.getTitle()
        movies2.get(movies2.size()-1).getDescription() == movieAdded.getDescription()
        movies2.get(movies2.size()-1).getDate() == movieAdded.getDate()
        movies2.get(movies2.size()-1).getDirector() == movieAdded.getDirector()

        where:
        title     | description | date       | director
        "Title"   | ""          | new Date() | "real"

    }

    def "MovieController get integration" (){

        given:
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token)

        Movie body = new Movie()
        body.setTitle(title)
        body.setDescription(description)
        body.setDate(date)
        body.setDirector(director)
        HttpEntity httpEntity = new HttpEntity<>(body, headers)
        ResponseEntity<Movie> resultCreate = restTemplate.exchange("/api/v1/movies", HttpMethod.POST, httpEntity,
                new ParameterizedTypeReference<Movie>() {})
        Movie movieAdded = resultCreate.getBody()
        Long id = movieAdded.getId()
        ResponseEntity<Movie> result = restTemplate.exchange("/api/v1/movies/" + id, HttpMethod.GET, httpEntity,
                new ParameterizedTypeReference<Movie>() {})
        Movie movieResulting = result.getBody()


        expect:
        movieResulting.getId() == movieAdded.getId()
        movieResulting.getTitle() == movieAdded.getTitle()

        where:
        title     | description | date       | director
        "Title"   | ""          | new Date() | "real"

    }

    def "MovieController delete integration" (){

        given:
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token)

        Movie body = new Movie()
        body.setTitle(title)
        body.setDescription(description)
        body.setDate(date)
        body.setDirector(director)
        HttpEntity httpEntity = new HttpEntity<>(body, headers)
        ResponseEntity<Movie> resultCreate = restTemplate.exchange("/api/v1/movies", HttpMethod.POST, httpEntity,
                new ParameterizedTypeReference<Movie>() {})
        Movie movieAdded = resultCreate.getBody()

        ResponseEntity<List<Movie>> resultGetAll1 = restTemplate.exchange("/api/v1/movies", HttpMethod.GET,
                new HttpEntity(headers), new ParameterizedTypeReference<List<Movie>>() {})
        List<Movie> moviesBeforeDelete = resultGetAll1.getBody()

        restTemplate.exchange("/api/v1/movies/" + movieAdded.getId(), HttpMethod.DELETE, new HttpEntity(headers),
                String.class)

        ResponseEntity<List<Movie>> resultGetAll2 = restTemplate.exchange("/api/v1/movies", HttpMethod.GET,
                new HttpEntity(headers), new ParameterizedTypeReference<List<Movie>>() {})
        List<Movie> moviesAfterDelete = resultGetAll2.getBody()

        ResponseEntity<Movie> result = restTemplate.exchange("/api/v1/movies/" + movieAdded.getId(), HttpMethod.GET, httpEntity,
                new ParameterizedTypeReference<Movie>() {})
        Movie movieResulting = result.getBody()

        expect:

        movieResulting == null

        moviesAfterDelete.size() == moviesBeforeDelete.size() - 1


        where:
        title     | description | date       | director
        "Title"   | ""          | new Date() | "real"

    }

    def "MovieController update integration" (title, description, date, director){

        given:
        HttpHeaders headers = new HttpHeaders()
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token)

        headers.setContentType(MediaType.APPLICATION_JSON)
        Movie body = new Movie()
        body.setTitle(title)
        body.setDescription(description)
        body.setDate(date)
        body.setDirector(director)
        HttpEntity httpEntity = new HttpEntity<>(body, headers)
        ResponseEntity<Movie> result = restTemplate.exchange("/api/v1/movies", HttpMethod.POST, httpEntity,
                new ParameterizedTypeReference<Movie>() {})
        Movie movie = result.getBody()
        String newDirector = "newDirector"
        body.setDirector(newDirector)
        ResponseEntity<Movie> resultUpdated = restTemplate.exchange("/api/v1/movies/" + movie.getId(), HttpMethod.PUT,
                httpEntity, new ParameterizedTypeReference<Movie>() {})
        Movie movieUpdated = resultUpdated.getBody()

        expect:
        movieUpdated.getDirector() == newDirector

        where:
        title     | description | date       | director
        "Title"   | ""          | new Date() | "real"

    }
}
