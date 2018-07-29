package up.god.micro.params;

import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.*;

@EndPoint
@Path("/api")
public class PathParamExecutor {

    @Path("param/path1/{name}")
    @GET
    public String sayPath(
            @PathParam("name") final String name
    ) {
        return "Hello: Path Get: " + name;
    }

    @Path("param/path1/{name}")
    @POST
    public String sayPath2(
            @PathParam("name") final String name
    ) {
        return "Hello: Path Post: " + name;
    }

    @Path("param/path1/{name}")
    @PUT
    public String sayPath3(
            @PathParam("name") final String name
    ) {
        return "Hello: Path Put: " + name;
    }
}
