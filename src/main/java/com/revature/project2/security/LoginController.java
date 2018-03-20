package com.revature.project2.security;

import com.revature.project2.users.User;
import com.revature.project2.users.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class LoginController {
  private UserRepository userRepository;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public LoginController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @PostMapping("/login")
  public ResponseEntity login(@RequestBody Map<String, String> body, HttpServletResponse resp) {
    User user = userRepository.findByUsername(body.get("username"));

    if (user == null) {
      return new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    if (bCryptPasswordEncoder.matches(body.get("password"), user.getPassword())) {
      log.info("passwords match");
      return new ResponseEntity<Map>(SecurityConstraints.generateTokenAndReturn(resp, user), HttpStatus.OK);
    } else {
      log.info("passwords don't match");
      return new ResponseEntity(null, HttpStatus.UNAUTHORIZED);
    }
  }

  @GetMapping("/check/{username}")
  public boolean isExist(@PathVariable String username){
      return userRepository.existsByUsername(username);
  }
  @GetMapping("/checkemail/{email}")
  public boolean isExistEmail(@PathVariable String email){
    return userRepository.existsByEmail(email);
  }
  @PostMapping("/sign-up")
  public void signUp(@RequestBody User user, HttpServletResponse resp) {
    System.out.println(user);
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    userRepository.save(user);
    SecurityConstraints.generateTokenAndAttachHeader(resp, user);
  }
}
