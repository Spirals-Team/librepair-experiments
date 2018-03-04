package pl.wasper.bandmanagement.event.dto.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class EventDtoValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {

    }
}
