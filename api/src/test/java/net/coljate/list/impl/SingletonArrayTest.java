package net.coljate.list.impl;

import net.coljate.SameObjectCreator;
import net.coljate.list.ImmutableArray;
import net.coljate.list.ImmutableArrayTest;

/**
 *
 * @author Ollie
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class SingletonArrayTest extends SameObjectCreator implements ImmutableArrayTest.OneElementTests<Object> {

    @Override
    public ImmutableArray<Object> createTestCollection() {
        return SingletonArray.of(this.getCollectionElement());
    }

}
