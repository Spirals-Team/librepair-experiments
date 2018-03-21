package ch.tbmelabs.tv.core.authorizationserver.test.security.logging;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import ch.tbmelabs.tv.core.authorizationserver.domain.repository.IPBlacklistCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.bruteforce.BruteforceFilterService;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;

@Transactional
public class LoginEndpointBruteforceFilterServiceIntTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static final String LOGIN_PROCESSING_URL = "/signin";
  private static final String USERNAME_PARAMETER_NAME = "username";
  private static final String PASSWORD_PARAMETER_NAME = "password";

  @Value("${authorization-server.security.max-login-attempts}")
  private Integer maxLoginAttempts;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private IPBlacklistCRUDRepository ipBlacklistRepository;

  @Before
  public void beforeTestSetUp() {
    ipBlacklistRepository.deleteAll();
    BruteforceFilterService.resetFilter();
  }

  @Test
  public void authenticationFailureListenerShouldRegisterFailingAuthentication() throws Exception {
    mockMvc.perform(
        post(LOGIN_PROCESSING_URL).param(USERNAME_PARAMETER_NAME, "invalid").param(PASSWORD_PARAMETER_NAME, "invalid"))
        .andDo(print()).andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));

    assertThat(BruteforceFilterService.getState()).hasSize(1);
  }

  @Test
  public void authenticationFailureListenerShouldBanIpAfterMaxLoginAttempts() {
    IntStream.range(0, maxLoginAttempts).forEach(iterator -> {
      try {
        mockMvc.perform(post(LOGIN_PROCESSING_URL).param(USERNAME_PARAMETER_NAME, "invalid")
            .param(PASSWORD_PARAMETER_NAME, "invalid")).andDo(print())
            .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
      } catch (Exception e) {
        throw new IllegalArgumentException(e);
      }
    });

    assertThat(ipBlacklistRepository.findAll()).hasSize(1);
    assertThat(ipBlacklistRepository.findAll().iterator().next()).hasFieldOrPropertyWithValue("startIp", "127.0.0.1")
        .hasFieldOrPropertyWithValue("endIp", "127.0.0.1");
  }
}