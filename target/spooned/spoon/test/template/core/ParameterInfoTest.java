package spoon.test.template.core;


public class ParameterInfoTest {
    @org.junit.Test
    public void testParameterNames() {
        org.junit.Assert.assertEquals("year", new spoon.pattern.internal.parameter.MapParameterInfo("year").getName());
        org.junit.Assert.assertEquals("year", ((spoon.pattern.internal.parameter.ParameterInfo) (new spoon.pattern.internal.parameter.MapParameterInfo("year").setContainerKind(spoon.reflect.meta.ContainerKind.MAP))).getName());
        org.junit.Assert.assertEquals("year", new spoon.pattern.internal.parameter.MapParameterInfo(new spoon.pattern.internal.parameter.MapParameterInfo("year")).getName());
        org.junit.Assert.assertEquals("year", new spoon.pattern.internal.parameter.MapParameterInfo(new spoon.pattern.internal.parameter.MapParameterInfo("year").setContainerKind(spoon.reflect.meta.ContainerKind.MAP)).getName());
        org.junit.Assert.assertEquals("year.age", new spoon.pattern.internal.parameter.MapParameterInfo("age", new spoon.pattern.internal.parameter.MapParameterInfo("year")).getName());
        org.junit.Assert.assertEquals("year.age", new spoon.pattern.internal.parameter.MapParameterInfo("age", new spoon.pattern.internal.parameter.MapParameterInfo("year").setContainerKind(spoon.reflect.meta.ContainerKind.MAP)).getName());
        org.junit.Assert.assertEquals("year", ((spoon.pattern.internal.parameter.ParameterInfo) (new spoon.pattern.internal.parameter.MapParameterInfo("year").setContainerKind(spoon.reflect.meta.ContainerKind.LIST))).getName());
        org.junit.Assert.assertEquals("year", new spoon.pattern.internal.parameter.ListParameterInfo(new spoon.pattern.internal.parameter.MapParameterInfo("year")).getName());
        org.junit.Assert.assertEquals("year", new spoon.pattern.internal.parameter.ListParameterInfo(new spoon.pattern.internal.parameter.MapParameterInfo("year").setContainerKind(spoon.reflect.meta.ContainerKind.LIST)).getName());
        org.junit.Assert.assertEquals("year[7]", new spoon.pattern.internal.parameter.ListParameterInfo(7, new spoon.pattern.internal.parameter.MapParameterInfo("year")).getName());
        org.junit.Assert.assertEquals("year[7]", new spoon.pattern.internal.parameter.ListParameterInfo(new spoon.pattern.internal.parameter.ListParameterInfo(7, new spoon.pattern.internal.parameter.MapParameterInfo("year"))).getName());
        org.junit.Assert.assertEquals("year[7][2]", new spoon.pattern.internal.parameter.ListParameterInfo(2, new spoon.pattern.internal.parameter.ListParameterInfo(7, new spoon.pattern.internal.parameter.MapParameterInfo("year"))).getName());
        org.junit.Assert.assertEquals("year[7][2].age", new spoon.pattern.internal.parameter.MapParameterInfo("age", new spoon.pattern.internal.parameter.ListParameterInfo(2, new spoon.pattern.internal.parameter.ListParameterInfo(7, new spoon.pattern.internal.parameter.MapParameterInfo("year")))).getName());
        org.junit.Assert.assertEquals("year", ((spoon.pattern.internal.parameter.ParameterInfo) (new spoon.pattern.internal.parameter.MapParameterInfo("year").setContainerKind(spoon.reflect.meta.ContainerKind.SET))).getName());
        org.junit.Assert.assertEquals("year", new spoon.pattern.internal.parameter.SetParameterInfo(new spoon.pattern.internal.parameter.MapParameterInfo("year")).getName());
        org.junit.Assert.assertEquals("year", new spoon.pattern.internal.parameter.SetParameterInfo(new spoon.pattern.internal.parameter.MapParameterInfo("year").setContainerKind(spoon.reflect.meta.ContainerKind.SET)).getName());
    }

