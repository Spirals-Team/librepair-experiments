package ua.com.company.store.validation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Владислав on 27.11.2017.
 */
public abstract class ValidatorAbstract {
    private ValidatorAbstract nextValidatorAbstract;
    public void setNextValidator(ValidatorAbstract validator){
    nextValidatorAbstract = validator;
    }
    public boolean validate(String ... text){
       boolean result = validateInput(text);
       if (!result) return false;
        if (nextValidatorAbstract !=null){
           result = nextValidatorAbstract.validateInput(text);
        }

        return result;
    }
    public abstract boolean validateInput(String ... inputText);
}
