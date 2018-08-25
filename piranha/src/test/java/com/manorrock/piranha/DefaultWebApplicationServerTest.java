/*
 *  Copyright (c) 2002-2018, Manorrock.com. All Rights Reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *      1. Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *
 *      2. Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 */
package com.manorrock.piranha;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 * The JUnit tests for the DefaultWebApplicationServer class.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class DefaultWebApplicationServerTest {

    /**
     * Test addMapping method.
     */
    @Test
    public void testAddMapping() {
        DefaultWebApplicationServer server = new DefaultWebApplicationServer();
        DefaultWebApplication webApp = new DefaultWebApplication();
        webApp.setServletContextName("mycontext");
        server.addWebApplication(webApp);
        server.addMapping("notthere", "notreal");
    }

    /**
     * Test addMapping method.
     */
    @Test
    public void testAddMapping2() {
        DefaultWebApplicationServer server = new DefaultWebApplicationServer();
        DefaultWebApplication webApp = new DefaultWebApplication();
        webApp.setServletContextName("mycontext");
        server.addWebApplication(webApp);
        server.addMapping("mycontext", "mycontextmapping");
    }

    /**
     * Test addMapping method.
     */
    @Test
    public void testAddMapping3() {
        DefaultWebApplicationServer server = new DefaultWebApplicationServer();
        DefaultWebApplication webApp = new DefaultWebApplication();
        webApp.setServletContextName("mycontext");
        server.addWebApplication(webApp);
        server.addMapping("mycontext", "myurlpattern");
        server.addMapping("mycontext", "myurlpattern");
    }

    /**
     * Test getRequestMapper method.
     */
    @Test
    public void testGetRequestMapper() {
        DefaultWebApplicationServer server = new DefaultWebApplicationServer();
        server.setRequestMapper(new DefaultWebApplicationServerRequestMapper());
        assertNotNull(server.getRequestMapper());
    }

    /**
     * Test service method.
     *
     * @throws Exception when a serious error occurs.
     */
    @Test
    public void testService() throws Exception {
        DefaultWebApplicationServer server = new DefaultWebApplicationServer();
        DefaultHttpServletRequest request = new TestHttpServletRequest();
        DefaultHttpServletResponse response = new TestHttpServletResponse();
        TestServletOutputStream outputStream = new TestServletOutputStream();
        response.setOutputStream(outputStream);
        outputStream.setResponse(response);
        server.service(request, response);
        assertEquals(404, response.getStatus());
    }

    /**
     * Test service method.
     *
     * @throws Exception when a serious error occurs.
     */
    @Test
    public void testService2() throws Exception {
        DefaultWebApplicationServer server = new DefaultWebApplicationServer();
        DefaultWebApplicationRequestMapper webApplicationRequestMapper = new DefaultWebApplicationRequestMapper();
        DefaultWebApplication webApp = new DefaultWebApplication();
        webApp.setWebApplicationRequestMapper(webApplicationRequestMapper);
        webApp.addServlet("snoop", new TestSnoopServlet());
        webApp.addServletMapping("snoop", "/snoop/*");
        webApp.setContextPath("/context");
        server.addWebApplication(webApp);
        DefaultHttpServletRequest request = new TestHttpServletRequest();
        request.setContextPath("/context");
        request.setPathInfo("/snoop/index.html");
        TestHttpServletResponse response = new TestHttpServletResponse();
        TestServletOutputStream outputStream = new TestServletOutputStream();
        response.setOutputStream(outputStream);
        outputStream.setResponse(response);
        server.initialize();
        server.start();
        server.service(request, response);
        server.stop();
        assertEquals(200, response.getStatus());
    }

    /**
     * Test process method.
     *
     * @throws Exception when a serious error occurs.
     */
    @Test
    public void testProcess() throws Exception {
        DefaultWebApplicationServer server = new DefaultWebApplicationServer();
        HttpServer httpServer = new DefaultHttpServer(7000, server);
        DefaultWebApplication webApp = new DefaultWebApplication();
        webApp.setContextPath("/context");
        webApp.addServlet("snoop", new TestSnoopServlet());
        webApp.addServletMapping("snoop", "/snoop/*");
        server.addWebApplication(webApp);
        server.initialize();
        server.start();
        httpServer.start();
        try {
            HttpClient client = HttpClients.createDefault();
            HttpGet request = new HttpGet("http://localhost:7000/context/snoop/index.html");
            HttpResponse response = client.execute(request);
            assertEquals(200, response.getStatusLine().getStatusCode());
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
        httpServer.stop();
        server.stop();
    }
}
