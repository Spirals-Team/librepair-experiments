package ru.job4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.dto.PostDto;
import ru.job4j.model.Post;
import ru.job4j.service.PostService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller to list advertisements.
 */
@RestController
public class ListPostsController {

    @Autowired
    private PostService service;

    @GetMapping(value = "/list", produces = "application/json;charset=UTF-8")
    public List<PostDto> getPostsCollection() {
        List<Post> posts = service.findAll();
        return posts.stream()
            .map(Post::convertPost).collect(Collectors.toList());
    }
}
