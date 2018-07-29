package up.god.micro.async;

import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@EndPoint
@Path("/api")
public class JavaDirectActor {
    // String out
    @POST
    @Path("request/java-direct")
    @Address("ZERO://ASYNC/JAVA/DIRECT")
    public String sayHello(
            @QueryParam("age") final int age) {
        return String.valueOf(age);
    }

    // Pojo out
    @POST
    @Path("request/java-pojo")
    @Address("ZERO://ASYNC/JAVA/POJO")
    public JavaJson sayPojo(
            @QueryParam("age") final int age) {
        final JavaJson json = new JavaJson();
        json.setAge(age);
        json.setName("Lang");
        json.setEmail("lang.yu@hpe.com");
        return json;
    }
}
