package com.m2dl.dlmovie.users.controllers

import com.m2dl.dlmovie.users.domain.User
import com.m2dl.dlmovie.users.repositories.UserRepository
import com.m2dl.dlmovie.users.services.UserService
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class UserControllerTest extends Specification {

    def setup() {

    }

    def "UserController register" (pseudo, email, password){
        given:
        def user = new User()
        def userService = Stub(UserService)
        def userController = new UserController(userService)
        userService.save(user) >> {
            User user1 -> user1.setId(1)
            user1
        }
        def userRegistered = userController.register(user)

        expect:
        user == userRegistered
        userRegistered.getId() != null

        where:
        pseudo    | email   | password
        "Pseudo1" | "e@e.e" | "password"
    }

}
