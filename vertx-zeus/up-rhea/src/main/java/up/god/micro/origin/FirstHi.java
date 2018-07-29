package up.god.micro.origin;

import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@EndPoint
@Path("/api")
public class FirstHi {

    @GET
    @Path("hi")
    public String hi(@QueryParam("name") final String name) {
        return null == name ?
                "Hi, Input your name" :
                "Hi " + name + ", welcome to Origin";
    }
}
