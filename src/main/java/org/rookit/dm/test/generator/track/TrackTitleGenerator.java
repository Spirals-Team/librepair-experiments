
package org.rookit.dm.test.generator.track;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import org.rookit.api.dm.track.title.Title;
import org.rookit.api.dm.track.title.TitleFactory;
import org.rookit.test.generator.AbstractGenerator;
import org.rookit.test.generator.Generator;

import javax.annotation.Generated;
import java.util.Objects;

final class TrackTitleGenerator extends AbstractGenerator<Title> {

    private final TitleFactory factory;
    private final Generator<String> stringGenerator;

    @Inject
    private TrackTitleGenerator(final TitleFactory factory,
                                final Generator<String> stringGenerator) {
        this.factory = factory;
        this.stringGenerator = stringGenerator;
    }

    @Override
    public Title createRandom() {
        return this.factory.create(this.stringGenerator.createRandom());
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public boolean equals(final Object object) {
        if (object instanceof TrackTitleGenerator) {
            if (!super.equals(object)) {
                return false;
            }
            final TrackTitleGenerator that = (TrackTitleGenerator) object;
            return Objects.equals(this.stringGenerator, that.stringGenerator);
        }
        return false;
    }

    @SuppressWarnings("boxing")
    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.stringGenerator);
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("super", super.toString())
                .add("stringGenerator", this.stringGenerator)
                .toString();
    }

}
