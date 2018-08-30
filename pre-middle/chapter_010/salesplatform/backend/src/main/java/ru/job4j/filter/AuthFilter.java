package ru.job4j.filter;

import com.google.gson.Gson;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.job4j.json.CredentialJson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Filter requests to find credentials.
 */
public class AuthFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request,
                                                final HttpServletResponse response)
        throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest;
        try (BufferedReader reader = request.getReader()) {
            CredentialJson json = new Gson().fromJson(reader, CredentialJson.class);
            authRequest =
                new UsernamePasswordAuthenticationToken(json.getEmail(), json.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
            authRequest = new UsernamePasswordAuthenticationToken("", "");
        }
        setDetails(request, authRequest);
        return getAuthenticationManager().authenticate(authRequest);
    }
}
