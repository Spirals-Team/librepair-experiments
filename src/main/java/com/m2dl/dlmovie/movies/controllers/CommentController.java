package com.m2dl.dlmovie.movies.controllers;

import com.m2dl.dlmovie.movies.domain.Comment;
import com.m2dl.dlmovie.movies.domain.Movie;
import com.m2dl.dlmovie.movies.requests.CommentRequest;
import com.m2dl.dlmovie.movies.requests.MarkRequest;
import com.m2dl.dlmovie.movies.services.CommentService;
import com.m2dl.dlmovie.movies.services.MovieService;
import com.m2dl.dlmovie.users.domain.User;
import com.m2dl.dlmovie.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/comments")
public class CommentController {

    private CommentService commentService;

    private UserService userService;

    private MovieService movieService;

    @Autowired
    public CommentController(CommentService commentService, UserService userService, MovieService movieService) {
        this.commentService = commentService;
        this.userService = userService;
        this.movieService = movieService;
    }

    @GetMapping
    public List<Comment> index() {
        return commentService.findAll();
    }

    @PostMapping
    public Comment save(@RequestBody CommentRequest commentRequest) {
        User user = userService.getById(commentRequest.getUserId());
        Movie movie = movieService.get(commentRequest.getMovieId());
        Comment comment = new Comment(commentRequest.getText());
        comment.setMovie(movie);
        comment.setUser(user);
        return commentService.addComment(comment);
    }


}
