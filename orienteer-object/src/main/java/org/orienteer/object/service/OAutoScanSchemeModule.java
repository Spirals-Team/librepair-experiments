package org.orienteer.object.service;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import com.google.inject.persist.PersistService;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import ru.vyarus.guice.persist.orient.db.scheme.SchemeInitializer;
import ru.vyarus.guice.persist.orient.db.scheme.impl.AutoScanSchemeInitializer;
import ru.vyarus.guice.persist.orient.db.scheme.impl.NoOpSchemeInitializer;
import ru.vyarus.guice.persist.orient.db.scheme.initializer.ObjectSchemeInitializer;
import ru.vyarus.guice.persist.orient.db.scheme.initializer.core.ext.ExtensionsDescriptorFactory;
import ru.vyarus.guice.persist.orient.support.AbstractSchemeModule;

import java.util.Set;

/**
 * Guice module for init {@link SchemeInitializer}
 * Need custom module, for allow add packages which contains OrientDB models in Orienteer modules and project based on Orienteer
 * For add your packages with models just use this:
 * {@code
 *      Multibinder<String> binder = Multibinder.newSetBinder(binder(), String.class, Names.named("orient.model.packages"));
 *      binder.addBinding().toInstance("package1");
 *      binder.addBinding().toInstance("package2");
 * }
 * @see <a href="https://github.com/google/guice/wiki/Multibindings">Guice Multibindings</a>
 * @see <a href="https://github.com/xvik/guice-persist-orient#scheme-initialization">Schema initialization</a>
 */
public class OAutoScanSchemeModule extends AbstractModule {

    /**
     * Configure module.
     * Used init code from {@link AbstractSchemeModule}
     */
    @Override
    protected void configure() {
        // prevent usage without main OrientModule
        requireBinding(PersistService.class);

        // init empty set of packages
        Multibinder.newSetBinder(binder(), String.class, Names.named("orient.model.packages"));

        // required explicit binding to inject correct injector instance (instead of always root injector)
        bind(ExtensionsDescriptorFactory.class);
    }

    /**
     * Join set of packages by ','.
     * Need for provide {@link SchemeInitializer} in {@link OAutoScanSchemeModule#provideSchemaInitializer(String, Provider, ObjectSchemeInitializer)}
     * For inject packages used Guice Multibindings
     * @param packages {@link Set<String>} packages
     * @return string which contains packages separated by ',' or empty string if packages are empty
     */
    @Provides
    @Singleton
    @Named("orient.model.package")
    public String provideOrientModelPackage(@Named("orient.model.packages") Set<String> packages) {
        return packages.isEmpty() ? "" : Joiner.on(',').join(packages);
    }

    /**
     * Create {@link SchemeInitializer} depends on appPkgs.
     * @param appPkgs {@link String} string which contains packages with OrientDB models, separated by ','
     * @param dbProvider {@link Provider<OObjectDatabaseTx>} provider for {@link OObjectDatabaseTx}
     * @param schemeInitializer {@link ObjectSchemeInitializer}
     * @return {@link SchemeInitializer} if appPkgs is not empty returns {@link AutoScanSchemeInitializer} otherwise {@link NoOpSchemeInitializer}
     */
    @Provides
    @Singleton
    public SchemeInitializer provideSchemaInitializer(@Named("orient.model.package") final String appPkgs,
                                                      final Provider<OObjectDatabaseTx> dbProvider,
                                                      final ObjectSchemeInitializer schemeInitializer) {
        if (!Strings.isNullOrEmpty(appPkgs)) {
            return new AutoScanSchemeInitializer(appPkgs, dbProvider, schemeInitializer);
        }
        return new NoOpSchemeInitializer();
    }
}
