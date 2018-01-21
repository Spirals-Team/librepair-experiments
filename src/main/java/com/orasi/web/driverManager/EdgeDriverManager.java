package com.orasi.web.driverManager;

import java.io.File;
import java.net.URL;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.orasi.DriverManager;
import com.orasi.DriverType;
import com.orasi.web.WebDriverConstants;

public class EdgeDriverManager extends DriverManager {

    private EdgeDriverService service;
    private EdgeOptions options = null;

    public EdgeDriverManager() {
        options = new EdgeOptions();
        options.setPageLoadStrategy(WebDriverConstants.DEFAULT_EDGE_PAGE_LOAD_STRATEGY.toString());
    }

    public EdgeDriverManager(EdgeOptions options) {
        this.options = options;
    }

    @Override
    public void startService() {
        if (null == service) {
            try {
                service = new EdgeDriverService.Builder()
                        .usingDriverExecutable(new File(getDriverLocation(WebDriverConstants.DRIVER_EXE_NAME_EDGE)))
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
        driver = new EdgeDriver(service, options);
    }

    @Override
    public void createDriver(URL url) {
        driver = new RemoteWebDriver(url, options);
    }

    @Override
    public DriverType getDriverType() {
        return DriverType.EDGE;
    }
}