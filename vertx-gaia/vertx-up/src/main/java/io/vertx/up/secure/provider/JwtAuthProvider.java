package io.vertx.up.secure.provider;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.AsyncMap;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.auth.jwt.impl.JWTUser;
import io.vertx.ext.jwt.JWT;
import io.vertx.ext.jwt.JWTOptions;
import io.vertx.up.aiki.Ux;
import io.vertx.up.exception.*;
import io.vertx.up.log.Annal;
import io.zero.epic.fn.Fn;

import java.util.Collections;
import java.util.function.Function;

public class JwtAuthProvider implements JwtAuth {
    private static final JsonArray EMPTY_ARRAY = new JsonArray();
    private static final String AUTH_POOL = "JWT_AUTH_TOKEN_POOL";
    private static final Annal LOGGER = Annal.get(JwtAuthProvider.class);
    private final JWT jwt;
    private final String permissionsClaimKey;
    private final JWTOptions jwtOptions;
    private final Function<JsonObject, Future<Boolean>> executor;
    private transient AsyncMap<String, Boolean> authorizeMap;

    public JwtAuthProvider(final Vertx vertx, final JWTAuthOptions config, final Function<JsonObject, Future<Boolean>> executor) {
        this.executor = executor;
        this.permissionsClaimKey = config.getPermissionsClaimKey();
        this.jwtOptions = config.getJWTOptions();
        // File reading here.
        this.jwt = Ux.Jwt.create(config, vertx.fileSystem()::readFileBlocking);
        vertx.sharedData().<String, Boolean>getAsyncMap(AUTH_POOL, res -> {
            if (res.succeeded()) {
                LOGGER.debug(Info.MAP_INITED, AUTH_POOL);
                this.authorizeMap = res.result();
            }
        });
    }

    @Override
    public void authenticate(final JsonObject authInfo, final Handler<AsyncResult<User>> resultHandler) {
        final String token = authInfo.getString("jwt");
        // Set token as key for async map extraction;
        if (null == this.authorizeMap) {
            // Async map initialized failure, execute common validation flow.
            this.authenticate(authInfo).setHandler(this.authenticate(token, resultHandler));
        } else {
            // Get data from authorizeMap
            this.authorizeMap.get(token, res -> {
                if (null != res && null != res.result() && res.result()) {
                    // Get data from cache.
                    LOGGER.info(Info.MAP_HIT, token, res.result());
                    this.authorize(authInfo).setHandler(this.authenticate(token, resultHandler));
                } else {
                    // Couldn't getNull data from cache
                    LOGGER.debug(Info.MAP_MISSING, token);
                    this.authenticate(authInfo).setHandler(this.authenticate(token, resultHandler));
                }
            });
        }
    }

    private Handler<AsyncResult<User>> authenticate(final String token, final Handler<AsyncResult<User>> handler) {
        return (user -> this.authorizeMap.get(token, res -> {
            if (!(null != res && null != res.result() && res.result())) {
                this.authorizeMap.put(token, Boolean.TRUE, result -> {
                    LOGGER.debug(Info.MAP_PUT, token, Boolean.TRUE);
                    handler.handle(Future.succeededFuture(user.result()));
                });
            } else {
                handler.handle(Future.succeededFuture(user.result()));
            }
        }));
    }

    private Future<User> authenticate(final JsonObject authInfo) {
        return Fn.getSemi(null == this.executor, null,
                () -> this.authorize(authInfo),
                () -> this.executor.apply(authInfo).compose(result -> {
                    if (result) {
                        return this.authorize(authInfo);
                    } else {
                        return Future.failedFuture(new _401JwtExecutorException(this.getClass(), authInfo.getString("jwt")));
                    }
                }));
    }

    private Future<User> authorize(final JsonObject authInfo) {
        try {
            final JsonObject payload = this.jwt.decode(authInfo.getString("jwt"));
            if (this.jwt.isExpired(payload, this.jwtOptions)) {
                return Future.failedFuture(new _401JwtExpiredException(this.getClass(), payload));
            }
            if (this.jwtOptions.getAudience() != null) {
                final JsonArray target;
                if (payload.getValue("aud") instanceof String) {
                    target = (new JsonArray()).add(payload.getValue("aud", ""));
                } else {
                    target = payload.getJsonArray("aud", EMPTY_ARRAY);
                }

                if (Collections.disjoint(this.jwtOptions.getAudience(), target.getList())) {
                    return Future.failedFuture(new _401JwtAudientException(this.getClass(), Json.encode(this.jwtOptions.getAudience())));
                }
            }

            if (this.jwtOptions.getIssuer() != null && !this.jwtOptions.getIssuer().equals(payload.getString("iss"))) {
                return Future.failedFuture(new _401JwtIssuerException(this.getClass(), payload.getString("iss")));
            }
            return Future.succeededFuture(new JWTUser(payload, this.permissionsClaimKey));
        } catch (final RuntimeException var5) {
            return Future.failedFuture(new _500JwtRuntimeException(this.getClass(), var5));
        }
    }

    @Override
    public String generateToken(final JsonObject claims, final JWTOptions options) {
        final JsonObject _claims = claims.copy();
        if (options.getPermissions() != null && !_claims.containsKey(this.permissionsClaimKey)) {
            _claims.put(this.permissionsClaimKey, new JsonArray(options.getPermissions()));
        }
        return this.jwt.sign(_claims, options);
    }
}
