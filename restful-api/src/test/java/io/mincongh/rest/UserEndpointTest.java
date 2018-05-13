package io.mincongh.rest;

import io.mincongh.rest.dto.User;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.proxy.WebResourceFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests User API
 *
 * @author Mincong Huang
 */
public class UserEndpointTest {

  private HttpServer server;

  private UserEndpoint userEndpoint;

  @Before
  public void setUp() {
    server = Main.startServer();
    WebTarget target = ClientBuilder.newClient().target(Main.HTTP_STATUS_CODE_URI);
    userEndpoint = WebResourceFactory.newResource(UserEndpoint.class, target);
  }

  @After
  public void tearDown() {
    server.shutdownNow();
  }

  @Test
  public void createUser() {
    User foo = userEndpoint.createUser("Foo", 18);
    assertThat(foo.getName()).isEqualTo("Foo");
    assertThat(foo.getAge()).isEqualTo(18);
  }
}
