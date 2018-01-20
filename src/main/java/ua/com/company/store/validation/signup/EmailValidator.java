package ua.com.company.store.validation.signup;

import ua.com.company.store.validation.ValidatorAbstract;

/**
 * Created by Владислав on 29.11.2017.
 */
public class EmailValidator extends ValidatorAbstract {
    @Override
    public boolean validateInput(String... inputText) {
        String input = inputText[2];
        boolean result = true;
        if (input.isEmpty() || input.contains("<script>")){
            result = false;
        }
        return result;

    }
}
