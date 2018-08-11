package net.coljate.map;

import net.coljate.TestObjectCreator;

/**
 *
 * @author Ollie
 */
public class NewObjectEntryCreator implements TestObjectCreator<Entry<Object, Object>> {

    @Override
    public Entry<Object, Object> createTestObject() {
        return ImmutableEntry.of(new Object(), new Object());
    }

}
