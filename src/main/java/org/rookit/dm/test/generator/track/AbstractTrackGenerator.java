
package org.rookit.dm.test.generator.track;

import com.google.common.base.MoreObjects;
import com.kekstudio.musictheory.Key;
import com.tngtech.archunit.thirdparty.com.google.common.collect.ImmutableSet;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.track.Track;
import org.rookit.dm.test.generator.genre.AbstractGenreableGenerator;
import org.rookit.test.generator.Generator;

import javax.annotation.Generated;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;

import static org.rookit.test.utils.GeneratorUtils.randomlyConsume;
import static org.rookit.test.utils.GeneratorUtils.randomlyConsumeCollection;

abstract class AbstractTrackGenerator<T extends Track> extends AbstractGenreableGenerator<T> {

    private final Generator<Artist> artistGenerator;
    private final Generator<Key> trackKeyGenerator;
    private final Generator<Short> shortGenerator;
    private final Generator<String> stringGenerator;
    private final Generator<Boolean> booleanGenerator;
    private final Generator<Double> doubleGenerator;

    AbstractTrackGenerator(final Generator<String> idGenerator,
                           final Generator<Artist> artistGenerator,
                           final Generator<Duration> durationGenerator,
                           final Generator<LocalDate> pastGenerator,
                           final Generator<Genre> genreGenerator,
                           final Generator<Key> trackKeyGenerator,
                           final Generator<Long> longGenerator,
                           final Generator<Short> shortGenerator,
                           final Generator<String> stringGenerator,
                           final Generator<Boolean> booleanGenerator,
                           final Generator<Double> doubleGenerator) {
        super(idGenerator, durationGenerator, pastGenerator, genreGenerator, longGenerator);
        this.artistGenerator = artistGenerator;
        this.trackKeyGenerator = trackKeyGenerator;
        this.shortGenerator = shortGenerator;
        this.stringGenerator = stringGenerator;
        this.booleanGenerator = booleanGenerator;
        this.doubleGenerator = doubleGenerator;
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public boolean equals(final Object object) {
        if (object instanceof AbstractTrackGenerator) {
            if (!super.equals(object)) {
                return false;
            }
            final AbstractTrackGenerator<?> that = (AbstractTrackGenerator<?>) object;
            return Objects.equals(this.artistGenerator, that.artistGenerator)
                    && Objects.equals(this.trackKeyGenerator, that.trackKeyGenerator)
                    && Objects.equals(this.shortGenerator, that.shortGenerator)
                    && Objects.equals(this.stringGenerator, that.stringGenerator)
                    && Objects.equals(this.booleanGenerator, that.booleanGenerator)
                    && Objects.equals(this.doubleGenerator, that.doubleGenerator);
        }
        return false;
    }

    @SuppressWarnings("boxing")
    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.artistGenerator, this.trackKeyGenerator, this.shortGenerator,
                this.stringGenerator,
                this.booleanGenerator, this.doubleGenerator);
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("super", super.toString())
                .add("artistGenerator", this.artistGenerator)
                .add("trackKeyGenerator", this.trackKeyGenerator)
                .add("shortGenerator", this.shortGenerator)
                .add("stringGenerator", this.stringGenerator)
                .add("booleanGenerator", this.booleanGenerator)
                .add("doubleGenerator", this.doubleGenerator)
                .toString();
    }

    @Override
    protected final void fillGenreable(final T track) {
        final Collection<Artist> mainArtists = track.artists().mainArtists();
        final Collection<Artist> features = randomlyConsumeCollection(track::setFeatures,
                this.artistGenerator, mainArtists);
        final Collection<Artist> mainArtistsFeatures = ImmutableSet.<Artist>builder()
                .addAll(mainArtists)
                .addAll(features)
                .build();
        randomlyConsumeCollection(track::setProducers, this.artistGenerator, mainArtistsFeatures);

        randomlyConsume(track::setExplicit, this.booleanGenerator);
        randomlyConsume(track::setLyrics, this.stringGenerator);
        fillTrack(track);
    }

    protected abstract void fillTrack(final T track);

    protected final Generator<Artist> getArtistGenerator() {
        return this.artistGenerator;
    }

    protected final Generator<Key> getTrackKeyGenerator() {
        return this.trackKeyGenerator;
    }

}
