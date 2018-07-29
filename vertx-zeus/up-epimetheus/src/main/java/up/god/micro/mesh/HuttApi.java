package up.god.micro.mesh;

import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@EndPoint
@Path("/api")
public interface HuttApi {

    @Path("ipc/mesh/hutt/{name}")
    @GET
    @Address("ZERO://IPC/NODE/HUTT")
    String sayHutt(@PathParam("name") String name);
}
