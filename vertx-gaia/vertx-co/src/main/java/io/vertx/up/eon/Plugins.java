package io.vertx.up.eon;

import javax.inject.Inject;
import javax.inject.infix.*;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Default plugin applied to vertx
 */
public interface Plugins {

    String INJECT = "inject";

    String SERVER = "server";

    String ERROR = "error";

    String RESOLVER = "resolver";

    String[] DATA = new String[]{
            INJECT, ERROR, SERVER,
            RESOLVER
    };

    // Default infix
    interface Infix {

        String MONGO = "mongo";

        String MYSQL = "mysql";

        String JOOQ = "jooq";

        String RPC = "rpc";

        String REDIS = "redis";

        String SECURE = "secure";

        String LOGGER = "logger";

        Set<String> STANDAND = new HashSet<String>() {
            {
                this.add(MONGO);
                this.add(MYSQL);
                this.add(JOOQ);
                this.add(REDIS);
                this.add(RPC);
                this.add(LOGGER);
            }
        };
    }

    ConcurrentMap<Class<? extends Annotation>, String> INFIX_MAP =
            new ConcurrentHashMap<Class<? extends Annotation>, String>() {
                {
                    this.put(Mongo.class, Infix.MONGO);
                    this.put(MySql.class, Infix.MYSQL);
                    this.put(Jooq.class, Infix.JOOQ);
                    this.put(Rpc.class, Infix.RPC);
                    this.put(Redis.class, Infix.REDIS);
                }
            };

    Set<Class<? extends Annotation>> INJECT_ANNOTATIONS = new HashSet<Class<? extends Annotation>>() {
        {
            this.addAll(INFIX_MAP.keySet());
            this.add(Inject.class);
        }
    };
}
