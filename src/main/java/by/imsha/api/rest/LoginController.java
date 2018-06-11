package by.imsha.api.rest;

import by.imsha.domain.Parish;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alena Misan
 */


@RestController
@RequestMapping(value = "/login/callback")
public class LoginController extends AbstractRestHandler {


    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public void callback(HttpServletRequest request, HttpServletResponse response) {
        log.info("callback execution...");

    }


}
