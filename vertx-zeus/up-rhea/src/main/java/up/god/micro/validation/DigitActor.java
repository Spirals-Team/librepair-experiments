package up.god.micro.validation;

import io.vertx.up.annotations.EndPoint;

import javax.validation.constraints.Digits;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@EndPoint
@Path("/api/jsr303")
public class DigitActor {

    @Path("digit")
    @GET
    public String sayDigit(
            @Digits(integer = 2, fraction = 2)
            @QueryParam("digit") final Double currency
    ) {
        return "Hi, Currency is " + currency;
    }
}
