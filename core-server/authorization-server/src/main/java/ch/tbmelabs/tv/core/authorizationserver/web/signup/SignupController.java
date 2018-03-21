package ch.tbmelabs.tv.core.authorizationserver.web.signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.service.signup.UserSignupService;

@RestController
@RequestMapping("/signup")
public class SignupController {
  @Autowired
  private UserSignupService signupService;

  @PostMapping({ "/do-signup" })
  public User signup(@RequestBody(required = true) User newUser) {
    return signupService.signUpNewUser(newUser);
  }

  @PostMapping({ "/is-username-unique" })
  public void isUsernameUnique(@RequestBody(required = true) User newUser) {
    if (!signupService.isUsernameUnique(newUser)) {
      throw new IllegalArgumentException("Username already exists!");
    }
  }

  @PostMapping({ "/does-username-match-format" })
  public void doesUsernameMatchFormat(@RequestBody(required = true) User newUser) {
    if (!signupService.doesUsernameMatchFormat(newUser)) {
      throw new IllegalArgumentException("Username does not match format!");
    }
  }

  @PostMapping({ "/is-email-unique" })
  public void isEmailAddressUnique(@RequestBody(required = true) User newUser) {
    if (!signupService.isEmailAddressUnique(newUser)) {
      throw new IllegalArgumentException("E-mail address already in use!");
    }
  }

  @PostMapping({ "/is-email" })
  public void isEmailAddress(@RequestBody(required = true) User newUser) {
    if (!signupService.isEmailAddress(newUser)) {
      throw new IllegalArgumentException("Not a valid e-mail address!");
    }
  }

  @PostMapping({ "/does-password-match-format" })
  public void doesPasswordMatchFormat(@RequestBody(required = true) User newUser) {
    if (!signupService.doesPasswordMatchFormat(newUser)) {
      throw new IllegalArgumentException("Password does not match format!");
    }
  }

  @PostMapping({ "/do-passwords-match" })
  public void doPasswordsMatch(@RequestBody(required = true) User newUser) {
    if (!signupService.doPasswordsMatch(newUser)) {
      throw new IllegalArgumentException("Passwords do not match!");
    }
  }
}