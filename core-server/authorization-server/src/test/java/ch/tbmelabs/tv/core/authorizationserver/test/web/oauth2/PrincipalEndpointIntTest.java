package ch.tbmelabs.tv.core.authorizationserver.test.web.oauth2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;

@WithMockUser(username = "PrincipalEndpointTestUser")
public class PrincipalEndpointIntTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static final String ME_ENDPOINT = "/me";
  private static final String USER_ENDPOINT = "/user";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserCRUDRepository userRepository;

  private static User testUser = new User();

  @BeforeClass
  public static void beforeClassSetUp() {
    testUser.setUsername("PrincipalEndpointTestUser");
    testUser.setEmail("principalendpointtest.user@tbme.tv");
    testUser.setPassword(RandomStringUtils.random(11));
  }

  @Before
  public void beforeTestSetUp() {
    PrincipalEndpointIntTest.testUser = userRepository.save(testUser);
  }

  @Test
  public void meEndpointShouldReturnCorrectAuthentication() throws Exception {
    runJsonCredentialAssertChain(new JSONObject(mockMvc.perform(get(ME_ENDPOINT)).andDo(print())
        .andExpect(status().is(HttpStatus.OK.value())).andReturn().getResponse().getContentAsString()));
  }

  @Test
  public void userEndpointShouldReturnCorrectAuthentication() throws Exception {
    runJsonCredentialAssertChain(new JSONObject(mockMvc.perform(get(USER_ENDPOINT)).andDo(print())
        .andExpect(status().is(HttpStatus.OK.value())).andReturn().getResponse().getContentAsString()));
  }

  private void runJsonCredentialAssertChain(JSONObject jsonCredential) throws JSONException {
    assertThat(jsonCredential.getString("login")).isNotNull().isEqualTo("PrincipalEndpointTestUser");
  }
}