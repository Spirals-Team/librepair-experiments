package ru.job4j.controller;

import com.google.gson.Gson;
import ru.job4j.Tokenizer;
import ru.job4j.UserRepository;
import ru.job4j.json.AuthJson;
import ru.job4j.model.User;

import javax.security.sasl.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Signin servlet.
 */
public class LoginController extends HttpServlet {

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException, ServletException {
        String body = req.getReader().lines().sequential()
            .reduce(System.lineSeparator(), (accumulator, actual)
                -> accumulator + actual);
        User guest = new Gson().fromJson(body, User.class);

        if (!new UserRepository().isCredential(guest.getEmail(), guest.getPassw())) {
            throw new AuthenticationException(
                String.format("User: %s is unknown.", guest.getEmail()));
        }
        User user = new UserRepository().findByEmailPassw(guest.getEmail(), guest.getPassw());
        resp.setContentType("application/json");
        String json = new Gson().toJson(new AuthJson(
            new Tokenizer().codeEncodeToken(user.getEmail()), user.getId(), 300L));
        PrintWriter wr = new PrintWriter(resp.getOutputStream());
        wr.append(json);
        wr.flush();
    }
}