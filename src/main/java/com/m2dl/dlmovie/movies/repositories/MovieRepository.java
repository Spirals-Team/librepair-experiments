package com.m2dl.dlmovie.movies.repositories;

import com.m2dl.dlmovie.movies.domain.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long>{
    List<Movie> findAll();
}
