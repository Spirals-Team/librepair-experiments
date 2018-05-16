package io.joaopinheiro.userservice.service.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UserUpdateErrorException extends RuntimeException {

    public UserUpdateErrorException(){
        super("The User ID cannot be updated");
    }
}
