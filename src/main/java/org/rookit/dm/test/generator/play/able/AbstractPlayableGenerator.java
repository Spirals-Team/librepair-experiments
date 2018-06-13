
package org.rookit.dm.test.generator.play.able;

import com.google.common.base.MoreObjects;
import org.rookit.api.dm.play.able.Playable;
import org.rookit.dm.test.generator.AbstractRookitGenerator;
import org.rookit.test.generator.Generator;

import javax.annotation.Generated;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;

import static org.rookit.test.utils.GeneratorUtils.randomlyConsume;

@SuppressWarnings("javadoc")
public abstract class AbstractPlayableGenerator<T extends Playable> extends AbstractRookitGenerator<T> {

    private final Generator<Duration> durationGenerator;
    private final Generator<LocalDate> pastGenerator;
    private final Generator<Long> longGenerator;

    protected AbstractPlayableGenerator(final Generator<String> idGenerator,
            final Generator<Duration> durationGenerator,
            final Generator<LocalDate> pastGenerator,
            final Generator<Long> longGenerator) {
        super(idGenerator);
        this.longGenerator = longGenerator;
        this.durationGenerator = durationGenerator;
        this.pastGenerator = pastGenerator;
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public boolean equals(final Object object) {
        if (object instanceof AbstractPlayableGenerator) {
            if (!super.equals(object)) {
                return false;
            }
            final AbstractPlayableGenerator<?> that = (AbstractPlayableGenerator<?>) object;
            return Objects.equals(this.durationGenerator, that.durationGenerator)
                    && Objects.equals(this.pastGenerator, that.pastGenerator)
                    && Objects.equals(this.longGenerator, that.longGenerator);
        }
        return false;
    }

    @SuppressWarnings("boxing")
    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.durationGenerator, this.pastGenerator, this.longGenerator);
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("super", super.toString())
                .add("durationGenerator", this.durationGenerator)
                .add("pastGenerator", this.pastGenerator)
                .add("longGenerator", this.longGenerator)
                .toString();
    }

    protected abstract void fillPlayable(T playable);

    @Override
    protected final void fillRookitModel(final T playable) {
        randomlyConsume(playable::setDuration, this.durationGenerator);

        randomlyConsume(playable::setPlays, this.longGenerator);
        randomlyConsume(playable::setSkipped, this.longGenerator);
        randomlyConsume(playable::setLastSkipped, this.pastGenerator);
        randomlyConsume(playable::setLastPlayed, this.pastGenerator);

        fillPlayable(playable);
    }

    protected final Generator<Duration> getDurationGenerator() {
        return this.durationGenerator;
    }

    protected final Generator<LocalDate> getPastGenerator() {
        return this.pastGenerator;
    }

}
