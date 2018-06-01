package com.m2dl.dlmovie.users.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
class CustomUserDetailsServiceIntegrationTest extends Specification{

    @Autowired
    private CustomUserDetailsService customUserDetailsService

    def "CustomUserDetailsService loadUserById fail" (){

        when:
        customUserDetailsService.loadUserById(500)

        then:
        thrown UsernameNotFoundException


    }

    def "CustomUserDetailsService loadUserByUsername fail" (){

        when:
        customUserDetailsService.loadUserByUsername("")

        then:
        thrown UsernameNotFoundException


    }


}
