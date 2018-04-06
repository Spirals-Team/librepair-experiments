package com.m2dl.dlmovie.users.services

import com.m2dl.dlmovie.DlmovieApplication
import com.m2dl.dlmovie.users.domain.User
import com.m2dl.dlmovie.users.repositories.UserRepository
import com.m2dl.dlmovie.users.services.UserService
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class UserServiceTest extends Specification {

    def setup() {

    }

    def "UserService register" (pseudo, email, password){
        given:
        def user = new User()
        def userRepository = Stub(UserRepository)
        def userService = new UserService(userRepository)
        userRepository.save(user) >> {
            User user1 -> user1.setId(1)
            user1
        }
        def userRegistered = userService.register(user)

        expect:
        user == userRegistered
        userRegistered.getId() != null

        where:
        pseudo    | email   | password
        "Pseudo1" | "e@e.e" | "password"
    }


}