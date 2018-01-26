/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.servicecomb.common.rest.codec.param;

import javax.servlet.http.Part;
import javax.ws.rs.core.MediaType;

import org.junit.Assert;
import org.junit.Test;

import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.CaseInsensitiveHeaders;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpHeaders;
import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;

public class TestRestClientRequestImpl {
  @Mocked
  HttpClientRequest request;

  @Test
  public void testForm() throws Exception {
    RestClientRequestImpl restClientRequest = new RestClientRequestImpl(request, null, null);
    restClientRequest.addForm("abc", "Hello");
    restClientRequest.addForm("def", "world");
    restClientRequest.addForm("ghi", null);
    Buffer buffer = restClientRequest.getBodyBuffer();
    Assert.assertEquals("abc=Hello&def=world&", buffer.toString());
  }

  @Test
  public void testCookie() throws Exception {
    HttpClientRequest request = new MockUp<HttpClientRequest>() {

      MultiMap map = new CaseInsensitiveHeaders();

      @Mock
      public HttpClientRequest putHeader(CharSequence key, CharSequence val) {
        map.add(key, val);
        return null;
      }

      @Mock
      public MultiMap headers() {
        return map;
      }
    }.getMockInstance();
    RestClientRequestImpl restClientRequest = new RestClientRequestImpl(request, null, null);
    restClientRequest.addCookie("sessionid", "abcdefghijklmnopqrstuvwxyz");
    restClientRequest.addCookie("region", "china-north");
    restClientRequest.write(Buffer.buffer("I love servicecomb"));
    restClientRequest.end();
    Buffer buffer = restClientRequest.getBodyBuffer();
    Assert.assertEquals("I love servicecomb", buffer.toString());
    Assert.assertEquals("sessionid=abcdefghijklmnopqrstuvwxyz; region=china-north; ",
        restClientRequest.request.headers().get(HttpHeaders.COOKIE));
  }

  @Test
  public void fileBoundaryInfo_nullSubmittedFileName(@Mocked Part part) {
    new Expectations() {
      {
        part.getSubmittedFileName();
        result = null;
        part.getContentType();
        result = "abc";
      }
    };
    RestClientRequestImpl restClientRequest = new RestClientRequestImpl(request, null, null);
    Buffer buffer = restClientRequest.fileBoundaryInfo("boundary", "name", part);
    Assert.assertEquals("\r\n" +
        "--boundary\r\n" +
        "Content-Disposition: form-data; name=\"name\"; filename=\"null\"\r\n" +
        "Content-Type: abc\r\n" +
        "Content-Transfer-Encoding: binary\r\n" +
        "\r\n", buffer.toString());
  }

  @Test
  public void fileBoundaryInfo_validSubmittedFileName(@Mocked Part part) {
    new Expectations() {
      {
        part.getSubmittedFileName();
        result = "a.txt";
        part.getContentType();
        result = MediaType.TEXT_PLAIN;
      }
    };
    RestClientRequestImpl restClientRequest = new RestClientRequestImpl(request, null, null);
    Buffer buffer = restClientRequest.fileBoundaryInfo("boundary", "name", part);
    Assert.assertEquals("\r\n" +
        "--boundary\r\n" +
        "Content-Disposition: form-data; name=\"name\"; filename=\"a.txt\"\r\n" +
        "Content-Type: text/plain\r\n" +
        "Content-Transfer-Encoding: binary\r\n" +
        "\r\n", buffer.toString());
  }
}
