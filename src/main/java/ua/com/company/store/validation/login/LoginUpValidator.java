package ua.com.company.store.validation.login;

import ua.com.company.store.validation.ValidatorAbstract;

/**
 * Created by Владислав on 24.12.2017.
 */
public class LoginUpValidator extends ValidatorAbstract {
    @Override
    public boolean validateInput(String... inputText) {
        String input = inputText[0];
        return !(input.isEmpty() || input.contains("<script>"));
    }
}
