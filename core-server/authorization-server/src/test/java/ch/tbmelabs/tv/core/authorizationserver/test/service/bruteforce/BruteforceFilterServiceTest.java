package ch.tbmelabs.tv.core.authorizationserver.test.service.bruteforce;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.stereotype.Service;
import org.springframework.test.util.ReflectionTestUtils;

import ch.tbmelabs.tv.core.authorizationserver.domain.BlacklistedIp;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.IPBlacklistCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.bruteforce.BruteforceFilterService;

public class BruteforceFilterServiceTest {
  private static final Integer MAX_LOGIN_ATTEMPTS = 3;
  private static final String IP_ADDRESS = "127.0.0.1";

  @Mock
  private IPBlacklistCRUDRepository ipBlacklistRepositoryFixture;

  @Spy
  @InjectMocks
  private BruteforceFilterService fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    ReflectionTestUtils.setField(fixture, "maxLoginAttempts", MAX_LOGIN_ATTEMPTS);
  }

  @Test
  public void bruteforceFilterServiceShouldBeAnnotated() {
    assertThat(BruteforceFilterService.class).hasAnnotation(Service.class);
  }

  @Test
  public void bruteforceFilterServiceShouldHavePublicConstructor() {
    assertThat(new BruteforceFilterService()).isNotNull();
  }

  @Test
  @SuppressWarnings("unchecked")
  public void resetFilterShouldResetFailedLoginAttempts() {
    Map<String, Integer> failedLoginAttempts = new HashMap<>();
    failedLoginAttempts.put(RandomStringUtils.random(11), new Random().nextInt());

    ReflectionTestUtils.setField(BruteforceFilterService.class, "failedLoginAttempts", failedLoginAttempts);

    BruteforceFilterService.resetFilter();

    assertThat((Map<String, Integer>) ReflectionTestUtils.getField(fixture, "failedLoginAttempts")).hasSize(0);
  }

  @Test
  public void getStateShouldReturnFailedLoginAttempts() {
    Map<String, Integer> failedLoginAttempts = new HashMap<>();
    failedLoginAttempts.put(RandomStringUtils.random(11), new Random().nextInt());

    ReflectionTestUtils.setField(BruteforceFilterService.class, "failedLoginAttempts", failedLoginAttempts);

    assertThat(BruteforceFilterService.getState()).isEqualTo(failedLoginAttempts);
  }

  @Test
  public void authenticationFromIpFailedShouldSuspectBruteforceAttemptIfMaxLoginAttemptsExceed() {
    IntStream.range(0, MAX_LOGIN_ATTEMPTS).forEach(iterator -> fixture.authenticationFromIpFailed(IP_ADDRESS));

    verify(ipBlacklistRepositoryFixture, atLeastOnce()).save(Mockito.any(BlacklistedIp.class));
  }

  @Test
  @SuppressWarnings("unchecked")
  public void authenticationFromIpSucceedShouldClearFailedLoginAttempts() {
    Map<String, Integer> failedLoginAttempts = new HashMap<>();
    failedLoginAttempts.put(RandomStringUtils.random(11), new Random().nextInt());

    String ip = failedLoginAttempts.keySet().toArray(new String[failedLoginAttempts.size()])[0];

    fixture.authenticationFromIpSucceed(ip);

    assertThat((Map<String, Integer>) ReflectionTestUtils.getField(fixture, "failedLoginAttempts"))
        .doesNotContainKey(ip);
  }
}