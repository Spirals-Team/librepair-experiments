/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.druid.tests.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.google.inject.Inject;
import io.druid.guice.annotations.Client;
import io.druid.guice.http.LifecycleUtils;
import io.druid.https.SSLClientConfig;
import io.druid.java.util.common.ISE;
import io.druid.java.util.common.StringUtils;
import io.druid.java.util.common.lifecycle.Lifecycle;
import io.druid.java.util.common.logger.Logger;
import io.druid.java.util.http.client.CredentialedHttpClient;
import io.druid.java.util.http.client.HttpClient;
import io.druid.java.util.http.client.HttpClientConfig;
import io.druid.java.util.http.client.HttpClientInit;
import io.druid.java.util.http.client.Request;
import io.druid.java.util.http.client.auth.BasicCredentials;
import io.druid.java.util.http.client.response.StatusResponseHandler;
import io.druid.java.util.http.client.response.StatusResponseHolder;
import io.druid.server.security.TLSUtils;
import io.druid.testing.IntegrationTestingConfig;
import io.druid.testing.guice.DruidTestModuleFactory;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.ws.rs.core.MediaType;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Guice(moduleFactory = DruidTestModuleFactory.class)
public class ITTLSTest
{
  private static final Logger LOG = new Logger(ITTLSTest.class);

  @Inject
  IntegrationTestingConfig config;

  @Inject
  ObjectMapper jsonMapper;

  @Inject
  SSLClientConfig sslClientConfig;

  @Inject
  @Client
  HttpClient httpClient;

  StatusResponseHandler responseHandler = new StatusResponseHandler(StandardCharsets.UTF_8);

  @Test
  public void testTLSAccess() throws Exception
  {
    HttpClient adminClient = new CredentialedHttpClient(
        new BasicCredentials("admin", "priest"),
        httpClient
    );

    // access resources without TLS on every Druid server as admin
    checkNodeAccess(adminClient);

    // access TLS-secured resources on every Druid server as admin
    checkTLSNodeAccess(adminClient);

    // check that access fails when we do not provide a client certificate
    checkAccessWithNoCert();

    // check that access fails when the client certificate does not match the local hostname
    checkAccessWithWrongHostname();
  }

  private void checkNodeAccess(HttpClient httpClient)
  {
    makeRequest(httpClient, HttpMethod.GET, config.getCoordinatorUrl() + "/status", null);
    makeRequest(httpClient, HttpMethod.GET, config.getIndexerUrl() + "/status", null);
    makeRequest(httpClient, HttpMethod.GET, config.getBrokerUrl() + "/status", null);
    makeRequest(httpClient, HttpMethod.GET, config.getHistoricalUrl() + "/status", null);
    makeRequest(httpClient, HttpMethod.GET, config.getRouterUrl() + "/status", null);
  }

  private void checkTLSNodeAccess(HttpClient httpClient)
  {
    makeRequest(httpClient, HttpMethod.GET, config.getCoordinatorTLSUrl() + "/status", null);
    makeRequest(httpClient, HttpMethod.GET, config.getIndexerTLSUrl() + "/status", null);
    makeRequest(httpClient, HttpMethod.GET, config.getBrokerTLSUrl() + "/status", null);
    makeRequest(httpClient, HttpMethod.GET, config.getHistoricalTLSUrl() + "/status", null);
    makeRequest(httpClient, HttpMethod.GET, config.getRouterTLSUrl() + "/status", null);
  }

  private void checkAccessWithNoCert()
  {
    HttpClient certlessClient = makeCertlessClient();
    checkCertlessClientAccessFails(certlessClient, HttpMethod.GET, config.getCoordinatorTLSUrl() + "/status");
    checkCertlessClientAccessFails(certlessClient, HttpMethod.GET, config.getIndexerTLSUrl() + "/status");
    checkCertlessClientAccessFails(certlessClient, HttpMethod.GET, config.getBrokerTLSUrl() + "/status");
    checkCertlessClientAccessFails(certlessClient, HttpMethod.GET, config.getHistoricalTLSUrl() + "/status");
    checkCertlessClientAccessFails(certlessClient, HttpMethod.GET, config.getRouterTLSUrl() + "/status");
  }

  private void checkAccessWithWrongHostname()
  {
    HttpClient wrongHostnameClient = makeWrongHostnameClient();
    checkWrongHostnameFails(wrongHostnameClient, HttpMethod.GET, config.getCoordinatorTLSUrl() + "/status");
    checkWrongHostnameFails(wrongHostnameClient, HttpMethod.GET, config.getIndexerTLSUrl() + "/status");
    checkWrongHostnameFails(wrongHostnameClient, HttpMethod.GET, config.getBrokerTLSUrl() + "/status");
    checkWrongHostnameFails(wrongHostnameClient, HttpMethod.GET, config.getHistoricalTLSUrl() + "/status");
    checkWrongHostnameFails(wrongHostnameClient, HttpMethod.GET, config.getRouterTLSUrl() + "/status");
  }


