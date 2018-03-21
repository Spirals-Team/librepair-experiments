package ch.tbmelabs.tv.core.authorizationserver.test.web.signup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;

public class PasswordValidationEndpointIntTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static final String PASSWORD_VALIDATION_ENDPOINT = "/signup/does-password-match-format";
  private static final String PASSWORD_PARAMETER_NAME = "password";

  private static final String PASSWORD_VALIDATION_ERROR_MESSAGE = "Password does not match format!";

  private static final String TOO_SHORT_PASSWORD = "Asdf$12";
  private static final String PASSWORD_MISSING_NUMBERS = "ASDFghj$";
  private static final String PASSWORD_MISSING_LOWERCASE_LETTERS = "ASDF123$";
  private static final String PASSWORD_MISSING_UPPERCASE_LETTERS = "asdf123$";
  private static final String PASSWORD_MISSING_SPECIAL_CHARS = "ASDfgh123";
  private static final String VALID_PASSWORD = "ASdf99$$";

  @Autowired
  private MockMvc mockMvc;

  @Test(expected = NestedServletException.class)
  public void tooShortPasswordShouldFailValidation() throws Exception {
    try {
      mockMvc
          .perform(post(PASSWORD_VALIDATION_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
              .content(new JSONObject().put(PASSWORD_PARAMETER_NAME, TOO_SHORT_PASSWORD).toString()))
          .andDo(print()).andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    } catch (NestedServletException e) {
      assertThat(e.getCause()).isNotNull().isOfAnyClassIn(IllegalArgumentException.class)
          .hasMessage(PASSWORD_VALIDATION_ERROR_MESSAGE);

      throw e;
    }
  }

  @Test(expected = NestedServletException.class)
  public void passwordWithoutNumbersFailValidation() throws Exception {
    try {
      mockMvc
          .perform(post(PASSWORD_VALIDATION_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
              .content(new JSONObject().put(PASSWORD_PARAMETER_NAME, PASSWORD_MISSING_NUMBERS).toString()))
          .andDo(print()).andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    } catch (NestedServletException e) {
      assertThat(e.getCause()).isNotNull().isOfAnyClassIn(IllegalArgumentException.class)
          .hasMessage(PASSWORD_VALIDATION_ERROR_MESSAGE);

      throw e;
    }
  }

  @Test(expected = NestedServletException.class)
  public void passwordWithoutLowercaseLettersShouldFailValidation() throws Exception {
    try {
      mockMvc
          .perform(post(PASSWORD_VALIDATION_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
              .content(new JSONObject().put(PASSWORD_PARAMETER_NAME, PASSWORD_MISSING_LOWERCASE_LETTERS).toString()))
          .andDo(print()).andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    } catch (NestedServletException e) {
      assertThat(e.getCause()).isNotNull().isOfAnyClassIn(IllegalArgumentException.class)
          .hasMessage(PASSWORD_VALIDATION_ERROR_MESSAGE);

      throw e;
    }
  }

  @Test(expected = NestedServletException.class)
  public void passwordWithoutUppercaseLettersShouldFailValidation() throws Exception {
    try {
      mockMvc
          .perform(post(PASSWORD_VALIDATION_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
              .content(new JSONObject().put(PASSWORD_PARAMETER_NAME, PASSWORD_MISSING_UPPERCASE_LETTERS).toString()))
          .andDo(print()).andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    } catch (NestedServletException e) {
      assertThat(e.getCause()).isNotNull().isOfAnyClassIn(IllegalArgumentException.class)
          .hasMessage(PASSWORD_VALIDATION_ERROR_MESSAGE);

      throw e;
    }
  }

  @Test(expected = NestedServletException.class)
  public void passwordWithoutSpecialCharShouldFailValidation() throws Exception {
    try {
      mockMvc
          .perform(post(PASSWORD_VALIDATION_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
              .content(new JSONObject().put(PASSWORD_PARAMETER_NAME, PASSWORD_MISSING_SPECIAL_CHARS).toString()))
          .andDo(print()).andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    } catch (NestedServletException e) {
      assertThat(e.getCause()).isNotNull().isOfAnyClassIn(IllegalArgumentException.class)
          .hasMessage(PASSWORD_VALIDATION_ERROR_MESSAGE);

      throw e;
    }
  }

  @Test
  public void validPasswordShouldPassValidation() throws Exception {
    mockMvc
        .perform(post(PASSWORD_VALIDATION_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
            .content(new JSONObject().put(PASSWORD_PARAMETER_NAME, VALID_PASSWORD).toString()))
        .andDo(print()).andExpect(status().is(HttpStatus.OK.value()));
  }
}