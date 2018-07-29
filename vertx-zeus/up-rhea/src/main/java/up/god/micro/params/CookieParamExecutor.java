package up.god.micro.params;

import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@EndPoint
@Path("/api")
public class CookieParamExecutor {

    @Path("param/cookie")
    @GET
    public String sayCookie(
            @CookieParam("cookie-id") final String cookie
    ) {
        return "Hello, Cookie: " + cookie;
    }
}
