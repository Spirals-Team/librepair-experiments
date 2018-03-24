package ch.tbmelabs.core.adminserver.test.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import ch.tbmelabs.core.adminserver.test.AbstractAdminApplicationContextAware;

public class RootEndpointOAuth2SSOForwardIntTest extends AbstractAdminApplicationContextAware {
  private static final String FORWARD_HEADER_NAME = "location";
  private static final String EUREKA_AUTHENTICATOIN_ENTRY_POINT_URI = "http://localhost/login";
  private static final String OAUTH2_AUTHENTICATION_ENTRY_POINT_URI = "http://localhost/oauth/authorize";

  @Autowired
  private MockMvc mockMvc;

  @Value("${security.oauth2.client.clientId}")
  private String clientId;

  @Test
  public void requestToLoginEndpointShouldForwardToOAuth2AuthorizationEndpoint() throws Exception {
    String loginForward = mockMvc.perform(get("/")).andDo(print()).andExpect(status().is(HttpStatus.FOUND.value()))
        .andReturn().getResponse().getHeader(FORWARD_HEADER_NAME);

    assertThat(loginForward).isEqualTo(EUREKA_AUTHENTICATOIN_ENTRY_POINT_URI);

    String ssoForward = mockMvc.perform(get("/login")).andDo(print()).andExpect(status().is(HttpStatus.FOUND.value()))
        .andReturn().getResponse().getHeader(FORWARD_HEADER_NAME);

    assertThat(ssoForward).startsWith(OAUTH2_AUTHENTICATION_ENTRY_POINT_URI).contains("client_id=" + clientId)
        .contains("redirect_uri=" + EUREKA_AUTHENTICATOIN_ENTRY_POINT_URI).contains("response_type=code")
        .contains("state=");
  }
}