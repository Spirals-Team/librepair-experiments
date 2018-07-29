package up.god.micro.fetch;

import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@EndPoint
@Path("/api")
public interface FetchApi {

    @Path("tabular/by/{name}")
    @GET
    @Address("ZERO://QUEUE/BY/NAME")
    String fetchByName(@PathParam("name") String name);
}
