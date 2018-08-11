package net.coljate.map.impl;

import net.coljate.map.Entry;
import net.coljate.map.ImmutableMapTest;
import net.coljate.map.SameObjectEntryCreator;

/**
 * @author Ollie
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class SingletonMapTest
        extends SameObjectEntryCreator
        implements ImmutableMapTest.OneEntryTests<Object, Object> {

    @Override
    public SingletonMap<Object, Object> createTestCollection() {
        final Entry<Object, Object> entry = this.getCollectionElement();
        return SingletonMap.of(entry.key(), entry.value());
    }

}
