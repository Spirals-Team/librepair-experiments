package up.god.micro.redis;

import io.vertx.redis.RedisClient;
import io.vertx.up.annotations.EndPoint;

import javax.inject.infix.Redis;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("")
@EndPoint
public class RedisActor {
    @Redis
    private transient RedisClient client;

    @Path("/api/redis")
    @GET
    public String sayRedis(final String name) {
        System.out.println(this.client);
        return "Redis, " + name;
    }
}
