package io.vertx.up.boot;

import io.vertx.core.Vertx;
import io.vertx.tp.etcd.center.EtcdData;
import io.vertx.up.Launcher;
import io.vertx.up.annotations.Up;
import io.vertx.up.log.Annal;
import io.vertx.up.web.Runner;
import io.vertx.up.web.ZeroLauncher;
import io.vertx.up.web.anima.DetectScatter;
import io.vertx.up.web.anima.InfixScatter;
import io.vertx.up.web.anima.PointScatter;
import io.vertx.up.web.anima.Scatter;
import io.vertx.zero.exception.RpcPreparingException;
import io.vertx.zero.exception.UpClassArgsException;
import io.vertx.zero.exception.UpClassInvalidException;
import io.vertx.zero.mirror.Anno;
import io.zero.epic.Ut;
import io.zero.epic.fn.Fn;

import java.lang.annotation.Annotation;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Vertx Application start launcher for api gateway.
 * It's only used in Micro Service mode.
 */
public class DansApplication {

    private static final Annal LOGGER = Annal.get(DansApplication.class);

    private transient final Class<?> clazz;

    private transient ConcurrentMap<String, Annotation> annotationMap = new ConcurrentHashMap<>();

    private DansApplication(final Class<?> clazz) {
        // Must not null
        Fn.outUp(
                null == clazz,
                LOGGER,
                UpClassArgsException.class, this.getClass());
        this.clazz = clazz;
        this.annotationMap = Anno.get(clazz);
        // Must be invalid
        Fn.outUp(
                !this.annotationMap.containsKey(Up.class.getName()),
                LOGGER,
                UpClassInvalidException.class, this.getClass(), clazz.getName());
    }

    public static void run(final Class<?> clazz, final Object... args) {
        Fn.shuntRun(() -> {
            // Run vertx application.
            new DansApplication(clazz).run(args);
        }, LOGGER);
    }

    private void run(final Object... args) {
        // Check etcd server status, IPC Only
        Fn.outUp(!EtcdData.enabled(),
                LOGGER, RpcPreparingException.class, this.getClass());

        final Launcher<Vertx> launcher = Ut.singleton(ZeroLauncher.class);

        launcher.start(vertx -> {
            /** 1.Find Agent for deploy **/
            Runner.run(() -> {
                final Scatter<Vertx> scatter = Ut.singleton(PointScatter.class);
                scatter.connect(vertx);
            }, "gateway-runner");
            /** 2.Find Worker for deploy **/
            Runner.run(() -> {
                final Scatter<Vertx> scatter = Ut.singleton(DetectScatter.class);
                scatter.connect(vertx);
            }, "detect-runner");
            /** 3.Initialize Infix **/
            Runner.run(() -> {
                // Infix For Api Gateway
                final Scatter<Vertx> scatter = Ut.singleton(InfixScatter.class);
                scatter.connect(vertx);
            }, "infix-afflux-runner");
        });
    }
}
