
package org.rookit.dm.test.generator.genre;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.genre.factory.GenreFactory;
import org.rookit.dm.test.generator.IdGenerator;
import org.rookit.dm.test.generator.play.able.AbstractPlayableGenerator;
import org.rookit.test.generator.Generator;

import javax.annotation.Generated;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;

import static org.rookit.test.utils.GeneratorUtils.randomlyConsume;

class GenreGenerator extends AbstractPlayableGenerator<Genre> {

    private static final String PREFIX = "Genre";

    private final Generator<String> stringGenerator;
    private final GenreFactory genreFactory;

    @Inject
    private GenreGenerator(final GenreFactory genreFactory,
            @IdGenerator final Generator<String> idGenerator,
            final Generator<Duration> durationGenerator,
            final Generator<LocalDate> pastGenerator,
            final Generator<Long> longGenerator,
            final Generator<String> stringGenerator) {
        super(idGenerator, durationGenerator, pastGenerator, longGenerator);
        this.genreFactory = genreFactory;
        this.stringGenerator = stringGenerator;
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public boolean equals(final Object object) {
        if (object instanceof GenreGenerator) {
            if (!super.equals(object)) {
                return false;
            }
            final GenreGenerator that = (GenreGenerator) object;
            return Objects.equals(this.stringGenerator, that.stringGenerator)
                    && Objects.equals(this.genreFactory, that.genreFactory);
        }
        return false;
    }

    @SuppressWarnings("boxing")
    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.stringGenerator, this.genreFactory);
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("super", super.toString())
                .add("PREFIX", PREFIX)
                .add("stringGenerator", this.stringGenerator)
                .add("genreFactory", this.genreFactory)
                .toString();
    }

    @Override
    protected Genre constructRandom() {
        final String genreName = PREFIX + this.stringGenerator.createRandom();
        return this.genreFactory.createGenre(genreName);
    }

    @Override
    protected void fillPlayable(final Genre genre) {
        randomlyConsume(genre::setDescription, this.stringGenerator);
    }

}
