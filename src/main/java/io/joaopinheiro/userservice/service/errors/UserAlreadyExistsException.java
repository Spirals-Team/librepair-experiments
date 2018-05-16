package io.joaopinheiro.userservice.service.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends RuntimeException {

    public static final String USER_ALREADY_EXISTS_MESSAGE = "A User with the following ID already exists : ";

    public UserAlreadyExistsException(Long id){
        super(USER_ALREADY_EXISTS_MESSAGE + id);
    }
}
