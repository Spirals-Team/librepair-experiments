package net.coljate;

import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author Ollie
 */
public class SameObjectCreator extends NewObjectCreator {

    private Object sameObject;

    @BeforeEach
    public final void resetObject() {
        sameObject = this.createTestObject();
    }

    public Object getCollectionElement() {
        return sameObject;
    }

}
