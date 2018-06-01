package com.m2dl.dlmovie.movies.services

import com.m2dl.dlmovie.movies.domain.Movie
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration
@SpringBootTest
class MovieServiceIntegrationTest extends Specification{

    @Autowired
    private MovieService movieService;

    def "Test save"() {
        given: "a movie"
            def movie = new Movie("t1", "",  new Date(), "dir")
        when: "we save the movie"
            def movieSaved = movieService.create(movie)
        then: "the returned movie is the saved one"
            movieSaved.getId() != null
            movieSaved.getTitle() == movie.getTitle()
            movieSaved.getDate() == movie.getDate()
            movieSaved.getDescription() == movie.getDescription()
            movieSaved.getDirector() == movie.getDirector()
    }

    def "Test get all"() {
        given: "the number of movies"
            def numberOfMovies = movieService.getAll().size()
        when: "we add 3 movies"
            movieService.create(new Movie("t1", "",  new Date(), "dir"))
            movieService.create(new Movie("t2", "",  new Date(), "dir"))
            movieService.create(new Movie("t3", "",  new Date(), "dir"))
        then: "we have 3 more movies"
            movieService.getAll().size() == numberOfMovies + 3
    }

    def "Test get"() {
        given: "a movie saved"
            def movieSaved = movieService.create(new Movie("t1", "",  new Date(), "dir"))
        when: "we get the movie"
            def movie = movieService.get(movieSaved.getId())
        then: "we have the good one"
            movieSaved.getId() == movie.getId()
            movieSaved.getTitle() == movie.getTitle()
            movieSaved.getDate().getDateString() == movie.getDate().getDateString()
            movieSaved.getDescription() == movie.getDescription()
            movieSaved.getDirector() == movie.getDirector()
    }

    def "Test delete"() {
        given: "a movie added and the number of movies"
            def movie = movieService.create(new Movie("t1", "",  new Date(), "dir"))
            def numberOfMovies = movieService.getAll().size()
        when: "we delete the movie"
            movieService.delete(movie.getId())
        then: "we have 1 less movie"
            movieService.get(movie.getId()) == null
            movieService.getAll().size() == numberOfMovies - 1
    }

    def "Test update"() {
        given: "a movie added"
        def movie = movieService.create(new Movie("t1", "",  new Date(), "dir"))
        when: "we update the movie"
        def newDirector = "newDirector"
        movie.setDirector(newDirector)
        movieService.update(movie.getId(), movie)
        then: "we have the movie updated"
        movieService.get(movie.getId()).getDirector() == newDirector
    }


}
