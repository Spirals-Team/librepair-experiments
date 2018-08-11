package net.coljate.map;

import org.junit.jupiter.api.BeforeEach;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * @author Ollie
 */
public class SameObjectEntryCreator extends NewObjectEntryCreator {

    private volatile Entry<Object, Object> entry;

    @BeforeEach
    public void resetEntry() {
        entry = this.createTestObject();
    }

    @Nonnull
    public Entry<Object, Object> getCollectionElement() {
        Objects.requireNonNull(entry);
        return entry;
    }

}
