package up.god.micro.params;

import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@EndPoint
@Path("/api")
public class QueryParamExecutor {

    @Path("param/query")
    @GET
    public String sayQuery(
            @QueryParam("name") final String name) {
        return "Hello: Get " + name;
    }

    @Path("param/query")
    @POST
    public String sayPostQuery(
            @QueryParam("name") final String name) {
        return "Hello: Post " + name;
    }

    @Path("param/query-encode")
    @GET
    public String sayEncodeQuery(
            @QueryParam("name") final String name) {
        return "Hello: Encoded " + name;
    }
}
