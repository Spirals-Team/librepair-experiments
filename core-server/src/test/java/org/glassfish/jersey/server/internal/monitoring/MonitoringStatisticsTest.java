/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package org.glassfish.jersey.server.internal.monitoring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.glassfish.jersey.server.model.ResourceModel;
import org.glassfish.jersey.server.monitoring.ExecutionStatistics;
import org.glassfish.jersey.server.monitoring.ResourceMethodStatistics;
import org.glassfish.jersey.server.monitoring.ResourceStatistics;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Miroslav Fuksa
 */
public class MonitoringStatisticsTest {

    @Path("/test-resource")
    public static class TestResource {

        @GET
        public String get() {
            return "get";
        }

        @Path("child")
        @GET
        public String childGet() {
            return "childGet";
        }

        @Path("child")
        @POST
        public void childPost() {
        }

        @Path("child")
        @PUT
        public void childPut() {
        }

    }


    @Path("hello")
    public static class HelloResource {

        @GET
        public String get() {
            return "hello";
        }

        @POST
        public void post() {
        }

        @Path("/world")
        @GET
        public String childGet() {
            return "hello-world";
        }
    }


    public static class MyInflector implements Inflector<ContainerRequestContext, Object> {
        @Override
        public Object apply(ContainerRequestContext containerRequestContext) {
            return Response.ok().build();
        }
    }

    @Test
    public void testSimpleUris() {
        final MonitoringStatisticsImpl stats = getSimpleStats();
        final Set<String> keys = stats.getUriStatistics().keySet();
        final Iterator<String> iterator = keys.iterator();
        Assert.assertEquals("/hello", iterator.next());
        Assert.assertEquals("/hello/world", iterator.next());
        Assert.assertEquals("/test-resource", iterator.next());
        Assert.assertEquals("/test-resource/child", iterator.next());
    }

    private MonitoringStatisticsImpl getSimpleStats() {
        final List<Resource> resources = Stream.of(TestResource.class, HelloResource.class)
                                               .map(Resource::from)
                                               .collect(Collectors.toList());

        ResourceModel model = new ResourceModel.Builder(resources, false).build();
        MonitoringStatisticsImpl.Builder monBuilder = new MonitoringStatisticsImpl.Builder(model);
        return monBuilder.build();
    }

    private MonitoringStatisticsImpl.Builder getProgStats() {
        final Resource.Builder testBuilder = Resource.builder(TestResource.class);
        testBuilder.addChildResource("/prog-child").addMethod("GET").handledBy(MyInflector.class);
        final List<Resource> resources = new ArrayList<>();
        resources.add(testBuilder.build());
        resources.add(Resource.from(HelloResource.class));
        final Resource.Builder prog = Resource.builder("prog");
        prog.addMethod("GET").handledBy(MyInflector.class);

        resources.add(prog.build());

        ResourceModel model = new ResourceModel.Builder(resources, false).build();
        return new MonitoringStatisticsImpl.Builder(model);
    }

    @Test
    public void testSimpleResourceClasses() {
        final MonitoringStatisticsImpl stats = getSimpleStats();
        final Set<Class<?>> keys = stats.getResourceClassStatistics().keySet();
        final Iterator<Class<?>> it = keys.iterator();

        Assert.assertEquals(HelloResource.class, it.next());
        Assert.assertEquals(TestResource.class, it.next());
    }

    @Test
    public void testResourceClassesWithProgrammaticResources() {
        final MonitoringStatisticsImpl stats = getProgStats().build();
        final Set<Class<?>> keys = stats.getResourceClassStatistics().keySet();
        final Iterator<Class<?>> it = keys.iterator();

        Assert.assertEquals(HelloResource.class, it.next());
        Assert.assertEquals(MyInflector.class, it.next());
        Assert.assertEquals(TestResource.class, it.next());
    }

    @Test
    public void testUrisWithProgrammaticResources() {
        final MonitoringStatisticsImpl stats = getProgStats().build();
        final Iterator<Map.Entry<String, ResourceStatistics>> it = stats.getUriStatistics().entrySet().iterator();

        check(it, "/hello", 2);
        check(it, "/hello/world", 1);
        check(it, "/prog", 1);
        check(it, "/test-resource", 1);
        check(it, "/test-resource/child", 3);
        check(it, "/test-resource/prog-child", 1);
    }


    @Test
    public void testUrisWithProgrammaticResourcesAndExecution() {
        final MonitoringStatisticsImpl.Builder statBuilder = getProgStats();

        final Resource.Builder resourceBuilder = Resource.builder();
        resourceBuilder.addMethod("GET").handledBy(MyInflector.class);
        resourceBuilder.addMethod("POST").handledBy(MyInflector.class);
        final Resource res = resourceBuilder.build();
        ResourceMethod getMethod;
        ResourceMethod postMethod;
        if (res.getResourceMethods().get(0).getHttpMethod().equals("GET")) {
            getMethod = res.getResourceMethods().get(0);
            postMethod = res.getResourceMethods().get(1);
        } else {
            getMethod = res.getResourceMethods().get(1);
            postMethod = res.getResourceMethods().get(0);
        }

        statBuilder.addExecution("/new/elefant", getMethod, 10, 5, 8, 8);
        statBuilder.addExecution("/new/elefant", getMethod, 20, 12, 18, 10);
        statBuilder.addExecution("/new/elefant", postMethod, 30, 2, 28, 4);

        final MonitoringStatisticsImpl stat = statBuilder.build();
        final Iterator<Map.Entry<String, ResourceStatistics>> it = stat.getUriStatistics().entrySet().iterator();

        check(it, "/hello", 2);
        check(it, "/hello/world", 1);
        check(it, "/new/elefant", 2);
        check(it, "/prog", 1);
        check(it, "/test-resource", 1);
        check(it, "/test-resource/child", 3);
        check(it, "/test-resource/prog-child", 1);

        final Map<ResourceMethod, ResourceMethodStatistics> resourceMethodStatistics
                = stat.getUriStatistics().get("/new/elefant").getResourceMethodStatistics();
        for (ResourceMethodStatistics methodStatistics : resourceMethodStatistics.values()) {
            final ResourceMethod method = methodStatistics.getResourceMethod();
            final ExecutionStatistics st = methodStatistics.getMethodStatistics();
            if (method.getHttpMethod().equals("GET")) {
                Assert.assertEquals(20, st.getLastStartTime().getTime());
            } else if (method.getHttpMethod().equals("POST")) {
                Assert.assertEquals(30, st.getLastStartTime().getTime());
            } else {
                Assert.fail();
            }
        }
    }


    private void check(Iterator<Map.Entry<String, ResourceStatistics>> it,
                       String expectedUri, int expectedMethods) {
        Map.Entry<String, ResourceStatistics> entry = it.next();
        Assert.assertEquals(expectedUri, entry.getKey());
        Assert.assertEquals(expectedMethods, entry.getValue().getResourceMethodStatistics().size());
    }


}
