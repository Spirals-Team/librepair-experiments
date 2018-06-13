
package org.rookit.dm.test.generator.album;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;

import java.util.Random;

import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.album.TypeRelease;
import org.rookit.test.generator.EnumGenerator;
import org.rookit.test.generator.Generator;

@SuppressWarnings("javadoc")
public final class AlbumGeneratorModule extends AbstractModule {
    @Provides
    private Generator<TypeRelease> getTypeReleaseGenerator(final Random random) {
        return new EnumGenerator<>(random, TypeRelease.class);
    }

    @Override
    protected void configure() {
        bind(new TypeLiteral<Generator<Album>>() {
        }).to(AlbumGenerator.class).in(Singleton.class);
    }
}
