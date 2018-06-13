
package org.rookit.dm.test.generator;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;

import org.rookit.test.generator.Generator;

@SuppressWarnings("javadoc")
public final class RookitGeneratorModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<Generator<String>>() {
        }).annotatedWith(IdGenerator.class).to(new TypeLiteral<Generator<String>>() {
        }).in(Singleton.class);
    }
}
