package com.m2dl.dlmovie.users.controllers;

import com.m2dl.dlmovie.config.security.UserPrincipal
import com.m2dl.dlmovie.users.requests.FavoriteRequest;
import com.m2dl.dlmovie.users.services.UserService;
import org.springframework.boot.test.context.SpringBootTest;
import spock.lang.Specification;


@SpringBootTest
public class FavoriteControllerTest extends Specification{

    def "FavoriteController addFavorite" (){
        given:
        UserService userService = Mock()
        def user = new UserPrincipal(1, "", "", "", null)
        def favoriteController = new FavoriteController(userService)
        def request = new FavoriteRequest(1L);

        when:
        favoriteController.addFavorite(user, request)

        then:
        1 * userService.addFavorite(!null, !null)
    }

    def "FavoriteController deleteFavorite" (){
        given:
        UserService userService = Mock()
        def user = new UserPrincipal(1, "", "", "", null)
        def favoriteController = new FavoriteController(userService)

        when:
        favoriteController.deleteFavorite(user, 1L)

        then:
        1 * userService.deleteFavorite(!null, !null)
    }

    def "FavoriteController getAll" (){
        given:
        UserService userService = Mock()
        def user = new UserPrincipal(1, "", "", "", null)
        def favoriteController = new FavoriteController(userService)

        when:
        favoriteController.getAll(user)

        then:
        1 * userService.getFavorites(!null)
    }
}
