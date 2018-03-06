package eu.europeana.identifier.rest.exceptions;

import eu.europeana.itemization.IdentifierError;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ymamakis on 2/25/16.
 */
@Controller
public class IdentifierExceptionMapper {

    @ExceptionHandler(IdentifierException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView toResponse(IdentifierException e) {
        IdentifierError error = new IdentifierError();
        error.setMessage(e.getMessage());
        ModelAndView mav = new ModelAndView();
        mav.addObject("error", e.getMessage());
        return mav;
    }
}
