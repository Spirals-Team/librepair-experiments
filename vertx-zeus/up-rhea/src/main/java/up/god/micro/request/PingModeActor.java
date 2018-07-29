package up.god.micro.request;

import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@EndPoint
@Path("/api")
public class PingModeActor {

    @Path("request/ping")
    @GET
    public void check() {
        System.out.println("Hello, I'm working well.");
    }

    @Path("request/ping-false")
    @GET
    public boolean checkStatus() {
        System.out.println("Hello, I'm working well and return false.");
        return false;
    }
}
