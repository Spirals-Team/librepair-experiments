package io.vertx.up.web.anima;

import io.vertx.core.Vertx;
import io.vertx.up.atom.agent.Event;
import io.vertx.up.atom.worker.Receipt;
import io.vertx.up.eon.Plugins;
import io.vertx.up.log.Annal;
import io.vertx.up.web.Runner;
import io.vertx.up.web.ZeroAnno;
import io.zero.epic.Ut;
import io.zero.epic.fn.Fn;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * Injection system
 */
public class AffluxScatter implements Scatter<Vertx> {

    private static final Annal LOGGER = Annal.get(AffluxScatter.class);

    private static final ConcurrentMap<Class<?>, ConcurrentMap<String, Class<?>>>
            PENDINGS = ZeroAnno.getPlugins();

    private static final AffluxInfix INJECTOR = AffluxInfix.create(AffluxScatter.class);

    @Override
    public void connect(final Vertx vertx) {
        // Extract all events.
        final Set<Event> events = ZeroAnno.getEvents();
        Ut.itSet(events, (item, index) ->
                Runner.run(() -> this.inject(item.getProxy())
                        , "event-afflux-" + index));

        // Extract all receipts.
        final Set<Receipt> receipts = ZeroAnno.getReceipts();
        Ut.itSet(receipts, (item, index) ->
                Runner.run(() -> this.inject(item.getProxy())
                        , "receipt-afflux-" + index));

        // Extract non - event/receipts Objects
        final Set<Class<?>> injects = ZeroAnno.getInjects();
        Ut.itSet(injects, (item, index) -> Runner.run(() -> {
            // Initialize object
            final Object instance = Ut.singleton(item);
            // Initialize reference
            this.inject(instance);
        }, "injects-afflux-" + index));
    }

    private void inject(final Object proxy, final String key, final Class<?> type) {
        // Field set
        try {
            final Class<?> clazz = proxy.getClass();
            final Field field = clazz.getDeclaredField(key);
            final Object instance;
            if (Plugins.INFIX_MAP.keySet().stream().anyMatch(field::isAnnotationPresent)) {
                // Speicific Annotation
                instance = INJECTOR.inject(field);
            } else {
                // Inject Only
                instance = Ut.singleton(type);
            }
            // Set for field
            if (null != instance) {
                Ut.field(proxy, key, instance);
                // Scan continue for field
                this.inject(instance);
            }
        } catch (final NoSuchFieldException ex) {
            LOGGER.jvm(ex);
        }
    }

    private void inject(final Object proxy) {
        Fn.safeNull(() -> {
            final Class<?> clazz = proxy.getClass();
            if (PENDINGS.containsKey(clazz)) {
                // Scanned in started up
                final ConcurrentMap<String, Class<?>> injections
                        = PENDINGS.get(clazz);
                // Inject field
                injections.forEach((key, type) -> this.inject(proxy, key, type));
            }
        }, proxy);
    }
}
