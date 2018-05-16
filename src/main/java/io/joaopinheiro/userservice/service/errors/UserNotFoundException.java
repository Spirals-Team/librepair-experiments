package io.joaopinheiro.userservice.service.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException{

    private final static String USER_NOT_FOUND_MESSAGE = "No User with the following ID was not found : ";

    public UserNotFoundException(Long id){
        super(USER_NOT_FOUND_MESSAGE + id);
    }
}
