package com.m2dl.dlmovie.movies.service

import com.m2dl.dlmovie.movies.domain.Movie
import com.m2dl.dlmovie.movies.repositories.MovieRepository
import com.m2dl.dlmovie.movies.services.MovieService
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class MovieServiceTest extends Specification {

    def setup() {

    }

    def "MovieService register" (title, description, date, director){
        given:
        def movie = new Movie()
        def movieRepository = Stub(MovieRepository)
        def movieService = new MovieService(movieRepository)
        movieRepository.save(movie) >> {
                Movie movie1 -> movie1.setId(new Long(1))
                movie1
        }
        def movieRegistered = movieService.register(movie)

        expect:
        movie == movieRegistered
        movieRegistered.getId() == new Long(1)

        where:
        title     | description | date       | director
        "Title"   | ""          | new Date() | "real"
    }

    def "MovieService findAll" (title, description, date, director){
        given:
        def movie = new Movie()
        def movieRepository = Stub(MovieRepository)
        def movieService = new MovieService(movieRepository)
        movieRepository.findAll() >> {
                List<Movie> movies = new ArrayList<Movie>()
            movies.add(movie)
            movies
        }
        def movies = movieService.findAll()

        expect:
        movies.contains(movie)

        where:
        title     | description | date       | director
        "Title"   | ""          | new Date() | "real"
    }

}
