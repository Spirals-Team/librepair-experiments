package pl.wasper.bandmanagement.songbook.dto.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class TagDtoValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {

    }
}
