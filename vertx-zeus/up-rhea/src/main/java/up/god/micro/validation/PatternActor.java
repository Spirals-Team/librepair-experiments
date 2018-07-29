package up.god.micro.validation;

import io.vertx.up.annotations.EndPoint;

import javax.validation.constraints.Pattern;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@EndPoint
@Path("/api/jsr303")
public class PatternActor {

    @GET
    @Path("pattern")
    public String sayPattern(
            @Pattern(regexp = "^lang[0-9]+$")
            @QueryParam("pattern") final String pattern
    ) {
        return "Hi, Pattern = " + pattern;
    }
}
