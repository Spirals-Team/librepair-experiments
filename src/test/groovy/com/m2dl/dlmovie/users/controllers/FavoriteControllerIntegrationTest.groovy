package com.m2dl.dlmovie.users.controllers

import com.m2dl.dlmovie.config.security.JwtAuthenticationResponse
import com.m2dl.dlmovie.config.security.requests.LoginRequest
import com.m2dl.dlmovie.movies.domain.Movie
import com.m2dl.dlmovie.movies.repositories.MovieRepository
import com.m2dl.dlmovie.users.domain.User
import com.m2dl.dlmovie.users.requests.FavoriteRequest
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
class FavoriteControllerIntegrationTest extends Specification {

    @Autowired
    private TestRestTemplate restTemplate

    @Autowired
    private MovieRepository movieRepository


    @Shared token

    // workaround, autowired variables not accessible in setupSpec
    // http://spock-framework.3207229.n2.nabble.com/Autowiring-in-cleanupSpec-and-setupSpec-methods-td7573234.html
    def setup() {
        if(token == null) {
            initToken()
        }
    }

    def initToken() {
        def pseudo = "Testeur1"
        def email = "testeur@test.com1"
        def password = "TesteurPwd1"
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

    def "FavoriteController addFavorite success" (){

        given:
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        def movie = movieRepository.save(new Movie("movie", "", new Date(), "real"))
        FavoriteRequest body = new FavoriteRequest(movie.getId())
        HttpEntity httpEntity = new HttpEntity<>(body, headers)

        when:
        ResponseEntity result = restTemplate.exchange("/api/v1/favorites", HttpMethod.POST, httpEntity, String.class)

        then:
        result.getStatusCodeValue() == 200

    }

    def "FavoriteController addFavorite already reported" (){

        given:
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        def movie = movieRepository.save(new Movie("movie", "", new Date(), "real"))
        FavoriteRequest body = new FavoriteRequest(movie.getId())
        HttpEntity httpEntity = new HttpEntity<>(body, headers)
        restTemplate.exchange("/api/v1/favorites", HttpMethod.POST, httpEntity, String.class)

        when:
        ResponseEntity result = restTemplate.exchange("/api/v1/favorites", HttpMethod.POST, httpEntity, String.class)

        then:
        result.getStatusCodeValue() == 208

    }

    def "FavoriteController deleteFavorite" (){

        given:
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        def movie = movieRepository.save(new Movie("movie", "", new Date(), "real"))
        FavoriteRequest body = new FavoriteRequest(movie.getId())
        HttpEntity httpEntity = new HttpEntity<>(body, headers)
        restTemplate.exchange("/api/v1/favorites", HttpMethod.POST, httpEntity, String.class)

        when:
        ResponseEntity result = restTemplate.exchange("/api/v1/favorites/" + movie.getId(), HttpMethod.DELETE,
                httpEntity, String.class)

        then:
        result.getStatusCodeValue() == 200

    }

    def "FavoriteController getAll" (){

        given:
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        HttpEntity httpEntity = new HttpEntity<>(headers)

        when:
        ResponseEntity result = restTemplate.exchange("/api/v1/favorites", HttpMethod.GET,
                httpEntity, String.class)

        then:
        result.getStatusCodeValue() == 200

    }
}

