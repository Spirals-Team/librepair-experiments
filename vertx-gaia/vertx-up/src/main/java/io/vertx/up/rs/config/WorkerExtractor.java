package io.vertx.up.rs.config;

import io.vertx.core.DeploymentOptions;
import io.vertx.up.annotations.Worker;
import io.vertx.up.log.Annal;
import io.vertx.up.rs.Extractor;
import io.zero.epic.Ut;
import io.zero.epic.fn.Fn;

import java.lang.annotation.Annotation;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Worker verticle deployment
 */
public class WorkerExtractor implements Extractor<DeploymentOptions> {

    private static final Annal LOGGER = Annal.get(WorkerExtractor.class);

    private static final ConcurrentMap<Class<?>, DeploymentOptions>
            OPTIONS = new ConcurrentHashMap<>();

    @Override
    public DeploymentOptions extract(final Class<?> clazz) {
        Fn.safeNull(() -> LOGGER.info(Info.WORKER_HIT, clazz.getName()), clazz);
        return Fn.pool(OPTIONS, clazz, () -> this.transform(clazz));
    }

    private DeploymentOptions transform(final Class<?> clazz) {
        final Annotation annotation = clazz.getDeclaredAnnotation(Worker.class);
        // 1. Instance
        final int instances = Ut.invoke(annotation, Key.INSTANCES);
        final boolean ha = Ut.invoke(annotation, Key.HA);
        final String group = Ut.invoke(annotation, Key.GROUP);
        // 2. Record Log information
        final DeploymentOptions options = new DeploymentOptions();
        options.setHa(ha);
        options.setInstances(instances);
        options.setIsolationGroup(group);
        // 3. Disabled worker fetures.
        options.setWorker(true);
        LOGGER.info(Info.VTC_OPT, instances, group, ha, options.toJson());
        return options;
    }
}
