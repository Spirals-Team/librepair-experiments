package up.god.micro.agent;

import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.EndPoint;
import io.vertx.up.annotations.Ipc;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@EndPoint
@Path("/api")
public interface SimpleApi {

    @Path("ipc/stream/agent1/{name}")
    @GET
    @Ipc(to = "RPC://IPC/NODE/AGENT1", name = "ipc-hecate")
    JsonObject saySimple(@PathParam("name") String name);
}
