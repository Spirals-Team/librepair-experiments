package ch.tbmelabs.tv.core.authorizationserver.security.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.tbmelabs.tv.core.authorizationserver.domain.AuthenticationLog;
import ch.tbmelabs.tv.core.authorizationserver.domain.AuthenticationLog.AUTHENTICATION_STATE;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.AuthenticationLogCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;

@Component
public class AuthenticationAttemptLogger {
  private static final Logger LOGGER = LogManager.getLogger(AuthenticationAttemptLogger.class);

  @Autowired
  private AuthenticationLogCRUDRepository authenticationLogRepository;

  @Autowired
  private UserCRUDRepository userRepository;

  public void logAuthenticationAttempt(AUTHENTICATION_STATE state, String ip, String message, String username) {
    LOGGER.debug("Authentication attempt from " + ip + " with state " + state.name());

    User user;
    if ((user = userRepository.findByUsername(username)) == null) {
      LOGGER.warn("Invalid username \"" + username + "\" detected: Probably a bruteforcing attempt?");

      return;
    }

    authenticationLogRepository.save(new AuthenticationLog(state, ip, message, user));
  }
}