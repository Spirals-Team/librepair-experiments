/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.esigate.test.conn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.esigate.http.IncomingRequest;
import org.esigate.test.http.HttpResponseBuilder;
import org.junit.Test;

/**
 * Tests on UriResponse.
 * 
 * @author Nicolas Richeton
 * 
 */
public class UriResponseTest {

    @Test
    public void testUri() throws IOException {
        UriResponse seq =
                new UriResponse().response("http://test/path1",
                        new HttpResponseBuilder().status(HttpStatus.SC_OK).entity("OK 1").build()).response(
                        "http://test/path2", new HttpResponseBuilder().status(HttpStatus.SC_OK).entity("OK 2").build());

        assertEquals("OK 1",
                EntityUtils.toString(seq.execute(IncomingRequest.builder("http://test/path1").build()).getEntity()));
        assertEquals("OK 2",
                EntityUtils.toString(seq.execute(IncomingRequest.builder("http://test/path2").build()).getEntity()));

        try {
            seq.execute(IncomingRequest.builder("http://test/path3").build());
            fail("Should send an exception");
        } catch (IllegalStateException e) {
            // OK
        }
    }
}
