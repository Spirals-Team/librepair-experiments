package ru.job4j.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.filter.SalesPlatformUserPrincipal;
import ru.job4j.model.Post;
import ru.job4j.model.User;
import ru.job4j.service.PostService;

import javax.naming.AuthenticationException;
import java.security.Principal;

/**
 * Edit post (advertisement) controller.
 */
@RestController
public class EditPostController {

    @Autowired
    private PostService service;

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/editad")
    public void editPost(Principal principal,
                         @RequestParam("postad") final String postad)
        throws AuthenticationException {
        SalesPlatformUserPrincipal platformUserPrincipal =
            (SalesPlatformUserPrincipal) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        User user = platformUserPrincipal.getUser();
        if (null == user) {
            throw new IllegalStateException("User is null");
        }
        Gson gson = new GsonBuilder()
            .setDateFormat("yyyy.MM.dd 'at' HH:mm:ss").create();
        Post post = gson.fromJson(postad, Post.class);
        service.editPostAd(user, post);
    }
}

