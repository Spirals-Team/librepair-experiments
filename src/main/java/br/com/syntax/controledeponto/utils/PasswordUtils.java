package br.com.syntax.controledeponto.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {

  private static final Logger log = LoggerFactory.getLogger(PasswordUtils.class);

  public static String geraBCrypt(String senha) {
    if (senha == null) {
      return senha;
    }

    log.debug("Gerando hash da senha com BCrypt");
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder.encode(senha);
  }
}
