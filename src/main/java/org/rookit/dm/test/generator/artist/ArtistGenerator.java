
package org.rookit.dm.test.generator.artist;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.artist.GroupArtist;
import org.rookit.test.generator.AbstractGenerator;
import org.rookit.test.generator.Generator;

import javax.annotation.Generated;
import java.util.Objects;

final class ArtistGenerator extends AbstractGenerator<Artist> {

    private final Generator<GroupArtist> groupArtistGenerator;

    @Inject
    private ArtistGenerator(final Generator<GroupArtist> groupArtistGenerator) {
        this.groupArtistGenerator = groupArtistGenerator;
    }

    @Override
    public Artist createRandom() {
        return this.groupArtistGenerator.createRandom();
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public boolean equals(final Object object) {
        if (object instanceof ArtistGenerator) {
            if (!super.equals(object)) {
                return false;
            }
            final ArtistGenerator that = (ArtistGenerator) object;
            return Objects.equals(this.groupArtistGenerator, that.groupArtistGenerator);
        }
        return false;
    }

    @SuppressWarnings("boxing")
    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.groupArtistGenerator);
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("super", super.toString())
                .add("groupArtistGenerator", this.groupArtistGenerator)
                .toString();
    }

}
