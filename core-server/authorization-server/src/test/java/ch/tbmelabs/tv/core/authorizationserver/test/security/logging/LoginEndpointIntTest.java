package ch.tbmelabs.tv.core.authorizationserver.test.security.logging;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.AuthenticationLogCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.bruteforce.BruteforceFilterService;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;

@Transactional
public class LoginEndpointIntTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static final String LOGIN_PROCESSING_URL = "/signin";
  private static final String USERNAME_PARAMETER_NAME = "username";
  private static final String PASSWORD_PARAMETER_NAME = "password";

  private static final String USER_DISABLED_ERROR_MESSAGE = "Authentication Failed: User is disabled";
  private static final String USER_BLOCKED_ERROR_MESSAGE = "Authentication Failed: User account is locked";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AuthenticationLogCRUDRepository authenticationLogRepository;

  @Autowired
  private UserCRUDRepository userRepository;

  private static User testUser = new User();
  private static String password;

  @BeforeClass
  public static void beforeClassSetUp() {
    testUser.setUsername(RandomStringUtils.random(11));
    testUser.setEmail("loginendpointinttest.user@tbme.tv");
    testUser.setPassword(RandomStringUtils.random(11));
    testUser.setConfirmation(testUser.getPassword());

    password = testUser.getPassword();
  }

  @Before
  public void beforeTestSetUp() {
    testUser.setIsEnabled(true);
    testUser.setIsBlocked(false);

    testUser = userRepository.save(testUser);

    authenticationLogRepository.deleteAll();
    BruteforceFilterService.resetFilter();
  }

  @Test
  public void loginProcessingWithInvalidUsernameShouldFail() throws Exception {
    mockMvc.perform(
        post(LOGIN_PROCESSING_URL).param(USERNAME_PARAMETER_NAME, "invalid").param(PASSWORD_PARAMETER_NAME, password))
        .andDo(print()).andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
  }

  @Test
  public void loginProcessingWithInvalidPasswordShoulFail() throws Exception {
    mockMvc
        .perform(post(LOGIN_PROCESSING_URL).param(USERNAME_PARAMETER_NAME, testUser.getUsername())
            .param(PASSWORD_PARAMETER_NAME, "invalid"))
        .andDo(print()).andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
  }

  @Test
  public void loginProcessingWithValidCredentialsShouldSucceed() throws Exception {
    String redirectUrl = mockMvc
        .perform(post(LOGIN_PROCESSING_URL).param(USERNAME_PARAMETER_NAME, testUser.getUsername())
            .param(PASSWORD_PARAMETER_NAME, password))
        .andDo(print()).andExpect(status().is(HttpStatus.FOUND.value())).andReturn().getResponse().getRedirectedUrl();

    assertThat(redirectUrl).isNotNull().isEqualTo("/");
  }

  @Test
  public void loginProcessingShouldFailIfUserIsDisabled() throws Exception {
    testUser.setIsEnabled(false);
    userRepository.save(testUser);

    String errorMessage = mockMvc
        .perform(post(LOGIN_PROCESSING_URL).param(USERNAME_PARAMETER_NAME, testUser.getUsername())
            .param(PASSWORD_PARAMETER_NAME, password))
        .andDo(print()).andExpect(status().is(HttpStatus.UNAUTHORIZED.value())).andReturn().getResponse()
        .getErrorMessage();

    assertThat(errorMessage).isEqualTo(USER_DISABLED_ERROR_MESSAGE);
  }

  @Test
  public void loginProcessingShouldFailIfUserIsBlocked() throws Exception {
    testUser.setIsBlocked(true);
    userRepository.save(testUser);

    String errorMessage = mockMvc
        .perform(post(LOGIN_PROCESSING_URL).param(USERNAME_PARAMETER_NAME, testUser.getUsername())
            .param(PASSWORD_PARAMETER_NAME, password))
        .andDo(print()).andExpect(status().is(HttpStatus.UNAUTHORIZED.value())).andReturn().getResponse()
        .getErrorMessage();

    assertThat(errorMessage).isEqualTo(USER_BLOCKED_ERROR_MESSAGE);
  }
}