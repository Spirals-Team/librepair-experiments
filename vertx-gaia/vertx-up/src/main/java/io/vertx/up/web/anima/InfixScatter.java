package io.vertx.up.web.anima;

import io.reactivex.Observable;
import io.vertx.core.Vertx;
import io.vertx.up.annotations.Plugin;
import io.vertx.up.eon.Plugins;
import io.vertx.up.log.Annal;
import io.vertx.up.web.Runner;
import io.vertx.up.web.ZeroAmbient;
import io.vertx.up.web.ZeroAnno;
import io.vertx.zero.eon.Values;
import io.vertx.zero.exception.PluginSpecificationException;
import io.vertx.zero.marshal.node.Node;
import io.vertx.zero.marshal.node.ZeroLime;
import io.zero.epic.Ut;
import io.zero.epic.fn.Fn;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * Infix initialization
 */
public class InfixScatter implements Scatter<Vertx> {

    private static final Annal LOGGER = Annal.get(InfixScatter.class);

    private static final Node<ConcurrentMap<String, String>> node =
            Ut.singleton(ZeroLime.class);

    private static final Set<Class<?>> PLUGINS = ZeroAnno.getTps();

    private static final InfixPlugin PLUGIN = InfixPlugin.create(InfixScatter.class);

    @Override
    public void connect(final Vertx vertx) {
        final ConcurrentMap<String, Class<?>> wholeInjections = ZeroAmbient.getInjections();
        /** Enabled **/
        final ConcurrentMap<String, Class<?>> enabled =
                Ut.reduce(node.read().keySet(), wholeInjections);
        /** Scan all Infix **/
        final ConcurrentMap<Class<? extends Annotation>, Class<?>> injections =
                Ut.reduce(Plugins.INFIX_MAP, enabled);
        injections.values().stream().forEach(item -> {
            if (null != item && item.isAnnotationPresent(Plugin.class)) {
                final Method method = this.findInit(item);
                Fn.outUp(null == method, LOGGER,
                        PluginSpecificationException.class,
                        this.getClass(), item.getName());
                Fn.safeJvm(() -> method.invoke(null, vertx), LOGGER);
            }
        });
        /** Scan all extension Infix **/
        Observable.fromIterable(wholeInjections.keySet())
                .filter(key -> !Plugins.Infix.STANDAND.contains(key))
                .map(wholeInjections::get)
                .filter(item -> null != item && item.isAnnotationPresent(Plugin.class))
                .subscribe(item -> {
                    final Method method = this.findInit(item);
                    Fn.outUp(null == method, LOGGER,
                            PluginSpecificationException.class,
                            this.getClass(), item.getName());
                    Fn.safeJvm(() -> method.invoke(null, vertx), LOGGER);
                });
        /** After infix inject plugins **/
        Ut.itSet(PLUGINS, (clazz, index) -> Runner.run(() -> {
            /** Instance reference **/
            final Object reference = Ut.singleton(clazz);
            /** Injects scanner **/
            PLUGIN.inject(reference);
        }, "injects-plugin-scannner"));
    }


    /**
     * Check whether clazz has the method of name
     *
     * @param clazz
     * @return
     */
    private Method findInit(final Class<?> clazz) {
        return Fn.getNull(() -> {
            final Method[] methods = clazz.getDeclaredMethods();
            final List<Method> found = Arrays.stream(methods)
                    .filter(item -> "init".equals(item.getName()) && this.validMethod(item))
                    .collect(Collectors.toList());
            return Values.ONE == found.size() ? found.get(Values.IDX) : null;
        }, clazz);
    }

    private boolean validMethod(final Method method) {
        return (void.class == method.getReturnType() || Void.class == method.getReturnType())
                && Modifier.isStatic(method.getModifiers())
                && Modifier.isPublic(method.getModifiers());
    }
}
