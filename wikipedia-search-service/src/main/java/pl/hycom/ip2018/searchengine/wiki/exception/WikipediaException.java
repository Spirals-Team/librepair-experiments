package pl.hycom.ip2018.searchengine.wiki.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal Server Error")
public class WikipediaException extends Exception {

    public WikipediaException() {
    }

    public WikipediaException(String message) {
        super("Wikipedia" + message);
    }
}
