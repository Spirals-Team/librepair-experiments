package up.god.micro.validation;

import io.vertx.up.annotations.EndPoint;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@EndPoint
@Path("/api/jsr303")
public class AssertActor {
    @Path("assert")
    @GET
    public String sayAssert(
            @AssertTrue
            @QueryParam("male") final Boolean isMale,
            @AssertFalse
            @QueryParam("female") final Boolean isFemale) {
        return "Hi, Lang, the parameters is 'male' = " + isMale +
                ", 'female' = " + isFemale;
    }
}
