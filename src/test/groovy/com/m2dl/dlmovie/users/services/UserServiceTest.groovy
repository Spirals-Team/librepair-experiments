package com.m2dl.dlmovie.users.services

import com.m2dl.dlmovie.users.domain.Role
import com.m2dl.dlmovie.users.domain.RoleName
import com.m2dl.dlmovie.users.domain.User
import com.m2dl.dlmovie.users.exceptions.DBException
import com.m2dl.dlmovie.users.repositories.RoleRepository
import com.m2dl.dlmovie.users.repositories.UserRepository
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

@SpringBootTest
class UserServiceTest extends Specification {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder()

    def setup() {

    }

    def "UserService register" (pseudo, email, password){
        given:
        def user = new User(pseudo, email, password)
        def userRepository = Stub(UserRepository)
        def roleRepository = Stub(RoleRepository)
        def userService = new UserService(userRepository, null, roleRepository, passwordEncoder, null, null)
        userRepository.save(user) >> {
            User user1 -> user1.setId(1)
            user1
        }
        roleRepository.findByName(RoleName.ROLE_USER) >> {
            roleName1 ->
                return new Optional<Role>(new Role(RoleName.ROLE_USER))
        }
        def userRegistered = userService.register(user)

        expect:
        user == userRegistered
        userRegistered.getPassword() != null
        !userRegistered.getPassword().equals(password)
        def cryptedPwd = passwordEncoder.encode(password)
        passwordEncoder.matches(password, cryptedPwd)
        userRegistered.getId() != null

        where:
        pseudo    | email   | password
        "Pseudo1" | "e@e.e" | "password"
    }

    def "UserService DBException when roles data not initialize" () {

        given: // a DB not initialize with Roles data
        def roleRepository = Stub(RoleRepository)
        roleRepository.findByName(RoleName.ROLE_USER) >> {
            Optional.empty()
        }
        def userService = new UserService(null, null, roleRepository, passwordEncoder, null, null)

        when:
        userService.register(new User("abcdef", "a@a", "abcdef"))

        then:
        thrown DBException
    }



}