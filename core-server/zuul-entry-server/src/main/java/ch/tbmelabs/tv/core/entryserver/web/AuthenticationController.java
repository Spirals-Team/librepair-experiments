package ch.tbmelabs.tv.core.entryserver.web;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticated")
public class AuthenticationController {
  @GetMapping
  public boolean isAuthenticated(Principal principal) {
    return principal != null;
  }
}