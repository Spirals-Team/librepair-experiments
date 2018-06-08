/*
 * Copyright (c) 2018 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.vertx.junit5;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="https://julien.ponge.org/">Julien Ponge</a>
 */
@DisplayName("Integration tests")
class IntegrationTest {

  static class HttpServerVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {
      vertx.createHttpServer()
        .requestHandler(request -> request.response().end("Plop"))
        .listen(8080, ar -> {
          if (ar.succeeded()) {
            startFuture.complete();
          } else {
            startFuture.fail(ar.cause());
          }
        });
    }
  }

  @Nested
  @DisplayName("Tests without parameter injection and explicit Vertx and VertxTestContext instances")
  class Naked {

    @Test
    @DisplayName("Start a HTTP server")
    void start_http_server() throws InterruptedException {
      VertxTestContext testContext = new VertxTestContext();

      Vertx vertx = Vertx.vertx();
      vertx.createHttpServer()
        .requestHandler(req -> req.response().end())
        .listen(16969, testContext.succeeding(ar -> testContext.completeNow()));

      assertThat(testContext.awaitCompletion(5, TimeUnit.SECONDS)).isTrue();
    }

    @Test
    @DisplayName("Start a HTTP server, then issue a HTTP client request and check the response")
    void vertx_check_http_server_response() throws InterruptedException {
      Vertx vertx = Vertx.vertx();
      VertxTestContext testContext = new VertxTestContext();

      vertx.deployVerticle(new HttpServerVerticle(), testContext.succeeding(id -> {
        WebClient client = WebClient.create(vertx);
        client.get(8080, "localhost", "/")
          .as(BodyCodec.string())
          .send(testContext.succeeding(response -> testContext.verify(() -> {
            assertThat(response.body()).isEqualTo("Plop");
            testContext.completeNow();
          })));
      }));

      assertThat(testContext.awaitCompletion(5, TimeUnit.SECONDS)).isTrue();
    }
  }

  @Nested
  @ExtendWith(VertxExtension.class)
  @DisplayName("Tests with parameter injection")
  class WithExtension {

    @Test
    @Timeout(10_000)
    @DisplayName("Start a HTTP server, make 10 client requests, and use several checkpoints")
    void start_and_request_http_server_with_checkpoints(Vertx vertx, VertxTestContext testContext) {
      Checkpoint serverStarted = testContext.checkpoint();
      Checkpoint requestsServed = testContext.checkpoint(10);
      Checkpoint responsesReceived = testContext.checkpoint(10);

      vertx.createHttpServer()
        .requestHandler(req -> {
          req.response().end("Ok");
          requestsServed.flag();
        })
        .listen(8080, ar -> {
          if (ar.failed()) {
            testContext.failNow(ar.cause());
          } else {
            serverStarted.flag();
          }
        });

      WebClient client = WebClient.create(vertx);
      for (int i = 0; i < 10; i++) {
        client.get(8080, "localhost", "/")
          .as(BodyCodec.string())
          .send(ar -> {
            if (ar.failed()) {
              testContext.failNow(ar.cause());
            } else {
              testContext.verify(() -> assertThat(ar.result().body()).isEqualTo("Ok"));
              responsesReceived.flag();
            }
          });
      }
    }
  }
}
