package up.god.micro.validation;

import io.vertx.up.annotations.EndPoint;

import javax.validation.constraints.Size;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@EndPoint
@Path("/api/jsr303")
public class SizeActor {

    @Path("size")
    @GET
    public String saySize(
            @Size(min = 1, max = 20)
            @QueryParam("size") final String size
    ) {
        return "Hi, Size = " + size;
    }
}
