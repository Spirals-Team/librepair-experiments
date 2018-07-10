package ru.job4j.controller;

import com.google.gson.Gson;
import ru.job4j.PostRepository;
import ru.job4j.model.Post;
import ru.job4j.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AddPostController extends HttpServlet {

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getAttribute("user");
        String postad = req.getParameter("postad");
        Post post = new Gson().fromJson(postad, Post.class);
        List<byte[]> pics = readPics(req.getParts());
        new PostRepository().addPostAd(user, post, pics);
    }

    private List<byte[]> readPics(final Collection<Part> parts) throws IOException {
        List<byte[]> pics = new ArrayList<>();
        for (Part p : parts) {
            if (!p.getName().equalsIgnoreCase("postad")) {
                byte[] pic;
                try (InputStream in = p.getInputStream()) {
                    pic = new byte[in.available()];
                    in.read(pic);
                }
                pics.add(pic);
            }
        }
        return pics;
    }
}

