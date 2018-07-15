package org.simpleflatmapper.immutables;

import org.junit.Test;
import org.simpleflatmapper.jdbc.JdbcMapperBuilder;
import org.simpleflatmapper.jdbc.JdbcMapperFactory;
import org.simpleflatmapper.jdbc.PreparedStatementMapperBuilder;
import org.simpleflatmapper.reflect.meta.ClassMeta;

import static org.junit.Assert.assertNotNull;

public class TestMapFooBar {

    @Test
    public void mapFooBar() {
        final JdbcMapperBuilder<FoobarValue> builder = JdbcMapperFactory.newInstance().newBuilder(FoobarValue.class);

        assertNotNull(builder.addKey("foo").addKey("bar").addKey("crux").mapper());

        final PreparedStatementMapperBuilder<FoobarValue> buildFrom = JdbcMapperFactory.newInstance().buildFrom(FoobarValue.class);
        assertNotNull(buildFrom.addColumn("foo").addColumn("bar").addColumn("crux").buildIndexFieldMappers());
    }


    @Test
    public void mapFooBarNoBuilderLink() throws NoSuchMethodException {
        final JdbcMapperFactory mapperFactory = JdbcMapperFactory.newInstance();
        final ClassMeta<FoobarValueNoBuilderLink> meta =
                mapperFactory
                        .getClassMetaWithExtraInstantiator(
                                FoobarValueNoBuilderLink.class,
                                ImmutableFoobarValueNoBuilderLink.class.getMethod("builder"));
        final JdbcMapperBuilder<FoobarValueNoBuilderLink> builder =
                mapperFactory.newBuilder(meta);
        assertNotNull(builder.addKey("foo").addKey("bar").addKey("crux").mapper());
    }
}
