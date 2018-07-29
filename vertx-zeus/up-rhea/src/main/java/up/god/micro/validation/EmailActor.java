package up.god.micro.validation;

import io.vertx.up.annotations.EndPoint;

import javax.validation.constraints.Email;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@EndPoint
@Path("/api/jsr303")
public class EmailActor {

    @Path("email")
    @GET
    public String sayEmail(
            @Email
            @QueryParam("email") final String email
    ) {
        return "Hi, email = " + email;
    }
}