    @org.junit.Test
    public void testSingleValueParameterByNameIntoNullContainer() {
        spoon.pattern.internal.parameter.ParameterInfo namedParam = new spoon.pattern.internal.parameter.MapParameterInfo("year");
        {
            spoon.support.util.ImmutableMap val = namedParam.addValueAs(null, 2018);
            org.junit.Assert.assertNotNull(val);
            org.junit.Assert.assertEquals(map().put("year", 2018), val.asMap());
        }
    }

    @org.junit.Test
    public void testSingleValueParameterByNameIntoEmptyContainer() {
        spoon.pattern.internal.parameter.ParameterInfo namedParam = new spoon.pattern.internal.parameter.MapParameterInfo("year");
        {
            spoon.support.util.ImmutableMap empty = new spoon.support.util.ImmutableMapImpl();
            spoon.support.util.ImmutableMap val = namedParam.addValueAs(empty, 2018);
            org.junit.Assert.assertNotNull(val);
            org.junit.Assert.assertEquals(map().put("year", 2018), val.asMap());
            org.junit.Assert.assertEquals(map(), empty.asMap());
        }
    }

    @org.junit.Test
    public void testSingleValueParameterByNameWhenAlreadyExists() {
        spoon.pattern.internal.parameter.ParameterInfo namedParam = new spoon.pattern.internal.parameter.MapParameterInfo("year");
        {
            spoon.support.util.ImmutableMap oldContainer = new spoon.support.util.ImmutableMapImpl().putValue("year", 2018);
            org.junit.Assert.assertEquals(map().put("year", 2018), oldContainer.asMap());
            org.junit.Assert.assertSame(oldContainer, namedParam.addValueAs(oldContainer, 2018));
            org.junit.Assert.assertEquals(map().put("year", 2018), oldContainer.asMap());
        }
    }

    @org.junit.Test
    public void testSingleValueParameterByNameWhenDifferentExists() {
        spoon.pattern.internal.parameter.ParameterInfo namedParam = new spoon.pattern.internal.parameter.MapParameterInfo("year");
        {
            spoon.support.util.ImmutableMap oldContainer = new spoon.support.util.ImmutableMapImpl().putValue("year", 2018);
            org.junit.Assert.assertNull(namedParam.addValueAs(oldContainer, 2111));
            org.junit.Assert.assertNull(namedParam.addValueAs(oldContainer, 0));
            org.junit.Assert.assertNull(namedParam.addValueAs(oldContainer, null));
            org.junit.Assert.assertEquals(map().put("year", 2018), oldContainer.asMap());
        }
    }

    @org.junit.Test
    public void testOptionalSingleValueParameterByName() {
        spoon.pattern.internal.parameter.ParameterInfo namedParam = new spoon.pattern.internal.parameter.MapParameterInfo("year").setMinOccurences(0);
        {
            spoon.support.util.ImmutableMap container = new spoon.support.util.ImmutableMapImpl().putValue("a", "b");
            org.junit.Assert.assertSame(container, namedParam.addValueAs(container, null));
            org.junit.Assert.assertEquals(map().put("a", "b"), container.asMap());
        }
    }

    @org.junit.Test
    public void testMandatorySingleValueParameterByName() {
        spoon.pattern.internal.parameter.ParameterInfo namedParam = new spoon.pattern.internal.parameter.MapParameterInfo("year").setMinOccurences(1);
        {
            spoon.support.util.ImmutableMap container = new spoon.support.util.ImmutableMapImpl().putValue("a", "b");
            org.junit.Assert.assertNull(namedParam.addValueAs(container, null));
            org.junit.Assert.assertEquals(map().put("a", "b"), container.asMap());
        }
    }

    @org.junit.Test
    public void testSingleValueParameterByNameConditionalMatcher() {
        spoon.pattern.internal.parameter.ParameterInfo namedParam = new spoon.pattern.internal.parameter.MapParameterInfo("year").setMatchCondition(java.lang.Integer.class, ( i) -> i > 2000);
        spoon.support.util.ImmutableMap val = namedParam.addValueAs(null, 2018);
        org.junit.Assert.assertNotNull(val);
        org.junit.Assert.assertEquals(map().put("year", 2018), val.asMap());
        org.junit.Assert.assertNull(namedParam.addValueAs(null, 1000));
        org.junit.Assert.assertNull(namedParam.addValueAs(null, "3000"));
        org.junit.Assert.assertNull(namedParam.addValueAs(new spoon.support.util.ImmutableMapImpl().putValue("year", 3000), 2018));
    }

