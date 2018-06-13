
package org.rookit.dm.test.generator.genre;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;

import org.rookit.api.dm.genre.Genre;
import org.rookit.test.generator.Generator;

@SuppressWarnings("javadoc")
public class GenreGeneratorModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(new TypeLiteral<Generator<Genre>>() {
        }).to(GenreGenerator.class).in(Singleton.class);
    }

}
