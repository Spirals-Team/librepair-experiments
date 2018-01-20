package ru.curriculum.service.user.validation;

import ru.curriculum.service.user.dto.UserDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, UserDTO> {

    public void initialize(PasswordConstraint constraint) {
    }

    public boolean isValid(UserDTO userDTO, ConstraintValidatorContext context) {
        if (isNewUser(userDTO)) {
            return null != userDTO.getPassword() && passwordIsValid(userDTO);
        } else {
            return null == userDTO.getPassword() || userDTO.getPassword().isEmpty() || passwordIsValid(userDTO);
        }
    }

    private boolean isNewUser(UserDTO userDTO) {
        return null == userDTO.getId();
    }

    private boolean passwordIsValid(UserDTO userDTO) {
        return 3 <= userDTO.getPassword().length();
    }
}
