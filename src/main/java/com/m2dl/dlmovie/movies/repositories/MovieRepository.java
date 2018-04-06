package com.m2dl.dlmovie.movies.repositories;

import com.m2dl.dlmovie.movies.domain.Movie;

import java.util.List;

/**
 * Created by Julie on 30/03/2018.
 */
public interface MovieRepository {
    Movie save(Movie movie);
    List<Movie> findAll();
}
