package com.lala.app.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.lala.app.domain.Movie;
import com.lala.app.repository.MovieRepository;
import com.lala.app.repository.MovieRepositoryFactory;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@RestController
public class MovieApi {

    @Autowired
    MovieRepository movieRepository;

    @RequestMapping("/")
    public String index() {
        return "This is non rest, just checking if everything works.";
    }

    @RequestMapping(value = "/film/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Movie getMovie(@PathVariable("id") int id) throws SQLException {
        movieRepository = MovieRepositoryFactory.getInstance();
        return movieRepository.getById(id);
    }

    @RequestMapping(value = "/film", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Movie> getMovies() throws SQLException {
        movieRepository = MovieRepositoryFactory.getInstance();
        List<Movie> movies = new LinkedList<Movie>();
        for (Movie m : movieRepository.getAll()) {
                movies.add(m);
        }
        return movies;
    }

    @RequestMapping(value = "/film", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long addMovie(@RequestBody Movie m) {
        movieRepository = MovieRepositoryFactory.getInstance();
        return new Long(movieRepository.addMovie(m));
    }

    @RequestMapping(value = "/film/{id}", method = RequestMethod.DELETE)
    public Long deleteMovie(@PathVariable("id") Long id) throws SQLException {
        movieRepository = MovieRepositoryFactory.getInstance();
        return new Long(movieRepository.deleteMovie(id));
    }

    @RequestMapping(value = "/film/{id}", method = RequestMethod.PUT)
    public Long updateMovie(@PathVariable("id") Long id, @RequestBody Movie m) throws SQLException {
        movieRepository = MovieRepositoryFactory.getInstance();
        return new Long(movieRepository.updateMovie(id, m));
    }

}