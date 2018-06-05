package pl.hycom.ip2018.searchengine.localsearch.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal Server Error")
public class LocalSearchException extends Exception {

    public LocalSearchException() {
    }

    public LocalSearchException(String message) {
        super("LocalSearchException" + message);
    }
}