  private HttpClient makeCertlessClient()
  {
    SSLContext certlessClientSSLContext = TLSUtils.createSSLContext(
        sslClientConfig.getProtocol(),
        sslClientConfig.getTrustStoreType(),
        sslClientConfig.getTrustStorePath(),
        sslClientConfig.getTrustStoreAlgorithm(),
        sslClientConfig.getTrustStorePasswordProvider(),
        null,
        null,
        null,
        null,
        null,
        null
    );

    final HttpClientConfig.Builder builder = HttpClientConfig
        .builder()
        .withSslContext(certlessClientSSLContext);

    final Lifecycle lifecycle = new Lifecycle();

    return HttpClientInit.createClient(
        builder.build(),
        LifecycleUtils.asMmxLifecycle(lifecycle)
    );
  }

  private HttpClient makeWrongHostnameClient()
  {
    System.out.println("SSLCLIENTCONFIG: " + sslClientConfig);
    SSLContext wrongHostnameSSLContext = TLSUtils.createSSLContext(
        sslClientConfig.getProtocol(),
        sslClientConfig.getTrustStoreType(),
        sslClientConfig.getTrustStorePath(),
        sslClientConfig.getTrustStoreAlgorithm(),
        sslClientConfig.getTrustStorePasswordProvider(),
        sslClientConfig.getKeyStoreType(),
        "client_tls/invalid_hostname_client.jks",
        sslClientConfig.getKeyManagerFactoryAlgorithm(),
        sslClientConfig.getCertAlias(),
        sslClientConfig.getKeyStorePasswordProvider(),
        sslClientConfig.getKeyManagerPasswordProvider()
    );

    final HttpClientConfig.Builder builder = HttpClientConfig
        .builder()
        .withSslContext(wrongHostnameSSLContext);

    final Lifecycle lifecycle = new Lifecycle();

    return HttpClientInit.createClient(
        builder.build(),
        LifecycleUtils.asMmxLifecycle(lifecycle)
    );
  }

  private void checkWrongHostnameFails(HttpClient httpClient, HttpMethod method, String url)
  {
    try {
      makeRequest(httpClient, method, url, null, -1);
    }
    catch (RuntimeException re) {

      Throwable rootCause = Throwables.getRootCause(re);

      LOG.info("RCCCCCC " + rootCause.toString());

      Assert.assertTrue(rootCause instanceof SSLException);

      Assert.assertEquals(
          rootCause.getMessage(),
          "Received fatal alert: bad_certificate"
      );

      LOG.info("Wrong hostname client [%s] request failed as expected when accessing [%s]", method, url);
      return;
    }
    Assert.fail("Test failed, did not get SSLException.");
  }


  private void checkCertlessClientAccessFails(HttpClient httpClient, HttpMethod method, String url)
  {
    try {
      makeRequest(httpClient, method, url, null, -1);
    }
    catch (RuntimeException re) {

      Throwable rootCause = Throwables.getRootCause(re);

      Assert.assertTrue(rootCause instanceof SSLException);

      Assert.assertEquals(
          rootCause.getMessage(),
          "Received fatal alert: bad_certificate"
      );

      LOG.info("Certless client [%s] request failed as expected when accessing [%s]", method, url);
      return;
    }
    Assert.fail("Test failed, did not get SSLException.");
  }

  private StatusResponseHolder makeRequest(HttpClient httpClient, HttpMethod method, String url, byte[] content)
  {
    return makeRequest(httpClient, method, url, content, 4);
  }

  private StatusResponseHolder makeRequest(HttpClient httpClient, HttpMethod method, String url, byte[] content, int maxRetries)
  {
    try {
      Request request = new Request(method, new URL(url));
      if (content != null) {
        request.setContent(MediaType.APPLICATION_JSON, content);
      }
      int retryCount = 0;

      StatusResponseHolder response;

      while (true) {
        response = httpClient.go(
            request,
            responseHandler
        ).get();

        if (!response.getStatus().equals(HttpResponseStatus.OK)) {
          String errMsg = StringUtils.format(
              "Error while making request to url[%s] status[%s] content[%s]",
              url,
              response.getStatus(),
              response.getContent()
          );
          if (retryCount > maxRetries) {
            throw new ISE(errMsg);
          } else {
            LOG.error(errMsg);
            LOG.error("retrying in 3000ms, retryCount: " + retryCount);
            retryCount++;
            Thread.sleep(3000);
          }
        } else {
          LOG.info("[%s] request to [%s] succeeded.", method, url);
          break;
        }
      }
      return response;
    }
    catch (Exception e) {
      throw Throwables.propagate(e);
    }
  }
}
