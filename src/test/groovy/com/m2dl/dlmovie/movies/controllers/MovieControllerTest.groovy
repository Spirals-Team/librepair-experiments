package com.m2dl.dlmovie.movies.controllers

import com.m2dl.dlmovie.movies.domain.Movie
import com.m2dl.dlmovie.movies.services.MovieService
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import javax.rmi.CORBA.Stub

@SpringBootTest
class MovieControllerTest extends Specification{

    def "MovieController create" (){
        given:
        def movie = new Movie()
        MovieService movieService = Mock()
        def movieController = new MovieController(movieService)

        when:
        movieController.create(movie)

        then:
        1 * movieService.create(movie);
    }

    def "MovieController getAll" (){
        given:
        MovieService movieService = Mock()
        def movieController = new MovieController(movieService)

        when:
        movieController.getAll()

        then:
        1 * movieService.getAll();
    }

    def "MovieController get" (){
        given:
        MovieService movieService = Mock()
        def movieController = new MovieController(movieService)

        when:
        movieController.get(0)

        then:
        1 * movieService.get(0);
    }

    def "MovieController delete" (){
        given:
        MovieService movieService = Mock()
        def movieController = new MovieController(movieService)

        when:
        movieController.delete(0)

        then:
        1 * movieService.delete(0);
    }

    def "MovieController update" (){
        given:
        MovieService movieService = Mock()
        def movieController = new MovieController(movieService)
        def movie = new Movie("t1", "",  new Date(), "dir")

        when:
        movieController.update(0, movie)

        then:
        1 * movieService.update(0, movie);
    }

}
