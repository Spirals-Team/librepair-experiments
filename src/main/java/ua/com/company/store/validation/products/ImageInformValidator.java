package ua.com.company.store.validation.products;

import ua.com.company.store.validation.ValidatorAbstract;

/**
 * Created by Владислав on 13.12.2017.
 */
public class ImageInformValidator extends ValidatorAbstract {
    @Override
    public boolean validateInput(String... inputText) {
        String input1 = inputText[4];
        String input2 = inputText[5];
        boolean result = true;
        if (input1.isEmpty() || input1.contains("<script>") && input2.isEmpty() || input2.contains("<script>")){
            result = false;
        }
        return result;

    }
}
