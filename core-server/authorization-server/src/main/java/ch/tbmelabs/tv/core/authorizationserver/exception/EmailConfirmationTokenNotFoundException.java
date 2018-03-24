package ch.tbmelabs.tv.core.authorizationserver.exception;

import ch.tbmelabs.tv.core.authorizationserver.domain.EmailConfirmationToken;

public class EmailConfirmationTokenNotFoundException extends Exception {
  private static final long serialVersionUID = 1L;

  public EmailConfirmationTokenNotFoundException(String token) {
    super("Unable to find " + EmailConfirmationToken.class + ": " + token);
  }
}