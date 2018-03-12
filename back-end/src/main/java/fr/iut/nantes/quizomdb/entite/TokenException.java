package fr.iut.nantes.quizomdb.entite;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by E155722N on 12/03/18.
 */
public class TokenException extends Exception {

    public TokenException() {
        super("Invalid Token");
    }

    public String getJsonMsg(){
        return "{ \"error\" : \" Invalid Token\" }";
    }

    public ResponseEntity getResponseEntity(){
        return new ResponseEntity<>(getJsonMsg(),HttpStatus.UNAUTHORIZED);
    }

}
