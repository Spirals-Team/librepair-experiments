package up.god.micro.tabular;

import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@EndPoint
@Path("/api")
public interface DbApi {

    @Path("native/{type}")
    @GET
    @Address("ZERO://QUEUE/NATIVE/GET")
    String sayDb(@PathParam("type") String type);
}
