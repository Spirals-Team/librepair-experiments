
package org.rookit.dm.test.generator.album;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.album.TypeAlbum;
import org.rookit.api.dm.album.TypeRelease;
import org.rookit.api.dm.album.factory.AlbumFactory;
import org.rookit.api.dm.album.key.AlbumKey;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.track.Track;
import org.rookit.dm.test.generator.IdGenerator;
import org.rookit.dm.test.generator.genre.AbstractGenreableGenerator;
import org.rookit.test.generator.Generator;

import javax.annotation.Generated;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.rookit.test.utils.GeneratorUtils.randomlyConsume;

final class AlbumGenerator extends AbstractGenreableGenerator<Album> {

    private static final int TRACK_COUNT = 10;

    private static final String PREFIX = "AlbumTest";

    private final AlbumFactory albumFactory;
    private final Generator<Artist> artists;
    private final Generator<TypeRelease> typeReleases;
    private final Generator<Track> trackGenerator;
    private final Generator<String> stringGenerator;
    private final Generator<byte[]> byteArrayGenerator;

    @Inject
    private AlbumGenerator(final AlbumFactory albumFactory,
            final Generator<Artist> artists,
            final Generator<TypeRelease> typeReleases,
            @IdGenerator final Generator<String> idGenerator,
            final Generator<Duration> durationGenerator,
            final Generator<LocalDate> pastGenerator,
            final Generator<Genre> genreGenerator,
            final Generator<Long> longGenerator,
            final Generator<Track> trackGenerator,
            final Generator<String> stringGenerator,
            final Generator<byte[]> byteArrayGenerator) {
        super(idGenerator, durationGenerator, pastGenerator, genreGenerator, longGenerator);
        this.albumFactory = albumFactory;
        this.artists = artists;
        this.typeReleases = typeReleases;
        this.trackGenerator = trackGenerator;
        this.stringGenerator = stringGenerator;
        this.byteArrayGenerator = byteArrayGenerator;
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public boolean equals(final Object object) {
        if (object instanceof AlbumGenerator) {
            if (!super.equals(object)) {
                return false;
            }
            final AlbumGenerator that = (AlbumGenerator) object;
            return Objects.equals(this.albumFactory, that.albumFactory)
                    && Objects.equals(this.artists, that.artists)
                    && Objects.equals(this.typeReleases, that.typeReleases)
                    && Objects.equals(this.trackGenerator, that.trackGenerator)
                    && Objects.equals(this.stringGenerator, that.stringGenerator)
                    && Objects.equals(this.byteArrayGenerator, that.byteArrayGenerator);
        }
        return false;
    }

    @SuppressWarnings("boxing")
    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.albumFactory, this.artists, this.typeReleases, this.trackGenerator,
                this.stringGenerator,
                this.byteArrayGenerator);
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("super", super.toString())
                .add("TRACK_COUNT", TRACK_COUNT)
                .add("PREFIX", PREFIX)
                .add("albumFactory", this.albumFactory)
                .add("artists", this.artists)
                .add("typeReleases", this.typeReleases)
                .add("trackGenerator", this.trackGenerator)
                .add("stringGenerator", this.stringGenerator)
                .add("byteArrayGenerator", this.byteArrayGenerator)
                .toString();
    }

    @Override
    protected Album constructRandom() {
        final Set<Artist> artists = this.artists.createRandomSet();
        final String title = PREFIX + this.stringGenerator.createRandom();

        final AlbumKey key = mock(AlbumKey.class);
        when(key.type()).thenReturn(TypeAlbum.ARTIST);
        when(key.releaseType()).thenReturn(this.typeReleases.createRandom());
        when(key.title()).thenReturn(title);
        when(key.artists()).thenReturn(artists);

        return this.albumFactory.create(key);
    }

    @Override
    protected void fillGenreable(final Album album) {
        randomlyConsume(album::setCover, this.byteArrayGenerator);
        randomlyConsume(album::setReleaseDate, getPastGenerator());

        final String discName = this.stringGenerator.createRandom();
        for (int i = 1; i < TRACK_COUNT; i++) {
            album.addTrack(this.trackGenerator.createRandom(), i, discName);
        }
    }

}
