package ru.job4j.controller;

import com.google.gson.Gson;
import ru.job4j.PostRepository;
import ru.job4j.model.Post;
import ru.job4j.model.User;

import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditPostController extends HttpServlet {

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getAttribute("user");
        String postad = req.getParameter("postad");
        Post post = new Gson().fromJson(postad, Post.class);
        try {
            new PostRepository().editPostAd(user, post);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
    }
}

