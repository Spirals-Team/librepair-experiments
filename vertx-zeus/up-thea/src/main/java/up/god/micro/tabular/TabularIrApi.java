package up.god.micro.tabular;

import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@EndPoint
@Path("/api")
public interface TabularIrApi {

    @Path("tabular/{id}")
    @GET
    @Address("ZERO://QUEUE/TABULAR/ID")
    Long get(@PathParam("id") Long id);
}
