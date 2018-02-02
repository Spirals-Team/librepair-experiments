package com.orasi.web.driverManager;

import java.io.File;
import java.net.URL;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.orasi.DriverManager;
import com.orasi.DriverType;
import com.orasi.web.WebDriverConstants;

public class ChromeDriverManager extends DriverManager {

    private ChromeDriverService service;
    private ChromeOptions options = null;

    public ChromeDriverManager() {
        options = new ChromeOptions();
        options.setPageLoadStrategy(WebDriverConstants.DEFAULT_CHROME_PAGE_LOAD_STRATEGY);
    }

    public ChromeDriverManager(ChromeOptions options) {
        this.options = options;
    }

    @Override
    public void startService() {
        if (null == service) {
            try {
                service = new ChromeDriverService.Builder()
                        .usingDriverExecutable(new File(getDriverLocation(WebDriverConstants.DRIVER_EXE_NAME_CHROME)))
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
        driver = new ChromeDriver(service, options);
    }

    @Override
    public void createDriver(URL url) {
        driver = new RemoteWebDriver(url, options);
    }

    @Override
    public DriverType getDriverType() {
        return DriverType.CHROME;
    }
}