package ru.job4j.filter;

import ru.job4j.Tokenizer;
import ru.job4j.UserRepository;
import ru.job4j.model.User;

import javax.security.sasl.AuthenticationException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Authorization filter
 */
public class AuthFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse resp, final FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String auth = request.getHeader("Authorization");
        if (request.getRequestURI().contains("/login")) {
            chain.doFilter(req, resp);
        } else {
            if (auth != null) {
                String email = new Tokenizer().codeEncodeToken(auth);
                User user = new UserRepository().findByEmail(email);
                if (user.isEmpty()) {
                    throw new AuthenticationException(
                        String.format("User: %s is unknown.", user.getEmail()));
                }
                req.setAttribute("user", user);
                chain.doFilter(req, resp);
            }
        }
    }

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        //empty method
    }

    @Override
    public void destroy() {
        //empty method
    }
}
