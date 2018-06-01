package com.m2dl.dlmovie.movies.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = MarkRequestDeserializer.class)
public class MarkRequest {

    private int value;

    private Long movieId;

    private Long userId;

    public MarkRequest(int value, Long movieId, Long userId) {
        this.value = value;
        this.movieId = movieId;
        this.userId = userId;
    }

    public int getValue() {
        return value;
    }

    public Long getMovieId() {
        return movieId;
    }

    public Long getUserId() {
        return userId;
    }

}
