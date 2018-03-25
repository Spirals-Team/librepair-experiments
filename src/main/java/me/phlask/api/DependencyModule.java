package me.phlask.api;

import com.google.inject.AbstractModule;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import me.phlask.api.router.RouteHandler;
import me.phlask.api.router.TapRouter;
import me.phlask.api.service.TapService;

public class DependencyModule extends AbstractModule {
    private final Vertx vertx;
    private final JsonObject config;
    private final Router router;

    public DependencyModule(Vertx vertx, JsonObject config) {
        this.vertx = vertx;
        this.config = config;
        this.router = Router.router(vertx);
    }

    public Router getRouter() {
        return router;
    }

    @Override
    protected void configure() {
        bind(Router.class).toInstance(router);
        bind(TapService.class);
        bind(RouteHandler.class).to(TapRouter.class);
    }
}
