package up.god.micro.path;

import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@EndPoint
@Path("/api")
public class PathBoth {

    @Path("/path/both")
    @GET
    public String sayBoth() {
        return "Hi, welcome to path both ( Class / Method )";
    }
}