    @org.junit.Test
    public void testListParameterByNameIntoNull() {
        spoon.pattern.internal.parameter.ParameterInfo namedParam = new spoon.pattern.internal.parameter.MapParameterInfo("year").setContainerKind(spoon.reflect.meta.ContainerKind.LIST);
        {
            spoon.support.util.ImmutableMap val = namedParam.addValueAs(null, 2018);
            org.junit.Assert.assertNotNull(val);
            org.junit.Assert.assertEquals(map().put("year", java.util.Arrays.asList(2018)), val.asMap());
        }
    }

    @org.junit.Test
    public void testListParameterByNameIntoEmptyContainer() {
        spoon.pattern.internal.parameter.ParameterInfo namedParam = new spoon.pattern.internal.parameter.MapParameterInfo("year").setContainerKind(spoon.reflect.meta.ContainerKind.LIST);
        {
            spoon.support.util.ImmutableMap empty = new spoon.support.util.ImmutableMapImpl();
            spoon.support.util.ImmutableMap val = namedParam.addValueAs(empty, 2018);
            org.junit.Assert.assertNotNull(val);
            org.junit.Assert.assertEquals(map().put("year", java.util.Arrays.asList(2018)), val.asMap());
            org.junit.Assert.assertEquals(map(), empty.asMap());
        }
    }

    @org.junit.Test
    public void testListParameterByNameIntoEmptyContainerWithEmptyList() {
        java.util.function.Consumer<spoon.pattern.internal.parameter.ParameterInfo> check = ( namedParam) -> {
            spoon.support.util.ImmutableMap empty = new spoon.support.util.ImmutableMapImpl().putValue("year", java.util.Collections.emptyList());
            spoon.support.util.ImmutableMap val = namedParam.addValueAs(empty, 2018);
            spoon.support.util.ImmutableMap val2 = namedParam.addValueAs(val, 2018);
            org.junit.Assert.assertSame(val2, namedParam.addValueAs(val2, null));
            org.junit.Assert.assertEquals(map().put("year", java.util.Collections.emptyList()), empty.asMap());
            org.junit.Assert.assertNotNull(val);
            org.junit.Assert.assertEquals(map().put("year", java.util.Arrays.asList(2018)), val.asMap());
            org.junit.Assert.assertNotNull(val2);
            org.junit.Assert.assertEquals(map().put("year", java.util.Arrays.asList(2018, 2018)), val2.asMap());
        };
        check.accept(new spoon.pattern.internal.parameter.MapParameterInfo("year").setContainerKind(spoon.reflect.meta.ContainerKind.LIST));
        check.accept(new spoon.pattern.internal.parameter.MapParameterInfo("year"));
        check.accept(new spoon.pattern.internal.parameter.ListParameterInfo(new spoon.pattern.internal.parameter.MapParameterInfo("year")));
        check.accept(new spoon.pattern.internal.parameter.ListParameterInfo(new spoon.pattern.internal.parameter.MapParameterInfo("year").setContainerKind(spoon.reflect.meta.ContainerKind.LIST)));
    }

    @org.junit.Test
    public void testMergeOnDifferentValueTypeContainers() {
        java.util.function.BiConsumer<spoon.pattern.internal.parameter.ParameterInfo, spoon.support.util.ImmutableMap> checker = ( parameter, params) -> {
            org.junit.Assert.assertSame(params, parameter.addValueAs(params, "x"));
            org.junit.Assert.assertNull(parameter.addValueAs(params, "y"));
            org.junit.Assert.assertNull(parameter.addValueAs(params, null));
        };
        spoon.support.util.ImmutableMap empty = new spoon.support.util.ImmutableMapImpl();
        checker.accept(new spoon.pattern.internal.parameter.MapParameterInfo("year"), empty.putValue("year", "x"));
        checker.accept(new spoon.pattern.internal.parameter.ListParameterInfo(0, new spoon.pattern.internal.parameter.MapParameterInfo("year")), empty.putValue("year", java.util.Collections.singletonList("x")));
        checker.accept(new spoon.pattern.internal.parameter.ListParameterInfo(1, new spoon.pattern.internal.parameter.MapParameterInfo("year")), empty.putValue("year", java.util.Arrays.asList("zz", "x")));
        checker.accept(new spoon.pattern.internal.parameter.MapParameterInfo("key", new spoon.pattern.internal.parameter.ListParameterInfo(1, new spoon.pattern.internal.parameter.MapParameterInfo("year"))), empty.putValue("year", java.util.Arrays.asList("zz", empty.putValue("key", "x"))));
        checker.accept(new spoon.pattern.internal.parameter.MapParameterInfo("key", new spoon.pattern.internal.parameter.MapParameterInfo("year")), empty.putValue("year", empty.putValue("key", "x")));
    }

