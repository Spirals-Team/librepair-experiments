package ch.tbmelabs.tv.core.authorizationserver.service.bruteforce;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ch.tbmelabs.tv.core.authorizationserver.domain.BlacklistedIp;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.IPBlacklistCRUDRepository;

@Service
public class BruteforceFilterService {
  private static final Logger LOGGER = LogManager.getLogger(BruteforceFilterService.class);

  private static Map<String, Integer> failedLoginAttempts = new HashMap<>();

  @Autowired
  private IPBlacklistCRUDRepository ipBlacklistRepository;

  @Value("${authorization-server.security.max-login-attempts}")
  private Integer maxLoginAttempts;

  public static void resetFilter() {
    LOGGER.warn("Filter resets!");

    BruteforceFilterService.failedLoginAttempts.clear();
  }

  public static Map<String, Integer> getState() {
    return BruteforceFilterService.failedLoginAttempts;
  }

  public void authenticationFromIpFailed(String ip) {
    LOGGER.debug("Authentication from ip " + ip + " failed");

    if (!BruteforceFilterService.failedLoginAttempts.containsKey(ip)) {
      BruteforceFilterService.failedLoginAttempts.put(ip, 0);
    }

    BruteforceFilterService.failedLoginAttempts.put(ip, BruteforceFilterService.failedLoginAttempts.get(ip) + 1);

    if (BruteforceFilterService.failedLoginAttempts.get(ip) >= maxLoginAttempts) {
      LOGGER.fatal("Suspecting bruteforce attempt from " + ip + "!");

      suspectBruteforceAttempt(ip);
    }
  }

  private void suspectBruteforceAttempt(String ip) {
    LOGGER.warn("Maximum login attempts from " + ip + " exceed! Suspecting bruteforcing attempt");

    ipBlacklistRepository.save(new BlacklistedIp(ip, ip));
  }

  public void authenticationFromIpSucceed(String ip) {
    LOGGER.debug("Authentication from " + ip + " succeed: Cache for this ip is resetted");

    BruteforceFilterService.failedLoginAttempts.remove(ip);
  }
}