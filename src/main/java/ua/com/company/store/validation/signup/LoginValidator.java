package ua.com.company.store.validation.signup;

import ua.com.company.store.validation.ValidatorAbstract;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Владислав on 27.11.2017.
 */
public class LoginValidator extends ValidatorAbstract {

    @Override
    public boolean validateInput(String... inputText) {
        String input = inputText[0];
        return !(input.isEmpty() || input.contains("<script>"));
    }
}