    @org.junit.Test
    public void testAppendIntoList() {
        spoon.pattern.internal.parameter.ParameterInfo parameter = new spoon.pattern.internal.parameter.MapParameterInfo("years").setContainerKind(spoon.reflect.meta.ContainerKind.LIST);
        spoon.support.util.ImmutableMap params = parameter.addValueAs(null, 1000);
        org.junit.Assert.assertNotNull(params);
        org.junit.Assert.assertEquals(map().put("years", java.util.Arrays.asList(1000)), params.asMap());
        params = parameter.addValueAs(params, 100);
        org.junit.Assert.assertNotNull(params);
        org.junit.Assert.assertEquals(map().put("years", java.util.Arrays.asList(1000, 100)), params.asMap());
        params = parameter.addValueAs(params, "a");
        org.junit.Assert.assertNotNull(params);
        org.junit.Assert.assertEquals(map().put("years", java.util.Arrays.asList(1000, 100, "a")), params.asMap());
        params = parameter.addValueAs(params, "a");
        org.junit.Assert.assertNotNull(params);
        org.junit.Assert.assertEquals(map().put("years", java.util.Arrays.asList(1000, 100, "a", "a")), params.asMap());
    }

    @org.junit.Test
    public void testSetIntoList() {
        spoon.pattern.internal.parameter.ParameterInfo named = new spoon.pattern.internal.parameter.MapParameterInfo("years");
        spoon.support.util.ImmutableMap params = new spoon.pattern.internal.parameter.ListParameterInfo(2, named).addValueAs(null, 1000);
        org.junit.Assert.assertNotNull(params);
        org.junit.Assert.assertEquals(map().put("years", java.util.Arrays.asList(null, null, 1000)), params.asMap());
        params = new spoon.pattern.internal.parameter.ListParameterInfo(0, named).addValueAs(params, 10);
        org.junit.Assert.assertNotNull(params);
        org.junit.Assert.assertEquals(map().put("years", java.util.Arrays.asList(10, null, 1000)), params.asMap());
        params = new spoon.pattern.internal.parameter.ListParameterInfo(3, named).addValueAs(params, 10000);
        org.junit.Assert.assertNotNull(params);
        org.junit.Assert.assertEquals(map().put("years", java.util.Arrays.asList(10, null, 1000, 10000)), params.asMap());
    }

    @org.junit.Test
    public void testAppendIntoSet() {
        spoon.pattern.internal.parameter.ParameterInfo parameter = new spoon.pattern.internal.parameter.MapParameterInfo("years").setContainerKind(spoon.reflect.meta.ContainerKind.SET);
        spoon.support.util.ImmutableMap params = parameter.addValueAs(null, 1000);
        org.junit.Assert.assertNotNull(params);
        org.junit.Assert.assertEquals(map().put("years", spoon.test.template.core.ParameterInfoTest.asSet(1000)), params.asMap());
        params = parameter.addValueAs(params, 100);
        org.junit.Assert.assertNotNull(params);
        org.junit.Assert.assertEquals(map().put("years", spoon.test.template.core.ParameterInfoTest.asSet(1000, 100)), params.asMap());
        params = parameter.addValueAs(params, "a");
        org.junit.Assert.assertNotNull(params);
        org.junit.Assert.assertEquals(map().put("years", spoon.test.template.core.ParameterInfoTest.asSet(1000, 100, "a")), params.asMap());
        org.junit.Assert.assertSame(params, parameter.addValueAs(params, "a"));
        org.junit.Assert.assertNotNull(params);
        org.junit.Assert.assertEquals(map().put("years", spoon.test.template.core.ParameterInfoTest.asSet(1000, 100, "a")), params.asMap());
    }

