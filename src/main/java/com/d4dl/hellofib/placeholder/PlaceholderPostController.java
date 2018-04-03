package com.d4dl.hellofib.placeholder;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 *
 * A <a href="https://spring.io/understanding/HATEOAS">HATEOS</a> REST endpoing that returns post elements
 * r etrieved from <a target="_blank" href="https://jsonplaceholder.typicode.com/posts">JSONPlaceholder posts</a>
 *
 * The actual URI of each post is at
 * <a target="_blank" href="https://jsonplaceholder.typicode.com">JSONPlaceholder</a> so the selfrel link points
 * to it.  The Placeholder endpoint is, as its name implies, a placeholder for the entities at that address.
 */
@RestController
public class PlaceholderPostController {

    public static final String PLACEHOLDER_POST_URL = "https://jsonplaceholder.typicode.com/posts";

    /**
     * Default constructor
     */
    public PlaceholderPostController() {
    }

    /**
     * Retrieve all the Post elements from
     * <a target="_blank" href="https://jsonplaceholder.typicode.com/posts">JSONPlaceholder posts</a> convert them
     * to HATEOS entities that will be converted to HATEOS JSON objects by spring.
     * @return HATEOS HttpEntities that will be serialized to JSON objects.
     */
    @GetMapping("/placeholder-posts")
    public HttpEntity<List<Post>> getPlaceholderPosts() {
        ResponseEntity<List<Post>> response = new RestTemplate().exchange(PLACEHOLDER_POST_URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<Post>>() {});
        List<Post> posts = response.getBody();

        for (Post post : posts) {
            Link selfLink = new Link(PLACEHOLDER_POST_URL + "/" + post.getWrappedId()).withSelfRel();
            post.add(selfLink);
        }

        return new ResponseEntity(posts, HttpStatus.OK);
    }
}
