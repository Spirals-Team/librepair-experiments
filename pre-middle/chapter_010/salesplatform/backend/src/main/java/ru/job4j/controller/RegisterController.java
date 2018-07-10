package ru.job4j.controller;

import com.google.gson.Gson;
import ru.job4j.Tokenizer;
import ru.job4j.dao.UserDaoImpl;
import ru.job4j.model.Role;
import ru.job4j.model.User;
import ru.job4j.json.AuthJson;
import ru.job4j.json.UserJson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RegisterController extends HttpServlet {

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException, ServletException {
        String body = req.getReader().lines().sequential()
            .reduce(System.lineSeparator(), (accumulator, actual)
                -> accumulator + actual);
        User user = new Gson().fromJson(body, UserJson.class).getUser();
        user.setRole(new Role().setId(1L));
        Long id = new UserDaoImpl().create(user);
        resp.setContentType("application/json");
        String json = new Gson().toJson(new AuthJson(
            new Tokenizer().codeEncodeToken(user.getEmail()), id, 300L));
        PrintWriter wr = new PrintWriter(resp.getOutputStream());
        wr.append(json);
        wr.flush();
    }
}
