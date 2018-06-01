package com.m2dl.dlmovie.movies.services;

import com.m2dl.dlmovie.movies.domain.Movie;
import com.m2dl.dlmovie.movies.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie create(Movie movie) {
        return movieRepository.save(movie);
    }

    public List<Movie> getAll() {
        return movieRepository.findAll();
    }

    public Movie get(Long id) {
        return movieRepository.findOne(id);
    }

    public void delete(Long id) {
        movieRepository.delete(id);
    }

    public Movie update(Long id, Movie movie) {
        movie.setId(id);
        return movieRepository.save(movie);
    }
}
