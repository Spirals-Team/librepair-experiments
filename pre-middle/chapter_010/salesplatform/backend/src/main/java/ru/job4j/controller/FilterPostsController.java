package ru.job4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.Filter;
import ru.job4j.dto.PostDto;
import ru.job4j.model.Post;
import ru.job4j.service.DispatchFilterService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Get filtered posts controller.
 */
@RestController
public class FilterPostsController {

    @Autowired
    private DispatchFilterService service;

    @GetMapping(value = "/filter", produces = "application/json;charset=UTF-8")
    public List<PostDto> getFilteredPostsCollection(final HttpServletRequest req) {
        ArrayList<String> queryParams = new ArrayList<>(req.getParameterMap().keySet());
        String condition = queryParams.get(0);
        Filter filter = Filter.filterFromName(condition);
        List<Post> posts = service.sent(filter, req.getParameter(condition));
        return posts.stream()
            .map(Post::convertPost).collect(Collectors.toList());
    }
}
