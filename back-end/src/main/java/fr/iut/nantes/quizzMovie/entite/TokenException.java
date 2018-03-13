package fr.iut.nantes.quizzMovie.entite;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by E155722N on 12/03/18.
 */
public class TokenException extends Exception {

    /**
     * Constructor
     */
    public TokenException() {
        super("Invalid Token");
    }

    /**
     * @return the error message in a json
     */
    public String getJsonMsg(){
        return "{ \"error\" : \" Invalid Token\" }";
    }

    /**
     * @return the responseEntity in case of tokenException
     */
    public ResponseEntity getResponseEntity(){
        return new ResponseEntity<>(getJsonMsg(),HttpStatus.UNAUTHORIZED);
    }

}
