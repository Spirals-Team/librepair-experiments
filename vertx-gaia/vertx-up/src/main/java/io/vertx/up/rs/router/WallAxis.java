package io.vertx.up.rs.router;

import io.reactivex.Observable;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.AuthHandler;
import io.vertx.ext.web.handler.ChainAuthHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.up.atom.secure.Cliff;
import io.vertx.up.eon.Orders;
import io.vertx.up.rs.Axis;
import io.vertx.up.rs.secure.Bolt;
import io.vertx.up.web.ZeroAnno;
import io.vertx.up.web.failure.AuthenticateEndurer;
import io.vertx.zero.eon.Values;

import java.util.Set;
import java.util.TreeSet;

/**
 * Secure mount
 */
public class WallAxis implements Axis<Router> {

    /**
     * Extract all walls that will be generated route.
     */
    private static final Set<Cliff> WALLS =
            ZeroAnno.getWalls();

    static {
        WALLS.forEach(wall -> {
            // Initialize cliff set
            if (!Pool.WALL_MAP.containsKey(wall.getPath())) {
                Pool.WALL_MAP.put(wall.getPath(), new TreeSet<>());
            }
            // Add cliff instance by path
            Pool.WALL_MAP.get(wall.getPath()).add(wall);
        });
    }

    private transient final Vertx vertx;
    private transient final Bolt bolt;

    public WallAxis(final Vertx vertx) {
        this.vertx = vertx;
        this.bolt = Bolt.get();
    }

    @Override
    public void mount(final Router router) {
        // Session Global for Authorzation
        router.route().order(Orders.SESSION).handler(
                SessionHandler.create(LocalSessionStore.create(this.vertx))
        );
        Pool.WALL_MAP.forEach((path, cliffes) -> {
            // 1. Build Handler
            final AuthHandler handler = this.create(this.vertx, cliffes);
            // 2. Path/Order to set Router
            if (null != handler) {
                router.route(path).order(Orders.SECURE).handler(handler)
                        .failureHandler(AuthenticateEndurer.create());
            }
            // 3. Wall Advanced, For user data filling.
        });
    }

    /**
     * Two mode for handler supported.
     *
     * @param cliffes Cliff in zero system.
     * @return Auth Handler that will be mount to vertx router.
     */
    private AuthHandler create(final Vertx vertx, final Set<Cliff> cliffes) {
        AuthHandler resultHandler = null;
        if (Values.ONE < cliffes.size()) {
            // 1 < handler
            final ChainAuthHandler chain = ChainAuthHandler.create();
            Observable.fromIterable(cliffes)
                    .map(item -> this.bolt.mount(vertx, item))
                    .subscribe(chain::append).dispose();
            resultHandler = chain;
        } else {
            // 1 = handler
            if (!cliffes.isEmpty()) {
                final Cliff cliff = cliffes.iterator().next();
                resultHandler = this.bolt.mount(vertx, cliff);
            }
        }
        return resultHandler;
    }
}
