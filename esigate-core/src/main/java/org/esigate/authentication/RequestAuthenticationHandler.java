/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.esigate.authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.esigate.http.IncomingRequest;
import org.esigate.http.OutgoingRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Forward session and/or request attributes as HTTP request headers. Best when ESIGate is used in embedded mode.
 * <p>
 * Example : Forward the "username" session attribute as "X-ATTR-username" in the request fetching remote content.
 * <p>
 * Configuration uses the following attributes from driver.properties :
 * <p>
 * <ul>
 * <li>
 * <b>forwardSessionAttributes</b>: comma separated list of session attributes which will be forwarded.</li>
 * <li>
 * <b>forwardRequestAttributes</b>: comma separated list of request attributes which will be forwarded.</li>
 * <li><b>headerPrefix</b> : header prefix. Default is "X-ATTR-"</li>
 * </ul>
 * <p>
 * 
 * 
 * 
 * @author Nicolas Richeton
 */
public class RequestAuthenticationHandler extends GenericAuthentificationHandler {
    private static final Logger LOG = LoggerFactory.getLogger(RequestAuthenticationHandler.class);
    private final List<String> sessionAttributes = new ArrayList<>();
    private final List<String> requestAttributes = new ArrayList<>();
    private String headerPrefix = "X-ATTR-";

    @Override
    public boolean beforeProxy(HttpRequest httpRequest) {
        return true;
    }

    @Override
    public void init(Properties properties) {
        // Attributes for session
        String sessionAttributesProperty = properties.getProperty("forwardSessionAttributes");
        if (sessionAttributesProperty != null) {
            String[] attributes = sessionAttributesProperty.split(",");
            for (String attribute : attributes) {
                this.sessionAttributes.add(attribute.trim());
                if (LOG.isInfoEnabled()) {
                    LOG.info("Forwading session attribute: " + attribute);
                }
            }
        }

        // Attributes for request
        String requestAttributesProperty = (String) properties.get("forwardRequestAttributes");
        if (requestAttributesProperty != null) {
            String[] attributes = requestAttributesProperty.split(",");
            for (String attribute : attributes) {
                this.requestAttributes.add(attribute.trim());
                if (LOG.isInfoEnabled()) {
                    LOG.info("Forwading request attribute: " + attribute);
                }
            }
        }

        // Prefix name
        String headerPrefixProperty = (String) properties.get("headerPrefix");
        if (headerPrefixProperty != null) {
            this.headerPrefix = headerPrefixProperty;
        }
    }

    @Override
    public boolean needsNewRequest(HttpResponse response, OutgoingRequest outgoingRequest,
            IncomingRequest incomingRequest) {
        return false;
    }

    @Override
    public void preRequest(OutgoingRequest request, IncomingRequest httpRequest) {
        LOG.debug("preRequest");

        // Process session
        for (String attribute : this.sessionAttributes) {
            String value = (String) httpRequest.getSession().getAttribute(attribute);
            if (value != null) {
                LOG.debug("Adding session attribute {} ({}) as header ({}{})", attribute, value, this.headerPrefix,
                        attribute);
                request.addHeader(this.headerPrefix + attribute, value);
            }
        }

        // Process request
        for (String attribute : this.requestAttributes) {
            String value = httpRequest.getAttribute(attribute);
            if (value != null) {
                LOG.debug("Adding request attribute {} ({}) as header ({}{})", attribute, value, this.headerPrefix,
                        attribute);
                request.addHeader(this.headerPrefix + attribute, value);
            }
        }
    }

}
