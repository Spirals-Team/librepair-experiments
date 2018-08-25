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
import javax.servlet.ServletException;

/**
 * The WebApplicationServer API.
 *
 * @param <R> the request type.
 * @param <S> the response type.
 * @author Manfred Riem (mriem@manorrock.com)
 */
public interface WebApplicationServer<R, S> {

    /**
     * Add a web application.
     *
     * @param webApplication the web application to add.
     */
    void addWebApplication(WebApplication webApplication);

    /**
     * Get the request mapper.
     *
     * @return the request mapper.
     */
    WebApplicationServerRequestMapper getRequestMapper();

    /**
     * Service the request and response.
     *
     * @param request the HTTP request.
     * @param response the HTTP response.
     * @throws IOException when an I/O error occurs.
     * @throws ServletException when a Servlet error occurs.
     */
    void service(R request, S response) throws IOException, ServletException;

    /**
     * Initialize the server.
     */
    void initialize();

    /**
     * Set the request mapper.
     *
     * @param requestMapper the request mapper.
     */
    void setRequestMapper(WebApplicationServerRequestMapper requestMapper);

    /**
     * Start the server.
     */
    void start();

    /**
     * Stop the server.
     */
    void stop();
}
