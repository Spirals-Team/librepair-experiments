package com.orasi.web.driverManager;

import java.io.File;
import java.net.URL;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.orasi.DriverManager;
import com.orasi.DriverType;
import com.orasi.web.WebDriverConstants;

public class FirefoxDriverManager extends DriverManager {

    private GeckoDriverService service;
    private FirefoxOptions options = null;

    public FirefoxDriverManager() {
        FirefoxProfile profile = new FirefoxProfile();
        profile.setAcceptUntrustedCertificates(true);
        profile.setAssumeUntrustedCertificateIssuer(true);

        options = new FirefoxOptions().setProfile(profile);
        options.setAcceptInsecureCerts(true);
        options.setPageLoadStrategy(WebDriverConstants.DEFAULT_FIREFOX_PAGE_LOAD_STRATEGY);
    }

    public FirefoxDriverManager(FirefoxOptions options) {
        this.options = options;
    }

    @Override
    public void startService() {
        if (null == service) {
            try {
                service = new GeckoDriverService.Builder()
                        .usingDriverExecutable(new File(getDriverLocation(WebDriverConstants.DRIVER_EXE_NAME_FIREFOX)))
                        .usingAnyFreePort()
                        .build();
                service.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stopService() {
        if (null != service && service.isRunning()) {
            service.stop();
        }
    }

    @Override
    public void createDriver() {
        driver = new FirefoxDriver(service, options);
    }

    @Override
    public void createDriver(URL url) {
        driver = new RemoteWebDriver(url, options);
    }

    @Override
    public DriverType getDriverType() {
        return DriverType.FIREFOX;
    }
}