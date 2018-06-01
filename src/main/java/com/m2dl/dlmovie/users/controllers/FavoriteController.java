package com.m2dl.dlmovie.users.controllers;

import com.m2dl.dlmovie.config.security.CurrentUser;
import com.m2dl.dlmovie.config.security.UserPrincipal;
import com.m2dl.dlmovie.movies.domain.Movie;
import com.m2dl.dlmovie.users.requests.FavoriteRequest;
import com.m2dl.dlmovie.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("api/v1/favorites")
public class FavoriteController {

    private final UserService userService;

    @Autowired
    public FavoriteController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity addFavorite(@CurrentUser UserPrincipal currentUser,
                                      @Valid @RequestBody FavoriteRequest favoriteRequest) {
        userService.addFavorite(currentUser.getId(), favoriteRequest.getMovieId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity deleteFavorite(@CurrentUser UserPrincipal currentUser,
                                         @PathVariable("movieId") Long movieId) {
        userService.deleteFavorite(currentUser.getId(), movieId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Set<Movie> getAll(@CurrentUser UserPrincipal currentUser){
        return userService.getFavorites(currentUser.getId());
    }
}
