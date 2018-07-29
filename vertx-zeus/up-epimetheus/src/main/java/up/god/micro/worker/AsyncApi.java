package up.god.micro.worker;

import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@EndPoint
@Path("/api")
public interface AsyncApi {

    @Path("ipc/stream/async1/{name}")
    @GET
    @Address("ZERO://IPC/NODE/ASYNC1")
    String sayEnvelop(@PathParam("name") String name);
}
