package up.god.micro.params;

import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@EndPoint
@Path("/api")
public class FormParamExecutor {

    @Path("param/form")
    @GET
    public String sayFormGet(
            @FormParam("username") final String username,
            @FormParam("password") final String password) {
        return "Hello: GET: " + username + ", your password is: " + password;
    }

    @Path("param/form")
    @POST
    public String sayFormPost(
            @FormParam("username") final String username,
            @FormParam("password") final String password) {
        return "Hello: Post: " + username + ", your password is: " + password;
    }
}
