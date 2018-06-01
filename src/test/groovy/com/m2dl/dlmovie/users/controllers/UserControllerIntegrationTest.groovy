package com.m2dl.dlmovie.users.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.m2dl.dlmovie.config.security.JwtAuthenticationResponse
import com.m2dl.dlmovie.config.security.requests.LoginRequest
import com.m2dl.dlmovie.config.security.requests.SignUpRequest
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
class UserControllerIntegrationTest extends Specification{

    @Autowired
    private TestRestTemplate restTemplate

    @Shared headers

    def setupForAll() {
        headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)

        // REGISTER ONE USER
        HttpEntity<SignUpRequest> httpEntity = new HttpEntity<>(
                new SignUpRequest("User1", "user1@dlmovie.fr", "user1pwd"), headers)
        restTemplate.exchange("/api/v1/users", HttpMethod.POST, httpEntity, User.class)
    }

    def setup() {
        if(headers == null) setupForAll()
    }

    def "UserController register integration" (pseudo, email, password){

        given:

        HttpEntity<SignUpRequest> httpEntity = new HttpEntity<>(new SignUpRequest(pseudo, email, password), headers)
        ResponseEntity<User> result = restTemplate.exchange("/api/v1/users", HttpMethod.POST, httpEntity, User.class)
        User user = result.getBody()

        expect:
        user.getPassword() == null
        user.getId() != null
        user.getEmail() == email
        user.getPseudo() == pseudo

        where:
        pseudo       | email             | password
        "UserPseudo" | "user@dlmovie.fr" | "userpwd"
    }

    def "UserController login integration" (pseudo, email, password, success){

        given:
        // LOGIN
        HttpEntity<LoginRequest> httpEntityLogin = new HttpEntity<>(new LoginRequest(pseudo, password), headers)
        ResponseEntity<Map> response = restTemplate.exchange("/api/v1/users/login",
                HttpMethod.POST, httpEntityLogin, Map.class)

        expect:
        (response.getStatusCodeValue() == 200) == success

        where:
        pseudo    | email               | password      | success
        "User1"   | "user1@dlmovie.fr"  | "user1pwd"    | true
        "User1"   | "user1@dlmovie.fr"  | "user1pw"     | false
    }

    def "UserController getUser fail" (){

        when:
        ResponseEntity<User> response = restTemplate.exchange("/api/v1/users/" + 1,
                HttpMethod.GET, new HttpEntity<>(headers), User.class)

        then:
        response.getStatusCodeValue() != 200

    }

    def "UserController getUser success" (){

        given:
        HttpEntity<SignUpRequest> httpEntity = new HttpEntity<>(
                new SignUpRequest("UserPseudo", "user@dlmovie.fr", "userpwd"), headers)
        ResponseEntity<User> result = restTemplate.exchange("/api/v1/users", HttpMethod.POST, httpEntity, User.class)
        User user = result.getBody()

        when:
        ResponseEntity<User> response = restTemplate.exchange("/api/v1/users/" + user.getId(),
                HttpMethod.GET, new HttpEntity<>(headers), User.class)

        then:
        response.getStatusCodeValue() != 200

    }
}
