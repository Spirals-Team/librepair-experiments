package ru.job4j.service;

import ru.job4j.model.Post;
import ru.job4j.model.User;

import javax.naming.AuthenticationException;
import java.util.List;

public interface PostService {

    Long addPostAd(User user, Post post, List<byte[]> pics);

    void editPostAd(User user, Post post) throws AuthenticationException;

    List<Post> findAll();

    List<Post> findTodaysPosts();

    List<Post> findWithPics();

    List<Post> findByCarManufacture(String manufacture);
}
