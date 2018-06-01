package com.m2dl.dlmovie.movies.controllers;

import com.m2dl.dlmovie.movies.domain.Mark;
import com.m2dl.dlmovie.movies.domain.Movie;
import com.m2dl.dlmovie.movies.requests.MarkRequest;
import com.m2dl.dlmovie.movies.services.MarkService;
import com.m2dl.dlmovie.movies.services.MovieService;
import com.m2dl.dlmovie.users.domain.User;
import com.m2dl.dlmovie.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/marks")
public class MarkController {

    private MarkService markService;

    private MovieService movieService;

    private UserService userService;

    @Autowired
    public MarkController(MarkService markService, MovieService movieService, UserService userService) {
        this.markService = markService;
        this.movieService = movieService;
        this.userService = userService;
    }

    @GetMapping
    public List<Mark> getAll(){
        return markService.getAll();
    }

    @PostMapping
    public Mark save(@RequestBody MarkRequest markRequest){
        User user = userService.getById(markRequest.getUserId());
        Movie movie = movieService.get(markRequest.getMovieId());
        Mark mark = new Mark(markRequest.getValue(), new Date());
        mark.setMovie(movie);
        mark.setUser(user);
        return markService.createMark(mark);
    }

}
