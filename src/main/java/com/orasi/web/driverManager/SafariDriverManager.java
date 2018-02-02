package com.orasi.web.driverManager;

import java.net.URL;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariDriverService;
import org.openqa.selenium.safari.SafariOptions;

import com.orasi.DriverManager;
import com.orasi.DriverType;

public class SafariDriverManager extends DriverManager {

    private SafariDriverService service;
    private SafariOptions options = null;

    public SafariDriverManager() {
        options = new SafariOptions();
    }

    public SafariDriverManager(SafariOptions options) {
        this.options = options;
    }

    @Override
    public void startService() {
        if (null == service) {
            try {
                service = new SafariDriverService.Builder()
                        .usingAnyFreePort()
                        .usingTechnologyPreview(true)
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
        driver = new SafariDriver(service, options);
    }

    @Override
    public void createDriver(URL url) {
        driver = new RemoteWebDriver(url, options);
    }

    @Override
    public DriverType getDriverType() {
        return DriverType.SAFARI;
    }
}