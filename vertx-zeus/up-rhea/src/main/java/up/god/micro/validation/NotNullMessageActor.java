package up.god.micro.validation;

import io.vertx.up.annotations.EndPoint;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@EndPoint
@Path("/api/jsr303")
public class NotNullMessageActor {

    @Path("notnull/message")
    @GET
    public String verify(
            @NotNull(message = "Sorry, the system require you input the 'username'")
            @QueryParam("username") final String username,
            @NotNull(message = "Hi, you have not provide your password, did you forget ?")
            @QueryParam("password") final String password
    ) {
        return "Hi, " + username + ", Your password is: " + password;
    }
}