    @org.junit.Test
    public void testMapEntryInParameterByName() {
        java.util.function.BiConsumer<spoon.pattern.internal.parameter.ParameterInfo, spoon.support.util.ImmutableMap> checker = ( namedParam, empty) -> {
            org.junit.Assert.assertNull(namedParam.addValueAs(empty, "a value"));
            final spoon.support.util.ImmutableMap val = namedParam.addValueAs(empty, entry("year", 2018));
            org.junit.Assert.assertNotNull(val);
            org.junit.Assert.assertEquals(map().put("map", new spoon.support.util.ImmutableMapImpl().putValue("year", 2018)), val.asMap());
            org.junit.Assert.assertSame(val, namedParam.addValueAs(val, null));
            org.junit.Assert.assertSame(val, namedParam.addValueAs(val, entry("year", 2018)));
            org.junit.Assert.assertNull(namedParam.addValueAs(val, entry("year", 1111)));
            spoon.support.util.ImmutableMap val2 = namedParam.addValueAs(val, entry("age", "best"));
            org.junit.Assert.assertNotNull(val2);
            org.junit.Assert.assertEquals(map().put("map", new spoon.support.util.ImmutableMapImpl().putValue("year", 2018).putValue("age", "best")), val2.asMap());
            org.junit.Assert.assertEquals(map().put("map", new spoon.support.util.ImmutableMapImpl().putValue("year", 2018)), val.asMap());
        };
        checker.accept(new spoon.pattern.internal.parameter.MapParameterInfo("map").setContainerKind(spoon.reflect.meta.ContainerKind.MAP), new spoon.support.util.ImmutableMapImpl());
        checker.accept(new spoon.pattern.internal.parameter.MapParameterInfo("map").setContainerKind(spoon.reflect.meta.ContainerKind.MAP), new spoon.support.util.ImmutableMapImpl().putValue("map", null));
        checker.accept(new spoon.pattern.internal.parameter.MapParameterInfo("map").setContainerKind(spoon.reflect.meta.ContainerKind.MAP), new spoon.support.util.ImmutableMapImpl().putValue("map", java.util.Collections.emptyMap()));
        checker.accept(new spoon.pattern.internal.parameter.MapParameterInfo("map"), new spoon.support.util.ImmutableMapImpl().putValue("map", java.util.Collections.emptyMap()));
        checker.accept(new spoon.pattern.internal.parameter.MapParameterInfo("map"), new spoon.support.util.ImmutableMapImpl().putValue("map", new spoon.support.util.ImmutableMapImpl()));
    }

    @org.junit.Test
    public void testAddMapIntoParameterByName() {
        java.util.function.BiConsumer<spoon.pattern.internal.parameter.ParameterInfo, spoon.support.util.ImmutableMap> checker = ( namedParam, empty) -> {
            spoon.support.util.ImmutableMap val = namedParam.addValueAs(empty, java.util.Collections.emptyMap());
            org.junit.Assert.assertEquals(map().put("map", new spoon.support.util.ImmutableMapImpl()), val.asMap());
            val = namedParam.addValueAs(empty, map().put("year", 2018));
            org.junit.Assert.assertEquals(map().put("map", new spoon.support.util.ImmutableMapImpl().putValue("year", 2018)), val.asMap());
            val = namedParam.addValueAs(empty, map().put("year", 2018).put("age", 1111));
            org.junit.Assert.assertEquals(map().put("map", new spoon.support.util.ImmutableMapImpl().putValue("year", 2018).putValue("age", 1111)), val.asMap());
            org.junit.Assert.assertSame(val, namedParam.addValueAs(val, null));
            org.junit.Assert.assertSame(val, namedParam.addValueAs(val, entry("year", 2018)));
            org.junit.Assert.assertNull(namedParam.addValueAs(val, entry("year", 1111)));
        };
        checker.accept(new spoon.pattern.internal.parameter.MapParameterInfo("map").setContainerKind(spoon.reflect.meta.ContainerKind.MAP), new spoon.support.util.ImmutableMapImpl());
        checker.accept(new spoon.pattern.internal.parameter.MapParameterInfo("map").setContainerKind(spoon.reflect.meta.ContainerKind.MAP), new spoon.support.util.ImmutableMapImpl().putValue("map", null));
        checker.accept(new spoon.pattern.internal.parameter.MapParameterInfo("map").setContainerKind(spoon.reflect.meta.ContainerKind.MAP), new spoon.support.util.ImmutableMapImpl().putValue("map", java.util.Collections.emptyMap()));
        checker.accept(new spoon.pattern.internal.parameter.MapParameterInfo("map"), new spoon.support.util.ImmutableMapImpl().putValue("map", java.util.Collections.emptyMap()));
        checker.accept(new spoon.pattern.internal.parameter.MapParameterInfo("map"), new spoon.support.util.ImmutableMapImpl().putValue("map", new spoon.support.util.ImmutableMapImpl()));
        checker.accept(new spoon.pattern.internal.parameter.MapParameterInfo("map"), null);
    }

