package up.god.micro.worker;

import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@EndPoint
@Path("/api")
public interface ConsumeApi {

    @Path("ipc/stream/consumer1/{name}")
    @GET
    @Address("ZERO://IPC/NODE/WORKER1")
    String sayWorker1(@PathParam("name") String name);

    @Path("ipc/stream/consumer2/{name}")
    @GET
    @Address("ZERO://IPC/NODE/WORKER2")
    String sayWorker2(@PathParam("name") String name);

    @Path("ipc/stream/consumer3/{name}")
    @GET
    @Address("ZERO://IPC/NODE/WORKER3")
    String sayWorker3(@PathParam("name") String name);
}
