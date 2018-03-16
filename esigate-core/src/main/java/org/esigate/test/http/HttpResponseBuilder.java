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
package org.esigate.test.http;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.esigate.http.BasicCloseableHttpResponse;

/**
 * Fluent-style builder for HttpResponse.
 * 
 * <p>
 * Default response is
 * 
 * <pre>
 * 200 OK  HTTP/1.1
 * </pre>
 * 
 * @author Nicolas Richeton
 * 
 */
public class HttpResponseBuilder {

    private ProtocolVersion protocolVersion = new ProtocolVersion("HTTP", 1, 1);
    private int status = HttpStatus.SC_OK;
    private String reason = "Ok";
    private List<Header> headers = new ArrayList<>();
    private HttpEntity entity = null;

    /**
     * Build the HTTP response using all data previously set on this builder and/or use defaults.
     * 
     * @return The HTTP response
     */
    public CloseableHttpResponse build() {
        BasicHttpResponse response = new BasicHttpResponse(this.protocolVersion, this.status, this.reason);

        for (Header h : this.headers) {
            response.addHeader(h.getName(), h.getValue());
        }

        if (this.entity != null) {
            response.setEntity(this.entity);
        }
        return BasicCloseableHttpResponse.adapt(response);
    }

    public HttpResponseBuilder entity(HttpEntity paramEntity) {
        this.entity = paramEntity;
        if (this.entity.getContentType() != null) {
            this.headers.add(this.entity.getContentType());
        }
        return this;
    }

    public HttpResponseBuilder entity(String entityBody) throws UnsupportedEncodingException {
        this.entity = new StringEntity(entityBody);
        return this;
    }

    public HttpResponseBuilder header(String name, String value) {
        this.headers.add(new BasicHeader(name, value));
        return this;
    }

    public HttpResponseBuilder protocolVersion(ProtocolVersion paramProtocolVersion) {
        this.protocolVersion = paramProtocolVersion;
        return this;
    }

    public HttpResponseBuilder reason(String paramReason) {
        this.reason = paramReason;
        return this;
    }

    /**
     * Set HTTP Status.
     * 
     * @param paramStatus
     *            the response status.
     * @return this builder
     */
    public HttpResponseBuilder status(int paramStatus) {
        this.status = paramStatus;
        return this;
    }
}
