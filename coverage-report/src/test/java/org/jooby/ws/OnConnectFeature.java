package org.jooby.ws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.jooby.test.ServerFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.ws.WebSocket;
import com.ning.http.client.ws.WebSocketTextListener;
import com.ning.http.client.ws.WebSocketUpgradeHandler;

public class OnConnectFeature extends ServerFeature {

  {
    ws("/connect", ws -> {
      ws.send("connected!", () -> {
        Thread.sleep(300L);
        ws.close();
      });
    });

  }

  private AsyncHttpClient client;

  @Before
  public void before() {
    client = new AsyncHttpClient(new AsyncHttpClientConfig.Builder().build());
  }

  @After
  public void after() {
    client.close();
  }

  @Test
  public void connect() throws Exception {

    LinkedList<String> messages = new LinkedList<>();

    CountDownLatch latch = new CountDownLatch(2);

    client.prepareGet(ws("connect").toString())
        .execute(new WebSocketUpgradeHandler.Builder().addWebSocketListener(
            new WebSocketTextListener() {

              @Override
              public void onMessage(final String message) {
                messages.add(message);
                latch.countDown();
              }

              @Override
              public void onOpen(final WebSocket websocket) {
              }

              @Override
              public void onClose(final WebSocket websocket) {
                latch.countDown();
              }

              @Override
              public void onError(final Throwable t) {
              }
            }).build())
        .get();

    if (latch.await(1L, TimeUnit.SECONDS)) {
      assertEquals(Arrays.asList("connected!"), messages);
    }
  }

  @Test
  public void notfound() throws Exception {

    try {
      client.prepareGet(ws("notfound").toString())
          .execute(new WebSocketUpgradeHandler.Builder().addWebSocketListener(
              new WebSocketTextListener() {

                @Override
                public void onMessage(final String message) {
                }

                @Override
                public void onOpen(final WebSocket websocket) {
                }

                @Override
                public void onClose(final WebSocket websocket) {
                }

                @Override
                public void onError(final Throwable t) {
                }
              }).build())
          .get();
    } catch (ExecutionException ex) {
      assertTrue(ex.getCause().getMessage().contains("404"));
    }
  }

}
