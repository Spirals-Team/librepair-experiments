package io.symonk.sylenium.webdriver;

import io.symonk.sylenium.webdriver.interfaces.Containerisable;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

@Slf4j
public class SyRunner {

    public static Containerisable container = new SyDriverContainer();

    public static WebDriver getUnderlyingDriver() {
        return container.getUnderlyingDriver();
    }

    public static WebDriver confirmDriverAndRetrieve() {
        return container.confirmAndRetrieveDriver();
    }


}
