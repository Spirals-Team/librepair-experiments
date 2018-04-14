package com.alibaba.com.caucho.hessian.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import org.junit.Test;

import com.alibaba.com.caucho.hessian.io.base.SerializeTestBase;
import com.alibaba.com.caucho.hessian.io.beans.ConstructAlwaysNPE;
import com.alibaba.com.caucho.hessian.io.beans.ConstructNPE;

public class JavaDeserializerTest extends SerializeTestBase {

    /**
     * <a href="https://github.com/apache/incubator-dubbo/issues/210">#210</a>
     * @see org.springframework.jdbc.UncategorizedSQLException
     */
    @Test
    public void testConstructorNPE() throws Exception {
        String sql = "select * from demo";
        SQLException sqlEx = new SQLException("just a sql exception");

        for (int repeat = 0; repeat < 2; repeat++) {
            ConstructNPE normalNPE = new ConstructNPE("junit", sql, sqlEx);
            assertDesEquals(normalNPE, baseHession2Serialize(normalNPE));
            assertCompatibleConstructNPE(factory.getDeserializer(normalNPE.getClass()), true);

            assertDesEquals(normalNPE, baseHessionSerialize(normalNPE));
            assertCompatibleConstructNPE(factory.getDeserializer(normalNPE.getClass()), true);


            ConstructAlwaysNPE alwaysNPE = new ConstructAlwaysNPE("junit", sql, sqlEx);
            try {
                baseHession2Serialize(alwaysNPE);
                fail("must be always throw NullPointerException");
            } catch (HessianProtocolException e) {
                assertEquals(InvocationTargetException.class, e.getCause().getClass());
                assertEquals(NullPointerException.class, e.getCause().getCause().getClass());
            }
            assertCompatibleConstructNPE(factory.getDeserializer(alwaysNPE.getClass()), false);

            try {
                baseHessionSerialize(alwaysNPE);
                fail("must be always throw NullPointerException");
            } catch (HessianProtocolException e) {
                assertEquals(InvocationTargetException.class, e.getCause().getClass());
                assertEquals(NullPointerException.class, e.getCause().getCause().getClass());
            }
            assertCompatibleConstructNPE(factory.getDeserializer(alwaysNPE.getClass()), false);
        }
    }

    private void assertDesEquals(ConstructNPE expected, ConstructNPE actual) {
        assertEquals(expected.getMessage(), actual.getMessage());
        assertEquals(expected.getCause().getClass(), actual.getCause().getClass());
        assertEquals(expected.getSql(), actual.getSql());
    }

    private void assertCompatibleConstructNPE(Deserializer deserializer, boolean compatible) throws Exception {
        assertEquals(JavaDeserializer.class, deserializer.getClass());
        assertEquals(compatible, getFieldValue(deserializer, "compatibleConstructNPE"));
        Object[] args = (Object[]) getFieldValue(deserializer, "_constructorArgs");
        for (int i = 0; i < args.length; i++) {
            if (compatible) {
                assertNotNull(args[i]);
            } else {
                assertNull(args[i]);
            }
        }
    }

}
