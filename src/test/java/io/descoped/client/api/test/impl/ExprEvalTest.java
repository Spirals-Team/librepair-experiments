package io.descoped.client.api.test.impl;

import org.apache.commons.jexl3.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

// http://commons.apache.org/proper/commons-jexl/index.html
public class ExprEvalTest {

    private static Logger log = LoggerFactory.getLogger(ExprEvalTest.class);

    @Test
    public void testEval() throws Exception {
        Foo foo = new Foo("Ove");
        foo.getBar().setName("Ranheim");

        // Create or retrieve an engine
        JexlEngine jexl = new JexlBuilder().create();

        // Create an expression
        String jexlExp = "foo.bar.name";
        JexlExpression e = jexl.createExpression(jexlExp);

        // Create a context and add data
        JexlContext jc = new MapContext();
        jc.set("foo", foo);

        // Now evaluate the expression, getting the result
        Object o = e.evaluate(jc);

        log.trace("result1: {}", o);

        JexlContext jc2 = new MapContext();
        Map<String,String> map = new HashMap<>();
        map.put("a", "foo");
        map.put("b", "bar");
        jc2.set("foo", map);
        log.trace("result2: {}", jexl.createExpression("foo['a'].hashCode()").evaluate(jc2));
        log.trace("result2: {}", jexl.createExpression("foo['b'].hashCode()").evaluate(jc2));
        log.trace("result2: {}", jexl.createExpression("foo['c'].hashCode()").evaluate(jc2));
    }

    public static class Foo {
        private String name;

        private Bar bar = new Bar();

        public Foo() {
        }

        public Foo(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Bar getBar() {
            return bar;
        }

        public void setBar(Bar bar) {
            this.bar = bar;
        }

        @Override
        public String toString() {
            return "io.descoped.client.api.test.impl.ExprEvalTest.Foo{" +
                    "name='" + name + '\'' +
                    ", bar=" + bar +
                    '}';
        }
    }

    public static class Bar {
        private String name;

        public Bar() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "io.descoped.client.api.test.impl.ExprEvalTest.Bar{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
