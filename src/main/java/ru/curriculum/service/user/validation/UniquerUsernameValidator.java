package ru.curriculum.service.user.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import ru.curriculum.domain.admin.user.repository.UserRepository;
import ru.curriculum.service.user.dto.UserDTO;

// TODO: запихать в одно место с валидацией формы UserDTO
@Component
public class UniquerUsernameValidator {
    @Autowired
    private UserRepository userRepository;

    public void validate(UserDTO userDTO, BindingResult errors) {
        if(null != userRepository.findByUsername(userDTO.getUsername())) {
            errors.addError(new ObjectError("username", "Такой логин уже существует"));
        }
    }
}
