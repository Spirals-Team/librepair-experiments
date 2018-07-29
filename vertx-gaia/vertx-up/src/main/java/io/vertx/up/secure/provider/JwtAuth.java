package io.vertx.up.secure.provider;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.jwt.JWTOptions;

import java.util.function.Function;

public interface JwtAuth extends AuthProvider {

    static JwtAuth create(final Vertx vertx,
                          final JWTAuthOptions config,
                          final Function<JsonObject, Future<Boolean>> fnVerify) {
        return new JwtAuthProvider(vertx, config, fnVerify);
    }

    static JwtAuth create(final Vertx vertx,
                          final JWTAuthOptions config) {
        return new JwtAuthProvider(vertx, config, null);
    }

    String generateToken(JsonObject data, JWTOptions options);

    default String generateToken(final JsonObject claims) {
        return this.generateToken(claims, new JWTOptions());
    }
}
