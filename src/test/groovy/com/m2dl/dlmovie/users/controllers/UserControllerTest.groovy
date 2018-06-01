package com.m2dl.dlmovie.users.controllers

import com.m2dl.dlmovie.config.security.UserPrincipal
import com.m2dl.dlmovie.config.security.requests.SignUpRequest
import com.m2dl.dlmovie.users.domain.User
import com.m2dl.dlmovie.users.services.UserService
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class UserControllerTest extends Specification {

    def setup() {

    }

    def "UserController register" (){
        given:
        def signUpRequest = new SignUpRequest(null, null, null)
        UserService userService = Mock()
        def userController = new UserController(userService)

        when:
        userController.register(signUpRequest)

        then:
        1 * userService.register(!null)
    }

    def "UserController getUser" (){
        given:
        UserService userService = Mock()
        def user = new UserPrincipal(1, "", "", "", null)
        def userController = new UserController(userService)

        when:
        userController.getUser(user, 1)

        then:
        1 * userService.getById(!null)
    }
}
