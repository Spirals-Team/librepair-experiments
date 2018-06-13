
package org.rookit.dm.test.generator.artist;

import com.google.common.base.MoreObjects;
import com.neovisionaries.i18n.CountryCode;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.genre.Genre;
import org.rookit.dm.test.generator.genre.AbstractGenreableGenerator;
import org.rookit.test.generator.Generator;

import javax.annotation.Generated;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import static org.rookit.test.utils.GeneratorUtils.randomlyConsume;
import static org.rookit.test.utils.GeneratorUtils.randomlyConsumeCollection;

abstract class AbstractArtistGenerator<T extends Artist> extends AbstractGenreableGenerator<T> {

    private final Generator<String> stringGenerator;
    private final Generator<byte[]> byteArrayGenerator;
    private final Generator<CountryCode> countryCodeGenerator;

    AbstractArtistGenerator(final Generator<String> idGenerator,
                            final Generator<Duration> durationGenerator,
                            final Generator<LocalDate> pastGenerator,
                            final Generator<Genre> genreGenerator,
                            final Generator<Long> longGenerator,
                            final Generator<String> stringGenerator,
                            final Generator<byte[]> byteArrayGenerator,
                            final Generator<CountryCode> countryCodeGenerator) {
        super(idGenerator, durationGenerator, pastGenerator, genreGenerator, longGenerator);
        this.stringGenerator = stringGenerator;
        this.byteArrayGenerator = byteArrayGenerator;
        this.countryCodeGenerator = countryCodeGenerator;
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public boolean equals(final Object object) {
        if (object instanceof AbstractArtistGenerator) {
            if (!super.equals(object)) {
                return false;
            }
            final AbstractArtistGenerator<?> that = (AbstractArtistGenerator<?>) object;
            return Objects.equals(this.stringGenerator, that.stringGenerator)
                    && Objects.equals(this.byteArrayGenerator, that.byteArrayGenerator);
        }
        return false;
    }

    @SuppressWarnings("boxing")
    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.stringGenerator, this.byteArrayGenerator);
    }

    private void setEndDate(final T artist, final LocalDate date) {
        final Optional<LocalDate> beginDateOrNone = artist.profile().timeline().begin();
        final LocalDate endDate;
        if (beginDateOrNone.isPresent()) {
            final LocalDate beginDate = beginDateOrNone.get();
            if (beginDate.isAfter(date)) {
                artist.setBeginDate(date);
                endDate = beginDate;
            } else {
                endDate = date;
            }
        } else {
            endDate = date;
        }
        artist.setEndDate(endDate);
    }

    protected abstract void fillArtist(T artist);

    @Override
    protected final void fillGenreable(final T artist) {
        randomlyConsume(artist::setBeginDate, getPastGenerator());
        randomlyConsume(date -> setEndDate(artist, date), getPastGenerator());

        randomlyConsumeCollection(artist::setAliases, this.stringGenerator);
        randomlyConsume(artist::setIPI, this.stringGenerator);
        randomlyConsume(artist::setOrigin, this.countryCodeGenerator);
        randomlyConsume(artist::setPicture, this.byteArrayGenerator);
        fillArtist(artist);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("stringGenerator", this.stringGenerator)
                .add("byteArrayGenerator", this.byteArrayGenerator)
                .add("countryCodeGenerator", this.countryCodeGenerator)
                .toString();
    }
}
