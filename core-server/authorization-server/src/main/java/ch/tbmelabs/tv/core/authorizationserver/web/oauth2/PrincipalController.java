package ch.tbmelabs.tv.core.authorizationserver.web.oauth2;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.UserProfile;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.factory.ProfileFactory;
import ch.tbmelabs.tv.core.authorizationserver.service.oauth2.PrincipalService;

@RestController
public class PrincipalController {
  private static final Logger LOGGER = LogManager.getLogger(PrincipalController.class);

  @Autowired
  private PrincipalService principalService;

  @Autowired
  private ProfileFactory profileFactory;

  @RequestMapping({ "/me", "/user" })
  public Map<String, String> getPrincipal() {
    LOGGER.debug("Requesting current user information.");

    User user;
    if ((user = principalService.getCurrentUser()) == null) {
      return new HashMap<>();
    }

    Map<String, String> userInformation = new HashMap<>();
    userInformation.put("login", user.getUsername());
    userInformation.put("email", user.getEmail());

    return userInformation;
  }

  @RequestMapping({ "/profile" })
  public UserProfile getProfile(Principal principal) {
    LOGGER.debug("Requesting current user profile.");

    User user;
    if ((user = principalService.getCurrentUser()) == null) {
      throw new IllegalArgumentException("Please sign in in order to receive your account details!");
    }

    return profileFactory.getUserProfile(user);
  }
}