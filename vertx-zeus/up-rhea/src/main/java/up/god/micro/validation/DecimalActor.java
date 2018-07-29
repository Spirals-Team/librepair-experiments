package up.god.micro.validation;

import io.vertx.up.annotations.EndPoint;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@EndPoint
@Path("/api/jsr303")
public class DecimalActor {

    @Path("decimal")
    @GET
    public String sayDecimal(
            @DecimalMin("0.3")
            @QueryParam("min") final Double min,
            @DecimalMax("0.7")
            @QueryParam("max") final Double max
    ) {
        return "Hi, min = " + min + ", max = " + max;
    }
}
