package ch.tbmelabs.tv.core.authorizationserver.web.signup;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.tbmelabs.tv.core.authorizationserver.exception.EmailConfirmationTokenNotFoundException;
import ch.tbmelabs.tv.core.authorizationserver.service.signup.EmailConfirmationTokenService;

@RestController
@RequestMapping("/signup")
public class SignupConfirmationController {
  public static final String CONFIRMATION_ENDPOINT = "/signup/confirm-signup/";

  private static final String CONFIRMATION_ERROR_REDIRECT = "/signin?confirmation_failed";
  private static final String CONFIRMATION_SUCCESS_REDIRECT = "/signin?confirmation_succeed";

  @Value("${server.context-path:}")
  private String contextPath;

  @Autowired
  private EmailConfirmationTokenService emailConfirmationTokenService;

  @GetMapping({ "/confirm-signup/{token}" })
  public void confirmSignup(HttpServletResponse response, @PathVariable(name = "token", required = true) String token)
      throws IOException {
    try {
      emailConfirmationTokenService.confirmRegistration(token);
    } catch (EmailConfirmationTokenNotFoundException e) {
      response.sendRedirect(contextPath + CONFIRMATION_ERROR_REDIRECT);
      return;
    }

    response.sendRedirect(contextPath + CONFIRMATION_SUCCESS_REDIRECT);
  }
}