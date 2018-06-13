
package org.rookit.dm.test.generator.track;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import com.kekstudio.musictheory.Key;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.TypeTrack;
import org.rookit.api.dm.track.factory.TrackFactory;
import org.rookit.api.dm.track.key.TrackKey;
import org.rookit.dm.test.generator.IdGenerator;
import org.rookit.test.generator.Generator;

import javax.annotation.Generated;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

final class OriginalTrackGenerator extends AbstractTrackGenerator<Track> {

    private final TrackFactory trackFactory;
    private final Generator<Artist> artistGenerator;
    private final Generator<String> stringGenerator;

    @Inject
    private OriginalTrackGenerator(@IdGenerator final Generator<String> idGenerator,
            final Generator<Artist> artistGenerator,
            final Generator<Duration> durationGenerator,
            final Generator<LocalDate> pastGenerator,
            final Generator<Genre> genreGenerator,
            final Generator<Key> trackKeyGenerator,
            final Generator<Long> longGenerator,
            final Generator<Short> shortGenerator,
            final Generator<String> stringGenerator,
            final Generator<Boolean> booleanGenerator,
            final Generator<Double> doubleGenerator,
            final TrackFactory trackFactory) {
        super(idGenerator, artistGenerator, durationGenerator, pastGenerator, genreGenerator, trackKeyGenerator,
                longGenerator, shortGenerator, stringGenerator, booleanGenerator, doubleGenerator);
        this.trackFactory = trackFactory;
        this.artistGenerator = artistGenerator;
        this.stringGenerator = stringGenerator;
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public boolean equals(final Object object) {
        if (object instanceof OriginalTrackGenerator) {
            if (!super.equals(object)) {
                return false;
            }
            final OriginalTrackGenerator that = (OriginalTrackGenerator) object;
            return Objects.equals(this.trackFactory, that.trackFactory)
                    && Objects.equals(this.artistGenerator, that.artistGenerator);
        }
        return false;
    }

    @SuppressWarnings("boxing")
    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.trackFactory, this.artistGenerator);
    }

    @Override
    protected Track constructRandom() {
        final Set<Artist> artists = this.artistGenerator.createRandomSet();
        final String title = this.stringGenerator.createRandom();

        final TrackKey mockedKey = mock(TrackKey.class);
        when(mockedKey.type()).thenReturn(TypeTrack.ORIGINAL);
        when(mockedKey.title()).thenReturn(title);
        when(mockedKey.mainArtists()).thenReturn(artists);
        when(mockedKey.type()).thenReturn(TypeTrack.ORIGINAL);

        return this.trackFactory.create(mockedKey);
    }

    @Override
    protected void fillTrack(final Track track) {
        // Does nothing, as at this point there are not setters left.
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("trackFactory", this.trackFactory)
                .add("artistGenerator", this.artistGenerator)
                .add("stringGenerator", this.stringGenerator)
                .toString();
    }
}
