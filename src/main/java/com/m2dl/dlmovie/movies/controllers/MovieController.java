package com.m2dl.dlmovie.movies.controllers;

import com.m2dl.dlmovie.movies.domain.Movie;
import com.m2dl.dlmovie.movies.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public Movie create(@RequestBody Movie movie) {
        return movieService.create(movie);
    }

    @GetMapping
    public List<Movie> getAll() {
        return movieService.getAll();
    }

    @GetMapping("/{id}")
    public Movie get(@PathVariable Long id) { return movieService.get(id); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { movieService.delete(id); }

    @PutMapping("/{id}")
    public Movie update(@PathVariable Long id, @RequestBody Movie movie) { return movieService.update(id, movie); }

}
