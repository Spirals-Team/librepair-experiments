package net.coljate.collection.impl;

import net.coljate.collection.EmptyTest;

/**
 *
 * @author Ollie
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class EmptyCollectionTest implements EmptyTest<Object> {

    @Override
    public EmptyCollection<Object> createTestCollection() {
        return EmptyCollection.instance();
    }

}
