package up.god.micro.validation;

import io.vertx.up.annotations.EndPoint;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@EndPoint
@Path("/api/jsr303")
public class NotNullTplActor {

    @GET
    @Path("notnull/tpl")
    public String tpl(
            @NotNull(message = "{notnull.username}")
            @QueryParam("username") final String username,
            @NotNull(message = "{notnull.password}")
            @QueryParam("password") final String password
    ) {
        return "Hi, " + username + ", your password is " + password;
    }
}
