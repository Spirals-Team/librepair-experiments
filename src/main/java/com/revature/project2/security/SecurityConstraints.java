package com.revature.project2.security;

import com.revature.project2.users.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SecurityConstraints {
  public static final String SECRET = "SecretKeyToGenJWTs";
  public static final long EXPIRATION_TIME = 864_000_000;
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_STRING = "Authorization";
  public static final String SIGN_UP_URL = "/sign-up";

  public static String generateToken(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
        .compact();
  }

  public static void generateTokenAndAttachHeader(HttpServletResponse resp, User user) {
    resp.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + generateToken(user.getUsername()));
  }

  public static Map<String, Object> generateTokenAndReturn(User user) {
    Map<String, Object> response = new HashMap<>();
    response.put("token", generateToken(user.getUsername()));
    user.setPassword("");
    response.put("user", user);
    return response;
  }
}
