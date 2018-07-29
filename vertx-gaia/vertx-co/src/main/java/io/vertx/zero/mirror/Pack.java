package io.vertx.zero.mirror;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.vertx.core.impl.ConcurrentHashSet;
import io.vertx.up.log.Annal;
import io.vertx.zero.eon.Strings;
import io.zero.epic.Ut;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Pack the package to extract classes.
 */
public final class Pack {

    private static final Annal LOGGER = Annal.get(Pack.class);

    private static final Set<Class<?>> CLASSES = new HashSet<>();

    private static final ConcurrentHashSet<String> FORBIDDEN = new ConcurrentHashSet<String>() {
        {
            this.add("java");
            this.add("javax");
            this.add("jdk");
            // Sax & Yaml
            this.add("org.xml");
            this.add("org.yaml");
            // Idea
            this.add("com.intellij");
            // Sun
            this.add("sun");
            this.add("com.sun");
            // Netty
            this.add("io.netty");
            // Rxjava
            this.add("io.reactivex");
            // Jackson
            this.add("com.fasterxml");
            // Logback
            this.add("ch.qos");
            this.add("org.slf4j");
            this.add("org.apache");
            // Vert.x
            this.add("io.vertx.core");
            this.add("io.vertx.spi");
            // Asm
            this.add("org.ow2");
            this.add("org.objectweb");
            this.add("com.esotericsoftware");
            // Hazelcast
            this.add("com.hazelcast");
            // Glassfish
            this.add("org.glassfish");
            // Junit
            this.add("org.junit");
            this.add("junit");
            // Hamcrest
            this.add("org.hamcrest");
        }
    };

    private Pack() {
    }

    public static Set<Class<?>> getClasses(final Predicate<Class<?>> filter,
                                           final String... zeroScans) {
        if (CLASSES.isEmpty()) {
            if (0 < zeroScans.length) {
                CLASSES.addAll(multiClasses(zeroScans, filter));
            } else {
                final Package[] packages = Package.getPackages();
                final Set<String> packageDirs = new HashSet<>();
                for (final Package pkg : packages) {
                    final String pending = pkg.getName();
                    final boolean skip = FORBIDDEN.stream().anyMatch(pending::startsWith);
                    if (!skip) {
                        packageDirs.add(pending);
                    }
                }
                // Fix big issue of current classpath scan, Must put . of classpath into current scan path.
                packageDirs.add(Strings.DOT);
                LOGGER.info(Info.PACKAGES, String.valueOf(packageDirs.size()),
                        String.valueOf(packages.length));
                CLASSES.addAll(multiClasses(packageDirs.toArray(new String[]{}), filter));
            }
        }
        return CLASSES;
    }

    @SuppressWarnings("unused")
    private static Set<Class<?>> singleClasses(
            final String[] packageDir,
            final Predicate<Class<?>> filter) {
        return Observable.fromArray(packageDir)
                .map(pkgName -> PackScanner.getClasses(filter, pkgName))
                .reduce(new HashSet<Class<?>>(), Ut::combineSet)
                .blockingGet();
    }

    private static Set<Class<?>> multiClasses(
            final String[] packageDir,
            final Predicate<Class<?>> filter) {
        // Counter
        final Set<PackThread> references = new HashSet<>();
        final Disposable disposable = Observable.fromArray(packageDir)
                .map(item -> new PackThread(item, filter))
                .map(item -> Ut.addThen(references, item))
                .subscribe(Thread::start);

        // Wait
        final Set<Class<?>> result = new HashSet<>();
        try {
            for (final PackThread item : references) {
                item.join();
            }
            // Collect all the classes
            for (final PackThread thread : references) {
                result.addAll(thread.getClasses());
            }
            if (!disposable.isDisposed()) {
                disposable.dispose();
            }
        } catch (final InterruptedException ex) {
            LOGGER.jvm(ex);
        } catch (final Throwable ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
