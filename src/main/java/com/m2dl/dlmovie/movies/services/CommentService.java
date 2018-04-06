package com.m2dl.dlmovie.movies.services;

import com.m2dl.dlmovie.movies.domain.Comment;
import com.m2dl.dlmovie.movies.domain.Movie;
import com.m2dl.dlmovie.movies.repositories.CommentRepository;
import com.m2dl.dlmovie.users.domain.User;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment addComment(User user, Comment comment, Movie movie) {
        comment.setUser(user);
        comment.setMovie(movie);
        commentRepository.save(comment);
        return comment;
    }
}
