package org.jooby.issues;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.jooby.json.Jackson;
import org.jooby.mvc.Consumes;
import org.jooby.mvc.Path;
import org.jooby.mvc.Produces;
import org.jooby.test.ServerFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.ws.WebSocket;
import com.ning.http.client.ws.WebSocketTextListener;
import com.ning.http.client.ws.WebSocketUpgradeHandler;

public class Issue548b extends ServerFeature {

  public static class User {
    public String name;

    @Override
    public String toString() {
      return name;
    }
  }

  @Path("/548d/more-annotations")
  @Produces("json")
  @Consumes("json")
  public static class MySocket implements org.jooby.WebSocket.OnMessage<User> {

    private org.jooby.WebSocket ws;

    @Inject
    public MySocket(final org.jooby.WebSocket ws) {
      this.ws = ws;
    }

    @Override
    public void onMessage(final User message) throws Exception {
      User rsp = new User();
      rsp.name = message.name + " marmol";
      ws.send(rsp);
    }

  }

  {
    use(new Jackson());

    ws(MySocket.class);
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
  public void shouldParseAndRenderJsonViaAnnotationsOnTopLevelClass() throws Exception {
    LinkedList<String> messages = new LinkedList<>();

    CountDownLatch latch = new CountDownLatch(1);

    client.prepareGet(ws("548d/more-annotations").toString())
        .execute(new WebSocketUpgradeHandler.Builder().addWebSocketListener(
            new WebSocketTextListener() {

              @Override
              public void onMessage(final String message) {
                messages.add(message);
                latch.countDown();
              }

              @Override
              public void onOpen(final WebSocket websocket) {
                websocket.sendMessage("{\"name\":\"pablo\"}");
              }

              @Override
              public void onClose(final WebSocket websocket) {
              }

              @Override
              public void onError(final Throwable t) {
              }
            }).build())
        .get();
    if (latch.await(1L, TimeUnit.SECONDS)) {
      assertEquals("{\"name\":\"pablo marmol\"}", messages.get(0));
    }
  }

}
