package up.god.micro.request;

import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@EndPoint
@Path("/api")
public class SyncModeActor {

    @Path("request/sync")
    @GET
    public String sayHello() {
        return "Hello Sync Mode.";
    }
}