package com.m2dl.dlmovie.users.services

import com.m2dl.dlmovie.movies.domain.Movie
import com.m2dl.dlmovie.movies.repositories.MovieRepository
import com.m2dl.dlmovie.users.domain.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
class UserServiceIntegrationTest extends Specification{

    @Autowired
    private UserService userService

    @Autowired
    private MovieRepository movieRepository

    def "UserService getById fail" (){

        when:
        User user = userService.getById(200)

        then:
        user == null
    }

    def "UserService getById success" (){

        given:
        User user = userService.register(new User("abcdef", "a@a", "abcdef"))

        when:
        def userFound = userService.getById(user.getId())

        then:
        userFound != null
    }

    def "UserService add favorite" (){

        given:
        User user = userService.register(new User("name5","b@a","12345"))
        Movie movie = movieRepository.save(new Movie("num","",new Date(),"dir"))

        when:
        userService.addFavorite(user.getId(),movie.getId())

        then:
        User userAfterAdding = userService.getById(user.getId())
        userAfterAdding.getFavorites().contains(movie)
    }

    def "UserService delete favorite" (){

        given:
        User user = userService.register(new User("name5","b@a","12345"))
        Movie movie = movieRepository.save(new Movie("num","",new Date(),"dir"))
        userService.addFavorite(user.getId(),movie.getId())

        when:
        userService.deleteFavorite(user.getId(),movie.getId())

        then:
        User userAfterAdding = userService.getById(user.getId())
        !userAfterAdding.getFavorites().contains(movie)
    }

    def "UserService get all favorite" (){

        given:
        User user = userService.register(new User("name5","b@a","12345"))
        Movie movie = movieRepository.save(new Movie("num","",new Date(),"dir"))
        Movie movie2 = movieRepository.save(new Movie("num2","",new Date(),"dir"))

        userService.addFavorite(user.getId(),movie.getId())
        userService.addFavorite(user.getId(),movie2.getId())

        when:
        Set<Movie> movies = userService.getFavorites(user.getId())

        then:
        movies.contains(movie)
        movies.contains(movie2)
    }

}
