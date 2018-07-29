package up.god.micro.multi;

import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@EndPoint
@Path("/api")
public interface MultiApi {
    @Path("ipc/multi/{name}")
    @GET
    @Address("ZERO://RPC/MULTI")
    String sayHello(@PathParam("name") String name);
}
