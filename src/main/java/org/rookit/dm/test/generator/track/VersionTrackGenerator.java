
package org.rookit.dm.test.generator.track;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import com.kekstudio.musictheory.Key;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.TypeVersion;
import org.rookit.api.dm.track.VersionTrack;
import org.rookit.api.dm.track.factory.VersionTrackFactory;
import org.rookit.api.dm.track.key.TrackKey;
import org.rookit.dm.test.generator.IdGenerator;
import org.rookit.test.generator.Generator;

import javax.annotation.Generated;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

final class VersionTrackGenerator extends AbstractTrackGenerator<VersionTrack> {

    private final VersionTrackFactory trackFactory;
    private final Generator<Track> originalTrackGenerator;
    private final Generator<TypeVersion> typeVersionGenerator;
    private final Generator<Artist> artistGenerator;
    private final Generator<String> stringGenerator;

    @Inject
    private VersionTrackGenerator(@IdGenerator final Generator<String> idGenerator,
            final Generator<Artist> artistGenerator,
            final Generator<Duration> durationGenerator,
            final Generator<LocalDate> pastGenerator,
            final Generator<Genre> genreGenerator,
            final Generator<Key> trackKeyGenerator,
            final Generator<Track> orTrackGenerator,
            final Generator<TypeVersion> typeVersionGenerator,
            final Generator<Long> longGenerator,
            final Generator<Short> shortGenerator,
            final Generator<String> stringGenerator,
            final Generator<Boolean> booleanGenerator,
            final Generator<Double> doubleGenerator,
            final VersionTrackFactory trackFactory) {
        super(idGenerator, artistGenerator, durationGenerator, pastGenerator, genreGenerator, trackKeyGenerator,
                longGenerator, shortGenerator, stringGenerator, booleanGenerator, doubleGenerator);
        this.stringGenerator = stringGenerator;
        this.artistGenerator = artistGenerator;
        this.trackFactory = trackFactory;
        this.originalTrackGenerator = orTrackGenerator;
        this.typeVersionGenerator = typeVersionGenerator;
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public boolean equals(final Object object) {
        if (object instanceof VersionTrackGenerator) {
            if (!super.equals(object)) {
                return false;
            }
            final VersionTrackGenerator that = (VersionTrackGenerator) object;
            return Objects.equals(this.trackFactory, that.trackFactory)
                    && Objects.equals(this.originalTrackGenerator, that.originalTrackGenerator)
                    && Objects.equals(this.typeVersionGenerator, that.typeVersionGenerator)
                    && Objects.equals(this.artistGenerator, that.artistGenerator)
                    && Objects.equals(this.stringGenerator, that.stringGenerator);
        }
        return false;
    }

    @SuppressWarnings("boxing")
    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.trackFactory, this.originalTrackGenerator, this.typeVersionGenerator,
                this.artistGenerator, this.stringGenerator);
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("super", super.toString())
                .add("trackFactory", this.trackFactory)
                .add("originalTrackGenerator", this.originalTrackGenerator)
                .add("typeVersionGenerator", this.typeVersionGenerator)
                .add("artistGenerator", this.artistGenerator)
                .add("stringGenerator", this.stringGenerator)
                .toString();
    }

    @Override
    protected VersionTrack constructRandom() {
        final Set<Artist> artists = this.artistGenerator.createRandomSet();
        final Track original = this.originalTrackGenerator.createRandom();
        final TypeVersion typeVersion = this.typeVersionGenerator.createRandom();
        final String versionToken = this.stringGenerator.createRandom();

        final TrackKey mockedKey = mock(TrackKey.class);
        when(mockedKey.getVersionType()).thenReturn(typeVersion);
        when(mockedKey.versionToken()).thenReturn(Optional.of(versionToken));
        when(mockedKey.getVersionArtists()).thenReturn(artists);
        when(mockedKey.original()).thenReturn(original);

        return this.trackFactory.create(mockedKey);
    }

    @Override
    protected void fillTrack(final VersionTrack track) {
        // does nothing, as all the fields in VersionTrack are final.
    }

}
