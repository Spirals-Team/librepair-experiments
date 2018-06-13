
package org.rookit.dm.test;

import static org.mockito.Mockito.mock;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import org.rookit.api.dm.album.factory.AlbumFactory;
import org.rookit.api.dm.artist.factory.ArtistFactory;
import org.rookit.api.dm.factory.RookitFactories;
import org.rookit.api.dm.genre.factory.GenreFactory;
import org.rookit.api.dm.play.factory.PlaylistFactory;
import org.rookit.api.dm.track.factory.TrackFactory;
import org.rookit.test.AbstractUnitTest;

@SuppressWarnings("javadoc")
public class DataModelTestFactoryTest extends AbstractUnitTest<DataModelTestFactory> {

    private static Injector INJECTOR = Guice.createInjector(DataModelTestFactory.getModule(), new AbstractModule() {

        @Override
        protected void configure() {
            final TrackFactory mockedTrackFactory = mock(TrackFactory.class);
            final PlaylistFactory mockedPlaylistFactory = mock(PlaylistFactory.class);
            final GenreFactory mockedGenreFactory = mock(GenreFactory.class);
            final ArtistFactory mockedArtistFactory = mock(ArtistFactory.class);
            final AlbumFactory mockedAlbumFactory = mock(AlbumFactory.class);

            bind(RookitFactories.class).toInstance(mock(RookitFactories.class));
            bind(TrackFactory.class).toInstance(mockedTrackFactory);
            bind(PlaylistFactory.class).toInstance(mockedPlaylistFactory);
            bind(GenreFactory.class).toInstance(mockedGenreFactory);
            bind(ArtistFactory.class).toInstance(mockedArtistFactory);
            bind(AlbumFactory.class).toInstance(mockedAlbumFactory);
        }
    });

    @Override
    public DataModelTestFactory createTestResource() {
        return INJECTOR.getInstance(DataModelTestFactory.class);
    }

}
