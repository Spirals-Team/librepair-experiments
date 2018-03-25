package me.phlask.api;

import com.google.inject.Binding;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.sync.SyncVerticle;
import io.vertx.ext.web.Router;
import me.phlask.api.router.RouteHandler;

import java.util.List;

import static io.vertx.ext.sync.Sync.fiberHandler;

public class PhlaskService extends SyncVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhlaskService.class);
    private static final int PORT = 8080;

    @Override
    public void start() {
        DependencyModule dependencyModule = new DependencyModule(vertx, config());
        registerRouteHandler(Guice.createInjector(dependencyModule));
        Router router = dependencyModule.getRouter();

        vertx.createHttpServer()
                .requestHandler(fiberHandler(router::accept))
                .exceptionHandler(fiberHandler(PhlaskService::exceptionHandler))
                .listen(PORT);
    }

    private static void registerRouteHandler(Injector inject) {
        TypeLiteral<RouteHandler> handlers = TypeLiteral.get(RouteHandler.class);
        List<Binding<RouteHandler>> routers = inject.findBindingsByType(handlers);

        for (Binding<RouteHandler> binding : routers) {
            binding.getProvider().get().addRoutes();
        }
    }

    private static void exceptionHandler(Throwable event) {
        LOGGER.error("System exception", event.getMessage());
    }

}