    @org.junit.Test
    public void testAddListIntoParameterByName() {
        java.util.function.BiConsumer<spoon.pattern.internal.parameter.ParameterInfo, spoon.support.util.ImmutableMap> checker = ( namedParam, empty) -> {
            spoon.support.util.ImmutableMap val = namedParam.addValueAs(empty, java.util.Collections.emptyList());
            org.junit.Assert.assertEquals(map().put("list", java.util.Collections.emptyList()), val.asMap());
            val = namedParam.addValueAs(empty, java.util.Arrays.asList(2018));
            org.junit.Assert.assertEquals(map().put("list", java.util.Arrays.asList(2018)), val.asMap());
            val = namedParam.addValueAs(empty, java.util.Arrays.asList(2018, 1111));
            org.junit.Assert.assertEquals(map().put("list", java.util.Arrays.asList(2018, 1111)), val.asMap());
            org.junit.Assert.assertSame(val, namedParam.addValueAs(val, null));
        };
        checker.accept(new spoon.pattern.internal.parameter.MapParameterInfo("list").setContainerKind(spoon.reflect.meta.ContainerKind.LIST), new spoon.support.util.ImmutableMapImpl());
        checker.accept(new spoon.pattern.internal.parameter.MapParameterInfo("list").setContainerKind(spoon.reflect.meta.ContainerKind.LIST), new spoon.support.util.ImmutableMapImpl().putValue("list", null));
        checker.accept(new spoon.pattern.internal.parameter.MapParameterInfo("list").setContainerKind(spoon.reflect.meta.ContainerKind.LIST), new spoon.support.util.ImmutableMapImpl().putValue("list", java.util.Collections.emptyList()));
        checker.accept(new spoon.pattern.internal.parameter.MapParameterInfo("list").setContainerKind(spoon.reflect.meta.ContainerKind.LIST), new spoon.support.util.ImmutableMapImpl().putValue("list", java.util.Collections.emptySet()));
        checker.accept(new spoon.pattern.internal.parameter.MapParameterInfo("list"), new spoon.support.util.ImmutableMapImpl().putValue("list", java.util.Collections.emptyList()));
        checker.accept(new spoon.pattern.internal.parameter.MapParameterInfo("list"), null);
    }

