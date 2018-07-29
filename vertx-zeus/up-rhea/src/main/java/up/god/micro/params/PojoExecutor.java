package up.god.micro.params;

import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.BodyParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@EndPoint
@Path("/api")
public class PojoExecutor {

    @POST
    @Path("param/pojo")
    public JsonUser sayUser(
            @BodyParam final JsonUser user
    ) {
        return user;
    }

    @POST
    @Path("param/pojos")
    public List<JsonUser> sayUsers(
            @BodyParam final Set<JsonUser> users
    ) {
        return new ArrayList<>(users);
    }
}
