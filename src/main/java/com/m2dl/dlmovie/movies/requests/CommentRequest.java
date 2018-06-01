package com.m2dl.dlmovie.movies.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = CommentRequestDeserializer.class)
public class CommentRequest {

    private String text;

    private Long movieId;

    private Long userId;

    public CommentRequest(String text, Long movieId, Long userId) {
        this.text = text;
        this.movieId = movieId;
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public Long getMovieId() {
        return movieId;
    }

    public Long getUserId() {
        return userId;
    }
}
