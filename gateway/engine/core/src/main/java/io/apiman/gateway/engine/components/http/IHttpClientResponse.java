/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.apiman.gateway.engine.components.http;

/**
 * An http client response is returned when an http client request is
 * made to some remote server.
 *
 * @author eric.wittmann@redhat.com
 */
public interface IHttpClientResponse {
    
    /**
     * @return the http response code
     */
    int getResponseCode();
    
    /**
     * @return the http response message
     */
    String getResponseMessage();
    
    /**
     * @param headerName the name of the http response header to get
     * @return a response header value
     */
    String getHeader(String headerName);
    
    /**
     * @return the http response body
     */
    String getBody();

    /**
     * Called to close the connection to the remote server.
     */
    void close();

}
