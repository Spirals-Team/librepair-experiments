package org.rookit.dm.test.generator.play;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;

import org.rookit.api.dm.play.Playlist;
import org.rookit.test.generator.Generator;

@SuppressWarnings("javadoc")
public class PlaylistGeneratorModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(new TypeLiteral<Generator<Playlist>>() {
        }).to(PlaylistGenerator.class).in(Singleton.class);
    }

}
