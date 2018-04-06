package com.m2dl.dlmovie.movies.services;

import com.m2dl.dlmovie.movies.domain.Movie;
import com.m2dl.dlmovie.movies.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Julie on 09/03/2018.
 */
@Service
public class MovieService {

    private MovieRepository movieRepository;

    public MovieService() {
    }

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie register(Movie movie) {
        return movieRepository.save(movie);
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

}