    @org.junit.Test
    public void testAddSetIntoParameterByName() {
        java.util.function.BiConsumer<spoon.pattern.internal.parameter.ParameterInfo, spoon.support.util.ImmutableMap> checker = ( namedParam, empty) -> {
            spoon.support.util.ImmutableMap val = namedParam.addValueAs(empty, java.util.Collections.emptySet());
            org.junit.Assert.assertEquals(map().put("list", java.util.Collections.emptySet()), val.asMap());
            val = namedParam.addValueAs(empty, spoon.test.template.core.ParameterInfoTest.asSet(2018));
            org.junit.Assert.assertEquals(map().put("list", spoon.test.template.core.ParameterInfoTest.asSet(2018)), val.asMap());
            val = namedParam.addValueAs(empty, spoon.test.template.core.ParameterInfoTest.asSet(2018, 1111));
            org.junit.Assert.assertEquals(map().put("list", spoon.test.template.core.ParameterInfoTest.asSet(2018, 1111)), val.asMap());
            org.junit.Assert.assertSame(val, namedParam.addValueAs(val, null));
            org.junit.Assert.assertSame(val, namedParam.addValueAs(val, 1111));
            org.junit.Assert.assertSame(val, namedParam.addValueAs(val, spoon.test.template.core.ParameterInfoTest.asSet(1111)));
            org.junit.Assert.assertSame(val, namedParam.addValueAs(val, spoon.test.template.core.ParameterInfoTest.asSet(2018, 1111)));
        };
        checker.accept(new spoon.pattern.internal.parameter.MapParameterInfo("list").setContainerKind(spoon.reflect.meta.ContainerKind.SET), new spoon.support.util.ImmutableMapImpl());
        checker.accept(new spoon.pattern.internal.parameter.MapParameterInfo("list").setContainerKind(spoon.reflect.meta.ContainerKind.SET), new spoon.support.util.ImmutableMapImpl().putValue("list", null));
        checker.accept(new spoon.pattern.internal.parameter.MapParameterInfo("list").setContainerKind(spoon.reflect.meta.ContainerKind.SET), new spoon.support.util.ImmutableMapImpl().putValue("list", java.util.Collections.emptySet()));
        checker.accept(new spoon.pattern.internal.parameter.MapParameterInfo("list").setContainerKind(spoon.reflect.meta.ContainerKind.SET), new spoon.support.util.ImmutableMapImpl().putValue("list", java.util.Collections.emptyList()));
        checker.accept(new spoon.pattern.internal.parameter.MapParameterInfo("list"), new spoon.support.util.ImmutableMapImpl().putValue("list", java.util.Collections.emptySet()));
        checker.accept(new spoon.pattern.internal.parameter.MapParameterInfo("list"), null);
    }

    @org.junit.Test
    public void testFailOnUnpectedContainer() {
        spoon.pattern.internal.parameter.ParameterInfo namedParam = new spoon.pattern.internal.parameter.MapParameterInfo("year").setContainerKind(spoon.reflect.meta.ContainerKind.LIST);
        try {
            namedParam.addValueAs(new spoon.support.util.ImmutableMapImpl().putValue("year", "unexpected"), 1);
            org.junit.Assert.fail();
        } catch (java.lang.Exception e) {
        }
    }

    @org.junit.Test
    public void testSetEmptyMap() {
        spoon.pattern.internal.parameter.ParameterInfo namedParam = new spoon.pattern.internal.parameter.MapParameterInfo("year").setContainerKind(spoon.reflect.meta.ContainerKind.MAP);
        {
            spoon.support.util.ImmutableMap val = namedParam.addValueAs(null, null);
            org.junit.Assert.assertNotNull(val);
            org.junit.Assert.assertEquals(map().put("year", new spoon.support.util.ImmutableMapImpl()), val.asMap());
        }
    }

    private spoon.test.template.core.ParameterInfoTest.MapBuilder map() {
        return new spoon.test.template.core.ParameterInfoTest.MapBuilder();
    }

    private java.util.Map.Entry<java.lang.String, java.lang.Object> entry(java.lang.String key, java.lang.Object value) {
        return new java.util.Map.Entry<java.lang.String, java.lang.Object>() {
            @java.lang.Override
            public java.lang.Object setValue(java.lang.Object value) {
                throw new java.lang.RuntimeException();
            }

            @java.lang.Override
            public java.lang.Object getValue() {
                return value;
            }

            @java.lang.Override
            public java.lang.String getKey() {
                return key;
            }
        };
    }

    class MapBuilder extends java.util.LinkedHashMap<java.lang.String, java.lang.Object> {
        public spoon.test.template.core.ParameterInfoTest.MapBuilder put(java.lang.String key, java.lang.Object value) {
            super.put(key, value);
            return this;
        }
    }

    private static java.util.Set asSet(java.lang.Object... objects) {
        return new java.util.HashSet<>(java.util.Arrays.asList(objects));
    }
}

