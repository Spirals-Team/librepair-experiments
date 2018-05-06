package pl.hycom.ip2018.searchengine.googlesearch.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal Server Error")
public class GoogleSearchException extends Exception {

    public GoogleSearchException() {
    }

    public GoogleSearchException(String message) {
        super("GoogleSearchException" + message);
    }
}
