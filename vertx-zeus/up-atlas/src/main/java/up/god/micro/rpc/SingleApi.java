package up.god.micro.rpc;

import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@EndPoint
@Path("/api")
public interface SingleApi {

    @Path("rpc/{name}")
    @GET
    @Address("ZERO://RPC/FIRST")
    String sayHello(@PathParam("name") String name);
}
