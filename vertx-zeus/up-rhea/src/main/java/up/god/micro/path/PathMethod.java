package up.god.micro.path;

import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@EndPoint
public class PathMethod {

    @Path("/api/path/method")
    @GET
    public String sayBoth() {
        return "Hi, welcome to path both ( Method Only )";
    }
}
