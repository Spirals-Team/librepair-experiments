package com.m2dl.dlmovie.users.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.validation.constraints.NotNull;

@JsonDeserialize(using = FavoriteRequestDeserializer.class)
public class FavoriteRequest {
    @NotNull
    private final Long movieId;

    public FavoriteRequest(Long movieId) {
        this.movieId = movieId;
    }

    public Long getMovieId() {
        return movieId;
    }
}