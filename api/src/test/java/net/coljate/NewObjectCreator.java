package net.coljate;

/**
 *
 * @author Ollie
 */
public class NewObjectCreator implements TestObjectCreator<Object> {

    @Override
    public Object createTestObject() {
        return new Object();
    }

}
