package ua.com.company.store.validation.products;

import ua.com.company.store.validation.ValidatorAbstract;

/**
 * Created by Владислав on 13.12.2017.
 */
public class TitleValidator extends ValidatorAbstract {
    @Override
    public boolean validateInput(String... inputText) {
        String input = inputText[0];
        boolean result = true;
        if (input.isEmpty() || input.contains("<script>")){
            result = false;
        }
        return result;

    }
}
