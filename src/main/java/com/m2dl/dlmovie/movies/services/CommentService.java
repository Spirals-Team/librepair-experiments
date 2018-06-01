package com.m2dl.dlmovie.movies.services;

import com.m2dl.dlmovie.movies.domain.Comment;
import com.m2dl.dlmovie.movies.domain.Movie;
import com.m2dl.dlmovie.movies.repositories.CommentRepository;
import com.m2dl.dlmovie.users.domain.User;
import com.m2dl.dlmovie.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    private UserService userService;

    private MovieService movieService;

    @Autowired
    public CommentService (CommentRepository commentRepository, MovieService movieService, UserService userService) {
        this.commentRepository = commentRepository;
        this.movieService = movieService;
        this.userService = userService;
    }

    public Comment addComment(Comment comment) {
        if (comment == null) {
            throw new IllegalArgumentException("Comment must not be null");
        }
        if ( comment.getMovie() == null ) {
            throw new IllegalArgumentException("Movie must not be null");
        }
        if ( comment.getUser() == null ) {
            throw new IllegalArgumentException("User must not be null");
        }

        if ( comment.getMovie().getId() == null ) {
            movieService.create(comment.getMovie());
        }

        if ( comment.getUser().getId() == null ) {
            userService.register(comment.getUser());
        }

        return commentRepository.save(comment);
    }

    public List<Comment> findAll() {
        return (List<Comment>) commentRepository.findAll();
    }

    public Comment remove(Comment comment) {
        commentRepository.delete(comment);
        return comment;
    }
}
