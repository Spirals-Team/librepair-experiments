package io.symonk.sylenium.commands.driver;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

import static io.symonk.sylenium.webdriver.SyRunner.confirmDriverAndRetrieve;
import static io.symonk.sylenium.webdriver.SyRunner.getUnderlyingDriver;

/** Navigational class to wrap around basic navigational options of selenium. */
@Slf4j
public class Navigator {

  /** Refresh the current page */
  public void refreshPage() {
    getUnderlyingDriver().navigate().refresh();
  }

  /** Navigate forward */
  public void forward() {
    getUnderlyingDriver().navigate().forward();
  }

  /** Navigate backwards */
  public void back() {
    getUnderlyingDriver().navigate().back();
  }

  /** Load a particular page Spawns a driver instance if none are active */
  public void go(final String absoluteUrl) {
          final WebDriver driver = confirmDriverAndRetrieve();
          driver.navigate().to(absoluteUrl);
  }
}
