
package org.rookit.dm.test.generator.artist;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import com.neovisionaries.i18n.CountryCode;
import org.rookit.api.dm.artist.Musician;
import org.rookit.api.dm.artist.TypeArtist;
import org.rookit.api.dm.artist.TypeGender;
import org.rookit.api.dm.artist.factory.MusicianFactory;
import org.rookit.api.dm.artist.key.ArtistKey;
import org.rookit.api.dm.genre.Genre;
import org.rookit.dm.test.generator.IdGenerator;
import org.rookit.test.generator.Generator;

import javax.annotation.Generated;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

final class MusicianGenerator extends AbstractArtistGenerator<Musician> {

    private final MusicianFactory artistFactory;
    private final Generator<TypeGender> genderGenerator;
    private final Generator<String> stringGenerator;

    @Inject
    private MusicianGenerator(final MusicianFactory artistFactory,
            final Generator<TypeGender> genderGenerator,
            @IdGenerator final Generator<String> idGenerator,
            final Generator<Duration> durationGenerator,
            final Generator<LocalDate> pastGenerator,
            final Generator<Genre> genreGenerator,
            final Generator<Long> longGenerator,
            final Generator<String> stringGenerator,
            final Generator<byte[]> byteArrayGenerator,
            final Generator<CountryCode> countryCodeGenerator) {
        super(idGenerator, durationGenerator, pastGenerator, genreGenerator,
                longGenerator, stringGenerator, byteArrayGenerator, countryCodeGenerator);
        this.artistFactory = artistFactory;
        this.genderGenerator = genderGenerator;
        this.stringGenerator = stringGenerator;
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public boolean equals(final Object object) {
        if (object instanceof MusicianGenerator) {
            if (!super.equals(object)) {
                return false;
            }
            final MusicianGenerator that = (MusicianGenerator) object;
            return Objects.equals(this.artistFactory, that.artistFactory)
                    && Objects.equals(this.genderGenerator, that.genderGenerator)
                    && Objects.equals(this.stringGenerator, that.stringGenerator);
        }
        return false;
    }

    @SuppressWarnings("boxing")
    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.artistFactory, this.genderGenerator, this.stringGenerator);
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("super", super.toString())
                .add("artistFactory", this.artistFactory)
                .add("genderGenerator", this.genderGenerator)
                .add("stringGenerator", this.stringGenerator)
                .toString();
    }

    @Override
    protected Musician constructRandom() {
        final String name = this.stringGenerator.createRandom();
        final String isni = this.stringGenerator.createRandom();
        final TypeGender gender = this.genderGenerator.createRandom();
        final String fullName = this.stringGenerator.createRandom();

        final ArtistKey mockedKey = mock(ArtistKey.class);
        when(mockedKey.type()).thenReturn(TypeArtist.MUSICIAN);
        when(mockedKey.name()).thenReturn(name);
        when(mockedKey.isni()).thenReturn(isni);
        when(mockedKey.gender()).thenReturn(gender);
        when(mockedKey.fullName()).thenReturn(fullName);

        return this.artistFactory.create(mockedKey);
    }

    @Override
    protected void fillArtist(final Musician artist) {
        // empty, as at this point there are no setters left to apply
    }

}
