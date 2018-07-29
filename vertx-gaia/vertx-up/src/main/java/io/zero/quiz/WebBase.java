package io.zero.quiz;

import io.vertx.core.http.HttpServerOptions;
import io.vertx.up.atom.agent.Event;
import io.vertx.up.rs.Axis;
import io.vertx.up.rs.Extractor;
import io.vertx.up.rs.config.EventExtractor;
import io.vertx.up.rs.router.EventAxis;
import io.vertx.up.rs.router.RouterAxis;
import io.vertx.up.web.ZeroGrid;
import io.zero.epic.Ut;
import io.zero.quiz.web.WebTestBase;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class WebBase extends WebTestBase {

    private static final ConcurrentMap<Integer, HttpServerOptions>
            SERVERS = ZeroGrid.getServerOptions();
    private static final ConcurrentMap<Integer, AtomicInteger>
            LOGS = new ConcurrentHashMap<Integer, AtomicInteger>() {
        {
            SERVERS.forEach((port, option) -> {
                this.put(port, new AtomicInteger(0));
            });
        }
    };

    protected Event extract(final Class<?> clazz, final String name) {
        final Extractor<Set<Event>> extractor = Ut.singleton(EventExtractor.class);
        final Set<Event> events = extractor.extract(clazz);
        final List<Event> list = events.stream()
                .filter(item -> item.getAction().getName().equals(name))
                .collect(Collectors.toList());
        return list.get(0);
    }

    @SuppressWarnings("unchecked")
    protected void start() {
        /** 1.Get the default HttpServer Options **/
        SERVERS.forEach((port, option) -> {
            /** 4.Call router hub to mount commont **/
            Axis huber = Ut.singleton(RouterAxis.class);
            huber.mount(this.router);
            /** 5.Call route hub to mount defined **/
            huber = Ut.singleton(EventAxis.class);
            huber.mount(this.router);
        });
    }
}
