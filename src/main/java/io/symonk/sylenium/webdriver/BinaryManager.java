package io.symonk.sylenium.webdriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BinaryManager {

    public void prepareDriverBinaries() {
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
    }
}
