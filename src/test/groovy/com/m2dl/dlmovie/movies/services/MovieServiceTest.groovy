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

    def "MovieService create" (title, description, date, director){
        given:
        def movie = new Movie()
        def movieRepository = Stub(MovieRepository)
        def movieService = new MovieService(movieRepository)
        movieRepository.save(movie) >> {
                Movie movie1 -> movie1.setId(new Long(1))
                movie1
        }
        def movieRegistered = movieService.create(movie)

        expect:
        movie == movieRegistered
        movieRegistered.getId() == new Long(1)

        where:
        title     | description | date       | director
        "Title"   | ""          | new Date() | "real"
    }

    def "MovieService getAll" (title, description, date, director){
        given:
        def movie = new Movie()
        def movieRepository = Stub(MovieRepository)
        def movieService = new MovieService(movieRepository)
        movieRepository.findAll() >> {
                List<Movie> movies = new ArrayList<Movie>()
            movies.add(movie)
            movies
        }
        def movies = movieService.getAll()

        expect:
        movies.contains(movie)

        where:
        title     | description | date       | director
        "Title"   | ""          | new Date() | "real"
    }

    def "MovieService getOne" (){
        given:
        MovieRepository movieRepository = Mock()
        def movieService = new MovieService(movieRepository)

        when:
        movieService.get(0L)

        then:
        1 * movieRepository.findOne(0L);
    }

    def "MovieService delete" (){
        given:
        MovieRepository movieRepository = Mock()
        def movieService = new MovieService(movieRepository)

        when:
        movieService.delete(0L)

        then:
        1 * movieRepository.delete(0L);
    }

    def "MovieService update" (){
        given:
        MovieRepository movieRepository = Mock()
        def movieService = new MovieService(movieRepository)
        def movie = new Movie("t1", "",  new Date(), "dir")

        when:
        movieService.update(0L, movie)

        then:
        1 * movieRepository.save(movie);
    }

}
