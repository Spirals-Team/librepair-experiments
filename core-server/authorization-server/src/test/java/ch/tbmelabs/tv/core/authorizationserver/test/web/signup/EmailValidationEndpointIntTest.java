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

public class EmailValidationEndpointIntTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static final String EMAIL_VALIDATION_ENDPOINT = "/signup/is-email";
  private static final String EMAIL_PARAMETER_NAME = "email";

  private static final String EMAIL_VALIDATION_ERROR_MESSAGE = "Not a valid e-mail address!";

  private static final String INVALID_EMAIL = "ASDFGHJKL";
  private static final String VALID_EMAIl = "tbme@localhost.tv";

  @Autowired
  private MockMvc mockMvc;

  @Test(expected = NestedServletException.class)
  public void invalidEmailShouldFailValidation() throws Exception {
    try {
      mockMvc
          .perform(post(EMAIL_VALIDATION_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
              .content(new JSONObject().put(EMAIL_PARAMETER_NAME, INVALID_EMAIL).toString()))
          .andDo(print()).andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    } catch (NestedServletException e) {
      assertThat(e.getCause()).isNotNull().isOfAnyClassIn(IllegalArgumentException.class)
          .hasMessage(EMAIL_VALIDATION_ERROR_MESSAGE);

      throw e;
    }
  }

  @Test
  public void validEmailShouldPassValidation() throws Exception {
    mockMvc
        .perform(post(EMAIL_VALIDATION_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
            .content(new JSONObject().put(EMAIL_PARAMETER_NAME, VALID_EMAIl).toString()))
        .andDo(print()).andExpect(status().is(HttpStatus.OK.value()));
  }
}