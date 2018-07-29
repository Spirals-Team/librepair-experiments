package up.wall;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.web.handler.AuthHandler;
import io.vertx.up.aiki.Ux;
import io.vertx.up.annotations.Authenticate;
import io.vertx.up.annotations.Wall;
import io.vertx.up.secure.Security;
import io.vertx.up.secure.handler.JwtOstium;
import io.vertx.up.secure.provider.JwtAuth;

@Wall(value = "jwt", path = "/api/secure/*")
@SuppressWarnings("all")
public class JwtWall implements Security {

    @Authenticate
    public AuthHandler authenticate(final Vertx vertx,
                                    final JsonObject config) {
        return JwtOstium.create(JwtAuth.create(vertx, new JWTAuthOptions(config), this::verify));
    }

    @Override
    public Future<JsonObject> store(final JsonObject filter) {
        final JsonObject seed = new JsonObject()
                .put("username", filter.getString("username"))
                .put("id", filter.getString("_id"));
        final String token = Ux.Jwt.token(seed);
        return Ux.Mongo.findOneAndReplace("DB_USER", filter, "token", token);
    }

    @Override
    public Future<Boolean> verify(final JsonObject data) {
        final JsonObject extracted = Ux.Jwt.extract(data);
        final String token = data.getString("jwt");
        final JsonObject filters = new JsonObject()
                .put("_id", extracted.getString("id"))
                .put("token", token);
        return Ux.Mongo.existing("DB_USER", filters);
    }
}
