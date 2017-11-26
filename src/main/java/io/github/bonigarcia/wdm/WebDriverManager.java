/*
 * (C) Copyright 2015 Boni Garcia (http://bonigarcia.github.io/)
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 */
package io.github.bonigarcia.wdm;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Generic manager.
 *
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.3.1
 */
public class WebDriverManager extends BrowserManager {

    public static synchronized BrowserManager getInstance(
            Class<?> webDriverClass) {
        Class<? extends BrowserManager> browserManagerClass;

        switch (webDriverClass.getName()) {
        case "org.openqa.selenium.chrome.ChromeDriver":
            browserManagerClass = ChromeDriverManager.class;
            break;
        case "org.openqa.selenium.opera.OperaDriver":
            browserManagerClass = OperaDriverManager.class;
            break;
        case "org.openqa.selenium.ie.InternetExplorerDriver":
            browserManagerClass = InternetExplorerDriverManager.class;
            break;
        case "org.openqa.selenium.edge.EdgeDriver":
            browserManagerClass = EdgeDriverManager.class;
            break;
        case "org.openqa.selenium.phantomjs.PhantomJSDriver":
            browserManagerClass = PhantomJsDriverManager.class;
            break;
        case "org.openqa.selenium.firefox.FirefoxDriver":
            browserManagerClass = FirefoxDriverManager.class;
            break;
        default:
            browserManagerClass = VoidDriverManager.class;
            break;
        }

        try {
            instance = browserManagerClass.newInstance();
        } catch (Exception e) {
            throw new WebDriverManagerException(e);
        }
        return instance;
    }

    @Override
    protected List<URL> getDrivers() throws IOException {
        return instance.getDrivers();
    }

}
