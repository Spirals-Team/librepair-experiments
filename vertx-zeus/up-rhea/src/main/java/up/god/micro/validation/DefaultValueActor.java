package up.god.micro.validation;

import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@EndPoint
@Path("/api/jsr303")
public class DefaultValueActor {

    @Path("notnull/default")
    @GET
    public String sayDefault(
            @DefaultValue("1")
            @QueryParam("page") final Integer page,
            @DefaultValue("20")
            @QueryParam("size") final Integer size
    ) {
        return "Hi, your default page = " + page + ", size = " + size;
    }
}
