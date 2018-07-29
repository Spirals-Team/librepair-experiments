package up.god.micro.validation;

import io.vertx.up.annotations.EndPoint;

import javax.validation.constraints.Null;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@EndPoint
@Path("/api/jsr303")
public class NullActor {

    @Path("null")
    @GET
    public String sayNull(
            @Null(message = "Please do not provide value for username, it's system api")
            @QueryParam("username") final String username
    ) {
        return "Hi, this api is always running by " + username;
    }
}
