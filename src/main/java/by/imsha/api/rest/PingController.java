package by.imsha.api.rest;

import by.imsha.domain.City;
import by.imsha.domain.Ping;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alena Misan
 */


@RestController
@RequestMapping(value = "/ping1")
public class PingController extends AbstractRestHandler {

    public static Ping pingObject = new Ping("ping");

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/xml"})
    @ResponseStatus(HttpStatus.OK)

    public @ResponseBody Resource<Ping> ping(HttpServletRequest request, HttpServletResponse response) {
        log.info("ping-o execution...");

        return new Resource<Ping>(new Ping("1231312"));
    }



}
