package ch.tbmelabs.tv.core.entryserver.test.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import ch.tbmelabs.tv.core.entryserver.test.AbstractZuulApplicationContextAware;

public class LoginEndpointOAuth2SSOForwardIntTest extends AbstractZuulApplicationContextAware {
  private static final String FORWARD_HEADER_NAME = "location";
  private static final String ZUUL_AUTHENTICATION_ENTRY_POINT_URI = "http://localhost/login";
  private static final String OAUTH2_AUTHENTICATION_ENTRY_POINT_URI = "http://localhost/oauth/authorize";

  @Autowired
  private MockMvc mockMvc;

  @Value("${security.oauth2.client.clientId}")
  private String clientId;

  @Test
  public void requestToLoginEndpointShouldForwardToOAuth2AuthorizationEndpoint() throws Exception {
    String ssoForward = mockMvc.perform(get("/login")).andDo(print()).andExpect(status().is(HttpStatus.FOUND.value()))
        .andReturn().getResponse().getHeader(FORWARD_HEADER_NAME);

    assertThat(ssoForward).startsWith(OAUTH2_AUTHENTICATION_ENTRY_POINT_URI).contains("client_id=" + clientId)
        .contains("redirect_uri=" + ZUUL_AUTHENTICATION_ENTRY_POINT_URI).contains("response_type=code")
        .contains("state=");
  }
}