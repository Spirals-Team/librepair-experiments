/*
 * Copyright (c) 2010, 2018 Oracle and/or its affiliates. All rights reserved.
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

package org.glassfish.jersey.simple;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TraceTest extends AbstractSimpleServerTester {

    @Path("helloworld")
    public static class HelloWorldResource {
        public static final String CLICHED_MESSAGE = "Hello World!";

        @GET
        @Produces("text/plain")
        public String getHello() {
            return CLICHED_MESSAGE;
        }
    }

    @Path("/users")
    public class UserResource {

        @Path("/current")
        @GET
        @Produces("text/plain")
        public String getCurrentUser() {
            return "current user";
        }
    }

    private Client client;

    @Before
    public void setUp() throws Exception {
        startServerNoLoggingFilter(HelloWorldResource.class, UserResource.class); // disable crude
        // LoggingFilter
        setDebug(true);
        client = ClientBuilder.newClient();
    }

    @Override
    @After
    public void tearDown() {
        super.tearDown();
        client = null;
    }


    @Test
    public void testFooBarOptions() {
        for (int i = 0; i < 100; i++) {
            Response response = client.target(getUri()).path("helloworld").request()
                                      .header("Accept", "foo/bar").options();
            assertEquals(200, response.getStatus());
            final String allowHeader = response.getHeaderString("Allow");
            _checkAllowContent(allowHeader);
            assertEquals(0, response.getLength());
            assertEquals("foo/bar", response.getMediaType().toString());

            try {
                Thread.sleep(50);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void _checkAllowContent(final String content) {
        assertTrue(content.contains("GET"));
        assertTrue(content.contains("HEAD"));
        assertTrue(content.contains("OPTIONS"));
    }

    @Test
    public void testNoDefaultMethod() {
        Response response = client.target(getUri()).path("/users").request().options();
        assertThat(response.getStatus(), is(404));
    }
}
