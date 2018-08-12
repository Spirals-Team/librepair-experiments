package io.symonk.sylenium.webdriver.factories;

import io.symonk.sylenium.webdriver.BinaryManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;
import java.util.List;

import static io.symonk.sylenium.SyConfig.$distributed;
import static io.symonk.sylenium.SyConfig.$maximize;
import static io.symonk.sylenium.SyConfig.$powered;

@Slf4j
public class SyleniumDriverFactory {

    protected List<BaseDriverFactory> listOfFactories = Arrays.asList(
            new ChromeDriverFactory()
                                                                   );
    protected BinaryManager binaryManager = new BinaryManager();

    public WebDriver createDriver(final Proxy proxy) {
        if($distributed == false && $powered == true) binaryManager.prepareDriverBinaries();

        final WebDriver driver = listOfFactories.stream()
                .filter(BaseDriverFactory::matchesConfiguration)
                .findFirst()
                .map(factory -> factory.create(proxy))
                .orElseGet(() -> new StandardDriverFactory().create(proxy));

        if($maximize) driver.manage().window().maximize();
        return driver;
    }


}
