package io.symonk.sylenium.implementation;

import io.symonk.sylenium.SyConfig;
import io.symonk.sylenium.commands.driver.Navigator;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.symonk.sylenium.Sylenium.$go;
import static io.symonk.sylenium.webdriver.SyRunner.getUnderlyingDriver;
import static org.assertj.core.api.Assertions.assertThat;

public class NavigatorTest {

  private final Navigator navigator = new Navigator();

  @BeforeTest
  public void before() {
    SyConfig.$headless = false;
    SyConfig.$maximize = false;
    $go("https://www.bing.com");
  }

  @Test
  public void navigatorActions() {
    final String url = "https://www.google.co.uk/";
    navigator.go(url);
    navigator.back();
    navigator.forward();
    navigator.refreshPage();
    assertThat(getUnderlyingDriver().getCurrentUrl()).isEqualTo(url);
  }
}
